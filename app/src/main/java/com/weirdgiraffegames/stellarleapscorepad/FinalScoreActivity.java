package com.weirdgiraffegames.stellarleapscorepad;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class FinalScoreActivity extends AppCompatActivity {

    private String gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);
        gameId = getIntent().getExtras().getString(getString(R.string.game_id_key));
        Log.d("gameId","in FinalScoreActivity: " + gameId);
    }
}
