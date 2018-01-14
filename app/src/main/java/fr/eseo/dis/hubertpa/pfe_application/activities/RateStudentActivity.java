package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.GlobalVolleyCallback;

public class RateStudentActivity extends AppCompatActivity {

	TextView studentNameTextView;
	TextView studentIdTextView;
	Button buttonRate;
	EditText markeditText;

	int idStudent;
	int noteStudent;
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
		noteStudent = bundle.getInt("studentNote");
		idProject = bundle.getInt("projectId");
		nameStudent = bundle.getString("studentName");

		markeditText.setText(String.valueOf(noteStudent));
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
					if (note <= 20) {
						DetailProjectActivity.sendNoteStudentProject(note, idStudent, idProject, RateStudentActivity.this, getCallback());
					} else {
						Toast.makeText(RateStudentActivity.this, "Entrée non valide", Toast.LENGTH_LONG).show();
					}
				} catch (NumberFormatException e) {
					Toast.makeText(RateStudentActivity.this, "Entrée non valide", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	private GlobalVolleyCallback getCallback() {
		// L'action appelée si la connexion au site a été réalisée.
		return new GlobalVolleyCallback() {
			@Override
			public void onSuccess() {
				Toast.makeText(RateStudentActivity.this, "Note de l'étudiant " + nameStudent + " à été mise a jour", Toast.LENGTH_LONG).show();
			}
			@Override
			public void onError(String message) {
				Toast.makeText(RateStudentActivity.this, message, Toast.LENGTH_LONG).show();
			}
		};
	}
}
