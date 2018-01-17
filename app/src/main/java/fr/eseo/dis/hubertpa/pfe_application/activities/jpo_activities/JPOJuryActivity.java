package fr.eseo.dis.hubertpa.pfe_application.activities.jpo_activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import fr.eseo.dis.hubertpa.pfe_application.R;

public class JPOJuryActivity extends AppCompatActivity{
    Button add_jpo_jury;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpo_jury);

        //add_jpo_jury = findViewById(R.id.add_jury_button);

        //setListeners();
    }

    /*private void setListeners() {
        add_jpo_jury.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                addJpoJury();
            }

        });
    }

    private void addJpoJury() {

    }*/
}
