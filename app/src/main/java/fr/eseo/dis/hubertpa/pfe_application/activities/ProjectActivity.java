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
import android.widget.ListView;
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
import fr.eseo.dis.hubertpa.pfe_application.controller.adapters.ProjectAdapter;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackListProject;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.JsonParserAPI;
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


	RecyclerView recycler;

	CardView listView;

	public static int NEW_CARD_COUNTER;

	private ProjectAdapter projectAdapter;

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBottom(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

	    setContentView(R.layout.list_projects);


	    BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
	    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

	    navigation.getMenu().getItem(0).setChecked(true);

	    liproj = new LIPRJ();

	    projectListBuffer = new ArrayList<ProjectLIPRJ>();

	    NEW_CARD_COUNTER = 0;
	    recycler = (RecyclerView) findViewById(R.id.card_list_project);
	    recycler.setHasFixedSize(true);
	    LinearLayoutManager llm = new LinearLayoutManager(this);
	    llm.setOrientation(LinearLayoutManager.VERTICAL);
	    recycler.setLayoutManager(llm);


	    VolleyCallbackListProject volleyCallback = new VolleyCallbackListProject() {
		    @Override
		    public void onSuccess(LIPRJ liprj) {
				Log.d("ProjectActivity", String.valueOf(liprj.getProjectList().size()));
			    ProjectActivity.this.setLiproj(liprj);

			    projectAdapter = new ProjectAdapter(ProjectActivity.this);
			    recycler.setAdapter(projectAdapter);

		    }

		    @Override
		    public void onError(String errorMessage) {
			    Toast.makeText(ProjectActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

		    }
	    };

	    processGetProjects(volleyCallback);


    }

	public void clickItem(ProjectLIPRJ projectLIJUR) {
		Intent intent = new Intent(this, DetailProjectActivity.class);
		intent.putExtra("selected_project", projectLIJUR);
		startActivity(intent);
	}



	private void processGetProjects(final VolleyCallbackListProject callback) {
		String url = WebServiceConnexion.getLIPRJ("hubertpa", "123456789");

		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {

					JSONObject jsonObject = new JSONObject(response);
					String result = jsonObject.getString("result");
					if (result.equals("OK")) {
						LIPRJ liprj = JsonParserAPI.parseLIPRJ(jsonObject);
						ProjectActivity.this.liproj = liprj;

						ProjectActivity.this.setProjectListBuffer(liprj.getProjectList());

						callback.onSuccess(liprj);
					} else {
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
