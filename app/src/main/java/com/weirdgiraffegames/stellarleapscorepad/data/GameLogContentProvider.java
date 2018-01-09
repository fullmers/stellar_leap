package com.weirdgiraffegames.stellarleapscorepad.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import static com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract.GameLogEntry.TABLE_NAME;

/**
 * Created by sarah on 05/01/2018.
 */

public class GameLogContentProvider extends ContentProvider {

    private GameLogDbHelper mDbHelper;
    private Context mContext;

    public static final int GAME_LOGS = 100;
    public static final int GAME_LOGS_WITH_ID = 101;

    private static UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(GameLogContract.AUTHORITY, GameLogContract.PATH_GAME_LOGS, GAME_LOGS);
        uriMatcher.addURI(GameLogContract.AUTHORITY, GameLogContract.PATH_GAME_LOGS + "/#", GAME_LOGS_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mContext = getContext();
        mDbHelper = new GameLogDbHelper(mContext);
        return true;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case GAME_LOGS:
                long id = db.insert(TABLE_NAME, null,values);

                if (id != -1) {
                    returnUri = ContentUris.withAppendedId(GameLogContract.GameLogEntry.CONTENT_URI,id);
                } else {
                    throw new android.database.SQLException("failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return returnUri;
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor cursor;
        switch (match) {
            case GAME_LOGS:
                cursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case GAME_LOGS_WITH_ID:
                //URI: content://<authority>/game_logs/#
                String id = uri.getPathSegments().get(1);

                String mSelection = " _id=?";
                String[] mSelectionArgs = new String[]{id};
                cursor = db.query(TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri.toString());
        }

        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int gameLogsDeleted; // starts as 0

        switch (match) {
            case GAME_LOGS_WITH_ID:
                //URI: content://<authority>/game_logs/#
                String id = uri.getPathSegments().get(1);
                String mSelection = " _id=?";
                String[] mSelectionArgs = new String[]{id};
                gameLogsDeleted = db.delete(TABLE_NAME,
                        mSelection,
                        mSelectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri.toString());
        }

        if (gameLogsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return gameLogsDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        //Keep track of if an update occurs
        int gameLogsUpdated;
        int match = sUriMatcher.match(uri);

        switch (match) {
            case GAME_LOGS_WITH_ID:
                String id = uri.getPathSegments().get(1);
                gameLogsUpdated = mDbHelper.getWritableDatabase().update(TABLE_NAME, values, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (gameLogsUpdated != 0) {
            //set notifications if a task was updated
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return gameLogsUpdated;
    }


    @Override
    public String getType(@NonNull Uri uri) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
