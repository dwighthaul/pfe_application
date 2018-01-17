package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.adapters.JuryAdapter;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackListJury;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.JsonParserAPI;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.partials.BottomNavigationViewHelper;
import fr.eseo.dis.hubertpa.pfe_application.partials.NavigationBottom;
import lombok.Getter;
import lombok.Setter;

public class JuryActivity  extends AppCompatActivity {

	// The object contains a list of all the juries
	@Getter @Setter
	LIJUR lijur;

	@Getter @Setter
	LIJUR  myLijur;
	// The following list is use to be a buffer, the list to be display.
	// This list can be modified if the user filter the result, ask to only display the favorits one
	@Getter @Setter
	List<JuryLIJUR> juryListBuffer;

	public static int NEW_CARD_COUNTER;

	boolean stoppingBuffering = false;

	private JuryAdapter juryAdapter;

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBottom(this, NavigationBottom.PAGES.JURIES);

	VolleyCallbackListJury callback;

	RecyclerView recycler;

	SwipeRefreshLayout mySwipeRefreshLayout;

	SwitchCompat sButton;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_juries);

		mySwipeRefreshLayout = findViewById(R.id.swip_to_refresh);

		sButton = findViewById(R.id.markSwitchMines);

		BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
		BottomNavigationViewHelper.disableShiftMode(navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		navigation.getMenu().getItem(1).setChecked(true);

		lijur = new LIJUR();

		myLijur = new LIJUR();

		juryListBuffer = new ArrayList<JuryLIJUR>();

		NEW_CARD_COUNTER = 0;

		setRecycler();

		setCallback();

		setSwitchListeners();

		setActionOnRefrech();

		processGetJuries();
	}

	private void processGetJuries() {
		sButton.setChecked(false);
		processGetAllJuries();
		processGetMyJuries();
	}

	public void setSwitchListeners() {

		//Set a CheckedChange Listener for Switch Button
		sButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton cb, boolean on){
				if(on)
				{
					Toast.makeText(JuryActivity.this, "Mes juries. \n Nombre de juries : " +
							myLijur.getListJuries().size(), Toast.LENGTH_LONG).show();
					callback.onSuccess(myLijur.getListJuries());
				}
				else
				{
					Toast.makeText(JuryActivity.this, "Liste tous les juries. \n Nombre de juries : " +
							lijur.getListJuries().size(), Toast.LENGTH_LONG).show();
					callback.onSuccess(lijur.getListJuries());
				}
			}
		});
	}

	/**
	 * Set the recycler. Basic setter
	 */
	private void setRecycler() {
		recycler = findViewById(R.id.card_list_juries);
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
			public void onSuccess(List<JuryLIJUR> lijurList) {
				//Log.d("JuryActivity", String.valueOf(lijur.getListJuries().size()));

				// Set the list of juries
				JuryActivity.this.setJuryListBuffer(lijurList);

				// send the list to the projectAdapter and set the project adapter to the recycler
				juryAdapter = new JuryAdapter(JuryActivity.this);
				recycler.setAdapter(juryAdapter);

			}

			@Override
			public void onError(String errorMessage) {
				Toast.makeText(JuryActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

			}
		};
	}

	private void processGetAllJuries() {
		mySwipeRefreshLayout.setRefreshing(true);

		stoppingBuffering = true;
		stopBuffering();

		// Get the token from the saved data
		String token = WebServiceConnexion.getToken(this);
		String login = WebServiceConnexion.getLogin(this);

		stoppingBuffering = true;
		stopBuffering();

		// Get the good url with the good variables
		String url = WebServiceConnexion.getLIJUR(login, token);

		RequestQueue queue = Volley.newRequestQueue(this, new HurlStack(null, WebServiceConnexion.newSslSocketFactory(this)));
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String res) {
				stoppingBuffering = false;
				String response = "", str = "";
				try {
					str = new String(res.getBytes("ISO-8859-1"), "UTF-8");
				} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				}
				response = Html.fromHtml(str).toString();
				try {

					JSONObject jsonObject = new JSONObject(response);
					String result = jsonObject.getString("result");

					if (result.equals("OK")) {

						LIJUR lijur = JsonParserAPI.parseLIJUR(jsonObject);
						JuryActivity.this.setLijur(JsonParserAPI.parseLIJUR(jsonObject));

						callback.onSuccess(lijur.getListJuries());
					} else {
						String error = jsonObject.getString("error");
						callback.onError(jsonObject.getString(error));
					}
					mySwipeRefreshLayout.setRefreshing(false);
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


	private void processGetMyJuries() {
		// Get the token from the saved data
		final String _token = WebServiceConnexion.getToken(this);
		final String _login = WebServiceConnexion.getLogin(this);

		// Get the good url with the good variables

		String url = WebServiceConnexion.getMYJUR(_login, _token);

		RequestQueue queue = Volley.newRequestQueue(this, new HurlStack(null, WebServiceConnexion.newSslSocketFactory(this)));

		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String res) {
				String response = "", str = "";
				try {
					str = new String(res.getBytes("ISO-8859-1"), "UTF-8");
				} catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				}
				response = Html.fromHtml(str).toString();
				try {
					JSONObject jsonObject = new JSONObject(response);
					String result = jsonObject.getString("result");
					if (result.equals("OK")) {
						JuryActivity.this.myLijur = JsonParserAPI.parseLIJUR(jsonObject);
						Log.d("TEST", "" + JuryActivity.this.myLijur.getListJuries().size());
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


	// After 10 sec, the activity hind the annoying spining stuff and display an error
	public void stopBuffering() {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (stoppingBuffering) {
					Toast.makeText(JuryActivity.this, "Connexion error", Toast.LENGTH_SHORT).show();
					mySwipeRefreshLayout.setRefreshing(false);
				}
			}
		}, 10000);

	}

	/**
	 * If the user swipe down, this listener is trigger.
	 * The action is to ask to recive the list of the projects
	 */
	private void setActionOnRefrech() {
		mySwipeRefreshLayout.setOnRefreshListener(
				new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						processGetJuries();
					}
				}
		);
	}


	public void clickItem(JuryLIJUR juryLIJUR) {
		Intent intent = new Intent(this, DetailJuryActivity.class);
		intent.putExtra("selected_jury", juryLIJUR);
		startActivity(intent);
	}


}