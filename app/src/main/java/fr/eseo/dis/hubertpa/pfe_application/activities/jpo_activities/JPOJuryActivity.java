package fr.eseo.dis.hubertpa.pfe_application.activities.jpo_activities;

import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.jpoDAO.UserDAO;
import fr.eseo.dis.hubertpa.pfe_application.controller.sqliteDataBase.AnnotationDAO;
import fr.eseo.dis.hubertpa.pfe_application.partials.BottomNavigationViewHelper;
import fr.eseo.dis.hubertpa.pfe_application.partials.NavigationBottomJPO;

public class JPOJuryActivity extends AppCompatActivity{
    Button add_jpo_jury;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBottomJPO(this, NavigationBottomJPO.PAGES.JURIES_JPO);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpo_jury);

        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.getMenu().getItem(1).setChecked(true);

        add_jpo_jury = findViewById(R.id.add_jpo_jury_button);

        setListeners();
    }

    private void setListeners() {
        add_jpo_jury.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                setContentView(R.layout.add_jpo_jury_form);
            }

        });
    }
}
