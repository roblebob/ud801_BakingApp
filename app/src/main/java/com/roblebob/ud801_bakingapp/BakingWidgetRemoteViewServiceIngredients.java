package com.roblebob.ud801_bakingapp;

import static com.roblebob.ud801_bakingapp.BakingWidgetProvider.EXTRA_CURRENT_RECIPE_NAME;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.roblebob.ud801_bakingapp.data.AppDatabase;
import com.roblebob.ud801_bakingapp.data.AppStateDao;
import com.roblebob.ud801_bakingapp.data.IngredientDao;
import com.roblebob.ud801_bakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class BakingWidgetRemoteViewServiceIngredients extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new BakingWidgetItemFactoryIngredients(getApplicationContext(), intent);
    }


    class BakingWidgetItemFactoryIngredients implements RemoteViewsFactory {
        private Context mContext;
        private int mAppWidgetId;
        private List<Ingredient> mIngredientList = new ArrayList<>();
        private IngredientDao mIngredientDao;
        private AppStateDao mAppStateDao;
        private String mCurrentRecipeName;

        BakingWidgetItemFactoryIngredients(Context context, Intent intent) {
            mContext = context;
            AppDatabase appDatabase = AppDatabase.getInstance(mContext);
            mIngredientDao = appDatabase.ingredientDao();
            mAppStateDao = appDatabase.appStateDao();
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            mCurrentRecipeName = intent.getStringExtra(EXTRA_CURRENT_RECIPE_NAME);
            Log.e(this.getClass().getSimpleName(), "constructor: [[" + mCurrentRecipeName + "]]");

        }


        @Override
        public void onCreate() {
            mCurrentRecipeName = mAppStateDao.loadCurrentRecipeName();
            Log.e(this.getClass().getSimpleName(), "onCreate():  [[" + mCurrentRecipeName + "]]");
            mIngredientList = mIngredientDao.loadIngredients(mCurrentRecipeName);
        }

        @Override
        public void onDataSetChanged() {
            mCurrentRecipeName = mAppStateDao.loadCurrentRecipeName();
            Log.e(this.getClass().getSimpleName(), "onDataSetChanged(): [[" + mCurrentRecipeName + "]]");
            mIngredientList = mIngredientDao.loadIngredients(mCurrentRecipeName);
            Log.e(this.getClass().getSimpleName(), "size: " + mIngredientList.size());
        }

        @Override
        public RemoteViews getViewAt(int i) {
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

        @Override public int getCount() { return mIngredientList.size(); }
        @Override public RemoteViews getLoadingView() { return null; }
        @Override public int getViewTypeCount() { return 1; }
        @Override public long getItemId(int i) { return i; }
        @Override public boolean hasStableIds() { return true; }
        @Override public void onDestroy() { }


    }
}
