package com.example.admin.fingertwister;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Highscore-List";

    private static final String TABLE_SCORES = "scores";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_POINTS = "points";

    public DatabaseHandler(Context c) {
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_SCORES + "(" + KEY_ID + " INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, "  + KEY_NAME + " TEXT, " + KEY_POINTS + " INTEGER" + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    public void addScore(Score s) {
        // Datenbankverbindung öffnen und die Referenz auf die Datenbank holen.
        SQLiteDatabase db = this.getWritableDatabase();

        // Key - Value Paare setzen.
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, s.getName());
        values.put(KEY_POINTS, s.getPoints());

        // Datensatz in die Datenbank schreiben.
        db.insert(TABLE_SCORES, null, values);

        // Datenbankverbindung freigeben.
        db.close();
    }


     // Holt den Datensatz zu einer id aus der Tabelle.
     // Falls kein Datensatz existiert, wird null zurückgegeben.
    public Score getScore(int id) {

        // Datenbankverbindung öffnen und die Referenz auf die Datenbank holen.
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.query(TABLE_SCORES, new String[] {KEY_ID, KEY_NAME,
                        KEY_POINTS }, KEY_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        // Falls die Abfrage kein Ergebnis bringt....
        if (c == null) {
            return null;
        }

        // Ersten Datensatz wählen.
        if (!c.moveToFirst()) {
            return null;
        }

        // Werte gemäß der Spaltenreihenfolge der Abfrage interpretieren.
        Score s = new Score(
                Integer.parseInt(c.getString(0)),
                c.getString(1),
                Integer.parseInt(c.getString(2)));

        // Datenbank - Cursor freigeben.
        c.close();

        // Datenbankverbindung freigeben.
        db.close();
        return (s);
    }

    public int updateScore(Score s) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, s.getName());
        values.put(KEY_POINTS, s.getPoints());
        return db.update(TABLE_SCORES, values, KEY_ID + "=?",
                new String[] { String.valueOf(s.getId()) });
    }

    public void deleteScore(Score s) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCORES, KEY_ID + "=?",
                new String[]{String.valueOf(s.getPoints())}); //getID
        db.close();
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SCORES);
        db.close();
    }

    public ArrayList<Score> getScores() {

        // Datenbankverbindung öffnen.
        SQLiteDatabase db = this.getReadableDatabase();

        // Abfrage.
        String sql = "SELECT * FROM " + TABLE_SCORES;
        Cursor c = db.rawQuery(sql, null);

        ArrayList<Score> l = new ArrayList<Score>();

        // Irgendetwas ist schief gegangen.
        if (c == null) {
            return l;
        }

        while(c.moveToNext())
        {
            Score s = new Score(Integer.parseInt(c.getString(0)), c.getString(1), Integer.parseInt(c.getString(2)));
            l.add(s);
        }

        c.close();
        db.close();
        return l;
    }
}
