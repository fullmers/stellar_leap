package com.weirdgiraffegames.stellarleapscorepad;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract;
import com.weirdgiraffegames.stellarleapscorepad.data.GameLogDbHelper;

public class GameLogActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_log);

        GameLogDbHelper dbHelper = new GameLogDbHelper(this);

        mDb = dbHelper.getWritableDatabase();
    }


    public Cursor getAllGameLogs() {

        return mDb.query(GameLogContract.GameLogEntry.TABLE_NAME,
                null, //interested in all columns, so just pass in null
                null,
                null,
                null,
                null,
                GameLogContract.GameLogEntry.COLUMN_TIMESTAMP //order results by timestamp
        );

    }
}
