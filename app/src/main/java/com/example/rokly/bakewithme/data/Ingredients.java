package com.example.rokly.bakewithme.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredients implements Parcelable {

    public final static Parcelable.Creator<Ingredients> CREATOR = new Parcelable.Creator<Ingredients>() {

        @Override
        public Ingredients createFromParcel(Parcel parcel) {
            return new Ingredients(parcel);
        }

        @Override
        public Ingredients[] newArray(int i) {
            return new Ingredients[i];
        }

    };

    private String Quantity;
    private String Measure;
    private String Ingredient;

    public Ingredients(String quantity, String measure, String ingredient) {
        Quantity = quantity;
        Measure = measure;
        Ingredient = ingredient;
    }

    private Ingredients(Parcel in) {
        Quantity = in.readString();
        Measure = in.readString();
        Ingredient = in.readString();
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getMeasure() {
        return Measure;
    }

    public String getIngredient() {
        return Ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Quantity);
        parcel.writeString(Measure);
        parcel.writeString(Ingredient);
    }
}
