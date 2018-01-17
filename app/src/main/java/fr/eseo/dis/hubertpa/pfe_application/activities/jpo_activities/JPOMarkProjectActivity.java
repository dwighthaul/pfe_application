package fr.eseo.dis.hubertpa.pfe_application.activities.jpo_activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.sqliteDataBase.NoteProjectJPO;
import fr.eseo.dis.hubertpa.pfe_application.model.sqliteDataBase.Note;

public class JPOMarkProjectActivity extends AppCompatActivity {

	NoteProjectJPO noteAPI;
	int idProject;

	TextView textViewNameLabel;


	EditText editTextNote;
	Button saveButton;

	boolean existe;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mark_project);

		textViewNameLabel = findViewById(R.id.textViewNameLabel);
		editTextNote = findViewById(R.id.editTextNote);
		saveButton = findViewById(R.id.saveButton);

		existe = false;

		noteAPI = new NoteProjectJPO(this, true);

		Bundle data = getIntent().getExtras();
		assert data != null;

		textViewNameLabel.setText(data.getInt("nameProject"));
		idProject = data.getInt("idProject");

		loadText();

		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				saveData();
			}
		});

	}

	public void loadText() {
		noteAPI.open();

		List<Note> annotations = noteAPI.findNoteFromProject(idProject);
		Log.d("TakeNotes", "NBR GET " + annotations.size());
		if (annotations.size() != 0){
			existe = true;
			editTextNote.setText(String.valueOf(annotations.get(0).getNote()));
		}
		noteAPI.close();


	}


	public void saveData() {
		String markS = editTextNote.getText().toString();

		try {
			noteAPI.open();
			int mark = Integer.valueOf(markS);
			if (existe) {
				noteAPI.updateNote(mark, idProject);
			} else {
				noteAPI.createAnnotation(mark, idProject);
			}

			noteAPI.close();

		} catch (NumberFormatException e) {
			Log.d("NumberFormatException", e.toString());
		}

	}

}

