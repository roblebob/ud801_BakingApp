//package com.roblebob.ud801_bakingapp;
//
//import android.app.IntentService;
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//
//import androidx.annotation.Nullable;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.lifecycle.ViewModelStoreOwner;
//
//import com.roblebob.ud801_bakingapp.data.AppDatabase;
//import com.roblebob.ud801_bakingapp.data.AppRepository;
//import com.roblebob.ud801_bakingapp.model.Recipe;
//import com.roblebob.ud801_bakingapp.viewmodels.MasterViewModel;
//import com.roblebob.ud801_bakingapp.viewmodels.MasterViewModelFactory;
//import com.roblebob.ud801_bakingapp.viewmodels.WidgetViewModel;
//import com.roblebob.ud801_bakingapp.viewmodels.WidgetViewModelFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class BakingWidgetService extends IntentService {
//
//    public BakingWidgetService() { super("BakingWidgetService"); }
//
//
//    public static  final String ACTION_GET_RECIPE_NAMES = "com.roblebob.ud801_bakingapp.action.get_recipe_names";
//
//    public static void startActionExplicitly(Context context) {
//        Intent intent = new Intent( context, BakingWidgetService.class);
//        intent.setAction( ACTION_GET_RECIPE_NAMES);
//        context.startService( intent);
//    }
//
//
//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        if ( intent != null) {
//            final String action = intent.getAction();
//            if (ACTION_GET_RECIPE_NAMES.equals( action)) {
//                getRecipeNameList();
//            }
//        }
//
//    }
//
//    Context mContext;
//    AppDatabase mAppDatabase;
//    AppRepository mAppRepository;
//
//    List< String> mRecipeNameList = new ArrayList<>();
//    private void getRecipeNameList() {
//
//        if (mContext == null) {
//            mContext = this.getApplicationContext();
//            mAppDatabase = AppDatabase.getInstance( mContext);
//            mAppRepository = new AppRepository( mAppDatabase);
//        }
//
//        mRecipeNameList = new ArrayList<>(mAppRepository.getRecipeNameList());
//
//        Log.e(this.getClass().getSimpleName(), mRecipeNameList.get(0));
//    }
//}
