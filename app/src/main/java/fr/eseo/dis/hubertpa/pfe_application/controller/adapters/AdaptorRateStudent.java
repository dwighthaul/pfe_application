package fr.eseo.dis.hubertpa.pfe_application.controller.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.DetailProjectActivity;

/**
 * Created by paulhubert on 08/01/18.
 */

public class AdaptorRateStudent extends BaseAdapter {

	private final ArrayList _listStudents;

	DetailProjectActivity _activity;

	public AdaptorRateStudent(DetailProjectActivity activity, HashMap<Integer, String> listStudents) {
		_listStudents = new ArrayList();
		_listStudents.addAll(listStudents.entrySet());

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
			result = LayoutInflater.from(parent.getContext()).inflate(R.layout.line_rate_student, parent, false);
		} else {
			result = convertView;
		}

		final Map.Entry<Integer, String> item = getItem(position);

		((TextView) result.findViewById(R.id.StudentName)).setText(String.valueOf(item.getKey()));
		((TextView) result.findViewById(R.id.StudentID)).setText(item.getValue());

		ImageButton goToRate = ((ImageButton) result.findViewById(R.id.rateStudent));

		goToRate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Map.Entry<Integer, String> student = item;
				Log.d("Test","Go to rate" + student.getValue());
				// student.getKey(), student.getValue()
				_activity.goToRatePage(item);
			}
		});



		return result;
	}

}