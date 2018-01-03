package fr.eseo.dis.hubertpa.pfe_application.testActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import fr.eseo.dis.hubertpa.pfe_application.activities.DashboardActivity;
import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.WelcomeActivity;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.TestWebservice;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.JsonParserAPI;
import fr.eseo.dis.hubertpa.pfe_application.model.APIAndroid;
import lombok.Getter;
import lombok.Setter;

public class Test extends AppCompatActivity {

	@Getter @Setter
	TextView current_user_urlTextView;

	@Getter @Setter
	TextView current_user_authorizations_html_urlTextView;

	@Getter @Setter
	TextView authorizations_urlTextView;

	@Getter @Setter
	Button dashboardActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		current_user_urlTextView = (TextView) findViewById(R.id.current_user_url_value);
		current_user_authorizations_html_urlTextView = (TextView) findViewById(R.id.current_user_authorizations_html_url_value);
		authorizations_urlTextView = (TextView) findViewById(R.id.authorizations_url_value);

		dashboardActivity = (Button) findViewById(R.id.button_dashboard);

		dashboardActivity.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Log.d("Welcome", "Visiteur");
//				gotoDashboardActivity();
				gotoWelcomeActivity();
			}
		});

		processRequest();
	}


	public void gotoDashboardActivity() {
		Intent intent = new Intent(this, DashboardActivity.class);
		startActivity(intent);
	}

	public void gotoWelcomeActivity() {
		Intent intent = new Intent(this, WelcomeActivity.class);
		startActivity(intent);
	}


	public void processRequest() {

		RequestQueue queue = Volley.newRequestQueue(this);
		String url = TestWebservice.URL;

		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					APIAndroid api = JsonParserAPI.parseTest(jsonObject);
					current_user_urlTextView.setText(api.get_current_user_url());
					current_user_authorizations_html_urlTextView.setText(api.get_current_user_authorizations_html_url());
					authorizations_urlTextView.setText(api.get_authorizations_url());

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				current_user_urlTextView.setText("That didn't work!");
			}
		});
// Add the request to the RequestQueue.
		queue.add(stringRequest);


	}


}
