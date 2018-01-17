package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.adapters.MarkAdapter;
import fr.eseo.dis.hubertpa.pfe_application.controller.adapters.ProjectAdapter;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackListNotes;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackListProject;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.JsonParserAPI;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListNote;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.NOTES;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.partials.BottomNavigationViewHelper;
import fr.eseo.dis.hubertpa.pfe_application.partials.NavigationBottom;
import lombok.Getter;
import lombok.Setter;

public class MarkActivity extends AppCompatActivity {

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBottom(this, NavigationBottom.PAGES.MARKS);

	public static int NEW_CARD_COUNTER;

	@Getter @Setter
	NOTES notes;

	int idProject;

	@Getter @Setter
	ListNote listNoteBuffer;
	RecyclerView recycler;

	VolleyCallbackListNotes callback;

	MarkAdapter markAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_marks);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

	    Bundle data = getIntent().getExtras();
	    assert data != null;
	    idProject = data.getParcelable("idProject");


	    navigation.getMenu().getItem(2).setChecked(true);


	    notes = new NOTES();

	    listNoteBuffer = new ListNote();

	    NEW_CARD_COUNTER = 0;

	    setRecycler();

	    setCallback();

	    processGetNotes();

    }

	private void setRecycler () {
		recycler = findViewById(R.id.card_list_project);
		recycler.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recycler.setLayoutManager(llm);

	}

    private void setCallback() {
	    this.callback =  new VolleyCallbackListNotes() {
		    @Override
		    public void onSuccess(ListNote listNote) {

			    // Set the list of projects used to create the view
			    MarkActivity.this.setListNoteBuffer(listNote);

			    // send the list to the projectAdapter and set the project adapter to the recycler
			    markAdapter = new MarkAdapter(MarkActivity.this);
			    recycler.setAdapter(markAdapter);
		    }

		    @Override
		    public void onError(String errorMessage) {
			    Toast.makeText(MarkActivity.this, errorMessage, Toast.LENGTH_LONG).show();
		    }
	    };

    }


	private void processGetNotes() {

		// Get the token from the saved data
		final String _token = WebServiceConnexion.getToken(this);
		final String _login = WebServiceConnexion.getLogin(this);

		// Get the good url with the good variables

		String url = WebServiceConnexion.getNOTES(_login, _token, idProject);

		RequestQueue queue = Volley.newRequestQueue(this, new HurlStack(null, WebServiceConnexion.newSslSocketFactory(this)));

		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					String result = jsonObject.getString("result");

					if (result.equals("OK")) {
						NOTES notes = JsonParserAPI.parseNOTES(jsonObject);
						MarkActivity.this.setNotes(notes);

						callback.onSuccess(notes.getNotesList());
					} else {
						String error = jsonObject.getString("error");
						callback.onError(jsonObject.getString(error));
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
