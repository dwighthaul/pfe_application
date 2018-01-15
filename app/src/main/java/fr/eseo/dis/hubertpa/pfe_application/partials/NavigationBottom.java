package fr.eseo.dis.hubertpa.pfe_application.partials;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.JuryActivity;
import fr.eseo.dis.hubertpa.pfe_application.activities.MarkActivity;
import fr.eseo.dis.hubertpa.pfe_application.activities.ProjectActivity;
import fr.eseo.dis.hubertpa.pfe_application.activities.SettingsActivity;

public class NavigationBottom implements BottomNavigationView.OnNavigationItemSelectedListener {

	// Filter if the button clicked is not the same as the one already show
	public enum PAGES {
		PROJECTS,
		JURIES,
		MARKS,
		SETTINGS
	}


	AppCompatActivity activity;
	PAGES pageActivity;
	public NavigationBottom(AppCompatActivity _activity, PAGES page) {
		this.activity = _activity;
		pageActivity = page;

	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		Log.d("NavigationBottom", "onNavigationItemSelected");
		switch (item.getItemId()) {
			case R.id.action_project_tab:
				if(pageActivity != PAGES.PROJECTS) {
					Intent intent = new Intent(activity, ProjectActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					activity.startActivity(intent);
				}
				return true;
			case R.id.action_jury_tab:
				if(pageActivity != PAGES.JURIES) {
					Intent intent1 = new Intent(activity, JuryActivity.class);
					intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					activity.startActivity(intent1);
				}
				return true;
			case R.id.action_mark_tab:
				if(pageActivity != PAGES.MARKS) {
					Intent intent2 = new Intent(activity, MarkActivity.class);
					intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					activity.startActivity(intent2);
				}
				return true;
			case R.id.action_settings_tab:
				if(pageActivity != PAGES.SETTINGS) {
					Intent intent3 = new Intent(activity, SettingsActivity.class);
					intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					activity.startActivity(intent3);
				}
				return true;
		}
		return false;
	}

}
