package com.roblebob.ud801_bakingapp.appwidget;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.repository.AppDatabase;
import com.roblebob.ud801_bakingapp.repository.AppStateDao;
import com.roblebob.ud801_bakingapp.model.AppState;
import com.roblebob.ud801_bakingapp.ui.MainActivity;
import com.roblebob.ud801_bakingapp.repository.Executors;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_REFRESH = "com.roblebob.ud801_bakingapp.appwidget.action_refresh";
    public static final String EXTRA_ITEM_RECIPE_NAME = "com.roblebob.ud801_bakingapp.appwidget.extra_item_recipeName";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);

            // cake image starting the actual app
            Intent cakeIntent = new Intent(context, MainActivity.class);
            PendingIntent cakePendingIntent = PendingIntent.getActivity(context, 0, cakeIntent, 0);
            views.setOnClickPendingIntent( R.id.widget_image_view, cakePendingIntent);

            // refresh action to reset new current recipe name
            Intent clickIntent = new Intent(context, BakingWidgetProvider.class);
            clickIntent.setAction(ACTION_REFRESH);
            PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent, 0);
            views.setPendingIntentTemplate(R.id.widget_recipe_name_list_view, clickPendingIntent);

            // left (recipe name list)
            Intent serviceIntent = new Intent(context, BakingWidgetRemoteViewService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse( serviceIntent.toUri( Intent.URI_INTENT_SCHEME)));
            views.setRemoteAdapter(R.id.widget_recipe_name_list_view, serviceIntent);
            views.setEmptyView(R.id.widget_recipe_name_list_view, R.id.widget_recipe_name_empty_tv);

            // right (ingredient list)
            Intent serviceIntentIngredients = new Intent(context, BakingWidgetRemoteViewServiceIngredients.class);
            serviceIntentIngredients.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
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
    public void onReceive(Context context, Intent intent) {

        if (ACTION_REFRESH.equals(intent.getAction())) {

            String currentRecipeName = intent.getStringExtra(EXTRA_ITEM_RECIPE_NAME);
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

            Executors.getInstance().diskIO().execute( () -> {
                AppDatabase appDatabase = AppDatabase.getInstance(context);
                AppStateDao appStateDao = appDatabase.appStateDao();
                appStateDao.insert(new AppState("current_recipe_name", currentRecipeName));
            });

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_ingredient_list_view);
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Executors.getInstance().diskIO().execute( () -> {
            AppDatabase appDatabase = AppDatabase.getInstance(context);
            AppStateDao appStateDao = appDatabase.appStateDao();
            appStateDao.insert(new AppState("current_recipe_name", null));
        });
    }

}