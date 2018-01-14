package fr.eseo.dis.hubertpa.pfe_application.controller.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.JuryActivity;

public class AdaptorListProjectJury extends BaseAdapter {

	private final ArrayList _listStudents;

	private JuryActivity _activity;

	public AdaptorListProjectJury(JuryActivity activity, HashMap<Integer, String> listProjectsJury) {
		_listStudents = new ArrayList();
		_listStudents.addAll(listProjectsJury.entrySet());

		_activity = activity;

	}


	@Override
	public int getCount() {
		return _listStudents.size();
	}

	@Override
	public Map.Entry<Integer, String> getItem(int position) {
		return (Map.Entry) _listStudents.get(position);
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

		((TextView) result.findViewById(R.id.idProjectTextView)).setText(String.valueOf(item.getKey()));
		((TextView) result.findViewById(R.id.nameProjectTextView)).setText(item.getValue());

		return result;
	}

}
