package com.weirdgiraffegames.stellarleapscorepad.data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract.GameLogEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by sarah on 03/01/2018.
 */

public class TestUtil {
    public static void insertFakeData(SQLiteDatabase db){
        Log.d("TestUtil","insertFakeData");
        if(db == null){
            return;
        }
        //create a list of fake guests
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues testValues = new ContentValues();  //2 player game
        String uniqueID = UUID.randomUUID().toString();
        testValues.put(GameLogContract.GameLogEntry.COLUMN_GAME_ID,uniqueID);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_WINNER, 0);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_TUSKADON_MISSION_POINTS, 99);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_TUSKADON_PLAYER_BOARD_POINTS, 50);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_TUSKADON_RESOURCE_POINTS, 25);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_TUSKADON_TRAIT_POINTS, 85);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_TUSKADON_TOTAL_POINTS, 259);

        testValues.put(GameLogContract.GameLogEntry.COLUMN_STARLING_MISSION_POINTS, 101);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_STARLING_PLAYER_BOARD_POINTS, 51);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_STARLING_RESOURCE_POINTS, 25);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_STARLING_TRAIT_POINTS, 75);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_STARLING_TOTAL_POINTS, 252);

        list.add(testValues);


        testValues = new ContentValues(); //1 player game
        uniqueID = UUID.randomUUID().toString();
        testValues.put(GameLogContract.GameLogEntry.COLUMN_GAME_ID,uniqueID);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_WINNER, 0);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_TUSKADON_MISSION_POINTS, 99);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_TUSKADON_PLAYER_BOARD_POINTS, 50);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_TUSKADON_RESOURCE_POINTS, 25);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_TUSKADON_TRAIT_POINTS, 75);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_TUSKADON_TOTAL_POINTS, 249);
        list.add(testValues);


        testValues = new ContentValues(); //5 player game
        uniqueID = UUID.randomUUID().toString();
        testValues.put(GameLogContract.GameLogEntry.COLUMN_GAME_ID,uniqueID);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_WINNER, 4);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_TUSKADON_MISSION_POINTS, 49);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_TUSKADON_PLAYER_BOARD_POINTS, 50);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_TUSKADON_RESOURCE_POINTS, 25);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_TUSKADON_TRAIT_POINTS, 75);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_TUSKADON_TOTAL_POINTS, 199);

        testValues.put(GameLogContract.GameLogEntry.COLUMN_STARLING_MISSION_POINTS, 102);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_STARLING_PLAYER_BOARD_POINTS, 51);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_STARLING_RESOURCE_POINTS, 25);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_STARLING_TRAIT_POINTS, 75);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_STARLING_TOTAL_POINTS, 253);

        testValues.put(GameLogContract.GameLogEntry.COLUMN_COSMOSAURUS_MISSION_POINTS, 102);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_COSMOSAURUS_PLAYER_BOARD_POINTS, 51);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_COSMOSAURUS_RESOURCE_POINTS, 25);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_COSMOSAURUS_TRAIT_POINTS, 75);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_COSMOSAURUS_TOTAL_POINTS, 253);

        testValues.put(GameLogContract.GameLogEntry.COLUMN_SCOUTARS_MISSION_POINTS, 102);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_SCOUTARS_PLAYER_BOARD_POINTS, 50);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_SCOUTARS_RESOURCE_POINTS, 25);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_SCOUTARS_TRAIT_POINTS, 75);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_SCOUTARS_TOTAL_POINTS, 252);

        testValues.put(GameLogContract.GameLogEntry.COLUMN_ARAKLITH_MISSION_POINTS, 102);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_ARAKLITH_PLAYER_BOARD_POINTS, 50);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_ARAKLITH_RESOURCE_POINTS, 35);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_ARAKLITH_TRAIT_POINTS, 75);
        testValues.put(GameLogContract.GameLogEntry.COLUMN_ARAKLITH_TOTAL_POINTS, 262);

        list.add(testValues);

        //insert all game logs in one transaction
        try
        {
            db.beginTransaction();
            //clear the table first
            db.delete (GameLogEntry.TABLE_NAME,null,null);
            //go through the list and add one by one
            for(ContentValues c:list){
                Log.d("TestUtil","inserting item");
                db.insert(GameLogEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }

    }
}
