package com.weirdgiraffegames.stellarleapscorepad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

public class InputPointsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_points);

        ArrayList<String> selectedSpecies = getIntent().getExtras().getStringArrayList(getString(R.string.selected_species_key));

        for(String s:selectedSpecies) {
            Log.d("selected species",s);
        }
    }
}
