package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackListProject;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.JsonParserAPI;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Jury;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LOGON;
import fr.eseo.dis.hubertpa.pfe_application.partials.NavigationBottom;

/**
 * Created by Morgan on 30/12/2017.
 */

public class JuryActivity  extends AppCompatActivity {

	LIPRJ liproj;


	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBottom(this, NavigationBottom.PAGES.JURIES);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_juries);

		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		navigation.getMenu().getItem(1).setChecked(true);



	}

}