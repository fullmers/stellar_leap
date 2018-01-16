package com.weirdgiraffegames.stellarleapscorepad;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameLogActivity extends AppCompatActivity implements GameLogCursorAdapter.GameLogAdapterOnClickHandler, LoaderManager.LoaderCallbacks<Cursor>{

    private GameLogCursorAdapter mCursorAdapter;
    @BindView(R.id.all_game_logs_recycler_view) RecyclerView gameLogRecyclerView;
    @BindView(R.id.empty_list_view) LinearLayout emptyListLl;
    private static final int GAME_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_log);
        ButterKnife.bind(this);

        gameLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCursorAdapter = new GameLogCursorAdapter(this, this);
        gameLogRecyclerView.setAdapter(mCursorAdapter);

        getSupportLoaderManager().initLoader(GAME_LOADER_ID, null, this);
    }

    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(GAME_LOADER_ID,null,this);
    }

   @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(GameLogActivity.this,WelcomeScreenActivity.class);
        startActivity(i);
    }

    @Override
    public void onClick(Long gameId) {
        Context context = this;
        Class destinationClass = FinalScoreActivity.class;
        Intent intent = new Intent(context, destinationClass);
        Uri uri = ContentUris.withAppendedId(GameLogContract.GameLogEntry.CONTENT_URI,gameId);
        intent.setData(uri);
        intent.putExtra(getString(R.string.comes_from_game_log_activity_key),true);
        startActivity(intent);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch(id) {
            case GAME_LOADER_ID:
                String[] projection = {
                        GameLogContract.GameLogEntry._ID,
                        GameLogContract.GameLogEntry.COLUMN_TIMESTAMP,
                        GameLogContract.GameLogEntry.COLUMN_TUSKADON_TOTAL_POINTS,
                        GameLogContract.GameLogEntry.COLUMN_STARLING_TOTAL_POINTS,
                        GameLogContract.GameLogEntry.COLUMN_COSMOSAURUS_TOTAL_POINTS,
                        GameLogContract.GameLogEntry.COLUMN_SCOUTARS_TOTAL_POINTS,
                        GameLogContract.GameLogEntry.COLUMN_ARAKLITH_TOTAL_POINTS
                };
                return new CursorLoader(this,
                        GameLogContract.GameLogEntry.CONTENT_URI,
                        projection,
                        null,
                        null,
                        GameLogContract.GameLogEntry.COLUMN_TIMESTAMP + " DESC"); //most recent game on top
            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() == 0) {
            emptyListLl.setVisibility(View.VISIBLE);
        } else {
            emptyListLl.setVisibility(View.GONE);
        }

        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
