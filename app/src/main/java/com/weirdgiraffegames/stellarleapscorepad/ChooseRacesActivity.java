package com.weirdgiraffegames.stellarleapscorepad;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseRacesActivity extends AppCompatActivity {

    @BindView(R.id.tuskadon_chkbx) CheckBox chkbx_tuskadon;
    @BindView(R.id.starling_chkbx) CheckBox chkbx_starlings;
    @BindView(R.id.cosmosaurus_chkbx) CheckBox chkbx_cosmosaurus;
    @BindView(R.id.scoutars_chkbx) CheckBox chkbx_scoutars;
    @BindView(R.id.araklith_chkbx) CheckBox chkbx_araklith;
    @BindView(R.id.next_btn) Button btn_next;

    private boolean isTuskadonSelected;
    private boolean isStarlingsSelected;
    private boolean isCosmosaurusSelected;
    private boolean isScoutarsSelected;
    private boolean isAraklithSelected;

    private int numSelected = 0;

    private ArrayList<String> selectedSpecies;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_races);
        ButterKnife.bind(this);
        context = this;
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
                } else {
                    Toast.makeText(context,getString(R.string.must_select_one),Toast.LENGTH_SHORT).show();
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
        chkbx_tuskadon.setOnCheckedChangeListener(new RaceCheckedChangeListener());
        chkbx_starlings.setOnCheckedChangeListener(new RaceCheckedChangeListener());
        chkbx_cosmosaurus.setOnCheckedChangeListener(new RaceCheckedChangeListener());
        chkbx_scoutars.setOnCheckedChangeListener(new RaceCheckedChangeListener());
        chkbx_araklith.setOnCheckedChangeListener(new RaceCheckedChangeListener());

        Typeface prototype = Typeface.createFromAsset(getAssets(),"Prototype.ttf");
        chkbx_tuskadon.setTypeface(prototype);
        chkbx_starlings.setTypeface(prototype);
        chkbx_cosmosaurus.setTypeface(prototype);
        chkbx_scoutars.setTypeface(prototype);
        chkbx_araklith.setTypeface(prototype);
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
                //btn_next.setBackground(getResources().getDrawable(R.drawable.button_background_drawable));
                btn_next.setEnabled(true);
            } else {
                //btn_next.setBackground(getResources().getDrawable(R.drawable.disabled_button_background));
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
