package com.weirdgiraffegames.stellarleapscorepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class InputPointsActivity extends AppCompatActivity {

    ArrayList<String> selectedSpecies;
    int numSelectedSpecies = -1;
    int layoutIndex = 0;

    Button nextButton;
    View tuskadonLayout;
    View starlingsLayout;
    View cosmosaurusLayout;
    View scoutarsLayout;
    View araklithLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_points);
        setupUI();

        selectedSpecies = getIntent().getExtras().getStringArrayList(getString(R.string.selected_species_key));
        numSelectedSpecies = selectedSpecies.size();
        showLayout();
        for(String s:selectedSpecies) {
            Log.d("selected species",s);
        }
    }

    private void setupUI() {
        tuskadonLayout = findViewById(R.id.tuskadon_layout);
        starlingsLayout = findViewById(R.id.starlings_layout);
        cosmosaurusLayout = findViewById(R.id.cosmosaurus_layout);
        scoutarsLayout = findViewById(R.id.scoutars_layout);
        araklithLayout = findViewById(R.id.araklith_layout);

        nextButton = (Button) findViewById(R.id.next_btn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutIndex++;

                if(layoutIndex == numSelectedSpecies - 1) {
                    nextButton.setText(getString(R.string.see_totals));
                }
                if (layoutIndex == numSelectedSpecies) {
                    Intent i = new Intent(InputPointsActivity.this,FinalScoreActivity.class);
                    startActivity(i);
                } else {
                    showLayout();
                }
            }
        });
    }

    public void showLayout() {
        String currentSpecies = selectedSpecies.get(layoutIndex);
        Log.d("calling","replace layout, next layout = " + currentSpecies);

        if (currentSpecies.equals(getString(R.string.tuskadon))) {
            tuskadonLayout.setVisibility(View.VISIBLE);
            starlingsLayout.setVisibility(View.GONE);
            cosmosaurusLayout.setVisibility(View.GONE);
            scoutarsLayout.setVisibility(View.GONE);
            araklithLayout.setVisibility(View.GONE);
            return;
        }

        if (currentSpecies.equals(getString(R.string.starlings))) {
            tuskadonLayout.setVisibility(View.GONE);
            starlingsLayout.setVisibility(View.VISIBLE);
            cosmosaurusLayout.setVisibility(View.GONE);
            scoutarsLayout.setVisibility(View.GONE);
            araklithLayout.setVisibility(View.GONE);
            return;
        }

        if (currentSpecies.equals(getString(R.string.cosmosaurus))) {
            tuskadonLayout.setVisibility(View.GONE);
            starlingsLayout.setVisibility(View.GONE);
            cosmosaurusLayout.setVisibility(View.VISIBLE);
            scoutarsLayout.setVisibility(View.GONE);
            araklithLayout.setVisibility(View.GONE);
            return;
        }

        if (currentSpecies.equals(getString(R.string.scoutars))) {
            tuskadonLayout.setVisibility(View.GONE);
            starlingsLayout.setVisibility(View.GONE);
            cosmosaurusLayout.setVisibility(View.GONE);
            scoutarsLayout.setVisibility(View.VISIBLE);
            araklithLayout.setVisibility(View.GONE);
            return;
        }

        if (currentSpecies.equals(getString(R.string.araklith))) {
            tuskadonLayout.setVisibility(View.GONE);
            starlingsLayout.setVisibility(View.GONE);
            cosmosaurusLayout.setVisibility(View.GONE);
            scoutarsLayout.setVisibility(View.GONE);
            araklithLayout.setVisibility(View.VISIBLE);
            return;
        }

    }
}
