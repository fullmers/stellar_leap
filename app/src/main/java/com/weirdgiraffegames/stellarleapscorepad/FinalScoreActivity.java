package com.weirdgiraffegames.stellarleapscorepad;

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

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FinalScoreActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private Cursor mCursor;
    private Uri mUri;
    private boolean comesFromGameLogActivity;
    private static final String TAG = FinalScoreActivity.class.getSimpleName();
    private static final int FINAL_GAME_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);
        ButterKnife.bind(this);
        mUri = getIntent().getData();
        comesFromGameLogActivity = getIntent().getBooleanExtra(getString(R.string.comes_from_game_log_activity_key),false);
        Log.d("uri","in FinalScoreActivity: " + mUri.toString());

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
        getContentResolver().delete(mUri,null,null);
        Intent i = new Intent(FinalScoreActivity.this,GameLogActivity.class);
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
        //TODO replace below load all data into UI
        if(cursor != null) {
            mCursor = cursor;
            mCursor.moveToFirst();
            int tuskadonTotal = mCursor.getInt(cursor.getColumnIndex(GameLogContract.GameLogEntry.COLUMN_TUSKADON_TOTAL_POINTS));
            Log.d("tuskadon total",String.valueOf(tuskadonTotal));
            mCursor.close();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


}
