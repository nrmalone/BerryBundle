package com.cis4280.berrybundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ShortGameActivity extends AppCompatActivity {
    private SQLiteDatabase db;

    public static int score = 0;

    public boolean gameStarted;
    public boolean gameFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_short_game);

        gameStarted = false;
        gameFinished = false;

        SQLiteOpenHelper scoreDatabase = new ScoreDatabase(this);
        db = scoreDatabase.getWritableDatabase();
    }

    CountDownTimer mTimer = new CountDownTimer(10000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            TextView timerTextView = (TextView) findViewById(R.id.timer_tv);
            timerTextView.setText("Seconds Remaining: " + (millisUntilFinished/1000));
        }

        @Override
        public void onFinish() {
            ContentValues scoreInput = new ContentValues();

            scoreInput.put("SCORE", score);
            db.insert("SHORTGAMESCORES", null, scoreInput);

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
                TextView hintText = (TextView) findViewById(R.id.hintText);
                hintText.setVisibility(TextView.INVISIBLE);
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