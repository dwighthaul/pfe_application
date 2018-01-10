package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.adapters.AdaptorRateStudent;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListUser;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;

public class DetailProjectActivity extends AppCompatActivity {

	private TextView idView;
	private TextView titleView;
	private TextView descView;
	private TextView supeView;
	private RecyclerView studListView;
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
		studListView  = (RecyclerView) findViewById(R.id.textViewStudentValue);
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

		ListUser listStudents = projectLIPRJ.getListStudents();

		HashMap<Integer, String> listStudentsMap = new HashMap<Integer, String>();
		for (User student : listStudents) {
			Log.d("TEST", "" + student.getIdUser());
			String fullName = student.getForename() + " " + student.getSurname();
			listStudentsMap.put(student.getIdUser(), fullName);
		}

		AdaptorRateStudent adapter = new AdaptorRateStudent(this, listStudentsMap);
		studListView.setAdapter(adapter);

		String poster = (projectLIPRJ.isPoster()) ? "Poster Disponible" : "Poster non disponible";
		posterView.setText(poster);

	}

	public void goToRate() {
		Log.d("Test", "Voila");

	}

	public void goToRatePage(Map.Entry<Integer, String> student) {
		Log.d("Test", student.getValue());
//		Intent intent = new Intent(this, RateStudentActivity.class);
//		intent.putExtra("studentId", studentInfos.getKey());
//		intent.putExtra("studentName", studentInfos.getValue());
//		this.startActivity(intent);
	}


}
