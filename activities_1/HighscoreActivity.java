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

    private ListView listView;
    private DatabaseHandler dbh;
    private ArrayList<Score> scoreList;
    private String[] spieldaten;
    private ListAdapter adapter;
    private Score scoreNeu;
    private Score score;
    private int punkte;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        // Den Handler für den Zugriff auf die Datenbank erstellen.
        dbh = new DatabaseHandler(this);

        listView = (ListView) findViewById(R.id.listView);

        spieldaten = new String[11];

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, spieldaten);

        Intent i = getIntent();
        punkte = i.getIntExtra("zuege", 0);
        name = i.getStringExtra("name");

        this.addScore();

        scoreList = dbh.getScores();
        Collections.sort(scoreList);

        checkTableSize();

        this.printTable();
    }

    public void addScore() {
        if(name != null) {
            scoreNeu = new Score(name, punkte);
            dbh.addScore(scoreNeu);
        }
    }

    public void checkTableSize() {
        if(scoreList.size() > 10) {
            for (int i = scoreList.size()-1; i > 10; i--) {
                score = dbh.getScore(i);
                dbh.deleteScore(score);
            }
        }
    }

    // Info für mich: scores.get(0) gibt erstes Element der Array-List aus
    // scores.get(0).getName() gibt nue namen aus
    private void printTable() {
        if(scoreList.size() == 0) {
            makeToastEmptyTable();
        }
        if(scoreList.size() > 10) {
            for (int i = scoreList.size()-1; i > 10; i--) {
                score = dbh.getScore(i);
                dbh.deleteScore(score);
            }
        }
        else {
            for(int i = 0; i < scoreList.size(); i++) {
                spieldaten[i] = (i + 1) + "   Spieler: " + scoreList.get(i).getName() +
                        "   Punkte: " + scoreList.get(i).getPoints();
            }
            listView.setAdapter(adapter);
        }
    }

    public void makeToastEmptyTable() {
        Toast.makeText(HighscoreActivity.this, "Die Highscore-Table ist leer!",
                Toast.LENGTH_SHORT).show();
    }

    public void onClickDelet(View view) {
        dbh.deleteAll();
        finish();
    }

    /** ofiofero.
     *
     * Hinweis:
     *
     * @param view
     */
    public void onClickBack(View view) {
        finish();
    }

}
