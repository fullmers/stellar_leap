package com.weirdgiraffegames.stellarleapscorepad.util;

import com.weirdgiraffegames.stellarleapscorepad.data.GameLogContract.GameLogEntry;

/**
 * Created by sarah on 12/01/2018.
 */

 public class Constants {

     public static class Projections {
         public static final int MISSION_POINTS_INDEX = 0;
         public static final int PLAYER_BOARD_POINTS_INDEX = 1;
         public static final int TRAIT_POINTS_INDEX = 2;
         public static final int NUM_RESOURCES_INDEX = 3;
         public static final int RESOURCES_POINTS_INDEX = 4;
         public static final int TOTAL_POINTS_INDEX = 5;
         public static final int TRAIT_INDEX_INDEX = 6;

         public static final String[] tuskadonColumns = {GameLogEntry.COLUMN_TUSKADON_MISSION_POINTS, GameLogEntry.COLUMN_TUSKADON_PLAYER_BOARD_POINTS, GameLogEntry.COLUMN_TUSKADON_TRAIT_POINTS, GameLogEntry.COLUMN_TUSKADON_NUM_RESOURCE, GameLogEntry.COLUMN_TUSKADON_RESOURCE_POINTS,GameLogEntry.COLUMN_TUSKADON_TOTAL_POINTS,GameLogEntry.COLUMN_TUSKADON_TRAIT_INDEX};
         public static final String[] starlingColumns = {GameLogEntry.COLUMN_STARLING_MISSION_POINTS, GameLogEntry.COLUMN_STARLING_PLAYER_BOARD_POINTS, GameLogEntry.COLUMN_STARLING_TRAIT_POINTS, GameLogEntry.COLUMN_STARLING_NUM_RESOURCE, GameLogEntry.COLUMN_STARLING_RESOURCE_POINTS, GameLogEntry.COLUMN_STARLING_TOTAL_POINTS,GameLogEntry.COLUMN_STARLING_TRAIT_INDEX};
         public static final String[] cosmosaurusColumns = {GameLogEntry.COLUMN_COSMOSAURUS_MISSION_POINTS, GameLogEntry.COLUMN_COSMOSAURUS_PLAYER_BOARD_POINTS, GameLogEntry.COLUMN_COSMOSAURUS_TRAIT_POINTS, GameLogEntry.COLUMN_COSMOSAURUS_NUM_RESOURCE, GameLogEntry.COLUMN_COSMOSAURUS_RESOURCE_POINTS,GameLogEntry.COLUMN_COSMOSAURUS_TOTAL_POINTS,GameLogEntry.COLUMN_COSMOSAURUS_TRAIT_INDEX};
         public static final String[] scoutarColumns = {GameLogEntry.COLUMN_SCOUTARS_MISSION_POINTS, GameLogEntry.COLUMN_SCOUTARS_PLAYER_BOARD_POINTS, GameLogEntry.COLUMN_SCOUTARS_TRAIT_POINTS, GameLogEntry.COLUMN_SCOUTARS_NUM_RESOURCE, GameLogEntry.COLUMN_SCOUTARS_RESOURCE_POINTS,GameLogEntry.COLUMN_SCOUTARS_TOTAL_POINTS,GameLogEntry.COLUMN_SCOUTARS_TRAIT_INDEX};
         public static final String[] araklithColumns ={GameLogEntry.COLUMN_ARAKLITH_MISSION_POINTS, GameLogEntry.COLUMN_ARAKLITH_PLAYER_BOARD_POINTS, GameLogEntry.COLUMN_ARAKLITH_TRAIT_POINTS, GameLogEntry.COLUMN_ARAKLITH_NUM_RESOURCE, GameLogEntry.COLUMN_ARAKLITH_RESOURCE_POINTS,GameLogEntry.COLUMN_ARAKLITH_TOTAL_POINTS,GameLogEntry.COLUMN_ARAKLITH_TRAIT_INDEX};
     }
}
