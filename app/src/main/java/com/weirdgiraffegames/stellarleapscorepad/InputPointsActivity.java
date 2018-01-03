package com.weirdgiraffegames.stellarleapscorepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InputPointsActivity extends AppCompatActivity {

    private ArrayList<String> selectedSpecies;
    private int numSelectedSpecies = -1;
    private int layoutIndex = 0;

    @BindView(R.id.next_btn) Button nextButton;

    @BindView(R.id.tuskadon_layout) View tuskadonLayout;
    @BindView(R.id.starlings_layout) View starlingsLayout;
    @BindView(R.id.cosmosaurus_layout) View cosmosaurusLayout;
    @BindView(R.id.scoutars_layout)  View scoutarsLayout;
    @BindView(R.id.araklith_layout) View araklithLayout;

    @BindView(R.id.tuskadon_mission_points_et) EditText tuskadonMissionPointsET;
    @BindView(R.id.tuskadon_player_board_points_et) EditText tuskadonPlayerBoardPointsET;
    @BindView(R.id.tuskadon_trait_points_et) EditText tuskadonTraitPointsET;
    @BindView(R.id.tuskadon_resource_points_et) EditText tuskadonResourcePointsET;

    @BindView(R.id.starlings_mission_points_et) EditText starlingsMissionPointsET;
    @BindView(R.id.starlings_player_board_points_et) EditText starlingsPlayerBoardPointsET;
    @BindView(R.id.starlings_trait_points_et) EditText starlingsTraitPointsET;
    @BindView(R.id.starlings_resource_points_et) EditText starlingsResourcePointsET;

    @BindView(R.id.cosmosaurus_mission_points_et) EditText cosmosaurusMissionPointsET;
    @BindView(R.id.cosmosaurus_player_board_points_et) EditText cosmosauruPslayerBoardPointsET;
    @BindView(R.id.cosmosaurus_trait_points_et) EditText cosmosaurusTraitPointsET;
    @BindView(R.id.cosmosaurus_resource_points_et) EditText cosmosaurusResourcePointsET;

    @BindView(R.id.scoutars_mission_points_et) EditText scoutarsMissionPointsET;
    @BindView(R.id.scoutars_player_board_points_et) EditText scoutarsPlayerBoardPointsET;
    @BindView(R.id.scoutars_trait_points_et) EditText scoutarsTraitPointsET;
    @BindView(R.id.scoutars_resource_points_et) EditText scoutarsResourcePointsET;

    @BindView(R.id.araklith_mission_points_et) EditText araklithMissionPointsET;
    @BindView(R.id.araklith_player_board_points_et) EditText araklithPlayerBoardPointsET;
    @BindView(R.id.araklith_trait_points_et) EditText araklithTraitPointsET;
    @BindView(R.id.araklith_resource_points_et) EditText araklithResourcePointsET;
    
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
        ButterKnife.bind(this);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutIndex++;
                setNextButtonText();
                if (layoutIndex == numSelectedSpecies) {
                    Intent i = new Intent(InputPointsActivity.this,FinalScoreActivity.class);
                    startActivity(i);
                } else {
                    showLayout();
                }
            }
        });
    }

    private void setNextButtonText(){
        if(layoutIndex == numSelectedSpecies - 1) {
            nextButton.setText(getString(R.string.see_totals));
        } else {
            nextButton.setText(getString(R.string.next));
        }
    }

    @Override
    public void onBackPressed() {
        if (layoutIndex > 0){
            layoutIndex--;
            showLayout();
            setNextButtonText();
        } else {
            Intent i = new Intent(InputPointsActivity.this,ChooseRacesActivity.class);
            startActivity(i);
        }
    }

    public void showLayout() {
        String currentSpecies = selectedSpecies.get(layoutIndex);

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
