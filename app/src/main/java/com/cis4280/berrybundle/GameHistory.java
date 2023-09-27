package com.cis4280.berrybundle;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class GameHistory extends AppCompatActivity {
    private SQLiteDatabase db;
    private SQLiteDatabase dbDROP;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);

        SQLiteOpenHelper scoreDatabase = new ScoreDatabase(this);
        ListView gameHistory = (ListView) findViewById(R.id.gameHistoryList);

        try {
            db = scoreDatabase.getReadableDatabase();

            cursor = db.query("GAMESCORES", new String[]{"_id", "SCORE"}, null, null, null, null, "SCORE DESC");

            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[] {"SCORE"},
                    new int[]{android.R.id.text1},
                    0);

            gameHistory.setAdapter(listAdapter);
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this,"Database unavailable", Toast.LENGTH_SHORT);
        }
    }

    public void onClickClearDB(View view) {
        SQLiteOpenHelper scoreDatabase = new ScoreDatabase(this);
        dbDROP = scoreDatabase.getWritableDatabase();

        //drop existing scores
        dbDROP.execSQL("DROP TABLE GAMESCORES");

        //recreate scores table
        dbDROP.execSQL("CREATE TABLE GAMESCORES (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "SCORE INTEGER);");
        recreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}