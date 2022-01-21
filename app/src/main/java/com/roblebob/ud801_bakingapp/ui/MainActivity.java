package com.roblebob.ud801_bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.PictureInPictureParams;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.View;

import com.roblebob.ud801_bakingapp.R;

public class MainActivity extends AppCompatActivity
{
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onUserLeaveHint() {

        View rootOfStepFragment = (View) findViewById(R.id.fragment_step_root);

        if ( rootOfStepFragment != null) {

            Log.e(TAG, "entered pip");

            enterPictureInPictureMode(
                    new PictureInPictureParams
                            .Builder()
                            .setAspectRatio(new Rational(16, 9))
                            .build()
            );
        } else {
            Log.e(TAG, "avoided entering pip");
            super.onUserLeaveHint();
        }

    }

}
