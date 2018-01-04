package com.weirdgiraffegames.stellarleapscorepad;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract;
import com.weirdgiraffegames.stellarleapscorepad.data.GameLogDbHelper;

public class GameLogActivity extends AppCompatActivity implements GameLogAdapter.GameLogAdapterOnClickHandler{

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
        mAdapter = new GameLogAdapter(this, cursor, this);
        gameLogRecyclerView.setAdapter(mAdapter);

      /*  new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                String gameId = (String) viewHolder.itemView.getTag();
                deleteGame(gameId);
                mAdapter.swapCursor(getAllGameLogs());
            }
        }).attachToRecyclerView(gameLogRecyclerView); */
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
}
