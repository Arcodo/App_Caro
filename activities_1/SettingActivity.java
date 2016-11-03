package com.example.admin.fingertwister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    private TextView anweisung;
    private Button zurueck;
    private Button weiter;
    private Spinner groesseWaehlen;
    private String gewaehlteGroesse;
    private String[] auswahl;
    private ArrayAdapter<String> stringArrayAdapter;
    private int groesseSpielfeld;

    private Intent spielStarten;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        anweisung = (TextView) findViewById(R.id.anweisung);
        zurueck = (Button) findViewById(R.id.zurueck);
        weiter = (Button) findViewById(R.id.weiter);
        groesseWaehlen = (Spinner) findViewById(R.id.spinner);

        spielStarten = new Intent(this, PlayActivity.class);

        auswahl = new String[3];
        auswahl[0] = "klein";
        auswahl[1] = "mittel";
        auswahl[2] = "groß";

        anweisung.setText("Wählen Sie hier die Größe des Spielfeldes aus und drücken Sie danach " +
                "auf 'Weiter', um das Spiel zu starten. Sie sollten die Größe des Spielfeldes von " +
                "der Größe Ihres Bildschrims abhängig machen!");

        stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, auswahl);
        groesseWaehlen.setAdapter(stringArrayAdapter);

        groesseWaehlen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                gewaehlteGroesse = groesseWaehlen.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void onClickNext(View view) {
        if(gewaehlteGroesse == auswahl[0]) {
            groesseSpielfeld = 80;
            startGame();
        }
        if(gewaehlteGroesse == auswahl[1]) {
            groesseSpielfeld = 170;
            startGame();
        }
        if(gewaehlteGroesse == auswahl[2]) {
            groesseSpielfeld = 250;
            startGame();
        }
    }

    public void startGame() {
        spielStarten.putExtra("groesseSpielfeld", groesseSpielfeld);
        setResult(1, spielStarten);
        finish();
    }

    public void onClickBack(View view) {
        finish();
    }
}
