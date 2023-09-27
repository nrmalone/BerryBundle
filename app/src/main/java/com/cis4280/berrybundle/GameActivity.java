package com.cis4280.berrybundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private SQLiteDatabase db;

    public static int score = 0;
    private final int min = 10;
    private final int max = 790;

    public boolean gameStarted;
    public boolean gameFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameStarted = false;
        gameFinished = false;

        SQLiteOpenHelper scoreDatabase = new ScoreDatabase(this);
        db = scoreDatabase.getWritableDatabase();

        RelativeLayout playField = (RelativeLayout) findViewById(R.id.playField);
        RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        //figure out how to generate new strawberries
        for (int i = 0; i < 2; i++) {
            int x = (int)Math.floor(Math.random()*(max-min+1)+min);
            int y = (int)Math.floor(Math.random()*(max-min+1)+min);

            ImageButton btn = new ImageButton(this, null, R.style.Widget_AppCompat_Button_Borderless);
            buttonParams.setMargins(x,y,0,0);
            btn.setImageResource(R.drawable.strawberry_64);
            btn.setLayoutParams(buttonParams);
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (gameFinished == false) {
                        if (gameStarted == false) {
                            mTimer.start();
                            gameStarted = true;
                        }
                        score++;
                        TextView scoreTextView = (TextView) findViewById(R.id.score_tv);
                        scoreTextView.setText("Score: " + score);
                        btn.setVisibility(View.GONE);
                    }
                }
            });
            playField.addView(btn);
        }
    }

    CountDownTimer mTimer = new CountDownTimer(30000, 500) {
        @Override
        public void onTick(long millisUntilFinished) {
            TextView timerTextView = (TextView) findViewById(R.id.timer_tv);
            timerTextView.setText("Seconds Remaining: " + (millisUntilFinished/500)/2);

            RelativeLayout playField = (RelativeLayout) findViewById(R.id.playField);
            RelativeLayout.LayoutParams buttonParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < 2; i++) {
                int x = (int)Math.floor(Math.random()*(max-min+1)+min);
                int y = (int)Math.floor(Math.random()*(max-min+1)+min);

                ImageButton btn = new ImageButton(getApplicationContext(), null, R.style.Widget_AppCompat_Button_Borderless);
                buttonParams.setMargins(x,y,0,0);
                btn.setImageResource(R.drawable.strawberry_64);
                btn.setLayoutParams(buttonParams);
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (gameFinished == false) {
                            if (gameStarted == false) {
                                TextView hintText = (TextView) findViewById(R.id.hintText);
                                hintText.setVisibility(TextView.INVISIBLE);
                                mTimer.start();
                                gameStarted = true;
                            }
                            score++;
                            TextView scoreTextView = (TextView) findViewById(R.id.score_tv);
                            scoreTextView.setText("Score: " + score);
                            btn.setVisibility(View.GONE);
                        }
                    }
                });
                playField.addView(btn);
            }
        }

        @Override
        public void onFinish() {
            ContentValues scoreInput = new ContentValues();

            scoreInput.put("SCORE", score);
            db.insert("GAMESCORES", null, scoreInput);

            gameFinished = true;
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Your score: " + score,
                    Toast.LENGTH_LONG);
            toast.show();

            CountDownTimer finishTimer = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    finish();
                }
            };
            finishTimer.start();
        }
    };

    public void strawberryClick(View view) {
        if (gameFinished == false) {
            if (gameStarted == false) {
                mTimer.start();
                gameStarted = true;
            }
            score++;
            TextView scoreTextView = (TextView) findViewById(R.id.score_tv);
            scoreTextView.setText("Score: " + score);
        }
    }

    public void quitClick(View view) {
        mTimer.cancel();
        finish();
    }
}