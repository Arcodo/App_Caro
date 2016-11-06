package com.example.admin.fingertwister;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Quelle: vgl. Mebiskurs "050 - WaA - 1W inf - Einf�hrung in die App-Programmierung", Order "Code-
 * Snippets und sonstige Projektteile", Unterordner "Datenbank", Datei "DatabaseHandler"
 */
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

    /**
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_SCORES + "(" + KEY_ID + " INTEGER PRIMARY KEY " +
                "AUTOINCREMENT, "  + KEY_NAME + " TEXT, " + KEY_POINTS + " INTEGER" + ")";
        db.execSQL(sql);
    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    /**
     *
     * @param s
     */
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

    /**
     *
     */
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SCORES);
        db.close();
    }

    /**
     *
     * @return
     */
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

        while(c.moveToNext()) {
            Score s = new Score(Integer.parseInt(c.getString(0)), c.getString(1),
                    Integer.parseInt(c.getString(2)));
            l.add(s);
        }

        c.close();
        db.close();
        return l;
    }
}
