package com.example.rokly.bakewithme.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipes implements Parcelable {

    public static final String PARCELABLE_KEY = "parcelable_key";
    public final static Parcelable.Creator<Recipes> CREATOR = new Parcelable.Creator<Recipes>() {
        @Override
        public Recipes createFromParcel(Parcel parcel) {
            return new Recipes(parcel);
        }

        @Override
        public Recipes[] newArray(int i) {
            return new Recipes[i];
        }

    };
    private String Id;
    private String Name;

    public Recipes(String id, String name) {
        Id = id;
        Name = name;
    }

    private Recipes(Parcel in) {
        Id = in.readString();
        Name = in.readString();
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "Id='" + Id + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeString(Name);
    }
}
