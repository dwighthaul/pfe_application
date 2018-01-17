package fr.eseo.dis.hubertpa.pfe_application.activities.jpo_activities;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.SettingsActivity;
import fr.eseo.dis.hubertpa.pfe_application.activities.WelcomeActivity;
import fr.eseo.dis.hubertpa.pfe_application.controller.jpoDAO.UserDAO;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.partials.BottomNavigationViewHelper;
import fr.eseo.dis.hubertpa.pfe_application.partials.NavigationBottomJPO;

public class JPOSettingActivity extends AppCompatActivity {

	Button disconnect;

	RecyclerView recycler;

	private UserDAO userdao;

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBottomJPO(this, NavigationBottomJPO.PAGES.SETTINGS);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jpo_setting);

		BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
		BottomNavigationViewHelper.disableShiftMode(navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		navigation.getMenu().getItem(2).setChecked(true);

		disconnect = findViewById(R.id.button_disconnect);

		disconnect.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Log.d("JPOSettingsActivity", "disconnect");
				disconnectFromTheApp();
			}
		});

		setRecycler();
	}

	public void disconnectFromTheApp() {
		WebServiceConnexion.resetDataValue(this);

		Intent intent = new Intent(this, WelcomeActivity.class);

		// Use to set the default app as this new activity and clean the stack
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		JPOSettingActivity.this.startActivity(intent);

	}

	/**
	 * Set the recycler. Basic setter
	 */
	private void setRecycler() {
		recycler = findViewById(R.id.card_list_project);
		recycler.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recycler.setLayoutManager(llm);
	}

	private void getListusers() {
		userdao.open();
		userdao.getAllUsers();
		userdao.close();
	}
}
