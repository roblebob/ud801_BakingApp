package com.roblebob.ud801_bakingapp.data;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.common.util.concurrent.ExecutionError;
import com.roblebob.ud801_bakingapp.model.Ingredient;
import com.roblebob.ud801_bakingapp.model.Recipe;
import com.roblebob.ud801_bakingapp.model.Step;
import com.roblebob.ud801_bakingapp.util.Executors;
import com.roblebob.ud801_bakingapp.util.Networking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;



public class AppRepository {

    final String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    private AppDatabase mAppDatabase;
    public AppRepository(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }




    public LiveData<List<Recipe>> getRecipeNameListLive() {
        return this.mAppDatabase.recipeDao().loadRecipeList();
    }

    public LiveData<List<Ingredient>> getIngredientListLive(String recipeName) {
        return this.mAppDatabase.ingredientDao().loadIngredientsLive(recipeName);
    }

    public LiveData<List<Step>> getStepListLive(String recipeName) {
        return this.mAppDatabase.stepDao().loadStepListLive(recipeName);
    }




    public void insert( Recipe recipe) {
        Executors.getInstance().diskIO().execute( () -> this.mAppDatabase.recipeDao().insert( recipe));
    }

    public void insert( Ingredient ingredient) {
        Executors.getInstance().diskIO().execute( () -> this.mAppDatabase.ingredientDao().insert( ingredient));
    }

    public void insert( Step step) {
        Executors.getInstance().diskIO().execute( () -> this.mAppDatabase.stepDao().insert( step));
    }




    public void integrate() {
        Executors.getInstance().networkIO().execute( () -> {

            try {
                String result = Networking.getResponseFromHttpUrl(URL);

                JSONArray jsonArray = new JSONArray(result);
                for (int i=0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int recipeId = jsonObject.getInt("id");
                    String recipeName = jsonObject.getString("name");
                    int recipeServings = jsonObject.getInt("servings");
                    String recipeImage = jsonObject.getString("image");
                    insert(new Recipe( recipeId, recipeName, recipeServings, recipeImage));


                    JSONArray jsonArrayIngredients = jsonObject.getJSONArray("ingredients");
                    for (int i1=0; i1 < jsonArrayIngredients.length(); i1++) {
                        JSONObject jsonObjectIngredient = jsonArrayIngredients.getJSONObject(i1);

                        int quantity = jsonObjectIngredient.getInt("quantity");
                        String measure = jsonObjectIngredient.getString("measure");
                        String name = jsonObjectIngredient.getString("ingredient");
                        insert(new Ingredient(recipeName, quantity, measure, name));
                    }

                    JSONArray jsonArraySteps = jsonObject.getJSONArray("steps");
                    for (int i1=0; i1 < jsonArraySteps.length(); i1++) {
                        JSONObject jsonObjectStep = jsonArraySteps.getJSONObject(i1);

                        int stepNumber = jsonObjectStep.getInt("id");
                        String shortDescription = jsonObjectStep.getString("shortDescription");
                        String description = jsonObjectStep.getString("description");
                        String videoURL = jsonObjectStep.getString("videoURL");
                        String thumbnailURL = jsonObjectStep.getString("thumbnailURL");
                        insert(new Step( recipeName, stepNumber, shortDescription, description, videoURL, thumbnailURL));
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                Log.e(this.getClass().getSimpleName(), "Error, while gathering json data");
            }

        });
    }
}
