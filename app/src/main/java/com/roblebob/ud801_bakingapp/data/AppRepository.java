package com.roblebob.ud801_bakingapp.data;

import android.util.Log;

import androidx.lifecycle.LiveData;


import com.roblebob.ud801_bakingapp.model.AppState;
import com.roblebob.ud801_bakingapp.model.Ingredient;
import com.roblebob.ud801_bakingapp.model.Recipe;
import com.roblebob.ud801_bakingapp.model.Step;
import com.roblebob.ud801_bakingapp.util.Executors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;


public class AppRepository {

    final static String URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    private final AppDatabase mAppDatabase;
    public AppRepository(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }




    public LiveData<List<Recipe>> getRecipeListLive() {
        return this.mAppDatabase.recipeDao().loadRecipeListLive();
    }

    public LiveData<List<Ingredient>> getIngredientListLive(String recipeName) {
        return this.mAppDatabase.ingredientDao().loadIngredientsLive(recipeName);
    }

    public List<Ingredient> getIngredientList(String recipeName) {
        return this.mAppDatabase.ingredientDao().loadIngredients(recipeName);
    }
    public List<String> getRecipeNameList() {
        return this.mAppDatabase.recipeDao().loadRecipeNameList();
    }



    public LiveData<List<Step>> getStepListLive(String recipeName) {
        return this.mAppDatabase.stepDao().loadStepListLive(recipeName);
    }

    public LiveData<String> getAppStateLive(String key) {
        return this.mAppDatabase.appStateDao().loadState(key);
    }

    public LiveData<String> getCurrentRecipeNameLive() {
        return this.mAppDatabase.appStateDao().loadCurrentRecipeName();
    }

    public void insertCurrentRecipeName(String recipeName) {
        insert( new AppState("current_recipe_name", recipeName));
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
    public void insert(AppState appState) {
        Executors.getInstance().diskIO().execute( () -> this.mAppDatabase.appStateDao().insert( appState));
    }






    public void integrate() {
        Executors.getInstance().networkIO().execute( () -> {

            try {
                String result = getResponseFromHttpUrl(URL);

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

                        double quantity = jsonObjectIngredient.getDouble("quantity");
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





    /* *********************************************************************************************
     * This method returns the entire result from the HTTP response.
     *
     * @param   urlString The URL to fetch the HTTP response from (as a String and NOT as URL).
     * @return  The contents of the HTTP response.
     * @throws  IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(String urlString) throws IOException {

        java.net.URL url = new URL(urlString);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput)  return scanner.next();
            else           return null;
        }
        finally { urlConnection.disconnect(); }
    }
}
