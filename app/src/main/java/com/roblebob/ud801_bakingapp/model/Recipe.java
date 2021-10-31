package com.roblebob.ud801_bakingapp.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


import java.util.Objects;


@Entity(tableName = "Recipe")
public class Recipe {

    @PrimaryKey(autoGenerate = false)   private int     id;
    @ColumnInfo(name = "name")          private String  name;
    @ColumnInfo(name = "servings")      private int     servings;
    @ColumnInfo(name = "image")         private String  image;


    public Recipe(int id, String name, int servings, String image) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }
    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }



}

