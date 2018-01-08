package com.weirdgiraffegames.stellarleapscorepad;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract;
import com.weirdgiraffegames.stellarleapscorepad.data.GameLogDbHelper;

public class GameLogActivity extends AppCompatActivity implements GameLogCursorAdapter.GameLogAdapterOnClickHandler, LoaderManager.LoaderCallbacks<Cursor>{

    private SQLiteDatabase mDb;
    private GameLogCursorAdapter mCursorAdapter;

    private static final String TAG = GameLogActivity.class.getSimpleName();
    private static final int GAME_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_log);

        GameLogDbHelper dbHelper = new GameLogDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        //TestUtil.insertFakeData(mDb);

        RecyclerView gameLogRecyclerView;
        gameLogRecyclerView = (RecyclerView) this.findViewById(R.id.all_game_logs_recycler_view);
        gameLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCursorAdapter = new GameLogCursorAdapter(this, this);
        gameLogRecyclerView.setAdapter(mCursorAdapter);

        getSupportLoaderManager().initLoader(GAME_LOADER_ID, null, this);
    }

    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(GAME_LOADER_ID,null,this);
    }

    private boolean deleteGame(String gameId) {
        Log.d("deleting ", "game " + gameId);
        return mDb.delete(GameLogContract.GameLogEntry.TABLE_NAME,
                GameLogContract.GameLogEntry.COLUMN_GAME_ID + "=" + "\"" + gameId + "\"",
                null) == 1;
    }

    @Override
    public void onClick(String gameId) {
        Context context = this;
        Class destinationClass = FinalScoreActivity.class;
        Log.d("gameId","in GameLogActivity: " + gameId);
        Intent intent = new Intent(context, destinationClass);
        intent.putExtra(getString(R.string.game_id_key), gameId);
        startActivity(intent);
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
                String[] projection = {
                        GameLogContract.GameLogEntry._ID,
                        GameLogContract.GameLogEntry.COLUMN_TUSKADON_TOTAL_POINTS,
                        GameLogContract.GameLogEntry.COLUMN_STARLING_TOTAL_POINTS,
                        GameLogContract.GameLogEntry.COLUMN_COSMOSAURUS_TOTAL_POINTS,
                        GameLogContract.GameLogEntry.COLUMN_SCOUTARS_TOTAL_POINTS,
                        GameLogContract.GameLogEntry.COLUMN_ARAKLITH_TOTAL_POINTS
                };

                try {
                    return getContentResolver().query(GameLogContract.GameLogEntry.CONTENT_URI,
                            projection,
                            null,
                            null,
                            GameLogContract.GameLogEntry.COLUMN_TIMESTAMP);
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
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
