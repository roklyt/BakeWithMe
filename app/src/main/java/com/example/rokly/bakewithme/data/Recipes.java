package com.example.rokly.bakewithme.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

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
    private List<Ingredients> Ingredients;
    private List<Steps> Steps;

    public Recipes(String id, String name, List<Ingredients> ingredients, List<Steps> steps) {
        Id = id;
        Name = name;
        Ingredients = ingredients;
        Steps = steps;
    }

    private Recipes(Parcel in) {
        Id = in.readString();
        Name = in.readString();
        Ingredients = new ArrayList<Ingredients>();
        in.readList(this.Ingredients, Ingredients.class.getClassLoader());
        Steps = new ArrayList<Steps>();
        in.readList(this.Steps, Steps.class.getClassLoader());
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public List<Ingredients> getIngredients(){return Ingredients;};

    public List<Steps> getSteps(){return Steps;};

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeString(Name);
        parcel.writeList(Ingredients);
        parcel.writeList(Steps);
    }
}
