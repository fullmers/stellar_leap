package com.weirdgiraffegames.stellarleapscorepad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

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

    boolean isOneRaceSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_races);
        setupUI();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //at least one race must be selected to continue
                if (isOneRaceSelected) {
                    Log.d("button click","move to next activity");
                }
            }
        });
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

            if (isTuskadonSelected || isStarlingsSelected || isCosmosaurusSelected || isScoutarsSelected || isAraklithSelected) {
                isOneRaceSelected = true;
                btn_next.setBackgroundColor(getResources().getColor(R.color.activeButton));
                btn_next.setEnabled(true);
            } else {
                isOneRaceSelected = false;
                btn_next.setBackgroundColor(getResources().getColor(R.color.inactiveButton));
                btn_next.setEnabled(false);
            }
        }
    }
}
