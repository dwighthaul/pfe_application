package fr.eseo.dis.hubertpa.pfe_application.controller.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.eseo.dis.hubertpa.pfe_application.R;

/**
 * Created by Morgan on 10/01/2018.
 */

public class AdaptorListProjectJury extends BaseAdapter {
    private final ArrayList _listProjects;

    public AdaptorListProjectJury(JuryAdapter activity, HashMap<Integer, String> listProjects) {
        _listProjects = new ArrayList();
        _listProjects.addAll(listProjects.entrySet());

    }

    @Override
    public int getCount() {
        return _listProjects.size();
    }

    @Override
    public Map.Entry<Integer, String> getItem(int position) {
        return (Map.Entry) _listProjects.get(position);
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.jury_project, parent, false);
        } else {
            result = convertView;
        }

        final Map.Entry<Integer, String> item = getItem(position);

        ((TextView) result.findViewById(R.id.textViewTitle)).setText(item.getValue());
        ((TextView) result.findViewById(R.id.textViewConfid)).setText(item.getValue());
        ((TextView) result.findViewById(R.id.textViewPoster)).setText(item.getValue());
        ((TextView) result.findViewById(R.id.textViewSupervisorForename)).setText(item.getValue());
        ((TextView) result.findViewById(R.id.textViewSupervisorSurname)).setText(item.getValue());

        //ImageButton goToRate = ((ImageButton) result.findViewById(R.id.rateStudent));

        /*.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map.Entry<Integer, String> student = item;
                Log.d("Test","Go to rate" + student.getValue());
                // student.getKey(), student.getValue()
                _activity.goToRatePage(item);
            }
        });*/



        return result;
    }
}
