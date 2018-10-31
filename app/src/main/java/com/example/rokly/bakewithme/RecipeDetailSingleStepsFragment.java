package com.example.rokly.bakewithme;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import com.example.rokly.bakewithme.data.Ingredients;
import com.example.rokly.bakewithme.data.Steps;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

public class RecipeDetailSingleStepsFragment extends Fragment implements ExoPlayer.EventListener, View.OnClickListener{

    private static final String TAG = RecipeDetailSingleStepsFragment.class.getSimpleName();
    private static Steps currentStep;
    private TextView textView;
    private SimpleExoPlayer exoPlayer;
    private SimpleExoPlayerView playerView;
    private static MediaSessionCompat mediaSession;
    private PlaybackStateCompat.Builder stateBuilder;
    private Context context;
    private final static String CURRENT_POSITION = "currentPosition";
    private final static String CURRENT_URL = "currentUrl";
    private long currentPostion;
    private String videoUrl;
    private static int size;
    private static boolean isPhone;
    OnButtonClickListener mCallback;


    // Mandatory empty constructor
    public RecipeDetailSingleStepsFragment() {
    }

    public interface OnButtonClickListener {
        void onButtonSelected(int buttonId);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (RecipeDetailSingleStepsFragment.OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }
    // Inflates the detail view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_recipe_single_detail, container, false);

        playerView = rootView.findViewById(R.id.playerView);
        context = getContext();

        videoUrl = currentStep.getVideoUrl();

        if(savedInstanceState == null){
            currentPostion = 0;
        }else if(videoUrl.equals(savedInstanceState.getString(CURRENT_URL))){
            currentPostion = savedInstanceState.getLong(CURRENT_POSITION);
        }

        playerView.setDefaultArtwork(BitmapFactory.decodeResource
                (getResources(), R.drawable.loading_please_wait));
        initializeMediaSession();
        initializePlayer(Uri.parse(currentStep.getVideoUrl()));

        textView = rootView.findViewById(R.id.tv_instruction_step);
        textView.setText(currentStep.getDescription());

        ImageButton backButton = rootView.findViewById(R.id.back_button);
        backButton.setOnClickListener(this);

        ImageButton forwardButton = rootView.findViewById(R.id.forward_button);
        forwardButton.setOnClickListener(this);

        if(!isPhone){
            backButton.setVisibility(View.GONE);
            forwardButton.setVisibility(View.GONE);
        }else{
            if(currentStep.getId() == size -1){
                forwardButton.setVisibility(View.GONE);
            }
            if(currentStep.getId() == 0){
                backButton.setVisibility(View.GONE);
            }
        }

        if(videoUrl == null || videoUrl.equals("")){
            playerView.setDefaultArtwork(BitmapFactory.decodeResource
                    (getResources(), R.drawable.no_video));
        }else{
            playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
            if(isLandscape() && isPhone){
                backButton.setVisibility(View.GONE);
                forwardButton.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                playerView.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT));
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
                if (Build.VERSION.SDK_INT > 16) {
                    ((AppCompatActivity)getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }else{
                    View decorView = ((AppCompatActivity)getActivity()).getWindow().getDecorView();
                    // Hide the status bar.
                    int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
                    decorView.setSystemUiVisibility(uiOptions);
                }
            }
        }

        // Return the root view
        return rootView;
    }

    public void setCurrentStep(Steps currentStep){
        this.currentStep = currentStep;
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void setSize(int size){
        this.size = size;
    }

    public void setPhone(boolean isPhone){
        this.isPhone = isPhone;
    }

    private boolean isLandscape(){
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        currentState.putLong(CURRENT_POSITION, exoPlayer.getCurrentPosition());
        currentState.putString(CURRENT_URL, videoUrl);
    }

    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mediaSession = new MediaSessionCompat(context, TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        stateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mediaSession.setPlaybackState(stateBuilder.build());

        // MySessionCallback has methods that handle callbacks from a media controller.
        mediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mediaSession.setActive(true);

    }

    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (exoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            exoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(context, "BakeWithMe");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    context, userAgent), new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.seekTo(currentPostion);
            exoPlayer.setPlayWhenReady(true);
        }
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                mCallback.onButtonSelected(R.id.back_button);
                break;
            case R.id.forward_button:
                mCallback.onButtonSelected(R.id.forward_button);
                break;
            default:
                break;
        }
    }

    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    public class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            exoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            exoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            exoPlayer.seekTo(0);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        exoPlayer.setPlayWhenReady(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        exoPlayer.setPlayWhenReady(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mediaSession.setActive(false);
    }


    public static class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context context;
        List<Ingredients> ingredients;

        public ListRemoteViewsFactory(Context applicationContext, List<Ingredients> ingredients){
            context = applicationContext;
            this.ingredients = ingredients;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int i) {
            return null;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
