package fr.eseo.dis.hubertpa.pfe_application.activities.jpo_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.eseo.dis.hubertpa.pfe_application.R;

public class JPODisplayImageActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jpodisplay_image);

		Intent intent = getIntent();
		String stringImageProject = intent.getStringExtra("stringImageProject");
	}
}
