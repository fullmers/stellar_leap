package com.weirdgiraffegames.stellarleapscorepad;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract;
import com.weirdgiraffegames.stellarleapscorepad.data.GameLogDbHelper;

public class GameLogActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    private GameLogAdapter mAdapter;

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

        Cursor cursor = getAllGameLogs();
        mAdapter = new GameLogAdapter(this, cursor);
        gameLogRecyclerView.setAdapter(mAdapter);
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
