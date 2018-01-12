package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.GlobalVolleyCallback;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.JsonParserAPI;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.StyleProject;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.VolleyCallbackLOGON;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LOGON;

public class RateStudentActivity extends AppCompatActivity {

	TextView studentNameTextView;
	TextView studentIdTextView;
	Button buttonRate;
	EditText markeditText;

	int idStudent;
	int idProject;
	String nameStudent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rate_student);

		studentNameTextView = findViewById(R.id.textViewStudentName);
		studentIdTextView = findViewById(R.id.textViewStudentId);
		buttonRate = findViewById(R.id.buttonRate);
		markeditText = findViewById(R.id.markeditText);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();


		assert bundle != null;
		idStudent = bundle.getInt("studentId");
		idProject = bundle.getInt("projectId");
		nameStudent = bundle.getString("studentName");

		studentIdTextView.setText(String.valueOf(idStudent));
		studentNameTextView.setText(nameStudent);

		addListenerButton();
	}

	private void addListenerButton() {
		buttonRate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {
					int note = Integer.parseInt(markeditText.getText().toString());
					if (note > 20) {
						Toast.makeText(RateStudentActivity.this, "Entrée non valide", Toast.LENGTH_LONG).show();
					} else {
						sendNote(note, getCallback());
					}
				} catch (NumberFormatException e){
					Toast.makeText(RateStudentActivity.this, "Entrée non valide", Toast.LENGTH_LONG).show();
				}
			}
		});

	}


	private void sendNote(int note, final GlobalVolleyCallback callback) {
		// Get the token from the saved data
		final String _token = WebServiceConnexion.getToken(this);
		final String _login = WebServiceConnexion.getLogin(this);

		String url = WebServiceConnexion.getNEWNT(_login, _token, idProject, idStudent, note);

		Log.d("TEST", url);
		RequestQueue queue = Volley.newRequestQueue(this, new HurlStack(null, WebServiceConnexion.newSslSocketFactory(this)));

		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					String result = jsonObject.getString("result");

					if (result.equals("OK")) { callback.onSuccess(); }
					else { callback.onError(); }

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


	private GlobalVolleyCallback getCallback() {
		// L'action appelée si la connexion au site a été réalisée.
		return new GlobalVolleyCallback() {
			@Override
			public void onSuccess() {
				Toast.makeText(RateStudentActivity.this, "Note de l'étudiant mis a jour", Toast.LENGTH_LONG).show();
			}
			@Override
			public void onError() {
				Toast.makeText(RateStudentActivity.this, "Erreur", Toast.LENGTH_LONG).show();
			}
		};
	}
}
