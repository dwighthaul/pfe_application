package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.adapters.JuryAdapter;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackListJury;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.JsonParserAPI;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Jury;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LOGON;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.partials.NavigationBottom;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Morgan on 30/12/2017.
 */

public class JuryActivity  extends AppCompatActivity {

	// The object contains a list of all the juries
	@Getter @Setter
	LIJUR lijur;

	// The following list is use to be a buffer, the list to be display.
	// This list can be modified if the user filter the result, ask to only display the favorits one
	@Getter @Setter
	List<JuryLIJUR> juryListBuffer;

	RecyclerView recycler;

	CardView listView;

	public static int NEW_CARD_COUNTER;

	private JuryAdapter juryAdapter;

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBottom(this, NavigationBottom.PAGES.JURIES);

	VolleyCallbackListJury callback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_juries);

		BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		navigation.getMenu().getItem(1).setChecked(true);

		lijur = new LIJUR();

		juryListBuffer = new ArrayList<JuryLIJUR>();

		NEW_CARD_COUNTER = 0;

		setRecycler();

		setCallback();

		processGetJuries();
	}

	/**
	 * Set the recycler. Basic setter
	 */
	private void setRecycler() {
		recycler = (RecyclerView) findViewById(R.id.card_list_juries);
		recycler.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recycler.setLayoutManager(llm);
	}

	/**
	 * The class and the two functions called when the Volley get a response
	 */
	public void setCallback() {
		this.callback =  new VolleyCallbackListJury() {
			@Override
			public void onSuccess(LIJUR lijur) {
				//Log.d("JuryActivity", String.valueOf(lijur.getListJuries().size()));
				Log.d("JuryActivity","setCallback()");

				// Set the list of juries
				JuryActivity.this.setJuryListBuffer(lijur.getListJuries());
				JuryActivity.this.setLijur(lijur);

				juryAdapter = new JuryAdapter(JuryActivity.this);
				recycler.setAdapter(juryAdapter);

			}

			@Override
			public void onError(String errorMessage) {
				Toast.makeText(JuryActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

			}
		};
	}

	private void processGetJuries() {
		// Get the token from the saved data
		String token = WebServiceConnexion.getToken(this);
		String login = WebServiceConnexion.getLogin(this);

		// Get the good url with the good variables
		String url = WebServiceConnexion.getLIJUR(login, token);

		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {

					JSONObject jsonObject = new JSONObject(response);
					String result = jsonObject.getString("result");
					if (result.equals("OK")) {

						LIJUR lijur = JsonParserAPI.parseLIJUR(jsonObject);
						JuryActivity.this.lijur = lijur;

						JuryActivity.this.setJuryListBuffer(lijur.getListJuries());

						callback.onSuccess(lijur);
					} else {
						Log.d("JuryActivity","error()");
						callback.onError(jsonObject.getString("error"));
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			}
		});
		queue.add(stringRequest);
	}

}