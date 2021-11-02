package com.roblebob.ud801_bakingapp;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.roblebob.ud801_bakingapp.ui.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MyFirstTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void test_masterList_isDisplayed() {

        onView( withId(R.id.master_RV)) .check( matches( isDisplayed()));
    }



    @Test
    public void test_selectMasterListItem_isRecipeFragmentVisible() {
        onView( withId( R.id.master_RV))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView( withId( R.id.fragment_recipe_heading_tv))
                .check( matches( withText("Nutella Pie")));
    }

    @Test
    public void test_selectMasterListItem_isRecipeFragmentVisible_and_navigateBack() {
        onView( withId( R.id.master_RV))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView( withId( R.id.fragment_recipe_heading_tv))
                .check( matches( withText("Nutella Pie")));

        pressBack();

        onView( withId(R.id.master_RV)) .check( matches( isDisplayed()));
    }

    @Test
    public void test_navIngredientsFragment_validateIngredientsList() {

        onView( withId( R.id.master_RV))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView( withId( R.id.fragment_recipe_heading_tv))
                .check( matches( withText("Nutella Pie")));


        onView( withId( R.id.fragment_recipe_ingredients_button)).perform( click());


        final String INGREDIENTS_NAME = "Graham Cracker crumbs";

        onView(withId(R.id.ingredients_RV))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(INGREDIENTS_NAME)),
                        click()));
    }
}
