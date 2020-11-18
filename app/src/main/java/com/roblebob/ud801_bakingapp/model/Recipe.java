package com.roblebob.ud801_bakingapp.model;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

public class Recipe {

    private int  id;
    private String  name;
    private List<Ingredient> ingredients;
    private List<Step> steps;
    private int servings;
    private String image;


    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }
    public void setSteps(List<Step> steps) {
        this.steps = steps;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("ingredients", ingredients)
                .add("steps", steps)
                .add("servings", servings)
                .add("image", image)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id &&
                servings == recipe.servings &&
                Objects.equal(name, recipe.name) &&
                Objects.equal(ingredients, recipe.ingredients) &&
                Objects.equal(steps, recipe.steps) &&
                Objects.equal(image, recipe.image);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, ingredients, steps, servings, image);
    }
}

