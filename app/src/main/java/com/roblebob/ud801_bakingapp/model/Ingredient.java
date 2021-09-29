package com.roblebob.ud801_bakingapp.model;


import androidx.annotation.NonNull;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

public class Ingredient {

    int quantity;
    Measure measure;
    String ingredient;


    public Ingredient(int quantity, Measure measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Measure getMeasure() {
        return measure;
    }
    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }
    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        //return quantity == that.quantity  &&  measure == that.measure  &&  Objects.equal(ingredient, that.ingredient);
        return hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
        //return Objects.hashCode(quantity, measure, ingredient);
    }

    @NonNull
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("quantity", quantity)
                .add("measure", measure)
                .add("ingredient", ingredient)
                .toString();
    }

}

