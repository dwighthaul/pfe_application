package fr.eseo.dis.hubertpa.pfe_application.activities.jpo_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.DetailProjectActivity;
import fr.eseo.dis.hubertpa.pfe_application.controller.sqliteDataBase.AnnotationDAO;
import fr.eseo.dis.hubertpa.pfe_application.controller.sqliteDataBase.NoteProjectJPO;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectPORTE;
import fr.eseo.dis.hubertpa.pfe_application.model.sqliteDataBase.Annotation;
import fr.eseo.dis.hubertpa.pfe_application.model.sqliteDataBase.Note;

public class DetailProjectJPOActivity extends AppCompatActivity {
	ProjectPORTE projectPORTE;

	NoteProjectJPO noteAPI;
	Button buttonRate;

	TextView textViewMarkValue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_project_jpo);

		textViewMarkValue = findViewById(R.id.textViewMarkValue);
		buttonRate = findViewById(R.id.buttonRate);

		noteAPI = new NoteProjectJPO(this, false);

		Bundle data = getIntent().getExtras();
		assert data != null;

		projectPORTE = data.getParcelable("selected_project");

		TextView textViewTitle = findViewById(R.id.textViewTitle);
		textViewTitle.setText(projectPORTE.getProject().getTitle());


		TextView textViewDesc = findViewById(R.id.textViewDesc);
		textViewDesc.setText(projectPORTE.getProject().getDescription());


//		loadText();

		buttonRate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Toast.makeText(DetailProjectJPOActivity.this, "Not implemented yet", Toast.LENGTH_LONG).show();
//				Intent intent = new Intent(DetailProjectJPOActivity.this, JPOMarkProjectActivity.class);
//				intent.putExtra("nameProject", projectPORTE.getProject().getTitle());
//				intent.putExtra("idProject", projectPORTE.getProject().getIdProject());
//				startActivity(intent);

			}
		});

	}

	public void loadText() {
		noteAPI.open();

		List<Note> annotations = noteAPI.findNoteFromProject(projectPORTE.getProject().getIdProject());
		Log.d("TakeNotes", "NBR GET " + annotations.size());
		if (annotations.size() != 0){
			textViewMarkValue.setText(String.valueOf(annotations.get(0).getNote()));
		}
		noteAPI.close();

	}

}
