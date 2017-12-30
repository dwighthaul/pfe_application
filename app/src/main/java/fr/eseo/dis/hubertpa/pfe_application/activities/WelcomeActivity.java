package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {

	Button visiteur;
	Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		login = (Button) findViewById(R.id.button_login);
		visiteur = (Button) findViewById(R.id.button_visitor);

		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Log.d("Welcome", "Connect");
				LoginActivity();
			}

		});

		visiteur.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Log.d("Welcome", "Visiteur");
				VisitorActivity();
			}
		});

	}

	public void LoginActivity() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	public void VisitorActivity() {
		//VisitorActivity
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

}
