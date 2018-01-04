package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;

public class DetailProjectActivity extends AppCompatActivity {

	private TextView idView;
	private TextView titleView;
	private TextView descView;
	private TextView supeView;
	private TextView studView;
	private TextView posterView;

	ProjectLIPRJ projectLIPRJ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_project);

		Bundle data = getIntent().getExtras();
		projectLIPRJ = (ProjectLIPRJ) data.getParcelable("selected_project");

//		Log.d("DetailProjectActivity", "" + );

		idView = (TextView) findViewById(R.id.textViewiD);
		titleView = (TextView) findViewById(R.id.textViewTitle);
		descView = (TextView) findViewById(R.id.textViewDescription);
		supeView = (TextView) findViewById(R.id.textViewSupervisorValue);
		studView  = (TextView) findViewById(R.id.textViewStudentValue);
		posterView = (TextView) findViewById(R.id.TextViewPoster);

		loadElements();
	}

	private void loadElements() {

		String idProject = String.valueOf(projectLIPRJ.getProject().getIdProject());
		idView.setText(idProject);
		titleView.setText(projectLIPRJ.getProject().getTitle());
		descView.setText(projectLIPRJ.getProject().getDescription());

		String supervisor = projectLIPRJ.getSupervisor().getForename() + " " + projectLIPRJ.getSupervisor().getSurname();
		supeView.setText(supervisor);

//		String firstStudent = projectLIPRJ.getListStudents().get(0).getForename() + " " + projectLIPRJ.getListStudents().get(0).getSurname();
//		studView.setText(firstStudent);

		String poster = (projectLIPRJ.isPoster()) ? "Poster Disponible" : "Poster non disponible";
		posterView.setText(poster);

	}
}
