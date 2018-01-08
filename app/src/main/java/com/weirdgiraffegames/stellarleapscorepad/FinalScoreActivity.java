package com.weirdgiraffegames.stellarleapscorepad;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract;

public class FinalScoreActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private String gameId;
    private static final String TAG = FinalScoreActivity.class.getSimpleName();
    private static final int FINAL_GAME_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);
        gameId = getIntent().getExtras().getString(getString(R.string.game_id_key));
        Log.d("gameId","in FinalScoreActivity: " + gameId);

        getSupportLoaderManager().initLoader(FINAL_GAME_LOADER_ID, null, this);
    }

    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(FINAL_GAME_LOADER_ID,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize a Cursor, this will hold all the task data
            Cursor mTaskData = null;

            // onStartLoading() is called when a loader first starts loading data
            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    // Delivers any previously loaded data immediately
                    deliverResult(mTaskData);
                } else {
                    // Force a new load
                    forceLoad();
                }
            }

            // loadInBackground() performs asynchronous loading of data
            @Override
            public Cursor loadInBackground() {
                String[] idSelector = {gameId};
                try {
                    return getContentResolver().query(GameLogContract.GameLogEntry.CONTENT_URI,
                            null,
                            "_id=?",
                            idSelector,
                            null);
                }
                catch (Exception e) {
                    Log.d(TAG,"Failed to asynchronously load data");
                    return null;
                }
            }

            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    cursor.moveToFirst();
    int tuskadonTotal = cursor.getInt(cursor.getColumnIndex(GameLogContract.GameLogEntry.COLUMN_TUSKADON_TOTAL_POINTS));
    Log.d("tuskadon total",String.valueOf(tuskadonTotal));
    cursor.close();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
