package com.roblebob.ud801_bakingapp;

//import static com.roblebob.ud801_bakingapp.BakingWidgetConfig.KEY_BUTTON_TEXT;
//import static com.roblebob.ud801_bakingapp.BakingWidgetConfig.SHARED_PRES;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.roblebob.ud801_bakingapp.data.AppDatabase;
import com.roblebob.ud801_bakingapp.data.AppStateDao;
import com.roblebob.ud801_bakingapp.model.AppState;
import com.roblebob.ud801_bakingapp.ui.MainActivity;
import com.roblebob.ud801_bakingapp.util.Executors;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_REFRESH = "actionRefresh";
    public static final String EXTRA_ITEM_RECIPE_NAME = "extraItemRecipeName";
    public static final String EXTRA_CURRENT_RECIPE_NAME = "extraCurrentRecipeName";


    //private String mCurrentRecipeName;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            Log.e(this.getClass().getSimpleName(), "onUpdate()");

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);

            // cake image starting the actual app
            Intent cakeIntent = new Intent(context, MainActivity.class);
            PendingIntent cakePendingIntent = PendingIntent.getActivity(context, 0, cakeIntent, 0);
            views.setOnClickPendingIntent( R.id.widget_image_view, cakePendingIntent);


/*
            SharedPreferences prefs = context.getSharedPreferences(SHARED_PRES, Context.MODE_PRIVATE);
            String buttonText = prefs.getString(KEY_BUTTON_TEXT + appWidgetId, "press me");
            views.setCharSequence(R.id.widget_ingredient_button, "setText", buttonText);
*/

            Intent clickIntent = new Intent(context, BakingWidgetProvider.class);
            clickIntent.setAction(ACTION_REFRESH);
            PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, 0);
            views.setPendingIntentTemplate(R.id.widget_recipe_name_list_view, clickPendingIntent);


            Intent serviceIntent = new Intent(context, BakingWidgetRemoteViewService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse( serviceIntent.toUri( Intent.URI_INTENT_SCHEME)));
            views.setRemoteAdapter(R.id.widget_recipe_name_list_view, serviceIntent);
            views.setEmptyView(R.id.widget_recipe_name_list_view, R.id.widget_recipe_name_empty_tv);


            Intent serviceIntentIngredients = new Intent(context, BakingWidgetRemoteViewServiceIngredients.class);
            serviceIntentIngredients.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            //serviceIntentIngredients.putExtra(EXTRA_CURRENT_RECIPE_NAME, mCurrentRecipeName);
            serviceIntentIngredients.setData(Uri.parse(serviceIntentIngredients.toUri(Intent.URI_INTENT_SCHEME)));
            views.setRemoteAdapter(R.id.widget_ingredient_list_view, serviceIntentIngredients);
            views.setEmptyView(R.id.widget_ingredient_list_view, R.id.widget_ingredient_empty_tv);



            Bundle appWidgetsOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
            resizeWidget(appWidgetsOptions, views);

            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_recipe_name_list_view);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);
        resizeWidget(newOptions, views);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private void resizeWidget(Bundle appWidgetOptions, RemoteViews views) {

        // portrait mode
        int minWidth = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int maxHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);

        // landscape mode
        int maxWidth = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        int minHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);

        if (maxHeight > 200) {
            views.setViewVisibility(R.id.widget_image_view, View.VISIBLE);
        } else {
            views.setViewVisibility(R.id.widget_image_view, View.GONE);
        }
    }









    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.e(this.getClass().getSimpleName(), "onDelete()");
        Executors.getInstance().diskIO().execute( () -> {
            AppDatabase appDatabase = AppDatabase.getInstance(context);
            AppStateDao appStateDao = appDatabase.appStateDao();
            appStateDao.insert(new AppState("current_recipe_name", null));
        });
    }

    @Override
    public void onEnabled(Context context) {
        Log.e(this.getClass().getSimpleName(), "onEnable()");
    }

    @Override
    public void onDisabled(Context context) {
        Log.e(this.getClass().getSimpleName(), "onDisabled()");
    }


    @Override
    public void onReceive(Context context, Intent intent) {

        if (ACTION_REFRESH.equals(intent.getAction())) {

            String currentRecipeName = intent.getStringExtra(EXTRA_ITEM_RECIPE_NAME);


            Log.e(this.getClass().getSimpleName() + "  onReceive(): ", "[[" + currentRecipeName + "]]");


            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);



            Executors.getInstance().diskIO().execute( () -> {
                AppDatabase appDatabase = AppDatabase.getInstance(context);
                AppStateDao appStateDao = appDatabase.appStateDao();
                appStateDao.insert(new AppState("current_recipe_name", currentRecipeName));
            });

            //SystemClock.sleep(1000);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            // appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_recipe_name_list_view);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_ingredient_list_view);
        }



        super.onReceive(context, intent);
    }
}