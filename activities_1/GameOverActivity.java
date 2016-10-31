package com.example.admin.fingertwister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button speichern;
    private TextView erreichtePunkte;
    private EditText nameSpieler;
    private int spielstand;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        speichern = (Button) findViewById(R.id.speichern);
        erreichtePunkte = (TextView) findViewById(R.id.erreichtePunkte);
        nameSpieler = (EditText) findViewById(R.id.nameSpieler);

        Intent i = getIntent();
        spielstand = i.getIntExtra("zuege", 0);


        erreichtePunkte.setText("Das Spiel ist vorbei. Sie haben " + spielstand
                + " Punkte erreicht. Tragen Sie nun hier Ihren Namen ein, um Ihr Ergebnis in der "
                + "Highscore-Liste abzuspeichern.");
    }

    public void onClickSave(View view) {
        name = nameSpieler.getText().toString();
        Intent highscore = new Intent(this, HighscoreActivity.class);
        highscore.putExtra("zuege", spielstand);
        highscore.putExtra("name", name);
        startActivity(highscore);
    }
}
