package fr.eseo.dis.hubertpa.pfe_application.activities;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.SearchView;
import android.widget.Switch;
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
import fr.eseo.dis.hubertpa.pfe_application.controller.adapters.ProjectAdapter;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackListProject;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.JsonParserAPI;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.BasicSettings;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Project;
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

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

	    setContentView(R.layout.activity_list_projects);

		mySwipeRefreshLayout = findViewById(R.id.swip_to_refresh);
		findProjectSearchView = findViewById(R.id.findProjectSearchView);
		BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
		BottomNavigationViewHelper.disableShiftMode(navigation);
	    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//	    navigation.getMenu().getItem(0).setChecked(true);

	    liproj = new LIPRJ();

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

				LIPRJ liproj = ProjectActivity.this.getLiproj();
				List<ProjectLIPRJ> liprjsToSend = new ArrayList<>();
				if (query.length() != 0) {

					for (ProjectLIPRJ projectLIPRJ: liproj.getProjectList()) {
						if(projectLIPRJ.getProject().getTitle().toLowerCase().contains(query.toLowerCase())) {
							liprjsToSend.add(projectLIPRJ);
						}
					}
					callback.onSuccess(liprjsToSend);
				} else {
					callback.onSuccess(liproj.getProjectList());
				}
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

	private void processGetProjectsFake() {
		String jsonObjectString = "{\"result\": \"OK\",\"api\": \"LIPRJ\",\"projects\": [{\"projectId\": 0,\"title\": \"Gestion main-libre tablette android\",\"descrip\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sed placerat justo. Nunc a nulla pulvinar, pellentesque elit sed, interdum tellus. Nullam scelerisque tortor vel diam sagittis feugiat. Nullam tincidunt lectus nibh, et vestibulum arcu vehicula eu. Nullam ut elit interdum, vestibulum augue at, rutrum diam. Donec convallis, libero a ultricies congue, sem odio malesuada tortor, vitae vestibulum turpis tortor a augue. Donec sit amet pharetra magna. Vestibulum venenatis ligula a urna sodales cursus. Morbi hendrerit est vitae porttitor interdum. Sed lectus urna, blandit et lacinia non, porttitor nec sem. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Maecenas ut fringilla justo. Integer accumsan vehicula pretium. Vestibulum non nisi et nulla lobortis fermentum. Interdum et malesuada fames ac ante ipsum primis in faucibus. Etiam urna sapien, maximus tempus orci ut, porta scelerisque nunc. Nunc dictum ultrices luctus. Aenean id tellus nec magna iaculis consequat. Donec sollicitudin tincidunt mauris. Mauris ut odio nulla. Etiam leo tellus, cursus cursus efficitur quis, sagittis quis turpis. Aenean et feugiat metus. Quisque leo neque, dictum sed ullamcorper non, varius sit amet eros. In ornare magna a mauris luctus, vel rutrum mauris mattis. Donec lacinia, urna quis condimentum pharetra, nisi nisl porta orci, non dapibus mi sem id eros. Donec iaculis rutrum tellus et vulputate. Phasellus eget luctus elit. Aliquam maximus tincidunt augue, vel lobortis mauris dignissim eget. Vivamus vestibulum pretium magna, vel ultricies augue elementum in. In vel hendrerit lacus. Aenean nec libero dictum, finibus libero sed, commodo dolor. Suspendisse magna dui, scelerisque quis sapien ut, vestibulum lacinia augue. Suspendisse a efficitur enim. In ullamcorper massa quis convallis imperdiet. In posuere ligula leo, hendrerit condimentum ligula iaculis eu. Vestibulum nisl lectus, porttitor vel rutrum et, scelerisque at dui. Phasellus a purus at nisi auctor laoreet et eu est. Aenean posuere.\",\"supervisor\": {\"forename\": \"Patrick\",\"surname\": \"ALBERS\"},\"poster\": true,\"confid\": 0,\"students\": [{\"userId\": 17,\"forename\": \"Victor\",\"surname\": \"VALLOIS\"},{\"userId\": 18,\"forename\": \"Josselin\",\"surname\": \"DORSO\"},{\"userId\": 19,\"forename\": \"Thomas\",\"surname\": \"JOUAULT\"}]},{\"projectId\": 1,\"title\": \"Reconnaissance langue des signes\",\"descrip\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sed placerat justo. Nunc a nulla pulvinar, pellentesque elit sed, interdum tellus. Nullam scelerisque tortor vel diam sagittis feugiat. Nullam tincidunt lectus nibh, et vestibulum arcu vehicula eu. Nullam ut elit interdum, vestibulum augue at, rutrum diam. Donec convallis, libero a ultricies congue, sem odio malesuada tortor, vitae vestibulum turpis tortor a augue. Donec sit amet pharetra magna. Vestibulum venenatis ligula a urna sodales cursus. Morbi hendrerit est vitae porttitor interdum. Sed lectus urna, blandit et lacinia non, porttitor nec sem. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Maecenas ut fringilla justo. Integer accumsan vehicula pretium. Vestibulum non nisi et nulla lobortis fermentum. Interdum et malesuada fames ac ante ipsum primis in faucibus. Etiam urna sapien, maximus tempus orci ut, porta scelerisque nunc. Nunc dictum ultrices luctus. Aenean id tellus nec magna iaculis consequat. Donec sollicitudin tincidunt mauris. Mauris ut odio nulla. Etiam leo tellus, cursus cursus efficitur quis, sagittis quis turpis. Aenean et feugiat metus. Quisque leo neque, dictum sed ullamcorper non, varius sit amet eros. In ornare magna a mauris luctus, vel rutrum mauris mattis. Donec lacinia, urna quis condimentum pharetra, nisi nisl porta orci, non dapibus mi sem id eros. Donec iaculis rutrum tellus et vulputate. Phasellus eget luctus elit. Aliquam maximus tincidunt augue, vel lobortis mauris dignissim eget. Vivamus vestibulum pretium magna, vel ultricies augue elementum in. In vel hendrerit lacus. Aenean nec libero dictum, finibus libero sed, commodo dolor. Suspendisse magna dui, scelerisque quis sapien ut, vestibulum lacinia augue. Suspendisse a efficitur enim. In ullamcorper massa quis convallis imperdiet. In posuere ligula leo, hendrerit condimentum ligula iaculis eu. Vestibulum nisl lectus, porttitor vel rutrum et, scelerisque at dui. Phasellus a purus at nisi auctor laoreet et eu est. Aenean posuere.\",\"supervisor\": {\"forename\": \"Patrick\",\"surname\": \"ALBERS\"},\"poster\": true,\"confid\": 0,\"students\": [{\"userId\": 20,\"forename\": \"Romain\",\"surname\": \"CREVAN\"},{\"userId\": 21,\"forename\": \"Anatole\",\"surname\": \"CHARRON\"},{\"userId\": 22,\"forename\": \"Thibaud\",\"surname\": \"CHEVRIER\"}]},{\"projectId\": 2,\"title\": \"Greenygrass\",\"descrip\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sed placerat justo. Nunc a nulla pulvinar, pellentesque elit sed, interdum tellus. Nullam scelerisque tortor vel diam sagittis feugiat. Nullam tincidunt lectus nibh, et vestibulum arcu vehicula eu. Nullam ut elit interdum, vestibulum augue at, rutrum diam. Donec convallis, libero a ultricies congue, sem odio malesuada tortor, vitae vestibulum turpis tortor a augue. Donec sit amet pharetra magna. Vestibulum venenatis ligula a urna sodales cursus. Morbi hendrerit est vitae porttitor interdum. Sed lectus urna, blandit et lacinia non, porttitor nec sem. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Maecenas ut fringilla justo. Integer accumsan vehicula pretium. Vestibulum non nisi et nulla lobortis fermentum. Interdum et malesuada fames ac ante ipsum primis in faucibus. Etiam urna sapien, maximus tempus orci ut, porta scelerisque nunc. Nunc dictum ultrices luctus. Aenean id tellus nec magna iaculis consequat. Donec sollicitudin tincidunt mauris. Mauris ut odio nulla. Etiam leo tellus, cursus cursus efficitur quis, sagittis quis turpis. Aenean et feugiat metus. Quisque leo neque, dictum sed ullamcorper non, varius sit amet eros. In ornare magna a mauris luctus, vel rutrum mauris mattis. Donec lacinia, urna quis condimentum pharetra, nisi nisl porta orci, non dapibus mi sem id eros. Donec iaculis rutrum tellus et vulputate. Phasellus eget luctus elit. Aliquam maximus tincidunt augue, vel lobortis mauris dignissim eget. Vivamus vestibulum pretium magna, vel ultricies augue elementum in. In vel hendrerit lacus. Aenean nec libero dictum, finibus libero sed, commodo dolor. Suspendisse magna dui, scelerisque quis sapien ut, vestibulum lacinia augue. Suspendisse a efficitur enim. In ullamcorper massa quis convallis imperdiet. In posuere ligula leo, hendrerit condimentum ligula iaculis eu. Vestibulum nisl lectus, porttitor vel rutrum et, scelerisque at dui. Phasellus a purus at nisi auctor laoreet et eu est. Aenean posuere.\",\"supervisor\": {\"forename\": \"Sebastien\",\"surname\": \"AUBIN\"},\"poster\": true,\"confid\": 0,\"students\": [{\"userId\": 23,\"forename\": \"Arnaud\",\"surname\": \"BILLY\"},{\"userId\": 24,\"forename\": \"Antoine\",\"surname\": \"ROBIC\"},{\"userId\": 25,\"forename\": \"Flavien\",\"surname\": \"REYNAUD\"},{\"userId\": 26,\"forename\": \"Timé\",\"surname\": \"KADEL\"},{\"userId\": 27,\"forename\": \"Lucas\",\"surname\": \"LEJEUNE\"}]},{\"projectId\": 3,\"title\": \"OPAL - Sécu des réseaux et dev. outils win\",\"descrip\": \"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sed placerat justo. Nunc a nulla pulvinar, pellentesque elit sed, interdum tellus. Nullam scelerisque tortor vel diam sagittis feugiat. Nullam tincidunt lectus nibh, et vestibulum arcu vehicula eu. Nullam ut elit interdum, vestibulum augue at, rutrum diam. Donec convallis, libero a ultricies congue, sem odio malesuada tortor, vitae vestibulum turpis tortor a augue. Donec sit amet pharetra magna. Vestibulum venenatis ligula a urna sodales cursus. Morbi hendrerit est vitae porttitor interdum. Sed lectus urna, blandit et lacinia non, porttitor nec sem. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Maecenas ut fringilla justo. Integer accumsan vehicula pretium. Vestibulum non nisi et nulla lobortis fermentum. Interdum et malesuada fames ac ante ipsum primis in faucibus. Etiam urna sapien, maximus tempus orci ut, porta scelerisque nunc. Nunc dictum ultrices luctus. Aenean id tellus nec magna iaculis consequat. Donec sollicitudin tincidunt mauris. Mauris ut odio nulla. Etiam leo tellus, cursus cursus efficitur quis, sagittis quis turpis. Aenean et feugiat metus. Quisque leo neque, dictum sed ullamcorper non, varius sit amet eros. In ornare magna a mauris luctus, vel rutrum mauris mattis. Donec lacinia, urna quis condimentum pharetra, nisi nisl porta orci, non dapibus mi sem id eros. Donec iaculis rutrum tellus et vulputate. Phasellus eget luctus elit. Aliquam maximus tincidunt augue, vel lobortis mauris dignissim eget. Vivamus vestibulum pretium magna, vel ultricies augue elementum in. In vel hendrerit lacus. Aenean nec libero dictum, finibus libero sed, commodo dolor. Suspendisse magna dui, scelerisque quis sapien ut, vestibulum lacinia augue. Suspendisse a efficitur enim. In ullamcorper massa quis convallis imperdiet. In posuere ligula leo, hendrerit condimentum ligula iaculis eu. Vestibulum nisl lectus, porttitor vel rutrum et, scelerisque at dui. Phasellus a purus at nisi auctor laoreet et eu est. Aenean posuere.\",\"supervisor\": {\"forename\": \"Olivier\",\"surname\": \"BEAUDOUX\"},\"poster\": true,\"confid\": 0,\"students\": [{\"userId\": 28,\"forename\": \"Alexis\",\"surname\": \"DEMAY\"},{\"userId\": 29,\"forename\": \"Romain\",\"surname\": \"HAMON\"},{\"userId\": 30,\"forename\": \"Thibaud\",\"surname\": \"DUBLE\"}]}]}\";	}";
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonObjectString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		liproj = JsonParserAPI.parseLIPRJ(jsonObject);

		callback.onSuccess(liproj.getProjectList());
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
						Toast.makeText(ProjectActivity.this, "List of all the projects", Toast.LENGTH_LONG).show();

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
		//Get widgets reference from XML layout
		Switch sButton = findViewById(R.id.markSwitchMines);

		//Set a CheckedChange Listener for Switch Button
		sButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton cb, boolean on){
				if(on)
				{
					//Do something when Switch button is on/checked
				}
				else
				{
					//Do something when Switch is off/unchecked
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
