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
import fr.eseo.dis.hubertpa.pfe_application.activities.SettingsActivity;
import fr.eseo.dis.hubertpa.pfe_application.activities.jpo_activities.JPOJuryActivity;
import fr.eseo.dis.hubertpa.pfe_application.activities.jpo_activities.JPOProjectActivity;

public class NavigationBottomJPO implements BottomNavigationView.OnNavigationItemSelectedListener {

	// Filter if the button clicked is not the same as the one already show
	public enum PAGES {
		PROJECTS_JPO,
		JURIES_JPO,
		MARKS_JPO,
		SETTINGS
	}


	AppCompatActivity activity;
	PAGES pageActivity;
	public NavigationBottomJPO(AppCompatActivity _activity, PAGES page) {
		this.activity = _activity;
		pageActivity = page;

	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		Log.d("NavigationBottom", "onNavigationItemSelected");
		switch (item.getItemId()) {
			case R.id.action_project_tab:
				if(pageActivity != PAGES.PROJECTS_JPO) {
					Intent intent = new Intent(activity, JPOProjectActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					activity.startActivity(intent);
				}
				return true;
			case R.id.action_jury_tab:
				if(pageActivity != PAGES.JURIES_JPO) {
					Intent intent1 = new Intent(activity, JPOJuryActivity.class);
					intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
					activity.startActivity(intent1);
				}
				return true;
//			case R.id.action_mark_tab:
//				if(pageActivity != PAGES.MARKS_JPO) {
////					Intent intent2 = new Intent(activity, JPOMarkActivity.class);
////					activity.startActivity(intent2);
//				}
//				return true;
			case R.id.action_settings_tab:
				if(pageActivity != PAGES.SETTINGS) {
					Intent intent3 = new Intent(activity, SettingsActivity.class);
					activity.startActivity(intent3);
				}
				return true;
		}
		return false;
	}

}
