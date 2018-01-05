package com.weirdgiraffegames.stellarleapscorepad.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by sarah on 03/01/2018.
 */

public class GameLogContract {
    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.weirdgiraffegames.stellarleapscorepad";

    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);
    public static final String PATH_GAME_LOGS = "gameLog";
    public static final String NUMBER_WILDCARD = "#";


    private GameLogContract () {}

    public static final class GameLogEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_GAME_LOGS).build();

        public static final String TABLE_NAME = "gameLog";

        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_WINNER= "winner";
        public static final String COLUMN_GAME_ID= "gameId";

        public static final String COLUMN_TUSKADON_MISSION_POINTS = "tuskadonMissionPoints";
        public static final String COLUMN_TUSKADON_PLAYER_BOARD_POINTS = "tuskadonPlayerBoardPoints";
        public static final String COLUMN_TUSKADON_TRAIT_POINTS = "tuskadonTraitPoints";
        public static final String COLUMN_TUSKADON_RESOURCE_POINTS = "tuskdaonResourcePoints";
        public static final String COLUMN_TUSKADON_TOTAL_POINTS = "tuskadonTotalPoints";

        public static final String COLUMN_STARLING_MISSION_POINTS = "starlingMissionPoints";
        public static final String COLUMN_STARLING_PLAYER_BOARD_POINTS = "starlingPlayerBoardPoints";
        public static final String COLUMN_STARLING_TRAIT_POINTS = "starlingTraitPoints";
        public static final String COLUMN_STARLING_RESOURCE_POINTS = "starlingResourcePoints";
        public static final String COLUMN_STARLING_TOTAL_POINTS = "starlingTotalPoints";

        public static final String COLUMN_COSMOSAURUS_MISSION_POINTS = "cosmosaurusMissionPoints";
        public static final String COLUMN_COSMOSAURUS_PLAYER_BOARD_POINTS = "cosmosaurusPlayerBoardPoints";
        public static final String COLUMN_COSMOSAURUS_TRAIT_POINTS = "cosmosaurusTraitPoints";
        public static final String COLUMN_COSMOSAURUS_RESOURCE_POINTS = "cosmosaurusResourcePoints";
        public static final String COLUMN_COSMOSAURUS_TOTAL_POINTS = "cosmosaurusTotalPoints";

        public static final String COLUMN_SCOUTARS_MISSION_POINTS = "scoutarsMissionPoints";
        public static final String COLUMN_SCOUTARS_PLAYER_BOARD_POINTS = "scoutarsPlayerBoardPoints";
        public static final String COLUMN_SCOUTARS_TRAIT_POINTS = "scoutarsTraitPoints";
        public static final String COLUMN_SCOUTARS_RESOURCE_POINTS = "scoutarsResourcePoints";
        public static final String COLUMN_SCOUTARS_TOTAL_POINTS = "scoutarsTotalPoints";

        public static final String COLUMN_ARAKLITH_MISSION_POINTS = "araklithMissionPoints";
        public static final String COLUMN_ARAKLITH_PLAYER_BOARD_POINTS = "araklithPlayerBoardPoints";
        public static final String COLUMN_ARAKLITH_TRAIT_POINTS = "araklithTraitPoints";
        public static final String COLUMN_ARAKLITH_RESOURCE_POINTS = "araklithResourcePoints";
        public static final String COLUMN_ARAKLITH_TOTAL_POINTS = "araklithTotalPoints";
    }
}
