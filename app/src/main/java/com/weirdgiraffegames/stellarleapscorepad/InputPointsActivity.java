package com.weirdgiraffegames.stellarleapscorepad;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract;
import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract.GameLogEntry;
import com.weirdgiraffegames.stellarleapscorepad.data.GameLogDbHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InputPointsActivity extends AppCompatActivity {

    private Context context;
    private ArrayList<String> selectedSpecies;
    private int numSelectedSpecies = -1;
    private int layoutIndex = 0;
    private String gameId;
    private SQLiteDatabase mDb;

    private List<EditText> currentEditTexts = null;
    private List<String> currentInputPointsColumns = null;
    private String currentTotalPointsColumn = "";

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
        context = this;
        selectedSpecies = getIntent().getExtras().getStringArrayList(getString(R.string.selected_species_key));
        numSelectedSpecies = selectedSpecies.size();
        gameId = getIntent().getExtras().getString(getString(R.string.game_id_key));
        GameLogDbHelper dbHelper = new GameLogDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        setupUI();
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

    private int insertSpeciesPoints() {
    // New value for species columns
        ContentValues values = new ContentValues();
        int total = 0;
        for (int i = 0; i<4; i++) {
            int pointValue = Integer.parseInt(currentEditTexts.get(i).getText().toString());
            total = total + pointValue;
            values.put(currentInputPointsColumns.get(i), pointValue);
        }
        values.put(currentTotalPointsColumn,total);

    // Which row to update, based on the gameId
        String selection = GameLogEntry.COLUMN_GAME_ID + " = ?";
        String[] selectionArgs = {gameId};

        int count = mDb.update(
                GameLogContract.GameLogEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        return count;

    }

    private void setupUI() {
        ButterKnife.bind(this);
        setupNextButton();
        showLayout();
    }


    private void setupNextButton() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValidInput = validateInput(currentEditTexts);
                if(isValidInput) {
                    layoutIndex++;
                    setNextButtonText();
                    if (layoutIndex == numSelectedSpecies) {
                        insertSpeciesPoints();
                        Intent i = new Intent(InputPointsActivity.this, FinalScoreActivity.class);
                        startActivity(i);
                    } else {
                        insertSpeciesPoints();
                        showLayout();
                    }
                } else {
                    Toast.makeText(context,getString(R.string.validation_toast),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInput(List<EditText> editTexts) {
        for (EditText et: editTexts) {
            if (TextUtils.isEmpty(et.getText().toString())) {
                return false;
            }
        }
        return true;
    }

    private void setNextButtonText(){
        if(layoutIndex == numSelectedSpecies - 1) {
            nextButton.setText(getString(R.string.see_totals));
        } else {
            nextButton.setText(getString(R.string.next));
        }
    }

    public void showLayout() {
        String currentSpecies = selectedSpecies.get(layoutIndex);

        if (currentSpecies.equals(getString(R.string.tuskadon))) {
            currentEditTexts = Arrays.asList(tuskadonMissionPointsET, tuskadonPlayerBoardPointsET, tuskadonTraitPointsET, tuskadonResourcePointsET);
            currentInputPointsColumns = Arrays.asList(GameLogEntry.COLUMN_TUSKADON_MISSION_POINTS,GameLogEntry.COLUMN_TUSKADON_PLAYER_BOARD_POINTS,GameLogEntry.COLUMN_TUSKADON_TRAIT_POINTS,GameLogEntry.COLUMN_TUSKADON_RESOURCE_POINTS);
            currentTotalPointsColumn = GameLogEntry.COLUMN_TUSKADON_TOTAL_POINTS;

            tuskadonLayout.setVisibility(View.VISIBLE);
            starlingsLayout.setVisibility(View.GONE);
            cosmosaurusLayout.setVisibility(View.GONE);
            scoutarsLayout.setVisibility(View.GONE);
            araklithLayout.setVisibility(View.GONE);
            return;
        }

        if (currentSpecies.equals(getString(R.string.starlings))) {
            currentEditTexts = Arrays.asList(starlingsMissionPointsET, starlingsPlayerBoardPointsET, starlingsTraitPointsET, starlingsResourcePointsET);
            currentInputPointsColumns = Arrays.asList(GameLogEntry.COLUMN_STARLING_MISSION_POINTS,GameLogEntry.COLUMN_STARLING_PLAYER_BOARD_POINTS,GameLogEntry.COLUMN_STARLING_TRAIT_POINTS,GameLogEntry.COLUMN_STARLING_RESOURCE_POINTS);
            currentTotalPointsColumn = GameLogEntry.COLUMN_STARLING_TOTAL_POINTS;

            tuskadonLayout.setVisibility(View.GONE);
            starlingsLayout.setVisibility(View.VISIBLE);
            cosmosaurusLayout.setVisibility(View.GONE);
            scoutarsLayout.setVisibility(View.GONE);
            araklithLayout.setVisibility(View.GONE);
            return;
        }

        if (currentSpecies.equals(getString(R.string.cosmosaurus))) {
            currentEditTexts = Arrays.asList(cosmosaurusMissionPointsET, cosmosauruPslayerBoardPointsET, cosmosaurusTraitPointsET, cosmosaurusResourcePointsET);
            currentInputPointsColumns = Arrays.asList(GameLogEntry.COLUMN_COSMOSAURUS_MISSION_POINTS,GameLogEntry.COLUMN_COSMOSAURUS_PLAYER_BOARD_POINTS,GameLogEntry.COLUMN_COSMOSAURUS_TRAIT_POINTS,GameLogEntry.COLUMN_COSMOSAURUS_RESOURCE_POINTS);
            currentTotalPointsColumn = GameLogEntry.COLUMN_COSMOSAURUS_TOTAL_POINTS;

            tuskadonLayout.setVisibility(View.GONE);
            starlingsLayout.setVisibility(View.GONE);
            cosmosaurusLayout.setVisibility(View.VISIBLE);
            scoutarsLayout.setVisibility(View.GONE);
            araklithLayout.setVisibility(View.GONE);
            return;
        }

        if (currentSpecies.equals(getString(R.string.scoutars))) {
            currentEditTexts = Arrays.asList(scoutarsMissionPointsET, scoutarsPlayerBoardPointsET, scoutarsTraitPointsET, scoutarsResourcePointsET);
            currentInputPointsColumns = Arrays.asList(GameLogEntry.COLUMN_SCOUTARS_MISSION_POINTS,GameLogEntry.COLUMN_SCOUTARS_PLAYER_BOARD_POINTS,GameLogEntry.COLUMN_SCOUTARS_TRAIT_POINTS,GameLogEntry.COLUMN_SCOUTARS_RESOURCE_POINTS);
            currentTotalPointsColumn = GameLogEntry.COLUMN_SCOUTARS_TOTAL_POINTS;

            tuskadonLayout.setVisibility(View.GONE);
            starlingsLayout.setVisibility(View.GONE);
            cosmosaurusLayout.setVisibility(View.GONE);
            scoutarsLayout.setVisibility(View.VISIBLE);
            araklithLayout.setVisibility(View.GONE);
            return;
        }

        if (currentSpecies.equals(getString(R.string.araklith))) {
            currentEditTexts = Arrays.asList(araklithMissionPointsET, araklithPlayerBoardPointsET, araklithTraitPointsET, araklithResourcePointsET);
            currentInputPointsColumns = Arrays.asList(GameLogEntry.COLUMN_ARAKLITH_MISSION_POINTS,GameLogEntry.COLUMN_ARAKLITH_PLAYER_BOARD_POINTS,GameLogEntry.COLUMN_ARAKLITH_TRAIT_POINTS,GameLogEntry.COLUMN_ARAKLITH_RESOURCE_POINTS);
            currentTotalPointsColumn = GameLogEntry.COLUMN_ARAKLITH_TOTAL_POINTS;

            tuskadonLayout.setVisibility(View.GONE);
            starlingsLayout.setVisibility(View.GONE);
            cosmosaurusLayout.setVisibility(View.GONE);
            scoutarsLayout.setVisibility(View.GONE);
            araklithLayout.setVisibility(View.VISIBLE);
            return;
        }

    }
}
