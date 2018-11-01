package com.example.rokly.bakewithme.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Steps implements Parcelable {

    public final static Parcelable.Creator<Steps> CREATOR = new Parcelable.Creator<Steps>() {

        @Override
        public Steps createFromParcel(Parcel parcel) {
            return new Steps(parcel);
        }

        @Override
        public Steps[] newArray(int i) {
            return new Steps[i];
        }

    };

    private int Id;
    private String ShortDescription;
    private String Description;
    private String VideoUrl;

    public Steps(int id, String shortDescription, String description, String videoUrl) {
        Id = id;
        ShortDescription = shortDescription;
        Description = description;
        VideoUrl = videoUrl;
    }

    private Steps(Parcel in) {
        Id = in.readInt();
        ShortDescription = in.readString();
        Description = in.readString();
        VideoUrl = in.readString();
    }

    public int getId() {
        return Id;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public String getDescription() {
        return Description;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Id);
        parcel.writeString(ShortDescription);
        parcel.writeString(Description);
        parcel.writeString(VideoUrl);
    }
}