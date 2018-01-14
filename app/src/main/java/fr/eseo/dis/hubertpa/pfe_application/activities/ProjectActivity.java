package fr.eseo.dis.hubertpa.pfe_application.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.SearchView;
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

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.adapters.ProjectAdapter;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackListProject;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.JsonParserAPI;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.BasicSettings;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.partials.BottomNavigationViewHelper;
import fr.eseo.dis.hubertpa.pfe_application.partials.NavigationBottom;
import lombok.Getter;
import lombok.Setter;

public class ProjectActivity extends AppCompatActivity {


	// The object contains a list of all the projects available
	@Getter @Setter
	LIPRJ liproj;

	@Getter @Setter
	LIPRJ myLiproj;
	// The following list is use to be a buffer, the list to be display.
	// This list can be modified if the user filter the result, ask to only display the favorits one
	@Getter @Setter
	List<ProjectLIPRJ> projectListBuffer;

	boolean stoppingBuffering = false;

	RecyclerView recycler;

	public static int NEW_CARD_COUNTER;

	private ProjectAdapter projectAdapter;

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBottom(this, NavigationBottom.PAGES.PROJECTS);

	VolleyCallbackListProject callback;

	SwipeRefreshLayout mySwipeRefreshLayout;

	SearchView findProjectSearchView;

	SwitchCompat sButton;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

	    setContentView(R.layout.activity_list_projects);

		sButton = findViewById(R.id.markSwitchMines);

		mySwipeRefreshLayout = findViewById(R.id.swip_to_refresh);
		findProjectSearchView = findViewById(R.id.findProjectSearchView);
		BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
		BottomNavigationViewHelper.disableShiftMode(navigation);
	    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//	    navigation.getMenu().getItem(0).setChecked(true);

	    liproj = new LIPRJ();

		myLiproj = new LIPRJ();

	    projectListBuffer = new ArrayList<>();

	    NEW_CARD_COUNTER = 0;

	    setRecycler();

		setCallback();

		setActionOnRefrech();

		setSwitchListeners();

		setActionOnResearch();

		processGetProjects();

    }

	private void setActionOnResearch() {

		findProjectSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {

				LIPRJ liproj = ProjectActivity.this.getLiproj();
				List<ProjectLIPRJ> liprjsToSend = new ArrayList<>();

				for (ProjectLIPRJ projectLIPRJ: liproj.getProjectList()) {
					if(projectLIPRJ.getProject().getTitle().toLowerCase().contains(query.toLowerCase())) {
						liprjsToSend.add(projectLIPRJ);
					}
				}
				callback.onSuccess(liprjsToSend);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				callback.onSuccess(liproj.getProjectList());
				return false;
			}
		});
		findProjectSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
			@Override
			public boolean onClose() {
				callback.onSuccess(liproj.getProjectList());
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
	    this.callback =  new VolleyCallbackListProject() {
		    @Override
		    public void onSuccess(List<ProjectLIPRJ>  liprjs) {

			    // Set the list of projects used to create the view
			    ProjectActivity.this.setProjectListBuffer(liprjs);

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



	/**
	 * Call the server using Volley.
	 * If the result is good:
	 * The JsonParserAPI.parseLIPRJ function parse the response given by the server, then it call the onSuccess
	 * If not the activity display an eror with the error message via a toaster.
	 *
	 */
	private void processGetProjects() {
		sButton.setChecked(false);
		getListProjects();
		getMyProjects();
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
						ProjectActivity.this.setLiproj(liprj);
						Toast.makeText(ProjectActivity.this, "List of all the projects. \n Nombre de projets : " + liprj.getProjectList().size(), Toast.LENGTH_LONG).show();

						callback.onSuccess(liprj.getProjectList());
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

	private void getMyProjects() {
		// Get the token from the saved data
		final String _token = WebServiceConnexion.getToken(this);
		final String _login = WebServiceConnexion.getLogin(this);

		// Get the good url with the good variables

		String url = WebServiceConnexion.getMYPRJ(_login, _token);

		RequestQueue queue = Volley.newRequestQueue(this, new HurlStack(null, WebServiceConnexion.newSslSocketFactory(this)));

		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					String result = jsonObject.getString("result");
					if (result.equals("OK")) {
						myLiproj = JsonParserAPI.parseLIPRJ(jsonObject);
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
					Toast.makeText(ProjectActivity.this, "Connexion error", Toast.LENGTH_SHORT).show();
					mySwipeRefreshLayout.setRefreshing(false);
				}
			}
		}, 10000);

	}

	public String selectRequest(){
		String login = WebServiceConnexion.getLogin(this);
		String token = WebServiceConnexion.getToken(this);
		int role = WebServiceConnexion.getRole(this);

		String url = "";
		if(role == BasicSettings.Roles.VISITOR.getRoleId()) {
			url = WebServiceConnexion.getPORTE(login, token, "1");
		} else if(role == BasicSettings.Roles.STUDENT.getRoleId()) {
			url = WebServiceConnexion.getLIPRJ(login, token);
		} else if (role == BasicSettings.Roles.TEACHER.getRoleId()) {
			url = WebServiceConnexion.getLIPRJ(login, token);

		}
		return url;
	}

	public void setSwitchListeners() {

		//Set a CheckedChange Listener for Switch Button
		sButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton cb, boolean on){
				if(on)
				{
					Toast.makeText(ProjectActivity.this, "Mes projets. \n Nombre de projets : " +
							myLiproj.getProjectList().size(), Toast.LENGTH_LONG).show();
					callback.onSuccess(myLiproj.getProjectList());
				}
				else
				{
					Toast.makeText(ProjectActivity.this, "Liste tous les projets. \n Nombre de projets : " +
							liproj.getProjectList().size(), Toast.LENGTH_LONG).show();
					callback.onSuccess(liproj.getProjectList());
				}
			}
		});
	}

	public void clickItem(ProjectLIPRJ projectLIPRJ) {
		Intent intent = new Intent(this, DetailProjectActivity.class);
		intent.putExtra("selected_project", projectLIPRJ);
		startActivity(intent);
	}

	public void takeNotes(ProjectLIPRJ projectLIPRJ) {
		Intent intent = new Intent(this, TakeNotesProjectActivity.class);
		Log.d("TEST", projectLIPRJ.getProject().getTitle());
		intent.putExtra("nameProject", projectLIPRJ.getProject().getTitle());
		intent.putExtra("idProject", projectLIPRJ.getProject().getIdProject());
		intent.putExtra("selected_project", projectLIPRJ);
		startActivity(intent);
	}

	public void seePoster(ProjectLIPRJ projectLIPRJ) {
		Intent intent = new Intent(this, DislayPosterActivity.class);
		intent.putExtra("selected_project", projectLIPRJ);
		startActivity(intent);
	}
}
