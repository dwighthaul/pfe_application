package fr.eseo.dis.hubertpa.pfe_application.activities.jpo_activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.DetailProjectActivity;
import fr.eseo.dis.hubertpa.pfe_application.activities.DislayPosterActivity;
import fr.eseo.dis.hubertpa.pfe_application.activities.TakeNotesProjectActivity;
import fr.eseo.dis.hubertpa.pfe_application.controller.adapters.JPOProjectAdapter;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackListProject;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackListProjectPORTE;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackNOTES;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.JsonParserAPI;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.BasicSettings;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.PORTE;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectPORTE;
import fr.eseo.dis.hubertpa.pfe_application.partials.BottomNavigationViewHelper;
import fr.eseo.dis.hubertpa.pfe_application.partials.NavigationBottomJPO;
import lombok.Getter;
import lombok.Setter;

public class JPOProjectActivity extends AppCompatActivity {

	// The object contains a list of all the projects available
	@Getter @Setter
	PORTE porte;

	// The following list is use to be a buffer, the list to be display.
	// This list can be modified if the user filter the result, ask to only display the favorits one
	@Getter @Setter
	List<ProjectPORTE> projectListBuffer;

	boolean stoppingBuffering = false;

	RecyclerView recycler;

	public static int NEW_CARD_COUNTER;

	private JPOProjectAdapter projectAdapter;

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBottomJPO(this, NavigationBottomJPO.PAGES.PROJECTS_JPO);

	VolleyCallbackListProjectPORTE callback;

	SwipeRefreshLayout mySwipeRefreshLayout;

	SearchView findProjectSearchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_jpo_projects);

		mySwipeRefreshLayout = findViewById(R.id.swip_to_refresh);
		findProjectSearchView = findViewById(R.id.findProjectSearchView);
		BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
		BottomNavigationViewHelper.disableShiftMode(navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//	    navigation.getMenu().getItem(0).setChecked(true);

		porte = new PORTE();

		projectListBuffer = new ArrayList<>();

		NEW_CARD_COUNTER = 0;

		setRecycler();

		setCallback();

		setActionOnRefrech();

		setActionOnResearch();

		processGetProjects();

	}

	private void setActionOnResearch() {

		findProjectSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {

				PORTE porte = JPOProjectActivity.this.getPorte();
				List<ProjectPORTE> liprjsToSend = new ArrayList<>();

				for (ProjectPORTE projectPORTE: porte.getListProject()) {
					if(projectPORTE.getProject().getTitle().toLowerCase().contains(query.toLowerCase())) {
						liprjsToSend.add(projectPORTE);
					}
				}
				callback.onSuccess(liprjsToSend);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				callback.onSuccess(porte.getListProject());
				return false;
			}
		});
		findProjectSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
			@Override
			public boolean onClose() {
				callback.onSuccess(porte.getListProject());
				return false;
			}
		});
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
						processGetProjects();
					}
				}
		);
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


	/**
	 * The class and the two functions called when the Volley get a response
	 */
	public void setCallback() {
		this.callback =  new VolleyCallbackListProjectPORTE() {
			@Override
			public void onSuccess(List<ProjectPORTE>  liprpo) {

				// Set the list of projects used to create the view
				JPOProjectActivity.this.setProjectListBuffer(liprpo);
				// send the list to the projectAdapter and set the project adapter to the recycler
				projectAdapter = new JPOProjectAdapter(JPOProjectActivity.this);
				recycler.setAdapter(projectAdapter);
			}

			@Override
			public void onError(String errorMessage) {
				Toast.makeText(JPOProjectActivity.this, errorMessage, Toast.LENGTH_LONG).show();
			}
		};
	}



	/**
	 * Call the server using Volley.
	 * If the result is good:
	 * The JsonParserAPI.parseLIPRJ function parse the response given by the server, then it call the onSuccess
	 * If not the activity display an eror with the error message via a toaster.
	 *
	 */
	private void processGetProjects() {
		getListProjects();
	}

	private void getListProjects() {
		mySwipeRefreshLayout.setRefreshing(true);

		// Dont care about this, it's just to display an error if the user
		// wait more than 10 sec. Never tested, should work.
		stoppingBuffering = true;
		stopBuffering();

		// Get the token from the saved data
		final String _token = WebServiceConnexion.getToken(this);
		final String _login = WebServiceConnexion.getLogin(this);

		// Get the good url with the good variables
		String seed = "1";

		String url = WebServiceConnexion.getPORTE(_login, _token, seed);

		RequestQueue queue = Volley.newRequestQueue(this, new HurlStack(null, WebServiceConnexion.newSslSocketFactory(this)));

		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				stoppingBuffering = false;
				try {
					JSONObject jsonObject = new JSONObject(response);
					String result = jsonObject.getString("result");

					if (result.equals("OK")) {
						PORTE porte = JsonParserAPI.parsePORTE(jsonObject);
						JPOProjectActivity.this.setPorte(porte);

						Toast.makeText(JPOProjectActivity.this, "JPO Liste de tous les projects. \n Nombre de projets : " + porte.getListProject().size(), Toast.LENGTH_LONG).show();

						callback.onSuccess(porte.getListProject());
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
				callback.onError(error.getMessage());
			}
		});

		stringRequest.setRetryPolicy(new DefaultRetryPolicy(
				50000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		queue.add(stringRequest);
	}

	// After 40 sec, the activity hind the annoying spining stuff and display an error
	public void stopBuffering() {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (stoppingBuffering) {
					Toast.makeText(JPOProjectActivity.this, "Connexion error", Toast.LENGTH_SHORT).show();
					mySwipeRefreshLayout.setRefreshing(false);
				}
			}
		}, 40000);

	}

	public void clickItem(ProjectPORTE projectPORTE) {
		// TODO : Create and Implement this page
		Log.d("JPOProjectActivity", projectPORTE.getProject().getTitle());

		//Intent intent = new Intent(this, DetailProjectJPOActivity.class);
		//		intent.putExtra("selected_project", projectPORTE);
		//startActivity(intent);
	}

	public void takeNotes(ProjectPORTE projectPORTE) {
		// TODO : Create and Implement this page
		Log.d("JPOProjectActivity", projectPORTE.getProject().getTitle());

		Intent intent = new Intent(this, JPODisplayImageActivity.class);
		intent.putExtra("stringImageProject", projectPORTE.getBase64Image());
		startActivity(intent);
	}

	public void seePoster(ProjectPORTE projectPORTE) {
		// TODO : Create and Implement this page
		Log.d("JPOProjectActivity", projectPORTE.getProject().getTitle());

//		Intent intent = new Intent(this, DislayPosterActivity.class);
//		intent.putExtra("selected_project", projectPORTE);
//		startActivity(intent);
	}

}
