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
    private Score score;
    private DatabaseHandler dbh;
    private ArrayList<Score> scoreList;
    private String[] spieldaten;
    private ListAdapter adapter;
    private int punkte;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        this.addScore();

        // Den Handler für den Zugriff auf die Datenbank erstellen.
        dbh = new DatabaseHandler(this);

        // scoreList = dbh.getScores();
        // Collections.sort(scoreList);

        spieldaten = new String[10];

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, spieldaten);

        listView = (ListView) findViewById(R.id.listView);

        Intent i = getIntent();
        punkte = i.getIntExtra("zuege", 0);
        name = i.getStringExtra("name");

        this.printTable();
    }

    public void addScore() {
        score = new Score(name, punkte);
        dbh.addScore(score);
        Log.d("addSave", "Score gespeichert: " + score.toString());
    }

    // scores.get(0) gibt erstes Element der Array-List aus
    // scores.get(0).getName() gibt nue namen aus
    private void printTable() {
        if (scoreList.size() == 0) {
            makeToastEmptyTable();
        }
        else {
            for (int i = 0; i < scoreList.size(); i++) {
                spieldaten[i] = (i + 1) + " " + scoreList.get(i).getName() + " "
                        + scoreList.get(i).getPoints() /*+ "\n"*/ ;
            }
            listView.setAdapter(adapter);
        }
    }

    public void makeToastEmptyTable() {
        Toast.makeText(HighscoreActivity.this, "Die Highscore-Table ist noch leer!",
                Toast.LENGTH_SHORT).show();
    }

    /** ofiofero.
     *
     * Hinweis:
     *
     * @param view
     */
    public void onClickBack (View view) {
        finish();
    }

}
