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

public class PlayActivity extends AppCompatActivity implements View.OnTouchListener {

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
        groesseKreis = 220;

        // Referenzen auf Viewobjekte zuweisen
        zurueck = (Button) findViewById(R.id.zurueck);
        spielStarten = (Button) findViewById(R.id.spielStarten);
        aktuellerFinger = (TextView) findViewById(R.id.aktuellerFinger);
        aktuelleFarbe = (TextView) findViewById(R.id.aktuelleFarbe);
        anzahlZuege = (TextView) findViewById(R.id.anzahlZuege);

        // Zufallsgenerator zuweisen
        generator = new Random();

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
        Intent intent = getIntent();
        groesseKreis = intent.getIntExtra("groesseSpielfeld", 0);

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
                kreisArray[i].getLayoutParams().height = groesseKreis; //80, 220
                kreisArray[i].getLayoutParams().width = groesseKreis; // 80, 220
            }
        }
    }

    // onClick-Methode des Buttons "neuer Zug",
    // die die beiden Methode newTask() und newMoveNumber() aufruft
    public void onClickNext (View view) {
        newTask();
        newMoveNumber();
    }

    // zählt Anzahl der Züge mit und gibt diese in der TextView "zuganzahlText" aus
    public void newMoveNumber() {
        zuege = zuege + 1;
        anzahlZuege.setText("Anzahl der Züge: " + zuege);
    }

    // diese Methode

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
     * startet einen Count-Down von 3 Sekunden der, wenn diese Zeit abgelaufen ist, ohne das die
     * richtige Taste gedrückt wurde, das Spiel beendet. Der Timer wurde eingeführt, um die
     * Schwierigkeit des Spiels zu steigern.
     */
    public void startCountDown() {
        timer = new CountDownTimer(10000, 3) {
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
     * @param view
     * @param event
     * @return
     */
    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()) {
            // grüne Tasten
            case R.id.gruen1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.gruen1, event, xGruen[0], yGruen[0]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xGruen[0], yGruen[0]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gruen1, xGruen[0], yGruen[0]);
                }
                break;
            case R.id.gruen2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.gruen2, event, xGruen[1], yGruen[1]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xGruen[1], yGruen[1]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gruen2, xGruen[1], yGruen[1]);
                }
                break;
            case R.id.gruen3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.gruen3, event, xGruen[2], yGruen[2]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xGruen[2], yGruen[2]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gruen3, xGruen[2], yGruen[2]);
                }
                break;
            case R.id.gruen4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.gruen4, event, xGruen[3], yGruen[3]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xGruen[3], yGruen[3]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gruen4, xGruen[3], yGruen[3]);
                }
                break;
            case R.id.gruen5:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[0], R.id.gruen5, event, xGruen[4], yGruen[4]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xGruen[4], yGruen[4]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gruen5, xGruen[4], yGruen[4]);
                }
                break;
            // gelbe Tasten
            case R.id.gelb1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[1], R.id.gelb1, event, xGelb[0], yGelb[0]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xGelb[0], yGelb[0]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gelb1, xGelb[0], yGelb[0]);
                }
                break;
            case R.id.gelb2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[1], R.id.gelb2, event, xGelb[1], yGelb[1]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xGelb[0], yGelb[0]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gelb2, xGelb[1], yGelb[11]);
                }
                break;
            case R.id.gelb3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[1], R.id.gelb3, event, xGelb[2], yGelb[2]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xGelb[0], yGelb[0]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gelb3, xGelb[2], yGelb[2]);
                }
                break;
            case R.id.gelb4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[1], R.id.gelb4, event, xGelb[3], yGelb[3]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xGelb[3], yGelb[3]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gelb4, xGelb[3], yGelb[3]);
                }
                break;
            case R.id.gelb5:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[1], R.id.gelb5, event, xGelb[4], yGelb[4]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xGelb[4], yGelb[4]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.gelb5, xGelb[4], yGelb[4]);
                }
                break;
            // blaue Tasten
            case R.id.blau1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[2], R.id.blau1, event, xBlau[0], yBlau[0]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xBlau[0], yBlau[0]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.blau1, xBlau[0], yBlau[0]);
                }
                break;
            case R.id.blau2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[2], R.id.blau2, event, xBlau[1], yBlau[1]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xBlau[1], yBlau[1]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.blau2, xBlau[1], yBlau[1]);
                }
                break;
            case R.id.blau3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[2], R.id.blau3, event, xBlau[2], yBlau[2]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xBlau[2], yBlau[2]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.blau3, xBlau[2], yBlau[2]);
                }
                break;
            case R.id.blau4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[2], R.id.blau4, event, xBlau[3], yBlau[3]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xBlau[3], yBlau[3]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.blau4, xBlau[3], yBlau[3]);
                }
                break;
            case R.id.blau5:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[2], R.id.blau5, event, xBlau[4], yBlau[4]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xBlau[4], yBlau[4]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.blau5, xBlau[4], yBlau[4]);
                }
                break;
            // rote Tasten
            case R.id.rot1:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[3], R.id.rot1, event, xRot[0], yRot[0]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xRot[0], yRot[0]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.rot1, xBlau[0], yBlau[0]);
                }
                break;
            case R.id.rot2:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[3], R.id.rot2, event, xRot[1], yRot[1]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xRot[1], yRot[1]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.rot2, xRot[1], yRot[1]);
                }
                break;
            case R.id.rot3:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[3], R.id.rot3, event, xRot[2], yRot[2]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xRot[2], yRot[2]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.rot3, xRot[2], yRot[2]);
                }
                break;
            case R.id.rot4:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[3], R.id.rot4, event, xRot[3], yRot[3]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xRot[3], yRot[3]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.rot4, xRot[3], yRot[3]);
                }
                break;
            case R.id.rot5:
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    checkActionDown(farbe[3], R.id.rot5, event, xRot[4], yRot[4]);
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    extentOfMove(event, xRot[4], yRot[4]);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    checkActionUp(R.id.rot5, xRot[4], yRot[4]);
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
     * @param event
     * @param xKoordinate
     * @param yKoordinate
     */
    public void checkActionDown(String farbeFeld, int idFeld, MotionEvent event,
                                float xKoordinate, float yKoordinate) {
        if(checkTask() == true) {
            if(farbeFeld == farbe[farbwahl]) {
                saveCoordinates(event, xKoordinate, yKoordinate);
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
     * weist den Variablen "xKoordinate" und "yKoordinate" jeweils die x- bzw die y-Koordinate des
     * MotionEvents "event", d.h. der Bildschrimberührung, zu.
     *
     * @param event
     * @param xKoordinate
     * @param yKoordinate
     */
    public void saveCoordinates(MotionEvent event, float xKoordinate, float yKoordinate) {
        xKoordinate = event.getX();
        yKoordinate = event.getY();
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
     * überprüft, das Ausmaß der Bewegung des MotionEvents "event", d.h. der Bildschrimkontakt
     * noch innerhalb des jeweiligen Feldes liegt.
     *
     * @param event
     * @param xKoordinate
     * @param yKoordinate
     */
    public void extentOfMove(MotionEvent event, float xKoordinate, float yKoordinate) {
        if(event.getX() > xKoordinate + (groesseKreis/2) ||
                event.getY() > yKoordinate + (groesseKreis/2)) {
            gameOver();
        }
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
     * @param xKoordinate
     * @param yKoordinate
     * @return
     */
    public boolean checkActionUp(int idFeld, float xKoordinate, float yKoordinate) {
        if(farbwahl == 9) {
            return true;
        }
        if(idFeld == positionDaumen && finger[0] == finger[fingerwahl]) {
            resetCoordinates(xKoordinate, yKoordinate);
            return true;
        }
        if(idFeld == positionZeigefinger && finger[1] == finger[fingerwahl]) {
            resetCoordinates(xKoordinate, yKoordinate);
            return true;
        }
        if(idFeld == positionMittelfinger && finger[2] == finger[fingerwahl]) {
            resetCoordinates(xKoordinate, yKoordinate);
            return true;
        }
        if(idFeld == positionRingfinger && finger[3] == finger[fingerwahl]) {
            resetCoordinates(xKoordinate, yKoordinate);
            return true;
        }
        if(idFeld == positionKleinerFinger && finger[4] == finger[fingerwahl]) {
            resetCoordinates(xKoordinate, yKoordinate);
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

    /**
     *
     * @param xKoordinate
     * @param yKoordinate
     */
    public void resetCoordinates(float xKoordinate, float yKoordinate) {
        xKoordinate = 0;
        yKoordinate = 0;
    }

    /**
     * beendet das Spiel, indem dem die Methode "addToHighscoreList" ausferufen wird.
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

    /**
     *
     */
    public void addToHighscoreList() {
        Intent spielVorbei = new Intent(this, GameOverActivity.class);
        spielVorbei.putExtra("zuege",zuege);
        startActivity(spielVorbei);
    }

    /**
     * schließt durch Buttonklick auf den Button "zurück" die "PlayActivity" und der User findet
     * sich auf der MainActivity wieder.
     * @param view
     */
    public void onClickBack (View view) {
        finish();
    }

}
