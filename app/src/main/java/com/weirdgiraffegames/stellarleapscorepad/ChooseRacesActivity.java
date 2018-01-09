package com.weirdgiraffegames.stellarleapscorepad;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract;

import java.util.ArrayList;

public class ChooseRacesActivity extends AppCompatActivity {

    CheckBox chkbx_tuskadon;
    CheckBox chkbx_starlings;
    CheckBox chkbx_cosmosaurus;
    CheckBox chkbx_scoutars;
    CheckBox chkbx_araklith;
    Button btn_next;

    boolean isTuskadonSelected;
    boolean isStarlingsSelected;
    boolean isCosmosaurusSelected;
    boolean isScoutarsSelected;
    boolean isAraklithSelected;

    int numSelected = 0;

    ArrayList<String> selectedSpecies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_races);
        setupUI();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //at least one race must be selected to continue
                if (numSelected >= 1) {
                    defineSelectedSpecies();
                    Intent i = new Intent(view.getContext(),InputPointsActivity.class);
                    i.putExtra(getString(R.string.selected_species_key),selectedSpecies);
                    Uri uri= initializeGameEntry();
                    i.setData(uri);
                    startActivity(i);
                }
            }
        });
    }

    private Uri initializeGameEntry() {
        ContentValues cv = new ContentValues();
        cv.put(GameLogContract.GameLogEntry.COLUMN_WINNER, -1);
        return getContentResolver().insert(GameLogContract.GameLogEntry.CONTENT_URI,cv);
    }

    private void defineSelectedSpecies() {
        selectedSpecies = new ArrayList<>();
        if (isTuskadonSelected) {
            selectedSpecies.add(getString(R.string.tuskadon));
        }

        if (isStarlingsSelected) {
            selectedSpecies.add(getString(R.string.starlings));
        }

        if (isCosmosaurusSelected) {
            selectedSpecies.add(getString(R.string.cosmosaurus));
        }

        if (isScoutarsSelected) {
            selectedSpecies.add(getString(R.string.scoutars));
        }

        if (isAraklithSelected) {
            selectedSpecies.add(getString(R.string.araklith));
        }
    }

    private void setupUI() {
        chkbx_tuskadon = findViewById(R.id.tuskadon_chkbx);
        chkbx_starlings = findViewById(R.id.starling_chkbx);
        chkbx_cosmosaurus = findViewById(R.id.cosmosaurus_chkbx);
        chkbx_scoutars = findViewById(R.id.scoutars_chkbx);
        chkbx_araklith = findViewById(R.id.araklith_chkbx);

        chkbx_tuskadon.setOnCheckedChangeListener(new RaceCheckedChangeListener());
        chkbx_starlings.setOnCheckedChangeListener(new RaceCheckedChangeListener());
        chkbx_cosmosaurus.setOnCheckedChangeListener(new RaceCheckedChangeListener());
        chkbx_scoutars.setOnCheckedChangeListener(new RaceCheckedChangeListener());
        chkbx_araklith.setOnCheckedChangeListener(new RaceCheckedChangeListener());

        btn_next = findViewById(R.id.next_btn);
    }

    private class RaceCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            isTuskadonSelected = chkbx_tuskadon.isChecked();
            isStarlingsSelected = chkbx_starlings.isChecked();
            isCosmosaurusSelected = chkbx_cosmosaurus.isChecked();
            isScoutarsSelected = chkbx_scoutars.isChecked();
            isAraklithSelected = chkbx_araklith.isChecked();

            boolean[] selected = new boolean[5];
            selected[0] = isTuskadonSelected;
            selected[1] = isStarlingsSelected;
            selected[2] = isCosmosaurusSelected;
            selected[3] = isScoutarsSelected;
            selected[4] = isAraklithSelected;

            int temp = 0;
            for (int i = 0; i<5;i++) {
                if (selected[i]) {
                    temp+=1;
                }
            }
            numSelected = temp;

            if (numSelected >= 1) {
                btn_next.setBackgroundColor(getResources().getColor(R.color.activeButton));
                btn_next.setEnabled(true);
            } else {
                btn_next.setBackgroundColor(getResources().getColor(R.color.inactiveButton));
                btn_next.setEnabled(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ChooseRacesActivity.this,WelcomeScreenActivity.class);
        startActivity(i);
    }
}
