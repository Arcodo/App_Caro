
package com.example.admin.fingertwister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;


public class SinglePlayerActivity extends AppCompatActivity implements View.OnTouchListener {

    // Viewobjekte
    private Button zurueck;
    private Button neuerZug;
    private TextView fingerText;
    private TextView farbeText;
    private TextView zuganzahlText;

    private Button test;

    // Arrays
    private String[] finger = new String[5];
    private String[] farbe = new String[4];

    // Objekte aus Java Paket
    private Random generator = new Random();

    // Variablen
    private int zustand = 0;
    private int fingerwahl = 0;
    private int farbwahl = 0;
    private int zuege = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        // Referenzen auf Viewobjekte zuweisen
        zurueck = (Button) findViewById(R.id.zurueck);
        neuerZug = (Button) findViewById(R.id.neuerZug);
        fingerText = (TextView) findViewById(R.id.fingerText);
        farbeText = (TextView) findViewById(R.id.farbeText);
        zuganzahlText = (TextView) findViewById(R.id.zuganzahlText);

        test = (Button) findViewById(R.id.test);
        test.setOnTouchListener(this);

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

    public void onClickNext(View view) {
        handleEvent(1);
    }

    public void handleEvent(int signal) {
        switch (zustand) {
            case 0:
                if (signal == 1) {
                    newTask();
                    newMoveNumber();
                    zustand = 0;
                }
                if (signal == 2) {
                    testResponse1();
                }
                if (signal == 3) {
                    testResponse2();
                }
                break;
        }
    }

    public void newTask() {
        newFinger();
        newColour();
    }

    public void newMoveNumber() {
        zuege = zuege + 1;
        zuganzahlText.setText("Anzahl der Züge: " + zuege);
    }

    public void newFinger() {
        fingerwahl = generator.nextInt(5);
        fingerText.setText(finger[fingerwahl] + "       auf");
    }

    public void newColour() {
        farbwahl = generator.nextInt(4);
        farbeText.setText(farbe[farbwahl]);
    }


    public void onClickBack(View view) {
        finish();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            handleEvent(2);
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            handleEvent(3);
        }
        return false;
    }

    public void testResponse1() {
        test.setText("wird gehalten");
    }

    public void testResponse2() {
        test.setText("wurde losgelassen");
    }
}

