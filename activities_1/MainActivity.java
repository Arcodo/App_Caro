package com.example.admin.fingertwister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Viewobjekte
    private TextView ausgabeWillkommen;
    private Button spielStarten;
    private Button bestenliste;
    private Button beschreibung;

    //
    private DatabaseHandler dB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referenzen auf Viewobjekte zuweisen
        ausgabeWillkommen = (TextView) findViewById(R.id.ausgabeWillkommen);
        spielStarten = (Button) findViewById(R.id.spielStarten);
        bestenliste = (Button) findViewById(R.id.bestenliste);
        beschreibung = (Button) findViewById(R.id.beschreibung);

        // Referenz auf          zuweisen
        dB = new DatabaseHandler(this);
    }

    public void onClickStartGame(View view) {
        Intent spielStarten = new Intent(this, PlayActivity.class);
        startActivity(spielStarten);
    }

    public void onClickHighscore(View view) {
        Intent highscoreList = new Intent(this, HighscoreActivity.class);
        startActivity(highscoreList);
    }

    public void onClickInfo(View view) {
        Intent spielInfo = new Intent(this, InfoActivity.class);
        startActivity(spielInfo);
    }

}
