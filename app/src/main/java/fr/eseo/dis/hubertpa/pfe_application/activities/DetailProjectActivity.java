package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
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
	private Button displayPoster;
	private TextView descView;
	private TextView supeView;
	private ListView studListView;
	private TextView posterView;

	ProjectLIPRJ projectLIPRJ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_project);

		Bundle data = getIntent().getExtras();
		assert data != null;
		projectLIPRJ = (ProjectLIPRJ) data.getParcelable("selected_project");

		idView = findViewById(R.id.textViewiD);
		titleView = findViewById(R.id.textViewTitle);
		displayPoster = findViewById(R.id.displayPoster);
		descView = findViewById(R.id.textViewDescriptionValue);
		descView.setMovementMethod(new ScrollingMovementMethod());
		supeView = findViewById(R.id.textViewSupervisorValue);
		studListView  = findViewById(R.id.textViewStudentValue);
		posterView = findViewById(R.id.TextViewPoster);

		loadElements();

		dealWithLayoutIssues();

	}

	private void dealWithLayoutIssues() {

		ListAdapter myListAdapter = studListView.getAdapter();

		int totalHeight = 0;
		for (int size = 0; size < myListAdapter.getCount(); size++) {
			View listItem = myListAdapter.getView(size, null, studListView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = studListView.getLayoutParams();
		params.height = totalHeight + (studListView.getDividerHeight() * (studListView.getCount() - 1));
		studListView.setLayoutParams(params);
	}



	private void loadElements() {

		String idProject = String.valueOf(projectLIPRJ.getProject().getIdProject());
		idView.setText(idProject);
		titleView.setText(projectLIPRJ.getProject().getTitle());
		descView.setText(projectLIPRJ.getProject().getDescription());

		displayPoster.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Log.d("Welcome", "Connect");
				seePoster(projectLIPRJ);
			}

		});

		String supervisor = projectLIPRJ.getSupervisor().getForename() + " " + projectLIPRJ.getSupervisor().getSurname();
		supeView.setText(supervisor);

		String poster = (projectLIPRJ.isPoster()) ? "Poster Disponible" : "Poster non disponible";
		posterView.setText(poster);

		ListUser listStudents = projectLIPRJ.getListStudents();

		HashMap<Integer, String> listStudentsMap = new HashMap<Integer, String>();
		for (User student : listStudents) {
			String fullName = student.getForename() + " " + student.getSurname();
			listStudentsMap.put(student.getIdUser(), fullName);
		}

		AdaptorRateStudent adapter = new AdaptorRateStudent(this, listStudentsMap);
		studListView.setAdapter(adapter);

		loadPoster();
	}

	public void loadPoster() {
		if(projectLIPRJ.isPoster()) {
			posterView.setVisibility(View.GONE);
		} else {
			ConstraintLayout constraintLayout = findViewById(R.id.containerImage);
			constraintLayout.setVisibility(View.GONE);
		}
		// TODO
	}

	public void goToRatePage(Map.Entry<Integer, String> student) {
		Intent intent = new Intent(DetailProjectActivity.this, RateStudentActivity.class);
		intent.putExtra("studentId", student.getKey());
		intent.putExtra("studentName", student.getValue());
		DetailProjectActivity.this.startActivity(intent);
	}

	public void seePoster(ProjectLIPRJ projectLIPRJ) {
		Intent intent = new Intent(this, DislayPosterActivity.class);
		intent.putExtra("selected_project", projectLIPRJ);
		startActivity(intent);
	}


}
