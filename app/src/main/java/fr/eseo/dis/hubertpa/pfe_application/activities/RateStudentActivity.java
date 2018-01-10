package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import fr.eseo.dis.hubertpa.pfe_application.R;

public class RateStudentActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rate_student);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		bundle.getInt("studentId");
		bundle.getString("studentName");

		TextView studentNameTextView = (TextView)  findViewById(R.id.textViewStudentName);
		TextView studentIdTextView = (TextView) findViewById(R.id.textViewStudentId);

	}

}
