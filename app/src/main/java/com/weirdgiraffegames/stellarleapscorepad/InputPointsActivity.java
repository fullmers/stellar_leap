package com.weirdgiraffegames.stellarleapscorepad;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract.GameLogEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InputPointsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor>{

    private static final int INPUT_POINTS_LOADER_ID = 2;
    private static final int MISSION_POINTS_INDEX = 0;
    private static final int PLAYER_BOARD_POINTS_INDEX = 1;
    private static final int TRAIT_POINTS_INDEX = 2;
    private static final int RESOURCES_POINTS_INDEX = 3;

    private static final int TUSKADON_INDEX = 0;
    private static final int STARLING_INDEX = 1;
    private static final int COSMOSAURUS_INDEX = 2;
    private static final int SCOUTAR_INDEX = 3;
    private static final int ARAKLITH_INDEX = 4;

    private Context context;
    private ArrayList<String> selectedSpecies;
    private int numSelectedSpecies = -1;
    private int layoutIndex = 0;
    private long gameId;
    private Uri mUri;
    public boolean comesFromFinalScoreActivity = false;

    private List<EditText> currentEditTexts = null;
    private List<String> currentInputPointsColumns = null;
    private String currentTotalPointsColumn = "";

    @BindView(R.id.next_btn) Button nextButton;

    @BindView(R.id.tuskadon_layout) View tuskadonLayout;
    @BindView(R.id.starlings_layout) View starlingsLayout;
    @BindView(R.id.cosmosaurus_layout) View cosmosaurusLayout;
    @BindView(R.id.scoutars_layout)  View scoutarsLayout;
    @BindView(R.id.araklith_layout) View araklithLayout;

    @BindView(R.id.tuskadon_trait_spinner) Spinner tuskadonSpinner;
    @BindView(R.id.starlings_trait_spinner) Spinner starlingSpinner;
    @BindView(R.id.cosmosaurus_trait_spinner) Spinner cosmosaurusSpinner;
    @BindView(R.id.scoutars_trait_spinner) Spinner scoutarsSpinner;
    @BindView(R.id.araklith_trait_spinner) Spinner araklithSpinner;

    @BindView(R.id.tuskadon_trait_points_instructions_tv) TextView tuskadonTraitInstructionsTV;
    @BindView(R.id.tuskadon_resource_points_instructions_tv) TextView tuskadonResourceInstructionsTV;
    @BindView(R.id.starlings_trait_points_instructions_tv) TextView starlingsTraitInstructionsTV;
    @BindView(R.id.starlings_resource_points_instructions_tv) TextView starlingsResourceInstructionsTV;
    @BindView(R.id.cosmosaurus_trait_points_instructions_tv) TextView cosmosaurusTraitInstructionsTV;
    @BindView(R.id.cosmosaurus_resource_points_instructions_tv) TextView cosmosaurusResourceInstructionsTV;
    @BindView(R.id.scoutars_trait_points_instructions_tv) TextView scoutarsTraitInstructionsTV;
    @BindView(R.id.scoutars_resource_points_instructions_tv) TextView scoutarsResourceInstructionsTV;
    @BindView(R.id.araklith_trait_points_instructions_tv) TextView araklithTraitInstructionsTV;
    @BindView(R.id.araklith_resource_points_instructions_tv) TextView araklithResourceInstructionsTV;

    @BindView(R.id.tuskadon_mission_points_et) EditText tuskadonMissionPointsET;
    @BindView(R.id.tuskadon_player_board_points_et) EditText tuskadonPlayerBoardPointsET;
    @BindView(R.id.tuskadon_trait_points_et) EditText tuskadonTraitPointsET;
    @BindView(R.id.tuskadon_resource_points_et) EditText tuskadonResourcePointsET;

    @BindView(R.id.starlings_mission_points_et) EditText starlingsMissionPointsET;
    @BindView(R.id.starlings_player_board_points_et) EditText starlingsPlayerBoardPointsET;
    @BindView(R.id.starlings_trait_points_et) EditText starlingsTraitPointsET;
    @BindView(R.id.starlings_resource_points_et) EditText starlingsResourcePointsET;

    @BindView(R.id.cosmosaurus_mission_points_et) EditText cosmosaurusMissionPointsET;
    @BindView(R.id.cosmosaurus_player_board_points_et) EditText cosmosaurusPlayerBoardPointsET;
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
        comesFromFinalScoreActivity = getIntent().getExtras().getBoolean(getString(R.string.comes_from_final_score_activity_key));
        if (comesFromFinalScoreActivity) {
            layoutIndex = getIntent().getExtras().getInt(getString(R.string.layout_index_key));
            Log.d("comes","from FinalScoreActivity");
        } else {
            Log.d("comes","from ChooseRacesActivity");
        }

        mUri = getIntent().getData();
        Log.d("InputPointsActivity",mUri.toString());
        gameId = Long.valueOf(mUri.getPathSegments().get(1));
        Log.d("InputPointsActivity","gameId: " + gameId);
        setupUI();
        getSupportLoaderManager().initLoader(INPUT_POINTS_LOADER_ID, null, this);
    }

    @Override
    public void onBackPressed() {
        if (comesFromFinalScoreActivity) {
            Intent i = new Intent(InputPointsActivity.this, FinalScoreActivity.class);
            i.setData(mUri);
            i.putExtra(getString(R.string.comes_from_game_log_activity_key), false);
            startActivity(i);
        } else {
            if (layoutIndex > 0) {
                layoutIndex--;
                showLayout();
                setNextButtonText();
            } else {
                Intent i = new Intent(InputPointsActivity.this, ChooseRacesActivity.class);
                startActivity(i);
            }
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

        return getContentResolver().update(mUri,values,null,null);
    }

    private void setupUI() {
        ButterKnife.bind(this);
        setupNextButton();
        setupSpinners();
        showLayout();
    }

    private void setupNextButton() {
        setNextButtonText();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValidInput = validateInput(currentEditTexts);
                if(isValidInput) {
                        layoutIndex++;
                        setNextButtonText();
                        if ((layoutIndex == numSelectedSpecies) || comesFromFinalScoreActivity ) {
                            insertSpeciesPoints();
                            Intent i = new Intent(InputPointsActivity.this, FinalScoreActivity.class);
                            i.setData(mUri);
                            i.putExtra(getString(R.string.comes_from_game_log_activity_key), false);
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
        Log.d("setting","next button text as");
        if (comesFromFinalScoreActivity) {
            Log.d("text",getString(R.string.done));
            nextButton.setText(getString(R.string.done));
        } else {
            if (layoutIndex == numSelectedSpecies - 1) {
                nextButton.setText(getString(R.string.see_totals));
                Log.d("text",getString(R.string.see_totals));
            } else {
                nextButton.setText(getString(R.string.next));
                Log.d("text",getString(R.string.next));
            }
        }
    }

    private void setupSpinners() {;
        tuskadonSpinner.setOnItemSelectedListener(this);
        starlingSpinner.setOnItemSelectedListener(this);
        cosmosaurusSpinner.setOnItemSelectedListener(this);
        scoutarsSpinner.setOnItemSelectedListener(this);
        araklithSpinner.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this,R.array.traits, R.layout.selected_trait);
        adapter.setDropDownViewResource(R.layout.spinner_item);

        tuskadonSpinner.setAdapter(adapter);
        starlingSpinner.setAdapter(adapter);
        cosmosaurusSpinner.setAdapter(adapter);
        scoutarsSpinner.setAdapter(adapter);
        araklithSpinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String[] traitInstructionsArray = getResources().getStringArray(R.array.trait_instructions);
        String[] resourceInstructionsArray = getResources().getStringArray(R.array.resource_instructions);

        switch (parent.getId()) {
            case R.id.tuskadon_trait_spinner:
                tuskadonTraitInstructionsTV.setText(traitInstructionsArray[pos]);
                tuskadonResourceInstructionsTV.setText(resourceInstructionsArray[pos]);
                break;
            case R.id.starlings_trait_spinner:
                starlingsTraitInstructionsTV.setText(traitInstructionsArray[pos]);
                starlingsResourceInstructionsTV.setText(resourceInstructionsArray[pos]);
                break;
            case R.id.cosmosaurus_trait_spinner:
                cosmosaurusTraitInstructionsTV.setText(traitInstructionsArray[pos]);
                cosmosaurusResourceInstructionsTV.setText(resourceInstructionsArray[pos]);
                break;
            case R.id.scoutars_trait_spinner:
                scoutarsTraitInstructionsTV.setText(traitInstructionsArray[pos]);
                scoutarsResourceInstructionsTV.setText(resourceInstructionsArray[pos]);
                break;
            case R.id.araklith_trait_spinner:
                araklithTraitInstructionsTV.setText(traitInstructionsArray[pos]);
                araklithResourceInstructionsTV.setText(resourceInstructionsArray[pos]);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void showLayout() {
        Log.d("layout Index", " "+ layoutIndex);
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
            currentEditTexts = Arrays.asList(cosmosaurusMissionPointsET, cosmosaurusPlayerBoardPointsET, cosmosaurusTraitPointsET, cosmosaurusResourcePointsET);
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


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (comesFromFinalScoreActivity) {
            switch(id) {
                case INPUT_POINTS_LOADER_ID:
                    return new CursorLoader(this,
                            mUri,
                            null,
                            null,
                            null,
                            null);
                default:
                    throw new RuntimeException("Loader Not Implemented: " + id);
            }
        } else {
            return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor != null && cursor.moveToFirst()) {
            String[] species = {
                    getString(R.string.tuskadon),
                    getString(R.string.starlings),
                    getString(R.string.cosmosaurus),
                    getString(R.string.scoutars),
                    getString(R.string.araklith)
            };

            EditText[][] pointsEditTextViews = {
                    {tuskadonMissionPointsET,tuskadonPlayerBoardPointsET,tuskadonTraitPointsET,tuskadonResourcePointsET},
                    {starlingsMissionPointsET,starlingsPlayerBoardPointsET,starlingsTraitPointsET,starlingsResourcePointsET},
                    {cosmosaurusMissionPointsET,cosmosaurusPlayerBoardPointsET,cosmosaurusTraitPointsET,cosmosaurusResourcePointsET},
                    {scoutarsMissionPointsET,scoutarsPlayerBoardPointsET,scoutarsTraitPointsET,scoutarsResourcePointsET},
                    {araklithMissionPointsET,araklithPlayerBoardPointsET,araklithTraitPointsET,araklithResourcePointsET}
            };

            int[][] columnIndices = {
                    {cursor.getColumnIndex(GameLogEntry.COLUMN_TUSKADON_MISSION_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_TUSKADON_PLAYER_BOARD_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_TUSKADON_TRAIT_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_TUSKADON_RESOURCE_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_TUSKADON_TOTAL_POINTS)},
                    {cursor.getColumnIndex(GameLogEntry.COLUMN_STARLING_MISSION_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_STARLING_PLAYER_BOARD_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_STARLING_TRAIT_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_STARLING_RESOURCE_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_STARLING_TOTAL_POINTS)},
                    {cursor.getColumnIndex(GameLogEntry.COLUMN_COSMOSAURUS_MISSION_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_COSMOSAURUS_PLAYER_BOARD_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_COSMOSAURUS_TRAIT_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_COSMOSAURUS_RESOURCE_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_COSMOSAURUS_TOTAL_POINTS)},
                    {cursor.getColumnIndex(GameLogEntry.COLUMN_SCOUTARS_MISSION_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_SCOUTARS_PLAYER_BOARD_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_SCOUTARS_TRAIT_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_SCOUTARS_RESOURCE_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_SCOUTARS_TOTAL_POINTS)},
                    {cursor.getColumnIndex(GameLogEntry.COLUMN_ARAKLITH_MISSION_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_ARAKLITH_PLAYER_BOARD_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_ARAKLITH_TRAIT_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_ARAKLITH_RESOURCE_POINTS),
                            cursor.getColumnIndex(GameLogEntry.COLUMN_ARAKLITH_TOTAL_POINTS)}
            };

            String currentSpecies = selectedSpecies.get(layoutIndex);
            for (int i =0; i<=4;i++) {
                if (currentSpecies.equals(species[i])) {
                    EditText[] mEditTextViews = pointsEditTextViews[i];
                    int missionPoints = cursor.getInt(columnIndices[i][MISSION_POINTS_INDEX]);
                    mEditTextViews[MISSION_POINTS_INDEX].setText(String.valueOf(missionPoints));
                    int playerBoardPoints = cursor.getInt(columnIndices[i][PLAYER_BOARD_POINTS_INDEX]);
                    mEditTextViews[PLAYER_BOARD_POINTS_INDEX].setText(String.valueOf(playerBoardPoints));
                    int traitPoints = cursor.getInt(columnIndices[i][TRAIT_POINTS_INDEX]);
                    mEditTextViews[TRAIT_POINTS_INDEX].setText(String.valueOf(traitPoints));
                    int resourcesPoints = cursor.getInt(columnIndices[i][RESOURCES_POINTS_INDEX]);
                    mEditTextViews[RESOURCES_POINTS_INDEX].setText(String.valueOf(resourcesPoints));
                }
            }

            cursor.close();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}
}
