package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.UserLoginTask;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.VolleyCallback;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;

public class WelcomeActivity extends AppCompatActivity {

	Button visiteur;
	Button login;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

//		SharedPreferences prefs = getSharedPreferences("userSettings", MODE_PRIVATE);
//		SharedPreferences.Editor editor = prefs.edit();
//		editor.putBoolean("isConnected", false);
//		editor.commit();


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
		UserLoginTask mAuthTask = new UserLoginTask(WebServiceConnexion.DEFAULT_LOGIN, WebServiceConnexion.DEFAULT_PASSWORD, this,  new VolleyCallback() {
			@Override
			public void onSuccessLogin(Boolean result) {
				if (result) {
					Intent intent = new Intent(WelcomeActivity.this, ProjectActivity.class);
					// Use to set the default app as this new activity and clean the stack
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					WelcomeActivity.this.startActivity(intent);
				}
			}
		});

		mAuthTask.execute((Void) null);

	}


}

