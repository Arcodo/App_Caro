package com.example.admin.fingertwister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameOverActivity extends AppCompatActivity {

    private Button speichern;
    private Button abbrechen;
    private TextView erreichtePunkte;
    private EditText nameSpieler;
    private int spielstand;
    private String name;

    private Intent highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        speichern = (Button) findViewById(R.id.speichern);
        abbrechen = (Button) findViewById(R.id.abbrechen);
        erreichtePunkte = (TextView) findViewById(R.id.erreichtePunkte);
        nameSpieler = (EditText) findViewById(R.id.nameSpieler);

        highscore = new Intent(this, HighscoreActivity.class);

        Intent i = getIntent();
        spielstand = i.getIntExtra("zuege", 0);


        erreichtePunkte.setText("Das Spiel ist vorbei. Sie haben " + spielstand
                + " Punkte erreicht. Tragen Sie nun hier Ihren Namen ein, um Ihr Ergebnis in der "
                + "Highscore-Liste abzuspeichern.");
    }

    public void onClickSave(View view) {
        if(nameSpieler.getText().toString().isEmpty() == true) {
            makeToastNoEntry();
        }
        else {
            name = nameSpieler.getText().toString();
            highscore.putExtra("zuege", spielstand);
            highscore.putExtra("name", name);
            setResult(3, highscore);
            finish();
        }
    }

    public void makeToastNoEntry() {
        Toast.makeText(GameOverActivity.this, "Geben Sie Ihren Namen ein oder drücken Sie "
                + "'Abbrechen', wenn Sie Ihren Spielstand nicht in die Highscore-Liste hinzufügen "
                + "möchten!", Toast.LENGTH_SHORT).show();
    }

    public void onClickCancel(View view) {
        finish();
    }
}
