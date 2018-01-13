package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.adapters.RateStudentsAdapter;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.GlobalVolleyCallback;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackNOTES;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.JsonParserAPI;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.VolleyQueue;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListNote;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListUser;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.NOTES;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.NotesNOTES;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;

public class MarkStudentsFromProjectActivity extends AppCompatActivity {


	VolleyCallbackNOTES callback;
	ProjectLIPRJ projectLIPRJ;

	private RecyclerView listViewStudentValue;
	private List<Integer> listIdStudentOnProject;

	private EditText globalMarkeditText;
	private Button buttonRateAllStudents;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mark_students_from_project);

		globalMarkeditText = findViewById(R.id.globalMarkeditText);
		buttonRateAllStudents = findViewById(R.id.buttonRateAllStudent);
		Bundle data = getIntent().getExtras();
		assert data != null;
		projectLIPRJ = data.getParcelable("selected_project");

		setListener();

		loadData();

		setRecycler();

		setCallbackNotes();

		loadMarks();

	}

	private void setListener() {
		for (final User student : projectLIPRJ.getListStudents()) {
			final int _idStudent = student.getIdUser();
			final String _nameStudent = student.getFormatedString();

			final GlobalVolleyCallback callback = getCallbackRateStudent();

			buttonRateAllStudents.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {

					for (User user : projectLIPRJ.getListStudents()) {

						try {
							int note = Integer.parseInt(globalMarkeditText.getText().toString());
							if (note <= 20) {
								DetailProjectActivity.sendNoteStudentProject(note, user.getIdUser(), projectLIPRJ.getProject().getIdProject(), MarkStudentsFromProjectActivity.this, callback);
							} else {
								Toast.makeText(MarkStudentsFromProjectActivity.this, "Entrée non valide", Toast.LENGTH_LONG).show();
							}
						} catch (NumberFormatException e){
							Toast.makeText(MarkStudentsFromProjectActivity.this, "Entrée non valide", Toast.LENGTH_LONG).show();
						}
					}

				}
				});
			}
	}

	/**
	 * Set the recycler. Basic setter
	 */
	private void setRecycler() {
		listViewStudentValue  = findViewById(R.id.ListViewStudentValue);
		listViewStudentValue.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		listViewStudentValue.setLayoutManager(llm);
}

	private void loadData() {
		TextView textViewProjectName = findViewById(R.id.textViewProjectName);
		textViewProjectName.setText(projectLIPRJ.getProject().getTitle());

		listIdStudentOnProject = new ArrayList<>();

		for (User student : projectLIPRJ.getListStudents()) {
			listIdStudentOnProject.add(student.getIdUser());
		}

	}

	private void loadMarks () {
		// Get the token from the saved data

		final String _token = WebServiceConnexion.getToken(this);
		final String _login = WebServiceConnexion.getLogin(this);

		int idProject = projectLIPRJ.getProject().getIdProject();

		// Get the URL to log into the server
		final String url = WebServiceConnexion.getNOTES(_login, _token, idProject);

		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					String result = jsonObject.getString("result");
					Log.d("Connect", "" + result);

					if (result.equals("OK")) {
						NOTES notes = JsonParserAPI.parseNOTES(jsonObject);
						callback.onSuccess(notes);
					} else {
						callback.onError(jsonObject.getString("error"));
					}

				} catch (JSONException e) {

					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
			}
		});

		RequestQueue queue = Volley.newRequestQueue(this, new HurlStack(null, WebServiceConnexion.newSslSocketFactory(this)));
		queue.add(stringRequest);
	}

	private void setCallbackNotes() {
		callback = new VolleyCallbackNOTES() {

			@SuppressLint("SetTextI18n")
			@Override
			public void onSuccess(NOTES notes) {

				// Get the notes from the
				ListNote listNotes = filterOnlyWantedStudent(notes.getNotesList());

				// Create the list of students
				ListNote listStudentsToDisplay = createListNotes(projectLIPRJ.getListStudents());

				for (NotesNOTES notesNOTES: listNotes) {
					for(int i = 0; i < listStudentsToDisplay.size(); i++) {

						if (notesNOTES.getStudent().getIdUser() == listStudentsToDisplay.get(i).getStudent().getIdUser()) {
							listStudentsToDisplay.get(i).setNoteSet(true);

							listStudentsToDisplay.get(i).setAvgnote(notesNOTES.getAvgnote());
							listStudentsToDisplay.get(i).setMynote(notesNOTES.getMynote());
						}
					}
				}

				if(listStudentsToDisplay.get(0).isNoteSet()) {
					globalMarkeditText.setText(String.valueOf(listStudentsToDisplay.get(0).getAvgnote()));
				} else {
					globalMarkeditText.setText("Non noté");
				}


				RateStudentsAdapter adapter = new RateStudentsAdapter(MarkStudentsFromProjectActivity.this, listStudentsToDisplay);
				listViewStudentValue.setAdapter(adapter);

			}

			@Override
			public void onError(String errorMessage) {

			}
		};
	}

	public void goToRateStudentPage(NotesNOTES notesNOTES) {
		Intent intent = new Intent(this, RateStudentActivity.class);
		intent.putExtra("studentId", notesNOTES.getStudent().getIdUser());
		intent.putExtra("projectId", projectLIPRJ.getProject().getIdProject());
		intent.putExtra("studentNote", notesNOTES.getMynote());
		String studentName = notesNOTES.getStudent().getForename() + " " + notesNOTES.getStudent().getSurname();
		intent.putExtra("studentName", studentName);
		this.startActivity(intent);
	}


	public ListNote createListNotes(ListUser listUser) {
		ListNote listNotes = new ListNote();
		for (User user : listUser) {
			NotesNOTES notesNOTES = new NotesNOTES();
			notesNOTES.setStudent(user);

			listNotes.add(notesNOTES);
		}
		return listNotes;
	}

	public ListNote filterOnlyWantedStudent(ListNote notesList) {
		ListNote listToReturn = new ListNote();

		for(NotesNOTES note : notesList) {
			if (listIdStudentOnProject.contains(note.getStudent().getIdUser())) {
				listToReturn.add(note);
			}
		}
		return listToReturn;
	}



	private GlobalVolleyCallback getCallbackRateStudent() {
		// L'action appelée si la connexion au site a été réalisée.
		return new GlobalVolleyCallback() {

			@Override
			public void onSuccess() {
				StringBuilder stringBuilder = new StringBuilder();
				for (int i = 0; i < projectLIPRJ.getListStudents().size(); i++) {
					User user = projectLIPRJ.getListStudents().get(i);
					stringBuilder.append(user.getFormatedString());
					if (i != projectLIPRJ.getListStudents().size() - 1)
						stringBuilder.append(", ");
				}
				Toast.makeText(MarkStudentsFromProjectActivity.this, "Note des étudiants " + stringBuilder.toString() + " mis a jour", Toast.LENGTH_LONG).show();
			}
			@Override
			public void onError(String message) {
				Toast.makeText(MarkStudentsFromProjectActivity.this, message, Toast.LENGTH_LONG).show();
			}
		};
	}
}
