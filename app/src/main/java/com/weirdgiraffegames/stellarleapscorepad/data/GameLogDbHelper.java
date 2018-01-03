package com.weirdgiraffegames.stellarleapscorepad.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract.GameLogEntry;

/**
 * Created by sarah on 03/01/2018.
 */

public class GameLogDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "gameLog.db";
    public static final int DATABASE_VERSION = 1;

    public GameLogDbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_GAME_LOG_TABLE = "CREATE TABLE " + GameLogEntry.TABLE_NAME
                + "("  +
                GameLogEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                GameLogEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                GameLogEntry.COLUMN_WINNER + " INTEGER NOT NULL," +

                GameLogEntry.COLUMN_TUSKADON_MISSION_POINTS + " INTEGER, " +
                GameLogEntry.COLUMN_TUSKADON_PLAYER_BOARD_POINTS+ " INTEGER, " +
                GameLogEntry.COLUMN_TUSKADON_TRAIT_POINTS + " INTEGER, " +
                GameLogEntry.COLUMN_TUSKADON_RESOURCE_POINTS + " INTEGER, " +
                GameLogEntry.COLUMN_TUSKADON_TOTAL_POINTS + " INTEGER, " +

                GameLogEntry.COLUMN_STARLING_MISSION_POINTS + " INTEGER, " +
                GameLogEntry.COLUMN_STARLING_PLAYER_BOARD_POINTS+ " INTEGER, " +
                GameLogEntry.COLUMN_STARLING_TRAIT_POINTS + " INTEGER, " +
                GameLogEntry.COLUMN_STARLING_RESOURCE_POINTS + " INTEGER, " +
                GameLogEntry.COLUMN_STARLING_TOTAL_POINTS + " INTEGER, " +

                GameLogEntry.COLUMN_COSMOSAURUS_MISSION_POINTS + " INTEGER, " +
                GameLogEntry.COLUMN_COSMOSAURUS_PLAYER_BOARD_POINTS+ " INTEGER, " +
                GameLogEntry.COLUMN_COSMOSAURUS_TRAIT_POINTS + " INTEGER, " +
                GameLogEntry.COLUMN_COSMOSAURUS_RESOURCE_POINTS + " INTEGER, " +
                GameLogEntry.COLUMN_COSMOSAURUS_TOTAL_POINTS + " INTEGER, " +

                GameLogEntry.COLUMN_SCOUTARS_MISSION_POINTS + " INTEGER, " +
                GameLogEntry.COLUMN_SCOUTARS_PLAYER_BOARD_POINTS+ " INTEGER, " +
                GameLogEntry.COLUMN_SCOUTARS_TRAIT_POINTS + " INTEGER, " +
                GameLogEntry.COLUMN_SCOUTARS_RESOURCE_POINTS + " INTEGER, " +
                GameLogEntry.COLUMN_SCOUTARS_TOTAL_POINTS + " INTEGER, " +

                GameLogEntry.COLUMN_ARAKLITH_MISSION_POINTS + " INTEGER, " +
                GameLogEntry.COLUMN_ARAKLITH_PLAYER_BOARD_POINTS+ " INTEGER, " +
                GameLogEntry.COLUMN_ARAKLITH_TRAIT_POINTS + " INTEGER, " +
                GameLogEntry.COLUMN_ARAKLITH_RESOURCE_POINTS + " INTEGER, " +
                GameLogEntry.COLUMN_ARAKLITH_TOTAL_POINTS + " INTEGER" +
                ");";

        sqLiteDatabase.execSQL(SQL_CREATE_GAME_LOG_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + GameLogEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
