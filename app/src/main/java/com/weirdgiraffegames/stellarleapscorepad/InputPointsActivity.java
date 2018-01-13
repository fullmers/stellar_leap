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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract.GameLogEntry;
import com.weirdgiraffegames.stellarleapscorepad.util.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InputPointsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {
    private Context context;
    private ArrayList<String> selectedSpecies;
    private int numSelectedSpecies = -1;
    private int layoutIndex = 0;
    private Uri mUri;
    private boolean comesFromFinalScoreActivity = false;
    private boolean wasBackPressed = false;
    private String currentSpecies;

    private static final int INPUT_POINTS_LOADER_ID = 2;

    //private List<String> currentInputPointsColumns = null;
    private String[] currentInputPointsColumns;
    private String currentTotalPointsColumn = "";
    private EditText[] pointsEditTextViews;

    @BindView(R.id.next_btn) Button nextButton;
    @BindView(R.id.selected_species_icon) ImageView speciesIcon;
    @BindView(R.id.selected_species_header_tv) TextView speciesHeaderTV;
    @BindView(R.id.mission_points_et) EditText missionPointsET;
    @BindView(R.id.player_board_points_et) EditText playerBoardPointsET;
    @BindView(R.id.trait_points_et) EditText traitPointsET;
    @BindView(R.id.resource_points_et) EditText resourcePointsET;
    @BindView(R.id.trait_spinner) Spinner traitSpinner;
    @BindView(R.id.trait_points_instructions_tv) TextView traitInstructionsTV;
    @BindView(R.id.resource_points_instructions_tv) TextView resourceInstructionsTV;

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
        }
        mUri = getIntent().getData();
        setupUI();

        Bundle loaderExtras = new Bundle();
        loaderExtras.putString(getString(R.string.selected_species_key),selectedSpecies.get(layoutIndex));
        getSupportLoaderManager().initLoader(INPUT_POINTS_LOADER_ID, loaderExtras, this);
    }

    @Override
    public void onBackPressed() {
        wasBackPressed = true;
        if (comesFromFinalScoreActivity) {
            Intent i = new Intent(InputPointsActivity.this, FinalScoreActivity.class);
            i.setData(mUri);
            i.putExtra(getString(R.string.comes_from_game_log_activity_key), false);
            startActivity(i);
        } else {
            if (layoutIndex > 0) {
                layoutIndex--;
                setupCurrentSpecies();
                Bundle loaderExtras = new Bundle();
                loaderExtras.putString(getString(R.string.selected_species_key),currentSpecies);
                getSupportLoaderManager().restartLoader(INPUT_POINTS_LOADER_ID, loaderExtras, this);
            } else {
                Intent i = new Intent(InputPointsActivity.this, ChooseRacesActivity.class);
                startActivity(i);
            }
        }
    }

    private void setupUI() {
        ButterKnife.bind(this);
        pointsEditTextViews = new EditText[]{missionPointsET, playerBoardPointsET, traitPointsET, resourcePointsET};
        setupCurrentSpecies();
        setupNextButton();
        setupSpinner();
    }

    private void setupNextButton() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValidInput = isValidInput();
                if (isValidInput) {
                    layoutIndex++;
                    if ((layoutIndex == numSelectedSpecies) || comesFromFinalScoreActivity) {
                        updateSpeciesPoints();
                        Intent i = new Intent(InputPointsActivity.this, FinalScoreActivity.class);
                        i.setData(mUri);
                        i.putExtra(getString(R.string.comes_from_game_log_activity_key), false);
                        startActivity(i);
                    } else {
                        updateSpeciesPoints();
                        clearInputs();
                        setupCurrentSpecies();
                        //TODO check if species already has points in it and load if needed
                        //ie, user entered some values, then pressed back and next again
                    }
                } else {
                    Toast.makeText(context, getString(R.string.validation_toast), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidInput() {
        for (EditText et : pointsEditTextViews) {
            if (TextUtils.isEmpty(et.getText().toString())) {
                return false;
            }
        }
        return true;
    }

    private void clearInputs() {
        for (EditText et : pointsEditTextViews) {
            et.getText().clear();
        }
        traitSpinner.setSelection(0);
    }

    private void setupSpinner() {
        traitSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this, R.array.traits, R.layout.selected_trait);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        traitSpinner.setAdapter(adapter);
    }

    private void setupCurrentSpecies() {
        currentSpecies = selectedSpecies.get(layoutIndex);
        if (currentSpecies.equals(getString(R.string.tuskadon))) {
            speciesIcon.setBackground(getResources().getDrawable(R.drawable.tuskadon_icon));
            speciesHeaderTV.setText(getString(R.string.tuskadon_points));
            speciesHeaderTV.setTextColor(getResources().getColor(R.color.tuskadon));
            currentInputPointsColumns = Constants.Projections.tuskadonColumns;
            currentTotalPointsColumn = GameLogEntry.COLUMN_TUSKADON_TOTAL_POINTS;
            return;
        }

        if (currentSpecies.equals(getString(R.string.starlings))) {
            speciesIcon.setBackground(getResources().getDrawable(R.drawable.starlings_icon));
            speciesHeaderTV.setText(getString(R.string.starling_points));
            speciesHeaderTV.setTextColor(getResources().getColor(R.color.starlings));
            currentInputPointsColumns = Constants.Projections.starlingColumns;
            currentTotalPointsColumn = GameLogEntry.COLUMN_STARLING_TOTAL_POINTS;
            return;
        }

        if (currentSpecies.equals(getString(R.string.cosmosaurus))) {
            speciesIcon.setBackground(getResources().getDrawable(R.drawable.cosmosaurus_icon));
            speciesHeaderTV.setText(getString(R.string.cosmosaurus_points));
            speciesHeaderTV.setTextColor(getResources().getColor(R.color.cosmosaurus));
            currentInputPointsColumns = Constants.Projections.cosmosaurusColumns;
            currentTotalPointsColumn = GameLogEntry.COLUMN_COSMOSAURUS_TOTAL_POINTS;
            return;
        }

        if (currentSpecies.equals(getString(R.string.scoutars))) {
            speciesIcon.setBackground(getResources().getDrawable(R.drawable.scoutar_icon));
            speciesHeaderTV.setText(getString(R.string.scoutars_points));
            speciesHeaderTV.setTextColor(getResources().getColor(R.color.scoutars));
            currentInputPointsColumns = Constants.Projections.scoutarColumns;
            currentTotalPointsColumn = GameLogEntry.COLUMN_SCOUTARS_TOTAL_POINTS;
        }

        if (currentSpecies.equals(getString(R.string.araklith))) {
            speciesIcon.setBackground(getResources().getDrawable(R.drawable.araklith_icon));
            speciesHeaderTV.setText(getString(R.string.araklith_points));
            speciesHeaderTV.setTextColor(getResources().getColor(R.color.araklith));
            currentInputPointsColumns = Constants.Projections.araklithColumns;
            currentTotalPointsColumn = GameLogEntry.COLUMN_ARAKLITH_TOTAL_POINTS;
            return;
        }
    }

    private int updateSpeciesPoints() {
        // New value for species columns
        ContentValues values = new ContentValues();
        int total = 0;
        for (int i = 0; i < 4; i++) {
            int pointValue = Integer.parseInt(pointsEditTextViews[i].getText().toString());
            total = total + pointValue;
            values.put(currentInputPointsColumns[i], pointValue);
        }
        values.put(currentTotalPointsColumn, total);

        return getContentResolver().update(mUri, values, null, null);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        String[] traitInstructionsArray = getResources().getStringArray(R.array.trait_instructions);
        String[] resourceInstructionsArray = getResources().getStringArray(R.array.resource_instructions);
        traitInstructionsTV.setText(traitInstructionsArray[pos]);
        resourceInstructionsTV.setText(resourceInstructionsArray[pos]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            switch (id) {
                case INPUT_POINTS_LOADER_ID:
                    return new CursorLoader(this,
                            mUri,
                            currentInputPointsColumns,
                            null,
                            null,
                            null);
                default:
                    throw new RuntimeException("Loader Not Implemented: " + id);
            }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //load values into edit texts if the user came to the view by pressing back or
        //navigating from the FinalScoreActivity, to edit the values
        if (comesFromFinalScoreActivity || wasBackPressed) {
            if (cursor != null && cursor.moveToFirst()) {
                int missionPoints = cursor.getInt(Constants.Projections.MISSION_POINTS_INDEX);
                pointsEditTextViews[Constants.Projections.MISSION_POINTS_INDEX].setText(String.valueOf(missionPoints));

                int playerBoardPoints = cursor.getInt(Constants.Projections.PLAYER_BOARD_POINTS_INDEX);
                pointsEditTextViews[Constants.Projections.PLAYER_BOARD_POINTS_INDEX].setText(String.valueOf(playerBoardPoints));

                int traitPoints = cursor.getInt(Constants.Projections.TRAIT_POINTS_INDEX);
                pointsEditTextViews[Constants.Projections.TRAIT_POINTS_INDEX].setText(String.valueOf(traitPoints));

                int resourcesPoints = cursor.getInt(Constants.Projections.RESOURCES_POINTS_INDEX);
                pointsEditTextViews[Constants.Projections.RESOURCES_POINTS_INDEX].setText(String.valueOf(resourcesPoints));
            }
        }
        wasBackPressed = false;
        cursor.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}