package com.example.rokly.bakewithme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class RecipeDetailFragment extends Fragment {

    // Mandatory empty constructor
    public RecipeDetailFragment() {
    }

    // Inflates the detail view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);


        // Return the root view
        return rootView;
    }

}
