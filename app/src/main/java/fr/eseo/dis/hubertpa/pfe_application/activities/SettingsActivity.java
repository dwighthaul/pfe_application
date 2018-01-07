package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.BasicSettings;
import fr.eseo.dis.hubertpa.pfe_application.partials.NavigationBottom;

public class SettingsActivity extends AppCompatActivity {

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBottom(this, NavigationBottom.PAGES.SETTINGS);

	Button disconnect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		navigation.getMenu().getItem(3).setChecked(true);

		TextView loginTextView = (TextView) findViewById(R.id.textViewLogin);
		TextView passwordTextView = (TextView) findViewById(R.id.textViewPassword);

		SharedPreferences settings = getSharedPreferences(BasicSettings.saveFilenameShared, 0);
		String login = settings.getString(BasicSettings.sharedLogin, "Default");
		String password = settings.getString(BasicSettings.sharedPassword, "Default");

		loginTextView.setText(login);
		passwordTextView.setText(password);


		disconnect = (Button) findViewById(R.id.button_disconnect);

		disconnect.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Log.d("SettingsActivity", "disconnect");
				disconnectFromTheApp();


			}
		});


	}


	public void disconnectFromTheApp() {
		WebServiceConnexion.resetDataValue(this);

		Intent intent = new Intent(this, WelcomeActivity.class);

		// Use to set the default app as this new activity and clean the stack
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		SettingsActivity.this.startActivity(intent);

	}
}
