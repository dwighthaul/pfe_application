package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import fr.eseo.dis.hubertpa.pfe_application.R;

public class RateStudentActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rate_student);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();

		TextView studentNameTextView = findViewById(R.id.textViewStudentName);
		TextView studentIdTextView = findViewById(R.id.textViewStudentId);

		assert bundle != null;
		studentNameTextView.setText(String.valueOf(bundle.getInt("studentId")));
		studentIdTextView.setText(bundle.getString("studentName"));
	}

}
