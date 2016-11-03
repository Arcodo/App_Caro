package com.example.admin.fingertwister;


import android.widget.ImageView;

public class Field {

    private String id;
    private String farbe;
    private int groesse;

    public Field(int idNeu, String farbeNeu, int groesseNeu) {
        this.setId(farbeNeu, idNeu);
        this.setColour(farbeNeu);
        this.setDimensions(groesseNeu);
    }

    public void setId(String farbe, int id) {
        this.id = farbe + id;
    }

    public String getId() {
        return id;
    }

    public void setColour(String farbe) {
        this.farbe = farbe;
    }

    public String getColour(){
        return farbe;
    }

    public void setDimensions(int groesse) {
        this.groesse = groesse;
    }

    public int getDimensions() {
        return groesse;
    }
}
