package com.weirdgiraffegames.stellarleapscorepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeScreenActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        ButterKnife.bind(this);

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        moveTaskToBack(true); // exit app
    }

    @OnClick(R.id.score_new_game_btn)
    public void scoreNewGame(View view) {
        Intent i = new Intent(WelcomeScreenActivity.this,ChooseRacesActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.about_app_btn)
    public void openAboutActivity(View view) {
        Intent i = new Intent(WelcomeScreenActivity.this,AboutActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.game_log_btn)
    public void seeGameLog(View view) {
        Intent i = new Intent(WelcomeScreenActivity.this,GameLogActivity.class);
        startActivity(i);
    }
}
