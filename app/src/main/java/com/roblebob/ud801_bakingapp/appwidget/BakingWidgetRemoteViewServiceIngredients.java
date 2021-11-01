package com.roblebob.ud801_bakingapp.appwidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.annotation.WorkerThread;

import com.roblebob.ud801_bakingapp.R;
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
        private final int mAppWidgetId;
        private List<Ingredient> mIngredientList = new ArrayList<>();
        private int mSize;


        BakingWidgetItemFactoryIngredients(Context context, Intent intent) {
            mContext = context;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override public void onCreate() { loadData(); }
        @Override public void onDataSetChanged() { loadData(); }
        @Override public int getCount() { return mSize; }
        @Override public RemoteViews getLoadingView() { return null; }
        @Override public int getViewTypeCount() { return 1; }
        @Override public long getItemId(int i) { return i; }
        @Override public boolean hasStableIds() { return true; }
        @Override public void onDestroy() { }



        @Override
        public RemoteViews getViewAt(int i) {
            if (i >= mSize) return null;

            Ingredient ingredient = mIngredientList.get(i);
            String s = ingredient.getName() +
                    "  " +
                    ((ingredient.getQuantity() - ((int) ingredient.getQuantity()) == 0.0) ?
                            String.valueOf( (int) ingredient.getQuantity()) :
                            String.valueOf(ingredient.getQuantity()))
                    +
                    " " +
                    ingredient.getMeasure();

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.baking_widget_single_item_ingredient);
            views.setTextViewText(R.id.baking_widget_single_item_ingredient_tv, s);
            return views;
        }

        private void loadData() {
            Executors.getInstance().diskIO().execute( () -> {
                AppDatabase appDatabase = AppDatabase.getInstance(mContext);
                AppStateDao appStateDao = appDatabase.appStateDao();
                IngredientDao ingredientDao = appDatabase.ingredientDao();
                String currentRecipeName = appStateDao.loadCurrentRecipeName();
                mIngredientList = ingredientDao.loadIngredients(currentRecipeName);
                mSize = mIngredientList.size();
            });
        }
    }
}
