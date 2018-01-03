package com.weirdgiraffegames.stellarleapscorepad.data;

import android.provider.BaseColumns;

/**
 * Created by sarah on 03/01/2018.
 */

public class GameLogContract {

    private GameLogContract () {}

    public static class GameLogEntry implements BaseColumns {

        public static String TABLE_NAME = "gameLog";
        public static String COLUMN_TIMESTAMP = "timestamp";
        public static String COLUMN_WINNER= "winner";

        public static String COLUMN_TUSKADON_MISSION_POINTS = "tuskadonMissionPoints";
        public static String COLUMN_TUSKADON_PLAYER_BOARD_POINTS = "tuskadonPlayerBoardPoints";
        public static String COLUMN_TUSKADON_TRAIT_POINTS = "tuskadonTraitPoints";
        public static String COLUMN_TUSKADON_RESOURCE_POINTS = "tuskdaonResourcePoints";
        public static String COLUMN_TUSKADON_TOTAL_POINTS = "tuskadonTotalPoints";

        public static String COLUMN_STARLING_MISSION_POINTS = "starlingMissionPoints";
        public static String COLUMN_STARLING_PLAYER_BOARD_POINTS = "starlingPlayerBoardPoints";
        public static String COLUMN_STARLING_TRAIT_POINTS = "starlingTraitPoints";
        public static String COLUMN_STARLING_RESOURCE_POINTS = "starlingResourcePoints";
        public static String COLUMN_STARLING_TOTAL_POINTS = "starlingTotalPoints";

        public static String COLUMN_COSMOSAURUS_MISSION_POINTS = "cosmosaurusMissionPoints";
        public static String COLUMN_COSMOSAURUS_PLAYER_BOARD_POINTS = "cosmosaurusPlayerBoardPoints";
        public static String COLUMN_COSMOSAURUS_TRAIT_POINTS = "cosmosaurusTraitPoints";
        public static String COLUMN_COSMOSAURUS_RESOURCE_POINTS = "cosmosaurusResourcePoints";
        public static String COLUMN_COSMOSAURUS_TOTAL_POINTS = "cosmosaurusTotalPoints";

        public static String COLUMN_SCOUTARS_MISSION_POINTS = "scoutarsMissionPoints";
        public static String COLUMN_SCOUTARS_PLAYER_BOARD_POINTS = "scoutarsPlayerBoardPoints";
        public static String COLUMN_SCOUTARS_TRAIT_POINTS = "scoutarsTraitPoints";
        public static String COLUMN_SCOUTARS_RESOURCE_POINTS = "scoutarsResourcePoints";
        public static String COLUMN_SCOUTARS_TOTAL_POINTS = "scoutarsTotalPoints";

        public static String COLUMN_ARAKLITH_MISSION_POINTS = "araklithMissionPoints";
        public static String COLUMN_ARAKLITH_PLAYER_BOARD_POINTS = "araklithPlayerBoardPoints";
        public static String COLUMN_ARAKLITH_TRAIT_POINTS = "araklithTraitPoints";
        public static String COLUMN_ARAKLITH_RESOURCE_POINTS = "araklithResourcePoints";
        public static String COLUMN_ARAKLITH_TOTAL_POINTS = "araklithTotalPoints";
    }
}
