package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.jpoDAO.MemoPosterDAO;
import fr.eseo.dis.hubertpa.pfe_application.model.jpoModel.MemoPosterJPO;

public class TakeNotesProjectActivity extends AppCompatActivity {

	TextView nameProjectTextView;
	EditText contentEditText;
	Button saveButton;
	int idProject;

	private MemoPosterDAO memoPosterDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_notes_project);
		nameProjectTextView = findViewById(R.id.titleProject);
		contentEditText = findViewById(R.id.contentEditText);
		saveButton = findViewById(R.id.saveButton);


		Bundle data = getIntent().getExtras();
		assert data != null;
		String nameProject = data.getString("nameProject");
		idProject = data.getInt("idProject");

		nameProjectTextView.setText(nameProject);

		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				saveData();
			}
		});

	}

	public void saveData() {
		Log.d("TakeNotes", "SaveData");
		String memoData = contentEditText.getText().toString();

		memoPosterDAO = new MemoPosterDAO(this);
		memoPosterDAO.open();

		MemoPosterJPO memoPosterJPO = new MemoPosterJPO();

		memoPosterJPO.setIdProject(idProject);
		memoPosterJPO.setText(memoData);

		memoPosterDAO.createMemoPoster(memoPosterJPO);

		Log.d("TakeNotes", "OKAJOUT");
	}
}
