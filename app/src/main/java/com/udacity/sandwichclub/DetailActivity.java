package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json, this);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView tvPlaceOfOrigin, tvAlsoKnownAs, tvIngredients, tvDescription;

        tvPlaceOfOrigin = (TextView) findViewById(R.id.origin_tv);
        tvAlsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        tvIngredients = (TextView) findViewById(R.id.ingredients_tv);
        tvDescription = (TextView) findViewById(R.id.description_tv);

        String placeOfOriginText = "-";
        if(!TextUtils.isEmpty(sandwich.getPlaceOfOrigin())) {
            placeOfOriginText = sandwich.getPlaceOfOrigin();
        }
        tvPlaceOfOrigin.setText(placeOfOriginText);

        String alsoKnownAsText = "-";
        if(sandwich.getAlsoKnownAs() != null) {
            alsoKnownAsText = sandwich.getAlsoKnownAs().get(0);
            for (int i = 1; i < sandwich.getAlsoKnownAs().size(); i++) {
                alsoKnownAsText += "\n" + sandwich.getAlsoKnownAs().get(i);
            }
        }
        tvAlsoKnownAs.setText(alsoKnownAsText);

        String ingredientsText = "-";
        if(sandwich.getIngredients() != null) {
            ingredientsText = "- " + sandwich.getIngredients().get(0);
            for (int i = 1; i < sandwich.getIngredients().size(); i++) {
                ingredientsText += "\n- " + sandwich.getIngredients().get(i);
            }
        }
        tvIngredients.setText(ingredientsText);

        String descriptionText = "-";
        if(!TextUtils.isEmpty(sandwich.getDescription())) {
            descriptionText = sandwich.getDescription();
        }
        tvDescription.setText(descriptionText);
    }
}
