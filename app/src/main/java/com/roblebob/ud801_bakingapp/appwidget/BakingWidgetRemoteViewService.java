package com.roblebob.ud801_bakingapp.appwidget;

import static com.roblebob.ud801_bakingapp.appwidget.BakingWidgetProvider.EXTRA_ITEM_RECIPE_NAME;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.annotation.WorkerThread;

import com.roblebob.ud801_bakingapp.R;
import com.roblebob.ud801_bakingapp.data.AppDatabase;
import com.roblebob.ud801_bakingapp.data.RecipeDao;
import com.roblebob.ud801_bakingapp.util.Executors;

import java.util.ArrayList;
import java.util.List;


public class BakingWidgetRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingWidgetItemFactory(getApplicationContext(), intent);
    }

    @WorkerThread
    static class BakingWidgetItemFactory implements RemoteViewsFactory{

        private final Context mContext;
        private final int mAppWidgetId;
        private List<String> mList = new ArrayList<>();

        BakingWidgetItemFactory(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override public void onCreate() {}
        @Override public void onDestroy() {}
        @Override public int getCount() {
            return mList.size();
        }
        @Override public RemoteViews getLoadingView() {
            return null;
        }
        @Override public int getViewTypeCount() {
            return 1;
        }
        @Override public long getItemId(int i) {
            return i;
        }
        @Override public boolean hasStableIds() {
            return true;
        }

        @Override
        public void onDataSetChanged() {
            Executors.getInstance().diskIO().execute( () -> {
                AppDatabase appDatabase = AppDatabase.getInstance(mContext);
                RecipeDao recipeDao = appDatabase.recipeDao();
                mList = recipeDao.loadRecipeNameList();
            });
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_widget_single_item_recipe_name);
            views.setTextViewText(R.id.baking_widget_single_item_recipe_name_tv, mList.get(i));

            Intent fillIntent = new Intent();
            fillIntent.putExtra(EXTRA_ITEM_RECIPE_NAME, mList.get(i));
            fillIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            views.setOnClickFillInIntent(R.id.baking_widget_single_item_recipe_name_tv, fillIntent);

            return views;
        }

    }

}
