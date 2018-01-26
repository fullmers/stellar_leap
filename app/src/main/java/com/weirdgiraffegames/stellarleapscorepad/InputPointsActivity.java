package com.weirdgiraffegames.stellarleapscorepad;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static com.weirdgiraffegames.stellarleapscorepad.util.Constants.Projections.RESOURCES_POINTS_INDEX;

public class InputPointsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, LoaderManager.LoaderCallbacks<Cursor> {
    private Context context;
    private ArrayList<String> selectedSpecies;
    private int numSelectedSpecies = -1;
    private int layoutIndex = 0;
    private Uri mUri;
    private boolean comesFromFinalScoreActivity = false;
    private String currentSpecies;

    private static final int INPUT_POINTS_LOADER_ID = 2;

    private String[] currentInputPointsColumns;
    private String currentTotalPointsColumn = "";
    private String currentTraitSpinnerIndexColumn = "";
    private EditText[] allEditTextViews;
    private int resourceDivider = 3;

    @BindView(R.id.next_btn) Button nextButton;
    @BindView(R.id.selected_species_icon) ImageView speciesIcon;
    @BindView(R.id.selected_species_header_tv) TextView speciesHeaderTV;
    @BindView(R.id.mission_points_et) EditText missionPointsET;
    @BindView(R.id.player_board_points_et) EditText playerBoardPointsET;
    @BindView(R.id.trait_points_et) EditText traitPointsET;
    @BindView(R.id.resource_points_et) EditText numResourcesET;
    @BindView(R.id.resource_points_tv) TextView resourcePointsTV;
    @BindView(R.id.trait_spinner) Spinner traitSpinner;
    @BindView(R.id.trait_points_instructions_tv) TextView traitInstructionsTV;
    @BindView(R.id.resource_points_instructions_tv) TextView resourceInstructionsTV;
    @Nullable @BindView(R.id.landscape_screen_divider) View landscapeDivider;

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
                showBackConfirmationDialog();
            }
        }
    }

    private void showBackConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.continue_back_dialog_msg));
        builder.setPositiveButton(R.string.continue_back, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getContentResolver().delete(mUri,null,null);
                Intent i = new Intent(InputPointsActivity.this,ChooseRacesActivity.class);
                startActivity(i);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setupUI() {
        ButterKnife.bind(this);
        numResourcesET.addTextChangedListener(resourcesWatcher);
        allEditTextViews = new EditText[]{missionPointsET, playerBoardPointsET, traitPointsET, numResourcesET};
        setupCurrentSpecies();
        setupNextButton();
        setupSpinner();
    }

    private final TextWatcher resourcesWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                resourcePointsTV.setText("0");
            } else{
                int numResources = Integer.valueOf(numResourcesET.getText().toString());
                int resourcePoints=numResources / resourceDivider; //always round down
                resourcePointsTV.setText(String.valueOf(resourcePoints));
            }
        }
    };

    private void setupNextButton() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValidInput = isValidInput();
                if (isValidInput) {
                    layoutIndex++;
                    if ((layoutIndex == numSelectedSpecies) || comesFromFinalScoreActivity) {
                        updateCurrentSpeciesColumns();
                        Intent i = new Intent(InputPointsActivity.this, FinalScoreActivity.class);
                        i.setData(mUri);
                        i.putExtra(getString(R.string.comes_from_game_log_activity_key), false);
                        startActivity(i);
                    } else {
                        updateCurrentSpeciesColumns();
                        clearInputs();
                        setupCurrentSpecies();

                        Bundle loaderExtras = new Bundle();
                        loaderExtras.putString(getString(R.string.selected_species_key),currentSpecies);
                        getSupportLoaderManager().restartLoader(INPUT_POINTS_LOADER_ID, loaderExtras,(LoaderManager.LoaderCallbacks<Cursor>) context);
                    }
                } else {
                    Toast.makeText(context, getString(R.string.validation_toast), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidInput() {
        for (EditText et : allEditTextViews) {
            if (TextUtils.isEmpty(et.getText().toString())) {
                return false;
            }
        }
        return true;
    }

    private void clearInputs() {
        for (EditText et : allEditTextViews) {
            et.getText().clear();
        }
        traitSpinner.setSelection(0);
        resourcePointsTV.setText("");
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
        boolean isLandscape = (getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE);

        if (currentSpecies.equals(getString(R.string.tuskadon))) {
            speciesIcon.setBackground(getResources().getDrawable(R.drawable.tuskadon_icon));
            speciesHeaderTV.setText(getString(R.string.tuskadon_points));
            speciesHeaderTV.setTextColor(getResources().getColor(R.color.tuskadon));
            currentInputPointsColumns = Constants.Projections.tuskadonColumns;
            currentTotalPointsColumn = GameLogEntry.COLUMN_TUSKADON_TOTAL_POINTS;
            currentTraitSpinnerIndexColumn = GameLogEntry.COLUMN_TUSKADON_TRAIT_INDEX;
            if (isLandscape){
                landscapeDivider.setBackground(getResources().getDrawable(R.drawable.tuskadon_line));
            }
            return;
        }

        if (currentSpecies.equals(getString(R.string.starlings))) {
            speciesIcon.setBackground(getResources().getDrawable(R.drawable.starlings_icon));
            speciesHeaderTV.setText(getString(R.string.starling_points));
            speciesHeaderTV.setTextColor(getResources().getColor(R.color.starlings));
            currentInputPointsColumns = Constants.Projections.starlingColumns;
            currentTotalPointsColumn = GameLogEntry.COLUMN_STARLING_TOTAL_POINTS;
            currentTraitSpinnerIndexColumn = GameLogEntry.COLUMN_STARLING_TRAIT_INDEX;
            if (isLandscape){
                landscapeDivider.setBackground(getResources().getDrawable(R.drawable.starlings_line));
            }
            return;
        }

        if (currentSpecies.equals(getString(R.string.cosmosaurus))) {
            speciesIcon.setBackground(getResources().getDrawable(R.drawable.cosmosaurus_icon));
            speciesHeaderTV.setText(getString(R.string.cosmosaurus_points));
            speciesHeaderTV.setTextColor(getResources().getColor(R.color.cosmosaurus));
            currentInputPointsColumns = Constants.Projections.cosmosaurusColumns;
            currentTotalPointsColumn = GameLogEntry.COLUMN_COSMOSAURUS_TOTAL_POINTS;
            currentTraitSpinnerIndexColumn = GameLogEntry.COLUMN_COSMOSAURUS_TRAIT_INDEX;
            if (isLandscape){
                landscapeDivider.setBackground(getResources().getDrawable(R.drawable.cosmosaurus_line));
            }
            return;
        }

        if (currentSpecies.equals(getString(R.string.scoutars))) {
            speciesIcon.setBackground(getResources().getDrawable(R.drawable.scoutar_icon));
            speciesHeaderTV.setText(getString(R.string.scoutars_points));
            speciesHeaderTV.setTextColor(getResources().getColor(R.color.scoutars));
            currentInputPointsColumns = Constants.Projections.scoutarColumns;
            currentTotalPointsColumn = GameLogEntry.COLUMN_SCOUTARS_TOTAL_POINTS;
            currentTraitSpinnerIndexColumn = GameLogEntry.COLUMN_SCOUTARS_TRAIT_INDEX;
            if (isLandscape){
                landscapeDivider.setBackground(getResources().getDrawable(R.drawable.scoutars_line));
            }
        }

        if (currentSpecies.equals(getString(R.string.araklith))) {
            speciesIcon.setBackground(getResources().getDrawable(R.drawable.araklith_icon));
            speciesHeaderTV.setText(getString(R.string.araklith_points));
            speciesHeaderTV.setTextColor(getResources().getColor(R.color.araklith));
            currentInputPointsColumns = Constants.Projections.araklithColumns;
            currentTotalPointsColumn = GameLogEntry.COLUMN_ARAKLITH_TOTAL_POINTS;
            currentTraitSpinnerIndexColumn = GameLogEntry.COLUMN_ARAKLITH_TRAIT_INDEX;
            if (isLandscape){
                landscapeDivider.setBackground(getResources().getDrawable(R.drawable.araklith_line));
            }
            return;
        }
    }

    private int updateCurrentSpeciesColumns() {
        // New value for species columns
        ContentValues values = new ContentValues();

        //from the spinner:
        int traitIndex = traitSpinner.getSelectedItemPosition();
        values.put(currentTraitSpinnerIndexColumn,traitIndex);

        //from all the edit texts:
        int missionPoints = Integer.parseInt(allEditTextViews[Constants.Projections.MISSION_POINTS_INDEX].getText().toString());
        values.put(currentInputPointsColumns[Constants.Projections.MISSION_POINTS_INDEX], missionPoints);

        int playerBoardPoints = Integer.parseInt(allEditTextViews[Constants.Projections.PLAYER_BOARD_POINTS_INDEX].getText().toString());
        values.put(currentInputPointsColumns[Constants.Projections.PLAYER_BOARD_POINTS_INDEX], playerBoardPoints);

        int traitPoints = Integer.parseInt(allEditTextViews[Constants.Projections.TRAIT_POINTS_INDEX].getText().toString());
        values.put(currentInputPointsColumns[Constants.Projections.TRAIT_POINTS_INDEX], traitPoints);

        int numResources = Integer.parseInt(allEditTextViews[Constants.Projections.NUM_RESOURCES_INDEX].getText().toString());
        values.put(currentInputPointsColumns[Constants.Projections.NUM_RESOURCES_INDEX], numResources);

        //from calculate resource points text view:
        int resourcePoints = Integer.parseInt(resourcePointsTV.getText().toString());
        values.put(currentInputPointsColumns[RESOURCES_POINTS_INDEX],resourcePoints);

        //total points
        int total = missionPoints + playerBoardPoints + traitPoints + resourcePoints;
        values.put(currentTotalPointsColumn, total);

        return getContentResolver().update(mUri, values, null, null);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
        String[] traitInstructionsArray = getResources().getStringArray(R.array.trait_instructions);
        String[] resourceInstructionsArray = getResources().getStringArray(R.array.resource_instructions);
        traitInstructionsTV.setText(traitInstructionsArray[pos]);
        resourceInstructionsTV.setText(resourceInstructionsArray[pos]);
        if (resourceInstructionsArray[pos].equals(getResources().getString(R.string.every_three_resources))) {
            resourceDivider = 3;
        } else {
            resourceDivider = 4;
        }
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
        //load values into edit texts if there is something there to load
            if (cursor != null && cursor.moveToFirst()) {
                int totalPoints = cursor.getInt(Constants.Projections.TOTAL_POINTS_INDEX);
                if (totalPoints != 0) { //there is something to load
                    int missionPoints = cursor.getInt(Constants.Projections.MISSION_POINTS_INDEX);
                    allEditTextViews[Constants.Projections.MISSION_POINTS_INDEX].setText(String.valueOf(missionPoints));

                    int playerBoardPoints = cursor.getInt(Constants.Projections.PLAYER_BOARD_POINTS_INDEX);
                    allEditTextViews[Constants.Projections.PLAYER_BOARD_POINTS_INDEX].setText(String.valueOf(playerBoardPoints));

                    int traitPoints = cursor.getInt(Constants.Projections.TRAIT_POINTS_INDEX);
                    allEditTextViews[Constants.Projections.TRAIT_POINTS_INDEX].setText(String.valueOf(traitPoints));

                    int numResources = cursor.getInt(Constants.Projections.NUM_RESOURCES_INDEX);
                    allEditTextViews[Constants.Projections.NUM_RESOURCES_INDEX].setText(String.valueOf(numResources));

                    int resourcesPoints = cursor.getInt(RESOURCES_POINTS_INDEX);
                    resourcePointsTV.setText(String.valueOf(resourcesPoints));

                    int traitIndex = cursor.getInt(Constants.Projections.TRAIT_INDEX_INDEX);
                    traitSpinner.setSelection(traitIndex);
                }
            }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}