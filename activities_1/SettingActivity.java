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

    // Viewobjekte
    private TextView anweisung;
    private Button zurueck;
    private Button weiter;
    private Spinner groesseWaehlen;

    // Array und ArrayAdapter für den Spinner
    private String[] auswahl;
    private ArrayAdapter<String> stringArrayAdapter;

    // Variablen
    private String gewaehlteGroesse;
    private int groesseSpielfeld;

    // Intent
    private Intent spielStarten;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Referenzen auf Viewobjekte zuweisen
        anweisung = (TextView) findViewById(R.id.anweisung);
        zurueck = (Button) findViewById(R.id.zurueck);
        weiter = (Button) findViewById(R.id.weiter);
        groesseWaehlen = (Spinner) findViewById(R.id.spinner);

        // Refernz auf Intent "spielStarten" zuweisen
        spielStarten = new Intent(this, PlayActivity.class);

        // Referenz auf Array "auswahl" und dessen Werte zuweisen
        auswahl = new String[5];
        auswahl[0] = "sehr klein";
        auswahl[1] = "klein";
        auswahl[2] = "mittel";
        auswahl[3] = "groß";
        auswahl[4] = "sehr groß";

        // setzt neuen Text für TextView "anweisung".
        anweisung.setText("Wählen Sie hier die Größe des Spielfeldes aus und drücken Sie danach " +
                "auf 'Weiter', um das Spiel zu starten. Sie sollten die Größe des Spielfeldes von " +
                "der Größe Ihres Bildschrims abhängig machen!");

        // weist die Refernz auf den ArrayAdapter zu.
        stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, auswahl);

        // weist dem Sipnner "groesseWaehlen" den  ArrayAdapter zu.
        groesseWaehlen.setAdapter(stringArrayAdapter);

        // weist dem Spinner "groesseWaehlen" den onItemSelectedListener zu.
        groesseWaehlen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                gewaehlteGroesse = groesseWaehlen.getSelectedItem().toString();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /** onClick-Methode des Buttons "Weiter", der den Wert des Spinners "groesseWaehlen" über den
     * String "gewaehlteGroesse" mit dem Wert des Arrays "auswahl" abgleicht und anschließend die
     * Methode "startGame()" aufruft.
     *
     * @param view ist das angeklickte View-Objekt, hier der Button "Weiter".
     */
    public void onClickNext(View view) {
        if(gewaehlteGroesse == auswahl[0]) {
            groesseSpielfeld = 75;
        }
        if(gewaehlteGroesse == auswahl[1]) {
            groesseSpielfeld = 125;
        }
        if(gewaehlteGroesse == auswahl[2]) {
            groesseSpielfeld = 175;
        }
        if(gewaehlteGroesse == auswahl[3]) {
            groesseSpielfeld = 225;
        }
        if(gewaehlteGroesse == auswahl[4]) {
            groesseSpielfeld = 275;
        }
        startGame();
    }

    /** übergibt den Wert der Varaible "groesseSpielfeld" an das Intent "spielStarten".
     * ruft Methode "setResult()" mit Eingangsparametern "1" als resetCode und "spielStarten" als
     * Intent auf und springt dadurch in die Methode "onActivityresult" der MainActivity.
     * beendet anschließend diese Activity.
     */
    public void startGame() {
        spielStarten.putExtra("groesseSpielfeld", groesseSpielfeld);
        setResult(1, spielStarten);
        finish();
    }

    /**
     * onClick-Methode des Buttons "Zurück", die die Activity beendet.
     *
     * @param view ist das angeklickte View-Objekt, hier der Button "Zurück".
     */
    public void onClickBack(View view) {
        finish();
    }
}
