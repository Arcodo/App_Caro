package com.example.admin.fingertwister;

import android.widget.ImageView;


public class Field {

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

    // Array für die unterschiedlichen Farben des Spielfeldes
    private String[] farbe;

    // Arrays für die unterschiedlichen Finger des Spielers
    private String[] finger;


    public Field() {
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

        // Referenz auf Array "farbe" und dessen Werte zuweisen
        farbe = new String[4];
        farbe[0] = "gruen";
        farbe[1] = "gelb";
        farbe[2] = "blau";
        farbe[3] = "rot";

        // Referenz auf Array "finger" und dessen Werte zuweisen
        finger = new String[5];
        finger[0] = "Daumen";
        finger[1] = "Zeigefinger";
        finger[2] = "Mittelfinger";
        finger[3] = "Ringfinger";
        finger[4] = "kleiner Finger";
    }

    public ImageView[] getGreenFields() {
        return grueneKreise;
    }

    public ImageView[] getYellowFields() {
        return gelbeKreise;
    }

    public ImageView[] getBlueFields() {
        return blaueKreise;
    }

    public ImageView[] getRedFields() {
        return roteKreise;
    }

    public void setX(String farbeKreis, int nummer, float wert) {
        if(farbeKreis == farbe[0]) {
            xGruen[nummer] = wert;
        }
        if(farbeKreis == farbe[1]) {
            xGelb[nummer] = wert;
        }
        if(farbeKreis == farbe[2]) {
            xBlau[nummer] = wert;
        }
        if(farbeKreis == farbe[3]) {
            xRot[nummer] = wert;
        }
    }

    public float getX(String farbeKreis, int nummer) {
        if(farbeKreis == farbe[0]) {
            return xGruen[nummer];
        }
        if(farbeKreis == farbe[1]) {
            return xGelb[nummer];
        }
        if(farbeKreis == farbe[2]) {
            return xBlau[nummer];
        }
        else {
            return xRot[nummer];
        }
    }

    public void setY(String farbeKreis, int nummer, float wert) {
        if(farbeKreis == farbe[0]) {
            yGruen[nummer] = wert;
        }
        if(farbeKreis == farbe[1]) {
            yGelb[nummer] = wert;
        }
        if(farbeKreis == farbe[2]) {
            yBlau[nummer] = wert;
        }
        if(farbeKreis == farbe[3]) {
            yRot[nummer] = wert;
        }
    }

    public float getY(String farbeKreis, int nummer) {
        if(farbeKreis == farbe[0]) {
            return yGruen[nummer];
        }
        if(farbeKreis == farbe[1]) {
            return yGelb[nummer];
        }
        if(farbeKreis == farbe[2]) {
            return yBlau[nummer];
        }
        else {
            return yRot[nummer];
        }
    }

    public String getColour(int farbeNeu) {
        return farbe[farbeNeu];
    }

    public String getFinger(int fingerNeu) {
        return finger[fingerNeu];
    }
}
