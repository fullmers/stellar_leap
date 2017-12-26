package com.weirdgiraffegames.stellarleapscorepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class WelcomeScreenActivity extends AppCompatActivity {

    Button btn_about;
    Button btn_start_scoring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        btn_about = findViewById(R.id.about_app_btn);
        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WelcomeScreenActivity.this,AboutActivity.class);
                startActivity(i);
            }
        });

        btn_start_scoring = findViewById(R.id.score_new_game_btn);
        btn_start_scoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WelcomeScreenActivity.this,ChooseRacesActivity.class);
                startActivity(i);
            }
        });
    }
}
