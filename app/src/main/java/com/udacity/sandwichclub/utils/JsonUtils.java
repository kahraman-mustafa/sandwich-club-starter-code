package com.udacity.sandwichclub.utils;

import android.content.Context;
import android.util.Log;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json, Context context) {

        JSONObject jsonRoot;

        try {

            jsonRoot = new JSONObject(json);

            Log.d("JSONUtils", jsonRoot.toString());

        } catch (Throwable t) {
            Log.e("JSONUtils", "Could not parse malformed JSON: \"" + json + "\"");
            return null;
        }

        String mainName = "";
        List<String> alsoKnownAs = null;
        String placeOfOrigin = "";
        String description = "";
        String image = "";
        List<String> ingredients = null;

        try {
            mainName = jsonRoot
                    .getJSONObject(context.getString(R.string.key_sandwich_name))
                    .getString(context.getString(R.string.key_sandwich_main_name));

            // Get JSON array of also known as names.
            // If array size is zero then sandwich also known as names will be returned as null
            // Otherwise also known as names will be returned
            JSONArray alsoKnownAsJSONArray = jsonRoot
                    .getJSONObject(context.getString(R.string.key_sandwich_name))
                    .getJSONArray(context.getString(R.string.key_sandwich_also_known_as));
            int knownAsSize = alsoKnownAsJSONArray.length();
            if(knownAsSize > 0) {
                alsoKnownAs = new ArrayList<String>();
                for (int i = 0; i < knownAsSize; i++) {
                    alsoKnownAs.add(alsoKnownAsJSONArray.getString(i));
                }
            }

            placeOfOrigin = jsonRoot.getString(context.getString(R.string.key_sandwich_place_of_origin));

            description = jsonRoot.getString(context.getString(R.string.key_sandwich_description));

            image = jsonRoot.getString(context.getString(R.string.key_sandwich_image));

            // Get JSON array of ingredients.
            // If array size is zero then sandwich ingredients will be returned as null
            // Otherwise ingredients will be returned
            JSONArray ingredientsJSONArray = jsonRoot
                    .getJSONArray(context.getString(R.string.key_sandwich_ingredients));
            int ingredientsSize = ingredientsJSONArray.length();
            if(ingredientsSize > 0) {
                ingredients = new ArrayList<String>();
                for (int i = 0; i < ingredientsSize; i++) {
                    ingredients.add(ingredientsJSONArray.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Sandwich sandwich = new Sandwich();
        sandwich.setMainName(mainName);
        sandwich.setAlsoKnownAs(alsoKnownAs);
        sandwich.setPlaceOfOrigin(placeOfOrigin);
        sandwich.setDescription(description);
        sandwich.setImage(image);
        sandwich.setIngredients(ingredients);
        return sandwich;
    }
}
