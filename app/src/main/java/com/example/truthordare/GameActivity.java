package com.example.truthordare;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {


    SpinnerBoard spinnerView;
    Button spin;
    ArrayList<String> names;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle bundle=getIntent().getExtras();
        names=bundle.getStringArrayList("players");


        spinnerView = findViewById(R.id.spinner);

        spinnerView.setNames(names);

        spin = findViewById(R.id.spin);


        spin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerView.startAnimation();
            }
        });


        spinnerView.setOnNameChoosing(new SpinnerBoard.OnNameChoosing() {
            @Override
            public void getName(String name) {
                Dialog dialog=Dialog.newInstance(name);
                dialog.show(getSupportFragmentManager(),"dialog");
            }
        } );

    }



}
