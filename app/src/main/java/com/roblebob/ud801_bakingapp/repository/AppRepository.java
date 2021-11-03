package com.roblebob.ud801_bakingapp.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.model.AppState;
import com.roblebob.ud801_bakingapp.model.Ingredient;
import com.roblebob.ud801_bakingapp.model.Recipe;
import com.roblebob.ud801_bakingapp.model.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;





public class AppRepository {

    final String mSrcUrl;
    final AppStateDao mAppStateDao;
    final RecipeDao mRecipeDao;
    final StepDao mStepDao;
    final IngredientDao mIngredientDao;

    public AppRepository(Context context) {
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        mRecipeDao = appDatabase.recipeDao();
        mStepDao = appDatabase.stepDao();
        mIngredientDao = appDatabase.ingredientDao();
        mAppStateDao = appDatabase.appStateDao();
        mSrcUrl = context.getString(R.string.src_url);
    }


    /**
     *  methods providing the view models with the required live data from the database
     */

    public LiveData<List<Recipe>> getRecipeListLive() {
        return mRecipeDao.loadRecipeListLive();
    }
    public LiveData<List<Ingredient>> getIngredientListLive(String recipeName) {
        return mIngredientDao.loadIngredientsLive(recipeName);
    }
    public LiveData<List<Step>> getStepListLive(String recipeName) {
        return mStepDao.loadStepListLive(recipeName);
    }


    /**
     *  Integrates the necessary data into the room database by first sending the http request,
     *  then parse the string response throw json to entities of the data model.
     */

    public void integrate() {
        Executors.getInstance().networkIO().execute( () -> {

            try {
                String result = getResponseFromHttpUrl_OkHttp(mSrcUrl);

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

                        String shortDescription = jsonObjectStep.getString("shortDescription");
                        String description = jsonObjectStep.getString("description");
                        String videoURL = jsonObjectStep.getString("videoURL");
                        String thumbnailURL = jsonObjectStep.getString("thumbnailURL");

                        // removing step number (inconsistent)
                        description = description.substring(
                                (description.contains(". ") && description.indexOf(".") < 4)
                                        ? description.indexOf(". ") + 2 : 0
                        );

                        insert(new Step( recipeName, /* stepNumber */ i1, shortDescription, description, videoURL, thumbnailURL));
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                Log.e(this.getClass().getSimpleName(), "Error, while gathering json data");
            }
        });
    }


    /**
     * This method returns the entire result from the HTTP response, using the third party library
     * OkHttp (part of the integration process)
     *
     * @param urlString The URL to fetch the HTTP response from (as a String and NOT as URL).
     * @return The contents of the HTTP response.
     * @throws IOException ...
     */

    public static String getResponseFromHttpUrl_OkHttp(String urlString)  throws IOException {

        final OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(urlString)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


    /**
     * (old/deprecated) This method returns the entire result from the HTTP response.
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



    /**
     * Insert a entities of the data model into the database
     * (overloaded methods, part of the integration process)
     */

    public void insert( Recipe recipe) {
        Executors.getInstance().diskIO().execute( () -> mRecipeDao.insert( recipe));
    }
    public void insert( Ingredient ingredient) {
        Executors.getInstance().diskIO().execute( () -> mIngredientDao.insert( ingredient));
    }
    public void insert( Step step) {
        Executors.getInstance().diskIO().execute( () -> mStepDao.insert( step));
    }
    public void insert(AppState appState) {
        Executors.getInstance().diskIO().execute( () -> mAppStateDao.insert( appState));
    }
}







