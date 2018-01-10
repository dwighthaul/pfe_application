package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.JsonParserAPI;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;

public class TakeNotesProjectActivity extends AppCompatActivity {

	TextView nameProjectTextView;
	EditText contentEditText;
	Button saveButton;
	int idProject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.take_notes_project);
		nameProjectTextView = findViewById(R.id.titleProject);
		contentEditText = findViewById(R.id.contentEditText);
		saveButton = findViewById(R.id.saveButton);


		Bundle data = getIntent().getExtras();
		String nameProject = data.getString("nameProject");
		idProject = data.getInt("idProject");

		nameProjectTextView.setText(nameProject);

		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				SaveData();
			}
		});

	}

	private void SaveData() {
		Log.d("TakeNotes", "SaveData");
		// TODO : Save the content
		// Get the token from the saved data
	}
}
