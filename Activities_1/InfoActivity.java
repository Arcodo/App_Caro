package com.example.admin.fingertwister;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    // Viewobjekte
    private Button zurueck;
    private TextView spielbeschreibung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // Referenzen auf Viewobjekte zuweisen
        zurueck = (Button) findViewById(R.id.zurueck);
        spielbeschreibung = (TextView) findViewById(R.id.spielbeschreibung);

        spielbeschreibung.setText("Die Fingertwister-App verfügt über einen Einzelspieler- und " +
                "einen Zweispieler-Modus. Die App ermittelt für jeden Spielzug eine zufällige " +
                "Farbe und einen Finger, der dann eine Taste mit der ermittelten Farbe gedrückt " +
                "halten muss bis für ihn eine neue, andere Farbe ermittelt wird. Mit welcher Hand " +
                "man das Spiel bewältigen möchte ist jedem selbst überlassen. Sobald man mit einem " +
                "Finger eine Taste verlässt, ist das Spiel vorbei. Ziel im Einzelspieler-Modus ist " +
                "es möglichst viele Runden zu bewältigen. Im Zweispieler-Modus wird das Ziel verfolgt, " +
                "mehr Runden zu bewältigen als sein Gegner und somit das Spiel zu gewinnen.");
    }

    public void onClickBack (View view) {
        finish();
    }

}
