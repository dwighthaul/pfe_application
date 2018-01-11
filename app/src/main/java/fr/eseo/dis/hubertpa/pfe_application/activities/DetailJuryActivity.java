package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.adapters.AdaptorRateStudent;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListProject;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListUser;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Project;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIJUR;

public class DetailJuryActivity extends AppCompatActivity {

	JuryLIJUR juryLIJUR;

	TextView textViewiDJury;
	TextView textViewDateValue;
	ListView textViewListMembersValue;
	ListView textViewListProjectsValue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_project_from_jury);

		textViewiDJury = findViewById(R.id.textViewiDJury);
		textViewDateValue = findViewById(R.id.textViewDateValue);
		textViewListMembersValue = findViewById(R.id.textViewListMembersValue);
		textViewListProjectsValue = findViewById(R.id.textViewListProjectsValue);

		Bundle data = getIntent().getExtras();
		juryLIJUR = (JuryLIJUR) data.getParcelable("selected_jury");

		loadElements();


	}

	private void loadElements() {
		textViewiDJury.setText(String.format("%d", juryLIJUR.getJury().getIdJury()));
		textViewDateValue.setText(juryLIJUR.getJury().getDate());

		ListUser listMembers = juryLIJUR.getListMembers();

		List<String> listMembersString = new ArrayList<String>();

//		HashMap<Integer, String> listMembersMap = new HashMap<Integer, String>();
		for (User student : listMembers) {
			String fullName = student.getForename() + " " + student.getSurname();
			listMembersString.add(fullName);
			//.put(student.getIdUser(), fullName);
		}

		ArrayAdapter<String> adapterJuryMemeber = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listMembersString);
		textViewListMembersValue.setAdapter(adapterJuryMemeber);

		ListProject listProjects = juryLIJUR.getListProject();
		List<String> listProjectString = new ArrayList<String>();

		for (ProjectLIJUR project : listProjects) {
			StringBuilder data = new StringBuilder();
			String dataProject = project.getProject().getIdProject() + " " + project.getProject().getTitle();
			data.append(dataProject);
			data.append("\n");

			String dataSupervisor = project.getSupervisor().getForename() + " " + project.getSupervisor().getSurname();
			data.append(dataSupervisor);
			data.append("\n");

			listProjectString.add(data.toString());
		}

		ArrayAdapter<String> adapterProjects = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, listProjectString);
		textViewListProjectsValue.setAdapter(adapterProjects);

	}
}
