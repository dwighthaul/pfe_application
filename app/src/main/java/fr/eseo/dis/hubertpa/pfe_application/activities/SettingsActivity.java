package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.partials.BottomNavigationViewHelper;
import fr.eseo.dis.hubertpa.pfe_application.partials.NavigationBottom;

public class SettingsActivity extends AppCompatActivity {

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBottom(this, NavigationBottom.PAGES.SETTINGS);

	Button disconnect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
		BottomNavigationViewHelper.disableShiftMode(navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		navigation.getMenu().getItem(2).setChecked(true);

		TextView loginTextView = findViewById(R.id.textViewLogin);

		String login = WebServiceConnexion.getLogin(this);

		loginTextView.setText(login);

		disconnect = findViewById(R.id.button_disconnect);

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
