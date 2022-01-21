package com.roblebob.ud801_bakingapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.fragment.NavHostFragment;

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

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        Log.e(TAG, String.valueOf(fragment));


        if (fragment instanceof StepFragment  && ((StepFragment) fragment).hasVideoPlayable()) {

            Log.e(TAG, "entered pip");

            enterPictureInPictureMode(
                    new PictureInPictureParams
                            .Builder()
                            .setAspectRatio(new Rational(16, 9))
                            .build()
            );
            getSupportActionBar().hide();

        } else {
            Log.e(TAG, "avoided entering pip");
            super.onUserLeaveHint();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().show();
    }
}
