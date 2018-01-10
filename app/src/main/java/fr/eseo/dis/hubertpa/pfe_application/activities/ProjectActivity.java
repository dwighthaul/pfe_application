package fr.eseo.dis.hubertpa.pfe_application.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.adapters.ProjectAdapter;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackListProject;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.JsonParserAPI;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.StyleProject;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Project;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.partials.NavigationBottom;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Morgan on 30/12/2017.
 */

public class ProjectActivity extends AppCompatActivity {


	// The object contains a list of all the projects available
	@Getter @Setter
	LIPRJ liproj;

	// The following list is use to be a buffer, the list to be display.
	// This list can be modified if the user filter the result, ask to only display the favorits one
	@Getter @Setter
	List<ProjectLIPRJ> projectListBuffer;

	boolean stoppingBuffering = false;

	RecyclerView recycler;

	CardView listView;

	public static int NEW_CARD_COUNTER;

	private ProjectAdapter projectAdapter;

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBottom(this, NavigationBottom.PAGES.PROJECTS);

	VolleyCallbackListProject callback;

	SwipeRefreshLayout mySwipeRefreshLayout;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

	    setContentView(R.layout.list_projects);

		mySwipeRefreshLayout = findViewById(R.id.swip_to_refresh);

	    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
	    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//	    navigation.getMenu().getItem(0).setChecked(true);

	    liproj = new LIPRJ();

	    projectListBuffer = new ArrayList<ProjectLIPRJ>();

	    NEW_CARD_COUNTER = 0;

	    setRecycler();

		setCallback();

		setActionOnRefrech();

	    processGetProjects();

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
		recycler = (RecyclerView) findViewById(R.id.card_list_project);
		recycler.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recycler.setLayoutManager(llm);
	}


	/**
	 * The class and the two functions called when the Volley get a response
	 */
	public void setCallback() {
	    this.callback =  new VolleyCallbackListProject() {
		    @Override
		    public void onSuccess(LIPRJ liprj) {
			    Toast.makeText(ProjectActivity.this, "List of all the projects", Toast.LENGTH_LONG).show();

			    // Set the list of projects used to create the view
			    ProjectActivity.this.setProjectListBuffer(liprj.getProjectList());

			    ProjectActivity.this.setLiproj(liprj);

				// send the list to the projectAdapter and set the project adapter to the recycler
			    projectAdapter = new ProjectAdapter(ProjectActivity.this);
			    recycler.setAdapter(projectAdapter);
		    }

		    @Override
		    public void onError(String errorMessage) {

			    Toast.makeText(ProjectActivity.this, errorMessage, Toast.LENGTH_LONG).show();
		    }
	    };
    }

	public void clickItem(ProjectLIPRJ projectLIJUR) {
		Intent intent = new Intent(this, DetailProjectActivity.class);
		intent.putExtra("selected_project", projectLIJUR);
		startActivity(intent);
	}


	/**
	 * Call the server using Volley.
	 * If the result is good:
	 * The JsonParserAPI.parseLIPRJ function parse the response given by the server, then it call the onSuccess
	 * If not the activity display an eror with the error message via a toaster.
	 *
	 */
	private void processGetProjects() {
		mySwipeRefreshLayout.setRefreshing(true);

		// Dont care about this, it's just to display an error if the user
		// wait more than 10 sec. Never tested, should work.
		stoppingBuffering = true;
		stopBuffering();

		// Get the token from the saved data
		final String _token = WebServiceConnexion.getToken(this);
		final String _login = WebServiceConnexion.getLogin(this);

		// Get the good url with the good variables
		String url = WebServiceConnexion.getLIPRJ(_login, _token);
		RequestQueue queue = Volley.newRequestQueue(this, new HurlStack(null, WebServiceConnexion.newSslSocketFactory(this)));

		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				stoppingBuffering = false;
				try {
					JSONObject jsonObject = new JSONObject(response);
					String result = jsonObject.getString("result");

					if (result.equals("OK")) {
						LIPRJ liprj = JsonParserAPI.parseLIPRJ(jsonObject);
						for(int i = 0; i < liprj.getProjectList().size(); i++) {
							//return POSTR + "&proj="  + idProject + "&style=" + style + addUsernameAndToken(username, token);
							int idProject = liprj.getProjectList().get(i).getProject().getIdProject();

							String urlPoster = WebServiceConnexion.getPOSTR(_login, _token, idProject, StyleProject.FULL);
							liprj.getProjectList().get(i).setPosterPath(urlPoster);
						}

						callback.onSuccess(liprj);
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


	// After 10 sec, the activity hind the annoying spining stuff and display an error
	public void stopBuffering() {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (stoppingBuffering) {
					Toast.makeText(ProjectActivity.this, "Connexion error", Toast.LENGTH_SHORT).show();
					mySwipeRefreshLayout.setRefreshing(false);
				}
			}
		}, 10000);

	}


}
