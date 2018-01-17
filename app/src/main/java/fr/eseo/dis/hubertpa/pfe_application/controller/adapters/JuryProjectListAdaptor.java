package fr.eseo.dis.hubertpa.pfe_application.controller.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.R;

public class JuryProjectListAdaptor extends ArrayAdapter<String> {
	List<String> objects;

	public JuryProjectListAdaptor(@NonNull Context context, int resource, @NonNull List<String> Inobjects) {
		super(context, resource, Inobjects);
		objects = Inobjects;
	}


}
