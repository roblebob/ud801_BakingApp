package com.roblebob.ud801_bakingapp;


import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth8.assertThat;

 

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */



public class TakeInsUnitTest {


/*
    @Test
    public void fromJson_isCorrect() throws IOException {
        assertThat(testObject).isEqualTo(TakeIns.fromJson(testJson));
    }


*/


/*

    public final List<Recipe> testObject =
            Arrays.asList(
                    new Recipe(
                            1,
                            "Test Recipe 1",
                            Arrays.asList(
                                    new Ingredient(1, Measure.CUP, "Test Recipe 1 - Ingredient_A"),
                                    new Ingredient(2, Measure.TBLSP, "Test Recipe 1 - Ingredient_B")
                            ),
                            Arrays.asList(
                                    new Step(0, "Test Recipe 1 - Step 0", "Test Recipe 1 - Step 0 - Description", "", "" ),
                                    new Step(1, "Test Recipe 1 - Step 1", "Test Recipe 1 - Step 1 - Description", "", "" )
                            ),
                            8,
                            ""
                    ),
                    new Recipe(
                            2,
                            "Test Recipe 2",
                            Arrays.asList(
                                    new Ingredient(1, Measure.CUP, "Test Recipe 2 - Ingredient_A"),
                                    new Ingredient(2, Measure.TBLSP, "Test Recipe 2 - Ingredient_B")
                            ),
                            Arrays.asList(
                                    new Step(0, "Test Recipe 2 - Step 0", "Test Recipe 2 - Step 0 - Description", "", "" ),
                                    new Step(1, "Test Recipe 2 - Step 1", "Test Recipe 2 - Step 1 - Description", "", "" )
                            ),
                            8,
                            ""
                    )
            );
*/

    public final String testJson =
                    "[\n" +
                    "  {\n" +
                    "    \"id\": 1,\n" +
                    "    \"name\": \"Test Recipe 1\",\n" +
                    "    \"ingredients\": [\n" +
                    "      {\n" +
                    "        \"quantity\": 1,\n" +
                    "        \"measure\": \"CUP\",\n" +
                    "        \"ingredient\": \"Test Recipe 1 - Ingredient_A\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"quantity\": 2,\n" +
                    "        \"measure\": \"TBLSP\",\n" +
                    "        \"ingredient\": \"Test Recipe 1 - Ingredient_B\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"steps\": [\n" +
                    "      {\n" +
                    "        \"id\": 0,\n" +
                    "        \"shortDescription\": \"Test Recipe 1 - Step 0\",\n" +
                    "        \"description\": \"Test Recipe 1 - Step 0 - Description\",\n" +
                    "        \"videoURL\": \"\",\n" +
                    "        \"thumbnailURL\": \"\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 1,\n" +
                    "        \"shortDescription\": \"Test Recipe 1 - Step 1\",\n" +
                    "        \"description\": \"Test Recipe 1 - Step 1 - Description\",\n" +
                    "        \"videoURL\": \"\",\n" +
                    "        \"thumbnailURL\": \"\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"servings\": 8,\n" +
                    "    \"image\": \"\"\n" +
                    "  },\n" +

                    "  {\n" +
                    "    \"id\": 2,\n" +
                    "    \"name\": \"Test Recipe 2\",\n" +
                    "    \"ingredients\": [\n" +
                    "      {\n" +
                    "        \"quantity\": 1,\n" +
                    "        \"measure\": \"CUP\",\n" +
                    "        \"ingredient\": \"Test Recipe 2 - Ingredient_A\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"quantity\": 2,\n" +
                    "        \"measure\": \"TBLSP\",\n" +
                    "        \"ingredient\": \"Test Recipe 2 - Ingredient_B\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"steps\": [\n" +
                    "      {\n" +
                    "        \"id\": 0,\n" +
                    "        \"shortDescription\": \"Test Recipe 2 - Step 0\",\n" +
                    "        \"description\": \"Test Recipe 2 - Step 0 - Description\",\n" +
                    "        \"videoURL\": \"\",\n" +
                    "        \"thumbnailURL\": \"\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"id\": 1,\n" +
                    "        \"shortDescription\": \"Test Recipe 2 - Step 1\",\n" +
                    "        \"description\": \"Test Recipe 2 - Step 1 - Description\",\n" +
                    "        \"videoURL\": \"\",\n" +
                    "        \"thumbnailURL\": \"\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"servings\": 8,\n" +
                    "    \"image\": \"\"\n" +
                    "  }\n" +
                    "]";
}