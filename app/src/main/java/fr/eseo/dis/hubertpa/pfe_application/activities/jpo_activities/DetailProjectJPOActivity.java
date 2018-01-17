package fr.eseo.dis.hubertpa.pfe_application.activities.jpo_activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectPORTE;

public class DetailProjectJPOActivity extends AppCompatActivity {
	ProjectPORTE projectPORTE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_project_jpo);

		Bundle data = getIntent().getExtras();
		assert data != null;

		projectPORTE = data.getParcelable("selected_project");


		TextView textViewTitle = findViewById(R.id.textViewTitle);
		textViewTitle.setText(projectPORTE.getProject().getTitle());


		TextView textViewDesc = findViewById(R.id.textViewDesc);
		textViewDesc.setText(projectPORTE.getProject().getDescription());
	}
}
