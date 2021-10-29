//package com.roblebob.ud801_bakingapp;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.PendingIntent;
//import android.appwidget.AppWidgetManager;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.RemoteViews;
//
//import com.roblebob.ud801_bakingapp.ui.MainActivity;
//
//public class BakingWidgetConfig extends AppCompatActivity {
//
//    public static final String SHARED_PRES = "prefs";
//    public static final String KEY_BUTTON_TEXT = "keyButtonText";
//
//    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
//    private EditText editTextButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_baking_widget_config);
//
//        Intent configIntent = getIntent();
//        Bundle extras = configIntent.getExtras();
//        if (extras != null) {
//            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
//        }
//
//
//        // if user leaves activity by using the back button, without setting anything up,
//        // normally  should not be necessary but on some phones launcher crashes when not sending an appWidgetId back
//        Intent resultValue = new Intent();
//        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        setResult(RESULT_CANCELED, resultValue);
//
//
//
//        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
//            finish();
//        }
//
//        editTextButton = findViewById(R.id.edit_text_button);
//    }
//
//    public void confirmConfiguration(View v) {
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
//
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
//
//        String buttonText = editTextButton.getText().toString();
//
//        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.baking_widget_provider);
//        views.setOnClickPendingIntent(R.id.widget_ingredient_button, pendingIntent);
//        views.setCharSequence(R.id.widget_ingredient_button, "setText", buttonText);
//        views.setInt(R.id.widget_ingredient_button, "setBackgroundColor", Color.RED);
//        views.setBoolean(R.id.widget_ingredient_button, "setEnabled", true);
//
//        appWidgetManager.updateAppWidget(appWidgetId, views);
//
//        SharedPreferences prefs = getSharedPreferences(SHARED_PRES, MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(KEY_BUTTON_TEXT + appWidgetId, buttonText); // ... + appWidgetId      -> to make it widget specific, can have more than one
//        editor.apply();
//
//        // finish that activity
//        Intent resultValue = new Intent();
//        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
//        setResult(RESULT_OK, resultValue);
//        finish();
//    }
//}