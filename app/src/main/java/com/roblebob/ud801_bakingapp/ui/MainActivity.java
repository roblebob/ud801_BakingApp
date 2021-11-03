package com.roblebob.ud801_bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.conectivity.AppConnectivity;
import com.roblebob.ud801_bakingapp.conectivity.ConnectionLiveData;
import com.roblebob.ud801_bakingapp.conectivity.ConnectionModel;
import com.roblebob.ud801_bakingapp.model.Recipe;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnRecipeClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }


    // Callback from the MasterListFragment
    @Override
    public void onRecipeSelected(Recipe recipe) {

        Log.e(this.getClass().getSimpleName(), recipe.getName() + " was clicked");

        final Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("recipeName", recipe.getName());
        intent.putExtra("servings", recipe.getServings());
        startActivity(intent);
    }
}





// TODO: get video thumbnail (last frame of the last step video) given its url, without downloading the entire video.

// https://stackoverflow.com/questions/30224416/how-to-get-video-thumbnail-frame-from-live-video-url
// https://stackoverflow.com/questions/45833545/how-get-frame-from-video-url-and-show-in-imageview