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

    // Intents
    private Intent startSettingActivity;
    private Intent startPlayActivity;
    private Intent startGameOverAcivity;
    private Intent startHighscoreActivity;
    private Intent startInfoActivity;

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

        // Referenzen der Intents zuweisen
        startSettingActivity = new Intent(this,SettingActivity.class);
        startPlayActivity = new Intent(this,PlayActivity.class);
        startGameOverAcivity = new Intent(this, GameOverActivity.class);
        startHighscoreActivity = new Intent(this, HighscoreActivity.class);
        startInfoActivity = new Intent(this, InfoActivity.class);

        // Referenz auf          zuweisen
        dB = new DatabaseHandler(this);
    }

    public void onClickStartGame(View view) {
        startActivityForResult(startSettingActivity, 1);
    }

    public void onClickHighscore(View view) {
        startActivityForResult(startHighscoreActivity, 4);
    }

    public void onClickInfo(View view) {
        startActivityForResult(startInfoActivity, 5);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode) {
            // aus SettingActivity kommend
            case 1:
                int mas = data.getIntExtra("groesseSpielfeld", 0);
                startPlayActivity.putExtra("groesseSpielfeld", mas);
                startActivityForResult(startPlayActivity, 1);
                break;
            // aus PlayActivity kommend
            case 2:
                int zuege = data.getIntExtra("zuege", 0);
                startGameOverAcivity.putExtra("zuege", zuege);
                startActivityForResult(startGameOverAcivity, 2);
                break;
            // aus GameOverActivity kommend
            case 3:
                int punkte = data.getIntExtra("zuege", 0);
                String name = data.getStringExtra("name");
                startHighscoreActivity.putExtra("zuege", punkte);
                startHighscoreActivity.putExtra("name", name);
                startActivityForResult(startHighscoreActivity, 3);
                break;
            // aus HighscoreActivity kommend
            case 4:
                break;
            // aus InfoActivity kommend
            case 5:
                break;
        }
    }
}
