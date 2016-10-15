package com.example.admin.fingertwister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Viewobjekte
    private TextView ausgabeWillk;
    private Button buttonSpiel;
    private Button buttonBest;
    private Button beschreibung;

    //
    private HighscoreDbHelper dB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referenzen auf Viewobjekte zuweisen
        ausgabeWillk = (TextView) findViewById(R.id.ausgabeWillk);
        buttonSpiel = (Button) findViewById(R.id.buttonSpiel);
        buttonBest = (Button) findViewById(R.id.buttonBest);
        beschreibung = (Button) findViewById(R.id.beschreibung);

        // Referenz auf          zuweisen
        dB = new HighscoreDbHelper(this);
    }

    public void OnClickGame(View view) {
        Intent spielauswahl = new Intent(this, PlayModeActivity.class);
        startActivity(spielauswahl);
    }

    public void OnClickInfo(View view) {
        Intent info = new Intent(this, InfoActivity.class);
        startActivity(info);
    }

}
