package fr.eseo.dis.hubertpa.pfe_application.activities.jpo_activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.jpoDAO.UserDAO;

public class FormAddJuryJPOActivity extends AppCompatActivity{

    Button validate_form_jury;

    private UserDAO userdao;

    private EditText jpoJury1Forename;
    private EditText jpoJury1Surname;
    private EditText jpoJury2Forename;
    private EditText jpoJury2Surname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_jpo_jury_form);

        validate_form_jury = findViewById(R.id.button_validate_add_jpo_jury);

        setListeners();
    }

    private void setListeners() {

        validate_form_jury.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getValueForm();
                setContentView(R.layout.activity_jpo_setting);
            }
        });
    }

    private void getValueForm(){
        jpoJury1Forename = findViewById(R.id.jpo_jury_1_forename);
        jpoJury1Surname = findViewById(R.id.jpo_jury_1_surname);
        jpoJury2Forename = findViewById(R.id.jpo_jury_2_forename);
        jpoJury2Surname = findViewById(R.id.jpo_jury_2_surname);

        final String jpoJury1ForenameText = jpoJury1Forename.getText().toString();
        final String jpoJury1SurnameText = jpoJury1Surname.getText().toString();
        final String jpoJury2ForenameText = jpoJury2Forename.getText().toString();
        final String jpoJury2SurnameText = jpoJury2Surname.getText().toString();

        addJpoJuryToBDD(jpoJury1ForenameText, jpoJury1SurnameText, jpoJury2ForenameText, jpoJury2SurnameText);
    }

    private void addJpoJuryToBDD(String jpoJury1ForenameText, String jpoJury1SurnameText, String jpoJury2ForenameText, String jpoJury2SurnameText) {
        userdao.open();
        userdao.createUser(jpoJury1ForenameText, jpoJury1SurnameText);
        userdao.createUser(jpoJury2ForenameText, jpoJury2SurnameText);
        userdao.close();
    }
}
