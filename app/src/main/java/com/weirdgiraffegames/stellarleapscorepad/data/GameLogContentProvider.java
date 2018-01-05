package com.weirdgiraffegames.stellarleapscorepad.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by sarah on 05/01/2018.
 */

public class GameLogContentProvider extends ContentProvider {

    private GameLogDbHelper mDbHelper;

    public static final int GAME_LOGS = 100;
    public static final int GAME_LOGS_WITH_ID = 101;

    private static UriMatcher sUriMatcher = builUriMatcher();

    private static UriMatcher builUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(GameLogContract.AUTHORITY, GameLogContract.PATH_GAME_LOGS, GAME_LOGS);
        uriMatcher.addURI(GameLogContract.AUTHORITY, GameLogContract.PATH_GAME_LOGS + "/#", GAME_LOGS_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDbHelper = new GameLogDbHelper(context);
        return true;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public String getType(@NonNull Uri uri) {

        throw new UnsupportedOperationException("Not yet implemented");
    }
}
