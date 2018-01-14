package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.jpo_activities.JPOProjectActivity;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.VolleyCallbackLOGON;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.BasicSettings;


public class WelcomeActivity extends AppCompatActivity {

	Button visiteur;
	Button login;
	VolleyCallbackLOGON processResponseVisitor;
	Snackbar snack;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		login = findViewById(R.id.button_login);
		visiteur = findViewById(R.id.button_visitor);

		setCallback();

		setSnackbar();

		setListeners();

		attemptLoginWithPreferences();

	}

	String defaultValue = BasicSettings.sharedLoginDefault;

	private void setListeners() {
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
				connectUser(WebServiceConnexion.DEFAULT_LOGIN, WebServiceConnexion.DEFAULT_PASSWORD);
			}
		});

	}

	public void connectUser(String userLogin, String userPassword) {
		WebServiceConnexion.getConnected(userLogin, userPassword, this, processResponseVisitor);
	}


	private void LoginActivity() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	private void setCallback() {
		// L'action appelée si la connexion au site a été réalisée.
		processResponseVisitor = new VolleyCallbackLOGON() {
			@Override
			public void onSuccess() {
				SharedPreferences prefs = getSharedPreferences(BasicSettings.saveFilenameShared, MODE_PRIVATE);

				String userLogin = prefs.getString(BasicSettings.sharedLogin, "default");
				Intent intent = new Intent(WelcomeActivity.this, ProjectActivity.class);
				if (userLogin.equals(WebServiceConnexion.DEFAULT_LOGIN)) {
					intent = new Intent(WelcomeActivity.this, JPOProjectActivity.class);
				}
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

				WelcomeActivity.this.startActivity(intent);
			}
			@Override
			public void onError() {
			}
		};
	}

	private void setSnackbar() {
		SharedPreferences prefs = getSharedPreferences(BasicSettings.saveFilenameShared, MODE_PRIVATE);

		// Récuperation des identifiants
		String userLogin = prefs.getString(BasicSettings.sharedLogin, "default");

		CharSequence text = "A profil has been find with the following activity_login : " + userLogin + "\n try to auto-connect";

		snack = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG);


		View snackbarView = snack.getView();
		FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)snackbarView.getLayoutParams();
		params.gravity = Gravity.TOP;
		snackbarView.setLayoutParams(params);

		snackbarView.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_eseo_3));

		TextView textView = snackbarView.findViewById(android.support.design.R.id.snackbar_text);
		textView.setMaxLines(3);  // show multiple line
		textView.setTextColor(ContextCompat.getColor(this, R.color.blue_eseo_1));
		textView.setBackgroundColor(ContextCompat.getColor(this, R.color.grey_eseo_3));

	}


	private void attemptLoginWithPreferences() {
		// Get the user preferences
		SharedPreferences prefs = getSharedPreferences(BasicSettings.saveFilenameShared, MODE_PRIVATE);

		// Récuperation des identifiants
		String userLogin = prefs.getString(BasicSettings.sharedLogin, BasicSettings.sharedLoginDefault);
		String userPassword = prefs.getString(BasicSettings.sharedPassword, BasicSettings.sharedPasswordDefault);

		// Si l'utilisateur s'est déjà identifié précedement
		if (!userLogin.equals(BasicSettings.sharedLoginDefault)) {
			snack.show();

			// Try to connect the user with the given values.
			connectUser(userLogin, userPassword);

		}

	}

}