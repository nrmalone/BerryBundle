package com.cis4280.berrybundle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScoreDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "HighScores";

    private static final int DB_VERSION = 1;

    ScoreDatabase(Context context) { super(context, DB_NAME, null, DB_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SHORTGAMESCORES (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
        + "SCORE INTEGER);");
        db.execSQL("CREATE TABLE GAMESCORES (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
        + "SCORE INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {

        }
        if (oldVersion < 3) {

        }
    }
}
