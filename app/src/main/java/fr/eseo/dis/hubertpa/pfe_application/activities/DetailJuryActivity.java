package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.adapters.JuryProjectAdapter;
import fr.eseo.dis.hubertpa.pfe_application.controller.adapters.JuryProjectListAdaptor;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListProject;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListUser;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIJUR;

public class DetailJuryActivity extends AppCompatActivity {

	JuryLIJUR juryLIJUR;

	TextView textViewiDJury;
	TextView textViewDateValue;
	ListView textViewListMembersValue;
	ListView textViewListProjectsValue;
	Button setAlarm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_project_from_jury);

		textViewiDJury = findViewById(R.id.textViewiDJury);
		textViewDateValue = findViewById(R.id.textViewDateValue);
		textViewListMembersValue = findViewById(R.id.textViewListMembersValue);
		textViewListProjectsValue = findViewById(R.id.textViewListProjectsValue);
		setAlarm = findViewById(R.id.setAlarm);

		Bundle data = getIntent().getExtras();
		assert data != null;
		juryLIJUR = data.getParcelable("selected_jury");

		loadElements();

		setAdapteur();

		setListener();

	}

	private void setListener() {
		setAlarm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {

				Calendar cal = Calendar.getInstance();
				Intent intent = new Intent(Intent.ACTION_EDIT);
				intent.setType("vnd.android.cursor.item/event");
				String[] parts = juryLIJUR.getJury().getDate().split("-");

				int year = Integer.valueOf(parts[0]);
				int month = Integer.valueOf(parts[1]);
				int date = Integer.valueOf(parts[2]) + 1;

				intent.putExtra("beginTime", cal.getTimeInMillis());
				intent.putExtra("allDay", true);
				intent.putExtra("rrule", "FREQ=YEARLY");
				intent.putExtra("endTime", cal.getTimeInMillis()+60*60*1000);
				intent.putExtra("title", "Jury ESEO");
				startActivity(intent);
			}

		});



	}

	private void loadElements() {
		textViewiDJury.setText(String.valueOf(juryLIJUR.getJury().getIdJury()));
		textViewDateValue.setText(juryLIJUR.getJury().getDate());

		ListUser listMembers = juryLIJUR.getListMembers();

		List<String> listMembersString = new ArrayList<>();

		for (User student : listMembers) {
			String fullName = student.getForename() + " " + student.getSurname();
			listMembersString.add(fullName);
		}

		ArrayAdapter<String> adapterJuryMember = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_1, listMembersString);
		textViewListMembersValue.setAdapter(adapterJuryMember);

		ListProject listProjects = juryLIJUR.getListProject();
		List<String> listProjectString = new ArrayList<>();

		for (ProjectLIJUR project : listProjects) {
			StringBuilder data = new StringBuilder();
			String dataProject = project.getProject().getIdProject() + " " + project.getProject().getTitle();
			data.append(dataProject);
			data.append("\n");

			String dataSupervisor = "Supervisor : " + project.getSupervisor().getFormatedString();
			data.append(dataSupervisor);
			data.append("\n");

			listProjectString.add(data.toString());

			JuryProjectListAdaptor adapterProjects = new JuryProjectListAdaptor(this,
					android.R.layout.simple_list_item_1, listProjectString);
			textViewListProjectsValue.setAdapter(adapterProjects);
		}


	}



	public void setAdapteur() {
//			JuryProjectAdapter adapterProjects = new JuryProjectAdapter(this, juryLIJUR);
//			textViewListProjectsValue.setAdapter(adapterProjects);




	}
}
