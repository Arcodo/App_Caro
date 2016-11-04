package com.example.admin.fingertwister;

import android.content.Intent;
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

public class PlayActivity extends AppCompatActivity implements View.OnTouchListener{

    // Viewobjekte
    private Button zurueck;
    private Button spielStarten;
    private TextView aktuellerFinger;
    private TextView aktuelleFarbe;
    private TextView anzahlZuege;

    // Bild-Arrays für die einzelnen Felder des Spielfeldes und deren Koordianaten
    // hier float, da berechnung damit schneller geht !
    // (entspricht double, ist aber ein wenig ungenauer -> schneller
    // grüne Felder
    private ImageView[] grueneKreise;
    private float[] xGruen;
    private float[] yGruen;
    // gelbe Felder
    private ImageView[] gelbeKreise;
    private float[] xGelb;
    private float[] yGelb;
    // blaue Felder
    private ImageView[] blaueKreise;
    private float[] xBlau;
    private float[] yBlau;
    // rote Felder
    private ImageView[] roteKreise;
    private float[] xRot;
    private float[] yRot;

    // Array-List für gesamtes Spielfeld
    private ArrayList<ImageView[]> kreise;
    private ImageView[] kreisArray;

    // Arrays zur zufälligen Ausgabe von Finger und Farbe für jeden Spielzug
    private String[] finger;
    private String[] farbe;

    // Objekte aus Java Paket
    private Random generator;

    //
    private CountDownTimer timer;
    private Intent spielVorbei;

    // Variablen
    private int fingerwahl;
    private int farbwahl;
    private int zuege;
    private int positionDaumen;
    private int positionZeigefinger;
    private int positionMittelfinger;
    private int positionRingfinger;
    private int positionKleinerFinger;
    private boolean spielVerloren;
    private int groesseKreis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Variablen den Wert zuweisen
        /* der Variable 'farbwahl' wird hier der Wert 9 zugewiesen um in der Methode 'checkTask'
        überprüfen zu können, ob bereits eine Aufgabenstellung besteht oder ob die Taste 'nächster
        Zug' erst noch betätigt werden muss. Ich habe bewusst den Wert 9 gewählt, da er klar von
        den Werten des Zufallsgenerators, von welcher er in der Methode 'newFinger' überschrieben
        wird, zu unterscheiden ist, denn die Werte des Zufallsgenerators liegen nur zwischen 0 und 3. */
        farbwahl = 9;
        zuege = -1;
        positionDaumen = 0;
        positionZeigefinger = 0;
        positionMittelfinger = 0;
        positionRingfinger = 0;
        positionKleinerFinger = 0;
        spielVerloren = false;
        groesseKreis = 0;

        // Referenzen auf Viewobjekte zuweisen
        zurueck = (Button) findViewById(R.id.zurueck);
        spielStarten = (Button) findViewById(R.id.spielStarten);
        aktuellerFinger = (TextView) findViewById(R.id.aktuellerFinger);
        aktuelleFarbe = (TextView) findViewById(R.id.aktuelleFarbe);
        anzahlZuege = (TextView) findViewById(R.id.anzahlZuege);

        // Zufallsgenerator zuweisen
        generator = new Random();

        // Referenz auf Intent zuweisen
        spielVorbei = new Intent(this, GameOverActivity.class);

        // Arrays für einzelne Felder des Spielfeldes und deren Koordinaten zuweisen
        grueneKreise = new ImageView[5];
        xGruen = new float[5];
        yGruen = new float[5];
        gelbeKreise = new ImageView[5];
        xGelb = new float[5];
        yGelb = new float[5];
        blaueKreise = new ImageView[5];
        xBlau = new float[5];
        yBlau = new float[5];
        roteKreise = new ImageView[5];
        xRot = new float[5];
        yRot = new float[5];

        // ArrayList für gesamtes Spielfeld zuweisen
        kreise = new ArrayList<ImageView[]>();
        kreisArray = new ImageView[20];

        // Elemenz
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

        // Inhalt des Intents wird der Variable "groesseKreis" zugewiesen
        Intent kreismas = getIntent();
        groesseKreis = kreismas.getIntExtra("groesseSpielfeld", 0);

        // Farbe, Id und OnTouchListener für die einzelnen Felder des Spielfeldes zuweisen, wobei
        // dem ImageView-Array "kreisArray" bei jedem Durchlauf der ersten for-Schleife ein neuer
        // Wert, in diesem Fall immer ein anderes Array aus der Array-List "kreise", zugewiesen wird
        for (int k = 0; k < 4; k++) {
            ImageView[] kreisArray = kreise.get(k);
            for (int i = 0; i < kreisArray.length; i++) {
                kreisArray[i] = (ImageView) findViewById(getResources().getIdentifier(
                        farbe[k] + (i + 1), "id", getPackageName()));
                kreisArray[i].setOnTouchListener(this);
                // die hier zugewiesenen Höhen und Breiten der Bilder überschreiben die in der
                // zugehörigen xml-Datei zugewiesenen Werte
                kreisArray[i].getLayoutParams().height = groesseKreis;
                kreisArray[i].getLayoutParams().width = groesseKreis;
            }
        }
    }

    // onClick-Methode des Buttons "neuer Zug",
    // die die beiden Methode newTask() und newMoveNumber() aufruft
    public void onClickNext (View view) {
        newTask();
        newMoveNumber();
    }

    /**
     * zählt Anzahl der Züge mit und gibt diese in der TextView "zuganzahlText" aus.
     */
    public void newMoveNumber() {
        zuege = zuege + 1;
        anzahlZuege.setText("Anzahl der Züge:      " + zuege);
    }

    /**
     * ruf die beiden Methoden newFinger() und newColour() auf, in denen per Zufallsprinzip der
     * Finger und die Farbe für den nächsten Spielzug ermittelt werden. Außerdem wird ein
     * Countdowm gestartet und der Button '' unsichtbar gesetzt.
     */
    public void newTask() {
        newFinger();
        newColour();
        startCountDown();
        makeButtonInvisible();
    }

    /**
     * bestimmt mit Hilfe des Zufallsgenerators den Finger für den nächsten Zug und gibt diesen
     * über die TextView "fingerText" aus.
     */
    public void newFinger() {
        fingerwahl = generator.nextInt(5);
        aktuellerFinger.setText(finger[fingerwahl] + "       auf");
    }

    /**
     * bestimmt mit Hilfe des Zufallsgenerators die Farbe für den nächsten Zug und gibt diese
     * über die TextView "farbeText" aus.
     */
    public void newColour() {
        farbwahl = generator.nextInt(4);
        aktuelleFarbe.setText(farbe[farbwahl]);
    }

    /**
     * startet einen Count-Down von 5 Sekunden der, wenn diese Zeit abgelaufen ist, ohne das die
     * richtige Taste gedrückt wurde, das Spiel beendet. Der Timer wurde eingeführt, um die
     * Schwierigkeit des Spiels zu steigern.
     */
    public void startCountDown() {
        timer = new CountDownTimer(10000, 5) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                // gameOver();
            }
        }.start();
    }

    /**
     *
     */
    public void cancelTimer() {
        timer.cancel();
        newTask();
        newMoveNumber();
    }

    /**
     * deaktiviert den Butten "neuerZug", sodass er vom Spieler nicht mehr manuell betätigt
     * werden kann.
     */
    public void makeButtonInvisible() {
        spielStarten.setEnabled(false);
    }

    // OnTouchListener für die Tasten des Spielfeldes
    // "return true;" nach MotionEvent.ACTION_DOWN ist notwendig, damit MotionEvent.ACTION_UP
    // ausgeführt werden kann.
    /**
     *
     *
     * @param view
     * @param event
     * @return
     */
    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()) {
            // grüne Tasten
            case R.id.gruen1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xGruen[0] = event.getX();
                    yGruen[0] = event.getY();
                    checkActionDown(farbe[0], R.id.gruen1);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xGruen[0] + (groesseKreis/2) ||
                                event.getY() > yGruen[0] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gruen1);
                }
                break;
            case R.id.gruen2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xGruen[1] = event.getX();
                    yGruen[1] = event.getY();
                    checkActionDown(farbe[0], R.id.gruen2);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xGruen[1] + (groesseKreis/2) ||
                                event.getY() > yGruen[1] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gruen2);
                }
                break;
            case R.id.gruen3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xGruen[2] = event.getX();
                    yGruen[2] = event.getY();
                    checkActionDown(farbe[0], R.id.gruen3);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xGruen[2] + (groesseKreis/2) ||
                                event.getY() > yGruen[2] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gruen3);
                }
                break;
            case R.id.gruen4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xGruen[3] = event.getX();
                    yGruen[3] = event.getY();
                    checkActionDown(farbe[0], R.id.gruen4);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xGruen[3] + (groesseKreis/2) ||
                                event.getY() > yGruen[3] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gruen4);
                }
                break;
            case R.id.gruen5:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xGruen[4] = event.getX();
                    yGruen[4] = event.getY();
                    checkActionDown(farbe[0], R.id.gruen5);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xGruen[4] + (groesseKreis/2) ||
                                event.getY() > yGruen[4] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gruen5);
                }
                break;
            // gelbe Tasten
            case R.id.gelb1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xGelb[0] = event.getX();
                    yGelb[0] = event.getY();
                    checkActionDown(farbe[1], R.id.gelb1);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xGelb[0] + (groesseKreis/2) ||
                                event.getY() > yGelb[0] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gelb1);
                }
                break;
            case R.id.gelb2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xGelb[1] = event.getX();
                    yGelb[1] = event.getY();
                    checkActionDown(farbe[1], R.id.gelb2);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xGelb[1] + (groesseKreis/2) ||
                                event.getY() > yGelb[1] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gelb2);
                }
                break;
            case R.id.gelb3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xGelb[2] = event.getX();
                    yGelb[2] = event.getY();
                    checkActionDown(farbe[1], R.id.gelb3);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xGelb[2] + (groesseKreis/2) ||
                                event.getY() > yGelb[2] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gelb3);
                }
                break;
            case R.id.gelb4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xGelb[3] = event.getX();
                    yGelb[3] = event.getY();
                    checkActionDown(farbe[1], R.id.gelb4);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xGelb[3] + (groesseKreis/2) ||
                                event.getY() > yGelb[3] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gelb4);
                }
                break;
            case R.id.gelb5:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xGelb[4] = event.getX();
                    yGelb[4] = event.getY();
                    checkActionDown(farbe[1], R.id.gelb5);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xGelb[4] + (groesseKreis/2) ||
                                event.getY() > yGelb[4] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gelb5);
                }
                break;
            // blaue Tasten
            case R.id.blau1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xBlau[0] = event.getX();
                    yBlau[0] = event.getY();
                    checkActionDown(farbe[2], R.id.blau1);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xBlau[0] + (groesseKreis/2) ||
                                event.getY() > yBlau[0] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.blau1);
                }
                break;
            case R.id.blau2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xBlau[1] = event.getX();
                    yBlau[1] = event.getY();
                    checkActionDown(farbe[2], R.id.blau2);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xBlau[1] + (groesseKreis/2) ||
                                event.getY() > yBlau[1] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.blau2);
                }
                break;
            case R.id.blau3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xBlau[2] = event.getX();
                    yBlau[2] = event.getY();
                    checkActionDown(farbe[2], R.id.blau3);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xBlau[2] + (groesseKreis/2) ||
                                event.getY() > yBlau[2] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.blau3);
                }
                break;
            case R.id.blau4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xBlau[3] = event.getX();
                    yBlau[3] = event.getY();
                    checkActionDown(farbe[2], R.id.blau4);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xBlau[3] + (groesseKreis/2) ||
                                event.getY() > yBlau[3] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.blau4);
                }
                break;
            case R.id.blau5:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xBlau[4] = event.getX();
                    yBlau[4] = event.getY();
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xBlau[4] + (groesseKreis/2) ||
                                event.getY() > yBlau[4] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.blau5);
                }
                break;
            // rote Tasten
            case R.id.rot1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xRot[0] = event.getX();
                    yRot[0] = event.getY();
                    checkActionDown(farbe[3], R.id.rot1);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xRot[0] + (groesseKreis/2) ||
                                event.getY() > yRot[0] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.rot1);
                }
                break;
            case R.id.rot2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xRot[1] = event.getX();
                    yRot[1] = event.getY();
                    checkActionDown(farbe[3], R.id.rot2);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xRot[1] + (groesseKreis/2) ||
                                event.getY() > yRot[1] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.rot2);
                }
                break;
            case R.id.rot3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xRot[2] = event.getX();
                    yRot[2] = event.getY();
                    checkActionDown(farbe[3], R.id.rot3);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xRot[2] + (groesseKreis/2) ||
                                event.getY() > yRot[2] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.rot3);
                }
                break;
            case R.id.rot4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xRot[3] = event.getX();
                    yRot[3] = event.getY();
                    checkActionDown(farbe[3], R.id.rot4);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xRot[3] + (groesseKreis/2) ||
                                event.getY() > yRot[3] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.rot4);
                }
                break;
            case R.id.rot5:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xRot[4] = event.getX();
                    yRot[4] = event.getY();
                    checkActionDown(farbe[3], R.id.rot5);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    if(farbwahl != 9) {
                        if(event.getX() > xRot[4] + (groesseKreis/2) ||
                                event.getY() > yRot[4] + (groesseKreis/2)) {
                            gameOver();
                        }
                    }
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.rot5);
                }
                break;
        }
        return false;
    }

    /**
     * überprüft, ob das gedrückte Feld die in diesem Spielzug angezeigte Farbe hat.
     *
     * @param farbeFeld
     * @param idFeld
     */
    public void checkActionDown(String farbeFeld, int idFeld) {
        if(checkTask() == true) {
            if(farbeFeld == farbe[farbwahl]) {
                saveFingerPosition(finger[fingerwahl], idFeld);
            }
            else {
                gameOver();
            }
        }
        else {
            makeToastNoTask();
        }
    }

    /**
     * überprüft, ob die Taste neuer Zug bereits einmal betätigt wurde, d.h. ob im dem Moment,
     * in dem eine Taste gedrückt wird, überhaupt schon eine Spielanweisung besteht.
     *
     * @return
     */
    public boolean checkTask() {
        if(farbwahl == 9) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * überprüft welcher Finger die Taste aktuell gedrückt hält und weist der Varaible für den
     * entsprechnden Finger die ID des gedrückten Feldes zu.
     *
     * @param benutzterFinger
     * @param idFeld
     */
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
        cancelTimer();
    }

    /**
     * überprüft, ob das Loslassen des Feldes berechtigt war, d.h. ob der neue Spielzug den Spieler
     * anweist, den Finger, welcher zuvor dieses Feld gedückt hielt, nun auf ein anderes Feld zu
     * setzen.
     *
     * Hinweis: Hierbei ist idFeld das Feld welches losgelassen wurde, position(jeweiliger Finger)
     * die letzte Position des jeweiligen Fingers gespeichert über die Id der zuletzt damit
     * gedrückten Taste, finger['Zahl von 0 bis 4'] der gleiche Finger, der jeweils mit
     * position(jeweiliger Finger) bezeichnet wird und finger[fingerwahl] steht für den im neuesten
     * Spielzug angezeigten Finger. Im Fall 'farbwahl == 9' wird überprüft, ob der Variable farbwahl
     * überhaupt schon ein Wert zugewiesen wurde oder ob noch gar kein Spielzug erzeugt wurde. Wenn
     * noch kein Spielzug durch die Betätigung der Taste 'nächster Zug' erzeugt wurde, ist der Wert
     * der Variable farbwahl 9.
     *
     * @param idFeld
     * @return
     */
    public boolean checkActionUp(int idFeld) {
        if(farbwahl == 9) {
            return true;
        }
        if(idFeld == positionDaumen && finger[0] == finger[fingerwahl]) {
            return true;
        }
        if(idFeld == positionZeigefinger && finger[1] == finger[fingerwahl]) {
            return true;
        }
        if(idFeld == positionMittelfinger && finger[2] == finger[fingerwahl]) {
            return true;
        }
        if(idFeld == positionRingfinger && finger[3] == finger[fingerwahl]) {
            return true;
        }
        if(idFeld == positionKleinerFinger && finger[4] == finger[fingerwahl]) {
            return true;
        }
        else {
            gameOver();
            return false;
        }
    }

    /**
     *
     */
    public void makeToastNoTask() {
        Toast.makeText(PlayActivity.this, "Es gibt noch keine Aufgabe. Betätige die Taste" +
                " 'neuer Zug' um eine Spielanweisung zu erhalten!", Toast.LENGTH_SHORT).show();
    }

    /** beendet das Spiel, indem dem die Methode "addToHighscoreList" ausferufen wird.
     *
     * Hinweis: Diese Methode soll nur ein einziges mal ausgeführt werden, nämlich nur wenn das
     * erste Mal ein Fehler gemacht wird, der das Spile beendet. Um zu verhindern, dass zum
     * Beispiel beim unberechtigten Loslassen der übrigen, bis zum Ende des Spiels gedrückt
     * gehaltenen, Tasten jedes Mal wieder ein neuer Intent erzeugt wird, gibt es die Variable
     * "spielVerloren", die sobald diese Methode das erste Mal aufgerufen wird, auf "true" gesetzt
     * wird.
     */
    public void gameOver() {
        if(spielVerloren == false) {
            spielVerloren = true;
            addToHighscoreList();
        }
    }

    /** übergibt den Wert der Varaible "zuege" an das Intent "spielVorbei".
     * ruft Methode "setResult()" mit Eingangsparametern "2" als resetCode und "spielVorbei" als
     * Intent auf und springt dadurch in die Methode "onActivityresult" der MainActivity.
     * beendet anschließend diese Activity.
     */
    public void addToHighscoreList() {
        spielVorbei.putExtra("zuege", zuege);
        setResult(2, spielVorbei);
        finish();
    }

    /** onClick-Listener des Buttons "Zurück", der die Activity beendet.
     *
     * @param view ist das angeklickte View-Objekt, hier der Button "Zurück".
     */
    public void onClickBack (View view) {
        finish();
    }

}
