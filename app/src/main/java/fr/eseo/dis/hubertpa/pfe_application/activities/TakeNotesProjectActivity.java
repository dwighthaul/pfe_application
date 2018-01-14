package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Comment;

import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.jpoDAO.MemoPosterDAO;
import fr.eseo.dis.hubertpa.pfe_application.controller.sqliteDataBase.AnnotationDAO;
import fr.eseo.dis.hubertpa.pfe_application.controller.sqliteDataBase.MySQLiteHelper;
import fr.eseo.dis.hubertpa.pfe_application.model.jpoModel.MemoPosterJPO;
import fr.eseo.dis.hubertpa.pfe_application.model.sqliteDataBase.Annotation;

public class TakeNotesProjectActivity extends AppCompatActivity {

	TextView nameProjectTextView;
	EditText contentEditText;
	Button saveButton;
	int idProject;

	private MemoPosterDAO memoPosterDAO;
	private AnnotationDAO annotationDAO;
	private boolean existe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_notes_project);
		nameProjectTextView = findViewById(R.id.titleProject);
		contentEditText = findViewById(R.id.contentEditText);
		saveButton = findViewById(R.id.saveButton);

		annotationDAO = new AnnotationDAO(this, false);

		Bundle data = getIntent().getExtras();
		assert data != null;
		String nameProject = data.getString("nameProject");
		idProject = data.getInt("idProject");

		nameProjectTextView.setText(nameProject);

		loadText();

		saveButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				saveData();
			}
		});
	}

	public void loadText() {
		annotationDAO.open();

		List<Annotation> annotations = annotationDAO.findAnnotationFromProject(idProject);
		Log.d("TakeNotes", "NBR GET " + annotations.size());
		if (annotations.size() != 0){
			existe = true;
			contentEditText.setText(annotations.get(0).getAnnotation());
		}
		annotationDAO.close();

	}

	public void saveData() {
		String memoData = contentEditText.getText().toString();
		annotationDAO.open();

		if (existe) {
			annotationDAO.updateAnnotation(memoData, idProject);
		} else {
			annotationDAO.createAnnotation(memoData, idProject);
		}

		annotationDAO.close();

	}
}
