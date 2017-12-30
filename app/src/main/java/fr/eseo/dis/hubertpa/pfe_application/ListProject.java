package fr.eseo.dis.hubertpa.pfe_application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import fr.eseo.dis.hubertpa.pfe_application.partials.NavigationBottom;

public class ListProject extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_project);

		NavigationBottom navigationBottom = new NavigationBottom();
	}


}
