package com.roblebob.ud801_bakingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private boolean mTwoPane;


    public static final String SRC_URL = Resources.getSystem().getString(R.string.src_url);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





    }





}





// TODO: get video thumbnail (last frame of the last step video) given its url, without downloading the entire video.

// https://stackoverflow.com/questions/30224416/how-to-get-video-thumbnail-frame-from-live-video-url
// https://stackoverflow.com/questions/45833545/how-get-frame-from-video-url-and-show-in-imageview