package com.example.admin.fingertwister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class HighscoreActivity extends AppCompatActivity {

    // Viewobjekte
    private ListView listView;

    // Objekte aus anderen Klassen
    private DatabaseHandler dbh;
    private Score score;

    // ArrayList um Elemente der Datenbank verwalten zu können
    private ArrayList<Score> scoreList;

    // String-Array und zugehöriger ArrayAdapter um Elemente der ArrayList in ListView
    // ausgeben zu können
    private String[] spieldaten;
    private ArrayAdapter<String> adapter;

    // Varaiblen
    private int punkte;
    private String name;

    /** erzeugt die Activity und setzt deren Layout.
     * erzeugt alle nötigen Objekte und weist deren Werte zu.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        // der Handler für den Zugriff auf die Datenbank wird erstellt
        dbh = new DatabaseHandler(this);

        // Referenz auf listView wird zugewiesen
        listView = (ListView) findViewById(R.id.listView);

        // die Informationen des Intents werden ausgelesen und in den Varaiblen "punkte" und
        // "name" gespeichert
        Intent i = getIntent();
        punkte = i.getIntExtra("zuege", 0);
        name = i.getStringExtra("name");

        this.addScore();

        // ArrayList "scoreList" wird mit den Elementen der Datenbank gefüllt
        scoreList = dbh.getScores();
        // ArrayList "scoreList" wird sortiert
        Collections.sort(scoreList);

        this.printTable();
    }

    /** überprüft, ob ein Wert für die Varaible "name" übergebeen wurde. Falls ein Wert übergeben
     * wurde, wird ein neues Objekt der Klasse "Score" erstellt und in die Datenbank eingefügt.
     */
    public void addScore() {
        if(name != null) {
            score = new Score(name, punkte);
            dbh.addScore(score);
        }
    }

    /** überprüft, ob die ArrayList überhaupt ein Element enthält. Wenn die ArrayList leer ist,
     * wird ein Toast erzeugt. Ansonsten werden die einzelnen Elemente über den ArrayAdapter in
     * der ListView ausgegeben.
     */
    private void printTable() {
        if(scoreList.size() == 0) {
            makeToastEmptyTable();
        }
        if(scoreList.size() < 10) {
            spieldaten = new String[scoreList.size()];
            for(int i = 0; i < scoreList.size(); i++) {
                spieldaten[i] = (i+1) + "   Name: " + scoreList.get(i).getName() + "   Punkte: "
                        + scoreList.get(i).getPoints();
            }
        }
        else {
            spieldaten = new String[10];
            for(int i = 0; i < 10; i++) {
                spieldaten[i] = (i+1) + "   Name: " + scoreList.get(i).getName() + "   Punkte: "
                        + scoreList.get(i).getPoints();
            }
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, spieldaten);
        listView.setAdapter(adapter);
    }

    /** erzeugt einen Toast.
     */
    public void makeToastEmptyTable() {
        Toast.makeText(HighscoreActivity.this, "Die Highscore-Liste ist leer!",
                Toast.LENGTH_SHORT).show();
    }

    /** onClick-Methode des Buttons "Löschen", die die Datenbank löscht und die Activity
     * anschließend beendet.
     *
     * @param view  ist das angeklickte View-Objekt, hier der Button "Löschen".
     */
    public void onClickDelete(View view) {
        dbh.deleteAll();
        finish();
    }

    /** onClick-Methode des Buttons "Löschen", die die Activity beendet.
     *
     * @param view ist das angeklickte View-Objekt, hier der Button "Zurück".
     */
    public void onClickBack(View view) {
        finish();
    }
}
