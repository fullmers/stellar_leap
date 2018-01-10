package com.weirdgiraffegames.stellarleapscorepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract.GameLogEntry;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FinalScoreActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private Cursor mCursor;
    private Uri mUri;
    private boolean comesFromGameLogActivity;
    private static final String TAG = FinalScoreActivity.class.getSimpleName();
    private static final int FINAL_GAME_LOADER_ID = 1;

    private static final int MISSION_POINTS_INDEX = 0;
    private static final int PLAYER_BOARD_POINTS_INDEX = 1;
    private static final int TRAIT_POINTS_INDEX = 2;
    private static final int RESOURCES_POINTS_INDEX = 3;
    private static final int TOTAL_POINTS_INDEX = 4;

    private static final int TUSKADON_INDEX = 0;
    private static final int STARLING_INDEX = 1;
    private static final int COSMOSAURUS_INDEX = 2;
    private static final int SCOUTAR_INDEX = 3;
    private static final int ARAKLITH_INDEX = 4;

    @BindView(R.id.btn_new_game) Button newGameButton;
    @BindView(R.id.btn_delete) Button deleteButton;

    @BindView(R.id.tuskadon_column) LinearLayout tuskadonPointsLayout;
    @BindView(R.id.tuskadon_header) ImageView tuskadonHeaderImage;
    @BindView(R.id.tuskadon_end_line) ImageView tuskadonEndLineImage;
    @BindView(R.id.tuskadon_mission_points_tv) TextView tuskadonMissionPointsTv;
    @BindView(R.id.tuskadon_player_board_points_tv) TextView tuskadonPlayerBoardPointsTv;
    @BindView(R.id.tuskadon_trait_points_tv) TextView tuskadonTraitPointsTv;
    @BindView(R.id.tuskadon_resources_points_tv) TextView tuskadonResourcesPointsTv;
    @BindView(R.id.tuskadon_total_points_tv) TextView tuskadonTotalPointsTv;

    @BindView(R.id.starling_column) LinearLayout starlingPointsLayout;
    @BindView(R.id.starling_header) ImageView starlingHeaderImage;
    @BindView(R.id.starling_end_line) ImageView starlingEndLineImage;
    @BindView(R.id.starling_mission_points_tv) TextView starlingMissionPointsTv;
    @BindView(R.id.starling_player_board_points_tv) TextView starlingPlayerBoardPointsTv;
    @BindView(R.id.starling_trait_points_tv) TextView starlingTraitPointsTv;
    @BindView(R.id.starling_resources_points_tv) TextView starlingResourcesPointsTv;
    @BindView(R.id.starling_total_points_tv) TextView starlingTotalPointsTv;

    @BindView(R.id.cosmosaurus_column) LinearLayout cosmosaurusPointsLayout;
    @BindView(R.id.cosmosaurus_header) ImageView cosmosaurusHeaderImage;
    @BindView(R.id.cosmosaurus_end_line) ImageView cosmosaurusEndLineImage;
    @BindView(R.id.cosmosaurus_mission_points_tv) TextView cosmosaurusMissionPointsTv;
    @BindView(R.id.cosmosaurus_player_board_points_tv) TextView cosmosaurusPlayerBoardPointsTv;
    @BindView(R.id.cosmosaurus_trait_points_tv) TextView cosmosaurusTraitPointsTv;
    @BindView(R.id.cosmosaurus_resources_points_tv) TextView cosmosaurusResourcesPointsTv;
    @BindView(R.id.cosmosaurus_total_points_tv) TextView cosmosaurusTotalPointsTv;

    @BindView(R.id.scoutars_column) LinearLayout scoutarsPointsLayout;
    @BindView(R.id.scoutars_header) ImageView scoutarsHeaderImage;
    @BindView(R.id.scoutars_end_line) ImageView scoutarsEndLineImage;
    @BindView(R.id.scoutars_mission_points_tv) TextView scoutarsMissionPointsTv;
    @BindView(R.id.scoutars_player_board_points_tv) TextView scoutarsPlayerBoardPointsTv;
    @BindView(R.id.scoutars_trait_points_tv) TextView scoutarsTraitPointsTv;
    @BindView(R.id.scoutars_resources_points_tv) TextView scoutarsResourcesPointsTv;
    @BindView(R.id.scoutars_total_points_tv) TextView scoutarsTotalPointsTv;

    @BindView(R.id.araklith_column) LinearLayout araklithPointsLayout;
    @BindView(R.id.araklith_header) ImageView araklithHeaderImage;
    @BindView(R.id.araklith_mission_points_tv) TextView araklithMissionPointsTv;
    @BindView(R.id.araklith_player_board_points_tv) TextView araklithPlayerBoardPointsTv;
    @BindView(R.id.araklith_trait_points_tv) TextView araklithTraitPointsTv;
    @BindView(R.id.araklith_resources_points_tv) TextView araklithResourcesPointsTv;
    @BindView(R.id.araklith_total_points_tv) TextView araklithTotalPointsTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);
        ButterKnife.bind(this);

        mUri = getIntent().getData();
        comesFromGameLogActivity = getIntent().getBooleanExtra(getString(R.string.comes_from_game_log_activity_key),false);
        if (comesFromGameLogActivity) {
            newGameButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            newGameButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.GONE);
        }
        getSupportLoaderManager().initLoader(FINAL_GAME_LOADER_ID, null, this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i;
        if (comesFromGameLogActivity) {
            i = new Intent(FinalScoreActivity.this, GameLogActivity.class);
        } else {
            i = new Intent(FinalScoreActivity.this, WelcomeScreenActivity.class);
        }
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(FINAL_GAME_LOADER_ID,null,this);
    }

    @OnClick(R.id.btn_delete)
    //TODO add confirmation dialog here
    public void deleteLog(View view) {
        showDeleteConfirmationDialog();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.delete_dialog_msg));
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getContentResolver().delete(mUri,null,null);
                Intent i = new Intent(FinalScoreActivity.this,GameLogActivity.class);
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

    @OnClick(R.id.btn_new_game)
    public void newGame(View view) {
        Intent i = new Intent(FinalScoreActivity.this,WelcomeScreenActivity.class);
        startActivity(i);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch(id) {
            case FINAL_GAME_LOADER_ID:

                return new CursorLoader(this,
                        mUri,
                        null,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor != null && cursor.moveToFirst()) {
            mCursor = cursor;

            String[] species = {
                    getString(R.string.tuskadon),
                    getString(R.string.starlings),
                    getString(R.string.cosmosaurus),
                    getString(R.string.scoutars),
                    getString(R.string.araklith)
            };

            LinearLayout[] pointsColumnLayouts =
                    {tuskadonPointsLayout,
                            starlingPointsLayout,
                            cosmosaurusPointsLayout,
                            scoutarsPointsLayout,
                            araklithPointsLayout};

            ImageView[] headerImages =
                    {tuskadonHeaderImage,
                            starlingHeaderImage,
                            cosmosaurusHeaderImage,
                            scoutarsHeaderImage,
                            araklithHeaderImage};

            ImageView[] endLineImages =
                    {tuskadonEndLineImage,
                            starlingEndLineImage,
                            cosmosaurusEndLineImage,
                            scoutarsEndLineImage};

            TextView[][] pointsTextViews = {
                    {tuskadonMissionPointsTv,tuskadonPlayerBoardPointsTv,tuskadonTraitPointsTv,tuskadonResourcesPointsTv,tuskadonTotalPointsTv},
                    {starlingMissionPointsTv,starlingPlayerBoardPointsTv,starlingTraitPointsTv,starlingResourcesPointsTv,starlingTotalPointsTv},
                    {cosmosaurusMissionPointsTv,cosmosaurusPlayerBoardPointsTv,cosmosaurusTraitPointsTv,cosmosaurusResourcesPointsTv,cosmosaurusTotalPointsTv},
                    {scoutarsMissionPointsTv,scoutarsPlayerBoardPointsTv,scoutarsTraitPointsTv,scoutarsResourcesPointsTv,scoutarsTotalPointsTv},
                    {araklithMissionPointsTv,araklithPlayerBoardPointsTv,araklithTraitPointsTv,araklithResourcesPointsTv,araklithTotalPointsTv}
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

            int tuskadonTotal = mCursor.getInt(columnIndices[TUSKADON_INDEX][TOTAL_POINTS_INDEX]);
            int starlingTotal = mCursor.getInt(columnIndices[STARLING_INDEX][TOTAL_POINTS_INDEX]);
            int cosmosaurusTotal = mCursor.getInt(columnIndices[COSMOSAURUS_INDEX][TOTAL_POINTS_INDEX]);
            int scoutarsTotal = mCursor.getInt(columnIndices[SCOUTAR_INDEX][TOTAL_POINTS_INDEX]);
            int araklithTotal = mCursor.getInt(columnIndices[ARAKLITH_INDEX][TOTAL_POINTS_INDEX]);

            int[] totals = {tuskadonTotal, starlingTotal, cosmosaurusTotal, scoutarsTotal, araklithTotal};
            final ArrayList<String> selectedSpecies = new ArrayList<>();

            int index = 0;
            int lastIndex = 0;
            for (int pointTotal : totals) {
                if (pointTotal != 0) {
                    selectedSpecies.add(species[index]);

                    pointsColumnLayouts[index].setVisibility(View.VISIBLE);
                    headerImages[index].setVisibility(View.VISIBLE);

                    TextView[] mTextViews = pointsTextViews[index];
                    int missionPoints = mCursor.getInt(columnIndices[index][MISSION_POINTS_INDEX]);
                    mTextViews[MISSION_POINTS_INDEX].setText(String.valueOf(missionPoints));
                    int playerBoardPoints = mCursor.getInt(columnIndices[index][PLAYER_BOARD_POINTS_INDEX]);
                    mTextViews[PLAYER_BOARD_POINTS_INDEX].setText(String.valueOf(playerBoardPoints));
                    int traitPoints = mCursor.getInt(columnIndices[index][TRAIT_POINTS_INDEX]);
                    mTextViews[TRAIT_POINTS_INDEX].setText(String.valueOf(traitPoints));
                    int resourcesPoints = mCursor.getInt(columnIndices[index][RESOURCES_POINTS_INDEX]);
                    mTextViews[RESOURCES_POINTS_INDEX].setText(String.valueOf(resourcesPoints));

                    mTextViews[TOTAL_POINTS_INDEX].setText(String.valueOf(pointTotal));

                    lastIndex = index;
                } else {
                    pointsColumnLayouts[index].setVisibility(View.GONE);
                    headerImages[index].setVisibility(View.GONE);
                }
                index++;
            }

            if (lastIndex != ARAKLITH_INDEX) {
                endLineImages[lastIndex].setVisibility(View.GONE);
            }

            //When just finishing inputting game points, let user edit values if they spot a mistake
            if (!comesFromGameLogActivity) {
                int speciesIndex = 0;
                for (String thisSpecies: species) {
                    int selectedSpeciesIndex = 0;
                    for (String thisSelectedSpecies : selectedSpecies) {
                        if (thisSpecies.equals(thisSelectedSpecies)) {
                        pointsColumnLayouts[speciesIndex].setClickable(true);
                        final int tempIndex = selectedSpeciesIndex;
                        pointsColumnLayouts[speciesIndex].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent i = new Intent(FinalScoreActivity.this, InputPointsActivity.class);
                                i.putExtra(getString(R.string.layout_index_key), tempIndex);
                                Log.d("layout Index", "in FinalScoreActivity"+ tempIndex);
                                i.putExtra(getString(R.string.selected_species_key),selectedSpecies);
                                i.putExtra(getString(R.string.comes_from_final_score_activity_key),true);
                                i.setData(mUri);
                                startActivity(i);
                            }
                        });
                    }
                        selectedSpeciesIndex++;
                    }
                    speciesIndex++;
                }
            }

            mCursor.close();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {}
}
