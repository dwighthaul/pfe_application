package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import fr.eseo.dis.hubertpa.pfe_application.R;

/**
 * Created by Morgan on 30/12/2017.
 */

public class MarkActivity extends AppCompatActivity{

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_project_tab:
                    Intent intent1 = new Intent(MarkActivity.this, ProjectActivity.class);
                    startActivity(intent1);
                    return true;
                case R.id.action_jury_tab:
                    Intent intent2 = new Intent(MarkActivity.this, JuryActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.action_mark_tab:

                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_marks);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.getMenu().getItem(2).setChecked(true);
    }
}
