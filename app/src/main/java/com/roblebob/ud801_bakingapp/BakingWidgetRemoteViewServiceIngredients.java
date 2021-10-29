package com.roblebob.ud801_bakingapp;

import static com.roblebob.ud801_bakingapp.BakingWidgetProvider.EXTRA_CURRENT_RECIPE_NAME;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.annotation.WorkerThread;

import com.roblebob.ud801_bakingapp.data.AppDatabase;
import com.roblebob.ud801_bakingapp.data.AppStateDao;
import com.roblebob.ud801_bakingapp.data.IngredientDao;
import com.roblebob.ud801_bakingapp.model.Ingredient;
import com.roblebob.ud801_bakingapp.util.Executors;

import java.util.ArrayList;
import java.util.List;

public class BakingWidgetRemoteViewServiceIngredients extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingWidgetItemFactoryIngredients(getApplicationContext(), intent);
    }

    @WorkerThread
    static class BakingWidgetItemFactoryIngredients implements RemoteViewsFactory {
        private final Context mContext;
        private int mAppWidgetId;
        private List<Ingredient> mIngredientList = new ArrayList<>();
        private int mSize;
//        private final IngredientDao mIngredientDao;
//        private final AppStateDao mAppStateDao;
        //private String mCurrentRecipeName;

        BakingWidgetItemFactoryIngredients(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

//            AppDatabase appDatabase = AppDatabase.getInstance(mContext);
//            mIngredientDao = appDatabase.ingredientDao();
//            mAppStateDao = appDatabase.appStateDao();
//            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
//
            //mCurrentRecipeName = intent.getStringExtra(EXTRA_CURRENT_RECIPE_NAME);
            //Log.e(this.getClass().getSimpleName(), "constructor: [[" + mCurrentRecipeName + "]]");

        }


        @Override
        public void onCreate() {

//            Executors.getInstance().diskIO().execute( () -> {
//                        AppDatabase appDatabase = AppDatabase.getInstance(mContext);
//                        AppStateDao appStateDao = appDatabase.appStateDao();
//                        IngredientDao ingredientDao = appDatabase.ingredientDao();
//                        mCurrentRecipeName = appStateDao.loadCurrentRecipeName();
//                        SystemClock.sleep(500);
//                        mIngredientList = ingredientDao.loadIngredients(mCurrentRecipeName);
//                    });

//            mCurrentRecipeName = mAppStateDao.loadCurrentRecipeName();
//            mIngredientList = mIngredientDao.loadIngredients(mCurrentRecipeName);

            //Log.e(this.getClass().getSimpleName(), "onCreate():  [[" + mCurrentRecipeName + "]]");
        }

        @Override
        public void onDataSetChanged() {

            Executors.getInstance().diskIO().execute( () -> {
                AppDatabase appDatabase = AppDatabase.getInstance(mContext);
                AppStateDao appStateDao = appDatabase.appStateDao();
                IngredientDao ingredientDao = appDatabase.ingredientDao();
                String currentRecipeName = appStateDao.loadCurrentRecipeName();
                mIngredientList = ingredientDao.loadIngredients(currentRecipeName);
                mSize = mIngredientList.size();
                Log.e(this.getClass().getSimpleName(), "onDataSetChanged(): [[" + currentRecipeName + "]]");
                Log.e(this.getClass().getSimpleName(), "size: " + mSize);
            });

//            mCurrentRecipeName = mAppStateDao.loadCurrentRecipeName();
//            mIngredientList = mIngredientDao.loadIngredients(mCurrentRecipeName);

            //Log.e(this.getClass().getSimpleName(), "onDataSetChanged(): [[" + mCurrentRecipeName + "]]");
            //Log.e(this.getClass().getSimpleName(), "size: " + mIngredientList.size());
        }

        @Override
        public RemoteViews getViewAt(int i) {
            if (i >= mSize) return null;
            Ingredient ingredient = mIngredientList.get(i);

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_widget_single_item_ingredient);

            String s = ingredient.getName() +
                    "  " +
                    ((ingredient.getQuantity() - ((int) ingredient.getQuantity()) == 0.0) ?
                            String.valueOf( (int) ingredient.getQuantity()) :
                            String.valueOf(ingredient.getQuantity()))
                    +
                    " " +
                    ingredient.getMeasure();

            views.setTextViewText(R.id.baking_widget_single_item_ingredient_tv, s);

            return views;
        }

        @Override public int getCount() { return mSize; }
        @Override public RemoteViews getLoadingView() { return null; }
        @Override public int getViewTypeCount() { return 1; }
        @Override public long getItemId(int i) { return i; }
        @Override public boolean hasStableIds() { return true; }
        @Override public void onDestroy() { }


    }
}
