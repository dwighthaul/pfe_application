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
import fr.eseo.dis.hubertpa.pfe_application.partials.BottomNavigationViewHelper;
import fr.eseo.dis.hubertpa.pfe_application.partials.NavigationBottom;

/**
 * Created by Morgan on 30/12/2017.
 */

public class MarkActivity extends AppCompatActivity {

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBottom(this, NavigationBottom.PAGES.MARKS);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_marks);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigation.getMenu().getItem(2).setChecked(true);
    }
}
