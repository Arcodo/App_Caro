
package com.example.admin.fingertwister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlayModeActivity extends AppCompatActivity {

    private TextView ausgabeWahl;
    private Button buttonEinzelSp;
    private Button buttonZweiSp;
    private Button zurueck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_mode);

        ausgabeWahl = (TextView) findViewById(R.id.ausgabeWahl);
        buttonEinzelSp = (Button) findViewById(R.id.buttonEinzelSp);
        buttonZweiSp = (Button) findViewById(R.id.buttonZweiSp);
        zurueck = (Button) findViewById(R.id.zurueck);
    }

    public void OnClickSingle (View view) {
        Intent einzelspielerModus = new Intent(this, SinglePlayerActivity.class);
        startActivity(einzelspielerModus);
    }

    public void OnClickMulti (View view) {
        Intent zweispielerModus = new Intent(this, MultiPlayerActivity.class);
        startActivity(zweispielerModus);
    }

    public void onClickBack (View view) {
        finish();
    }

}

