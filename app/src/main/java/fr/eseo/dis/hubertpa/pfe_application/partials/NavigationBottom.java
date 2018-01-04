package fr.eseo.dis.hubertpa.pfe_application.partials;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.JuryActivity;
import fr.eseo.dis.hubertpa.pfe_application.activities.MarkActivity;
import fr.eseo.dis.hubertpa.pfe_application.activities.ProjectActivity;

/**
 * Created by Morgan on 30/12/2017.
 */

public class NavigationBottom implements BottomNavigationView.OnNavigationItemSelectedListener {

	AppCompatActivity activity;
	public NavigationBottom(AppCompatActivity _activity) {
		this.activity = _activity;

	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_project_tab:

				return true;
			case R.id.action_jury_tab:
				Intent intent1 = new Intent(activity, JuryActivity.class);
				activity.startActivity(intent1);
				return true;
			case R.id.action_mark_tab:
				Intent intent2 = new Intent(activity, MarkActivity.class);
				activity.startActivity(intent2);
				return true;
		}
		return false;
	}

}
