package com.roblebob.ud801_bakingapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.util.Objects;


@Entity(tableName = "Ingredient", indices = @Index(value = {"recipeName", "quantity", "measure", "name"}, unique = true))
public class Ingredient {

    @PrimaryKey(autoGenerate = true)        private int     id;
    @ColumnInfo(name = "recipeName")        private String  recipeName;
    @ColumnInfo(name = "quantity")          private double  quantity;
    @ColumnInfo(name = "measure")           private String  measure;
    @ColumnInfo(name = "name")              private String  name;


    public Ingredient(String recipeName, double quantity, String measure, String name) {
        this.recipeName = recipeName;
        this.quantity = quantity;
        this.measure = measure;
        this.name = name;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }
    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public double getQuantity() {
        return quantity;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }
    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        Ingredient that = (Ingredient) obj;
        return
                this.getRecipeName().equals( that.getRecipeName()) &&
                this.getQuantity() == that.getQuantity() &&
                this.getMeasure().equals( that.getMeasure()) &&
                this.getName().equals( that.getRecipeName());
    }

    @Override
    public int hashCode() {
        return Objects.hash( getRecipeName(),  getQuantity(), getMeasure(), getName());
    }
}

