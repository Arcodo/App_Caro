
package com.example.admin.fingertwister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


class HighscoreDbHelper extends SQLiteOpenHelper{

    // Variablen für Datenbank
    public static  final String DB_NAME = "highscore.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_HIGHSCORE = "highscores";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_POINTS = "score";

    public static final String SQL_CREATE = "create table " + TABLE_HIGHSCORE + "(" +
            COLUMN_ID + " integer primary key, " +
            COLUMN_NAME + " text, " + COLUMN_POINTS + " integer);";


    public HighscoreDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drob table if exists " + TABLE_HIGHSCORE);
        onCreate(db);
    }

    public void addScore(Score s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, s.getName());
        values.put(COLUMN_POINTS, s.getPoints());
        db.insert(TABLE_HIGHSCORE, null, values);
        db.close();
    }

    public Score getScore(int idNew) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_HIGHSCORE, new String[] { COLUMN_ID, COLUMN_NAME,
                        COLUMN_POINTS }, COLUMN_ID + "=?",
                new String[] { String.valueOf(idNew) }, null, null, null, null);
        Score s = null;
        if (c.moveToFirst())
            s = new Score(Integer.parseInt(c.getString(0)), c.getString(1),
                    Integer.parseInt(c.getString(2)));
        c.close();
        db.close();
        return (s);
    }

    public ArrayList<Score> getScores()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_HIGHSCORE;
        Cursor c = db.rawQuery(sql, null);
        Score s = null;
        ArrayList<Score> l = null;
        if (c.moveToFirst())
        {
            l = new ArrayList<Score>();
            do
            {
                s = new Score(Integer.parseInt(c.getString(0)), c.getString(1),
                        Integer.parseInt(c.getString(2)));
                l.add(s);
            }
            while(c.moveToNext());
        }
        c.close();
        db.close();
        return(l);
    }

    public int updateScore(Score s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, s.getName());
        values.put(COLUMN_POINTS, s.getPoints());
        return db.update(TABLE_HIGHSCORE, values, COLUMN_ID + "=?",
                new String[] { String.valueOf(s.getId()) });
    }

    public void deleteScore(Score s) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HIGHSCORE, COLUMN_ID + "=?",
                new String[]{String.valueOf(s.getId())});
        db.close();
    }

    public int bestScore(String name) {
        String query = "SELECT MAX(" + COLUMN_POINTS + ") FROM " + TABLE_HIGHSCORE
                + " WHERE " + COLUMN_NAME + " = ’" + name + "’ GROUP BY "
                + COLUMN_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query, null);
        int maxVal = -1;
        if (c.moveToFirst())
            maxVal = Integer.parseInt(c.getString(0));
        c.close();
        db.close();
        return (maxVal);
    }

}
