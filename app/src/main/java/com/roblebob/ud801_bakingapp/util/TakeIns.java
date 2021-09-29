package com.roblebob.ud801_bakingapp.util;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;


import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.model.Recipe;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

public class TakeIns {



    public List<Recipe> takeIn(String src_url) {
        try {
            return fromJson( getResponseFromHttpUrl( src_url));
        } catch( IOException e) {
            e.printStackTrace();
            return null;
        }
    }




    public static List<Recipe> fromJson(String json)  {
        try {
            Moshi moshi = new Moshi.Builder().build();
            Type type = Types.newParameterizedType(List.class, Recipe.class);
            JsonAdapter<List<Recipe>> adapter = moshi.adapter(type);
            return adapter.nullSafe().fromJson(json);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getResponseFromHttpUrl(String urlString) throws IOException {

        URL url = new URL(urlString);
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
