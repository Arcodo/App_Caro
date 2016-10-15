package com.example.admin.fingertwister;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class SinglePlayerActivity extends AppCompatActivity implements View.OnTouchListener {

    // Viewobjekte
    private Button zurueck;
    private Button neuerZug;
    private TextView fingerText;
    private TextView farbeText;
    private TextView zuganzahlText;

    // Bild-Arrays für die einzelnen Felder des Spielfeldes
    // grüne Felder
    private ImageView[] grueneKreise;
    // gelbe Felder
    private ImageView[] gelbeKreise;
    // blaue Felder
    private ImageView[] blaueKreise;
    // rote Felder
    private ImageView[] roteKreise;

    // Array-List für gesamtes Spielfeld
    private ArrayList<ImageView[]> kreise;

    // Arrays zur zufälligen Ausgabe von Finger und Farbe für jeden Spielzug
    private String[] finger;
    private String[] farbe;

    // Objekte aus Java Paket
    private Random generator;
    private CountDownTimer timer;

    // Variablen
    private int fingerwahl;
    private int farbwahl;
    private int zuege;
    private int positionDaumen;
    private int positionZeigefinger;
    private int positionMittelfinger;
    private int positionRingfinger;
    private int positionKleinerFinger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        // Variablen den Wert zuweisen
        zuege = 0;
        positionDaumen = 0;
        positionZeigefinger = 0;
        positionMittelfinger = 0;
        positionRingfinger = 0;
        positionKleinerFinger = 0;

        // Referenzen auf Viewobjekte zuweisen
        zurueck = (Button) findViewById(R.id.zurueck);
        neuerZug = (Button) findViewById(R.id.neuerZug);
        fingerText = (TextView) findViewById(R.id.fingerText);
        farbeText = (TextView) findViewById(R.id.farbeText);
        zuganzahlText = (TextView) findViewById(R.id.zuganzahlText);

        // Zufallsgenerator zuweisen
        generator = new Random();

        // Arrays für einzelne Felder des Spielfeldes zuweisen
        grueneKreise = new ImageView[5];
        gelbeKreise = new ImageView[5];
        blaueKreise = new ImageView[5];
        roteKreise = new ImageView[5];

        // ArrayList für gesamtes Spielfeld zuweisen
        kreise = new ArrayList<ImageView[]>();
        kreise.add(0, grueneKreise);
        kreise.add(1, gelbeKreise);
        kreise.add(2, blaueKreise);
        kreise.add(3, roteKreise);

        // Arrays zuweisen
        finger = new String[5];
        farbe = new String[4];

        // Werte für Arrays zuweisen
        // Finger-Array
        finger[0] = "Daumen";
        finger[1] = "Zeigefinger";
        finger[2] = "Mittelfinger";
        finger[3] = "Ringfinger";
        finger[4] = "kleiner Finger";
        // Farben-Array
        farbe[0] = "gruen";
        farbe[1] = "gelb";
        farbe[2] = "blau";
        farbe[3] = "rot";

        // Farbe, Id und OnTouchListener für die einzelnen Felder des Spielfeldes zuweisen
        // grüne Felder
        for (int i = 0; i < grueneKreise.length; i++) {
            ImageView[] kreisArray = kreise.get(0);
            kreisArray[i].setImageResource(getResources().getIdentifier(farbe[0], "id", getPackageName()));
            kreisArray[i].setOnTouchListener(this);
            kreisArray[i] = (ImageView) findViewById(getResources().getIdentifier(
                    farbe[0] + (i + 1), "id", getPackageName()));
            kreisArray[i].getLayoutParams().height = 40;
        }
        // gelbe Felder
        for (int i = 0; i < gelbeKreise.length; i++) {
            ImageView[] kreisArray = kreise.get(1);
            kreisArray[i].setImageResource(getResources().getIdentifier(farbe[1], "id", getPackageName()));
            kreisArray[i].setOnTouchListener(this);
            kreisArray[i] = (ImageView) findViewById(getResources().getIdentifier(
                    farbe[1] + (i + 1), "id", getPackageName()));
            kreisArray[i].getLayoutParams().height = 40;
        }
        // blaue Felder
        for (int i = 0; i < blaueKreise.length; i++) {
            ImageView[] kreisArray = kreise.get(2);
            kreisArray[i].setImageResource(getResources().getIdentifier(farbe[2], "id", getPackageName()));
            kreisArray[i].setOnTouchListener(this);
            kreisArray[i] = (ImageView) findViewById(getResources().getIdentifier(
                    farbe[2] + (i + 1), "id", getPackageName()));
            kreisArray[i].getLayoutParams().height = 40;
        }
        // rote Felder
        for (int i = 0; i < roteKreise.length; i++) {
            ImageView[] kreisArray = kreise.get(3);
            kreisArray[i].setImageResource(getResources().getIdentifier(farbe[3], "id", getPackageName()));
            kreisArray[i].setOnTouchListener(this);
            kreisArray[i] = (ImageView) findViewById(getResources().getIdentifier(
                    farbe[3] + (i + 1), "id", getPackageName()));
            kreisArray[i].getLayoutParams().height = 40;
        }
    }

        /*
        imageGruen[0] = (ImageView) findViewById(R.id.gruen1);
        imageGruen[1] = (ImageView) findViewById(R.id.gruen2);
        imageGruen[2] = (ImageView) findViewById(R.id.gruen3);
        imageGruen[3] = (ImageView) findViewById(R.id.gruen4);
        imageGruen[4] = (ImageView) findViewById(R.id.gruen5);

        gelbeKreise[0] = (ImageView) findViewById(R.id.gelb1);
        gelbeKreise[1] = (ImageView) findViewById(R.id.gelb2);
        gelbeKreise[2] = (ImageView) findViewById(R.id.gelb3);
        gelbeKreise[3] = (ImageView) findViewById(R.id.gelb4);
        gelbeKreise[4] = (ImageView) findViewById(R.id.gelb5);

        blaueKreise[0] = (ImageView) findViewById(R.id.blau1);
        blaueKreise[1] = (ImageView) findViewById(R.id.blau2);
        blaueKreise[2] = (ImageView) findViewById(R.id.blau3);
        blaueKreise[3] = (ImageView) findViewById(R.id.blau4);
        blaueKreise[4] = (ImageView) findViewById(R.id.blau5);

        roteKreise[0] = (ImageView) findViewById(R.id.rot1);
        roteKreise[1] = (ImageView) findViewById(R.id.rot2);
        roteKreise[2] = (ImageView) findViewById(R.id.rot3);
        roteKreise[3] = (ImageView) findViewById(R.id.rot4);
        roteKreise[4] = (ImageView) findViewById(R.id.rot5);
        */

    // onClick-Methode des Buttons "neuer Zug",
    // die die beiden Methode newTask() und newMoveNumber() aufruft
    public void onClickNext (View view) {
        newTask();
        newMoveNumber();
    }

    // diese Methode ruf die beiden Methoden newFinger() und newColour() auf, in denen per
    // Zufallsprinzip der Finger und die Farbe für den nächsten Spielzug ermittelt werden;
    // außerdem wird ein Countdowm gestartet
    public void newTask() {
        newFinger();
        newColour();
        starteCountDown();
    }

    // zählt Anzahl der Züge mit und gibt diese in der TextView "zuganzahlText" aus
    public void newMoveNumber() {
        zuege = zuege + 1;
        zuganzahlText.setText("Anzahl der Züge: " + zuege);
    }

    // mit Hilfe des Zufallsgenerators wird der Finger für den nächsten Zug bestimmt und
    // in der TextView "fingerText" ausgegeben
    public void newFinger() {
        fingerwahl = generator.nextInt(5);
        fingerText.setText(finger[fingerwahl] + "       auf");
    }

    // mit Hilfe des Zufallsgenerators wird die Farbe für den nächsten Zug bestimmt und
    // in der TextView "farbeText" ausgegeben
    public void newColour() {
        farbwahl = generator.nextInt(4);
        farbeText.setText(farbe[farbwahl]);
    }

    // Timer, der einen Count-Down von 5 Sekunden startet und der, wenn diese Zeit abgelaufen ist,
    // ............................. macht !!!
    public void starteCountDown() {
        timer = new CountDownTimer(10000, 5) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Toast.makeText(SinglePlayerActivity.this, "Count-Down abgelaufen!", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    // OnTouchListener für die Tasten des Spielfeldes
    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()) {
            // grüne Tasten
            case R.id.gruen1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.gruen1);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gruen1);
                }
                break;
            case R.id.gruen2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.gruen2);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gruen2);
                }
                break;
            case R.id.gruen3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.gruen3);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gruen3);
                }
                break;
            case R.id.gruen4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.gruen4);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gruen4);
                }
                break;
            case R.id.gruen5:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.gruen5);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gruen5);
                }
                break;
            // gelbe Tasten
            case R.id.gelb1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.gelb1);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gelb1);
                }
                break;
            case R.id.gelb2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.gelb2);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gelb2);
                }
                break;
            case R.id.gelb3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.gelb3);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gelb3);
                }
                break;
            case R.id.gelb4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.gelb4);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gelb4);
                }
                break;
            case R.id.gelb5:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.gelb5);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gelb5);
                }
                break;
            // blaue Tasten
            case R.id.blau1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.blau1);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.blau1);
                }
                break;
            case R.id.blau2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.blau2);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.blau2);
                }
                break;
            case R.id.blau3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.blau3);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.blau3);
                }
                break;
            case R.id.blau4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.blau4);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.blau4);
                }
                break;
            case R.id.blau5:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.blau5);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.blau5);
                }
                break;
            // rote Tasten
            case R.id.rot1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.rot1);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.rot1);
                }
                break;
            case R.id.rot2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.rot2);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.rot2);
                }
                break;
            case R.id.rot3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.rot3);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.rot3);
                }
                break;
            case R.id.rot4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.rot4);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.rot4);
                }
                break;
            case R.id.rot5:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.rot5);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.rot5);
                }
                break;
        }
        return false;
    }

    // überprüft, ob die Taste neuer Zug bereits einmal betätigt wurde, d.h. ob im dem Moment,
    // in dem eine Taste gedrückt wird, überhaupt schon eine Spielanweisung besteht
    public boolean checkTask() {
        if(farbwahl == 0) {
            return true;
        }
        if(farbwahl == 1) {
            return true;
        }
        if(farbwahl == 2) {
            return true;
        }
        if(farbwahl == 3) {
            return true;
        }
        else {
            return false;
        }
    }

    // überprüft, ob das gedrückte Feld die in diesem Spielzug angezeigte Farbe hat
    public void checkActionDown(String farbeFeld, int idFeld) {
        if(checkTask() == true) {
            if(farbeFeld == farbe[farbwahl]) {
                saveFingerPosition(finger[fingerwahl], idFeld);
            }
            else {
                makeToast2();
            }
        }
        else {
            makeToast1();
        }
    }

    // in den Variablen wird gespeichert, welcher Finger gerade welche Taste gedrückt hält
    public void saveFingerPosition(String benutzterFinger, int idFeld) {
        if (benutzterFinger == finger[0]) {
            positionDaumen = idFeld;
        }
        if (benutzterFinger == finger[1]) {
            positionZeigefinger = idFeld;
        }
        if (benutzterFinger == finger[2]) {
            positionMittelfinger = idFeld;
        }
        if (benutzterFinger == finger[3]) {
            positionRingfinger = idFeld;
        }
        if (benutzterFinger == finger[4]) {
            positionKleinerFinger = idFeld;
        }
    }

    // überprüft, ob das Loslassen des Feldes berechtigt war, d.h. ob der neue Spielzug den Spieler
    // anweist, den Finger, welcher zuvor dieses Feld gedückt hielt, nun auf ein anderes Feld zu setzen
    public void checkActionUp(int idFeld) {
        if(idFeld == positionDaumen && finger[0] == finger[fingerwahl]) {
            makeToast3();
        }
        if(idFeld == positionZeigefinger && finger[1] == finger[fingerwahl]) {
            makeToast3();
        }
        if(idFeld == positionMittelfinger && finger[2] == finger[fingerwahl]) {
            makeToast3();
        }
        if(idFeld == positionRingfinger && finger[3] == finger[fingerwahl]) {
            makeToast3();
        }
        if(idFeld == positionKleinerFinger && finger[4] == finger[fingerwahl]) {
            makeToast3();
        }
        else {
            makeToast2();
        }
    }

    public void makeToast1() {
        Toast.makeText(SinglePlayerActivity.this, "Es gibt noch keine Aufgabe. Betätige die Taste" +
                " 'neuer Zug' um eine Spielanweisung zu erhalten!", Toast.LENGTH_SHORT).show();
    }

    public void makeToast2() {
        Toast.makeText(SinglePlayerActivity.this, "Verloren! Spiel ist vorbei.",
                Toast.LENGTH_SHORT).show();
    }

    public void makeToast3() {
        Toast.makeText(SinglePlayerActivity.this, "Taste berechtigt losgelassen!",
                Toast.LENGTH_SHORT).show();
    }

    // durch Buttonklick wird die SinglePlayerActivty geschlossen
    // der User findet sich auf der PlayModeActivity wieder
    public void onClickBack (View view) {
        finish();
    }

}
