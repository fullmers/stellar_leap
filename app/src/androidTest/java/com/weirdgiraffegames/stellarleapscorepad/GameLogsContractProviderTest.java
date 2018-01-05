package com.weirdgiraffegames.stellarleapscorepad;

import android.content.Context;
import android.content.UriMatcher;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContentProvider;
import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract;
import com.weirdgiraffegames.stellarleapscorepad.data.GameLogDbHelper;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by sarah on 05/01/2018.
 */

public class GameLogsContractProviderTest {

    /* Context used to access various parts of the system */
    private final Context mContext = InstrumentationRegistry.getTargetContext();

    /**
     * Because we annotate this method with the @Before annotation, this method will be called
     * before every single method with an @Test annotation. We want to start each test clean, so we
     * delete all entries in the tasks directory to do so.
     */
    @Before
    public void setUp() {
        /* Use TaskDbHelper to get access to a writable database */
        GameLogDbHelper dbHelper = new GameLogDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        database.delete(GameLogContract.GameLogEntry.TABLE_NAME, null, null);
    }

    //================================================================================
    // Test UriMatcher
    //================================================================================


    private static final Uri TEST_GAME_LOGS = GameLogContract.GameLogEntry.CONTENT_URI;
    // Content URI for a single task with id = 1
    private static final Uri TEST_GAME_LOGS_WITH_ID = TEST_GAME_LOGS.buildUpon().appendPath("1").build();
    //
//
//    /**
//     * This function tests that the UriMatcher returns the correct integer value for
//     * each of the Uri types that the ContentProvider can handle. Uncomment this when you are
//     * ready to test your UriMatcher.
//     */
    @Test
    public void testUriMatcher() {

        /* Create a URI matcher that the TaskContentProvider uses */
        UriMatcher testMatcher = GameLogContentProvider.buildUriMatcher();

        /* Test that the code returned from our matcher matches the expected GAME_LOGS int */
        String tasksUriDoesNotMatch = "Error: The GAME_LOGS URI was matched incorrectly.";
        int actualTasksMatchCode = testMatcher.match(TEST_GAME_LOGS);
        int expectedTasksMatchCode = GameLogContentProvider.GAME_LOGS;
        assertEquals(tasksUriDoesNotMatch,
                actualTasksMatchCode,
                expectedTasksMatchCode);

        /* Test that the code returned from our matcher matches the expected TASK_WITH_ID */
        String taskWithIdDoesNotMatch =
                "Error: The GAME_LOGS_WITH_ID URI was matched incorrectly.";
        int actualTaskWithIdCode = testMatcher.match(TEST_GAME_LOGS_WITH_ID);
        int expectedTaskWithIdCode = GameLogContentProvider.GAME_LOGS_WITH_ID;
        assertEquals(taskWithIdDoesNotMatch,
                actualTaskWithIdCode,
                expectedTaskWithIdCode);
    }


}
