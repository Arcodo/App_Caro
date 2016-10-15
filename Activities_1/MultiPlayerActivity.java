package com.example.admin.fingertwister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class MultiPlayerActivity extends AppCompatActivity {

    // Viewobjekte
    private Button zurueck1;
    private Button zurueck2;
    private Button neuerZug1;
    private Button neuerZug2;
    private TextView fingerText;
    private TextView farbeText;
    private TextView zuganzahlText1;
    private TextView zuganzahlText2;

    // Arrays
    private String[] finger = new String[5];
    private String[] farbe = new String[4];

    // Objekte aus Java Paket
    private Random generator = new Random();

    // Variablen
    private int zustand = 0;
    private int fingerwahl = 0;
    private int farbwahl = 0;
    private int zuege1 = 0;
    private int zuege2 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_player);

        // Referenzen auf Viweobjekte zuweisen
        zurueck1 = (Button) findViewById(R.id.zurueck1);
        zurueck2 = (Button) findViewById(R.id.zurueck2);
        neuerZug1 = (Button) findViewById(R.id.neuerZug1);
        neuerZug2 = (Button) findViewById(R.id.neuerZug2);
        fingerText = (TextView) findViewById(R.id.fingerText);
        farbeText = (TextView) findViewById(R.id.farbeText);
        zuganzahlText1 = (TextView) findViewById(R.id.zuganzahlText1);
        zuganzahlText2 = (TextView) findViewById(R.id.zuganzahlText2);

        finger[0] = "Daumen";
        finger[1] = "Zeigefinger";
        finger[2] = "Mittelfinger";
        finger[3] = "Ringfinger";
        finger[4] = "kleiner Finger";

        farbe[0] = "rot";
        farbe[1] = "gelb";
        farbe[2] = "grün";
        farbe[3] = "blau";
    }

    public void onClickFirstPlayer (View view) {
        handleEvent(1);
    }

    public void onClickSecondPlayer (View view) {
        handleEvent(2);
    }

    public void handleEvent(int signal) {
        switch(zustand) {
            case 0:
                if(signal == 1) {
                    newTask();
                    newMoveNumberOne();
                    zustand = 1;
                }
                if(signal == 2) {
                    makeToast();
                    zustand = 0;
                }
                if(signal == 3) {

                }
                if(signal == 4) {

                }
                break;
            case 1:
                if(signal == 1) {
                    makeToast();
                    zustand = 1;
                }
                if(signal == 2) {
                    newTask();
                    newMoveNumberTwo();
                    zustand = 0;
                }
                if(signal == 3) {

                }
                if(signal == 4){

                }
                break;
        }
    }

    public void newTask() {
        newFinger();
        newColour();
    }

    public void newMoveNumberOne() {
        zuege1 = zuege1 + 1;
        zuganzahlText1.setText("Anzahl der Züge: " + zuege1);
    }

    public void newMoveNumberTwo() {
        zuege2 = zuege2 + 1;
        zuganzahlText2.setText("Anzahl der Züge: " + zuege2);
    }

    public void newFinger() {
        fingerwahl = generator.nextInt(5);
        fingerText.setText(finger[fingerwahl] + "       auf");
    }

    public void newColour() {
        farbwahl = generator.nextInt(4);
        farbeText.setText(farbe[farbwahl]);
    }

    public void makeToast() {
        Toast.makeText(MultiPlayerActivity.this, "Der andere Spieler ist an der Reihe. " +
                "Bitte warten Sie, bis er seinen Zug gemacht hat.", Toast.LENGTH_SHORT).show();
    }

    public void onClickBack (View view) {
        finish();
    }

}
