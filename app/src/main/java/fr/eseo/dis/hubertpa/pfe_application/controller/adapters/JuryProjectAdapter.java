package fr.eseo.dis.hubertpa.pfe_application.controller.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.DetailJuryActivity;
import fr.eseo.dis.hubertpa.pfe_application.activities.MarkStudentsFromProjectActivity;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListNote;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListProject;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Project;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.NotesNOTES;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;
import lombok.Getter;
import lombok.Setter;

public class JuryProjectAdapter extends RecyclerView.Adapter<JuryProjectAdapter.JuryProjectAdapterViewHolder> {

	private DetailJuryActivity activity;

	@Getter @Setter
	private JuryLIJUR juryLIJUR;

	public JuryProjectAdapter(DetailJuryActivity juryActivity, JuryLIJUR juryLIJUR) {
		Log.d("TEST" ,"" + juryLIJUR.getListProject().size());
		this.activity = juryActivity;
		this.juryLIJUR = juryLIJUR;
	}

	@Override
	public JuryProjectAdapter.JuryProjectAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View juryView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.line_rate_project, parent, false);
		return new JuryProjectAdapterViewHolder(juryView);
	}

	@Override
	public void onBindViewHolder(JuryProjectAdapterViewHolder holder, final int position) {
		Log.d("JuryViewHolder","onBindViewHolder()");

		final ProjectLIJUR projectLIJUR = juryLIJUR.getListProject().get(position);
		holder.projectIDTextView.setText(String.valueOf(projectLIJUR.getProject().getIdProject()));
		holder.projectNameTextView.setText(projectLIJUR.getProject().getTitle());

		holder.supervisierIDTextView.setText(String.valueOf((projectLIJUR.getSupervisor().getIdUser())));
		holder.supervisierNameTextView.setText(projectLIJUR.getSupervisor().getFormatedString());

		holder.rateProjectImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				activity.loadProject(projectLIJUR.getProject().getIdProject());
			}
		});

	}


	@Override
	public int getItemCount() {
		return this.getJuryLIJUR().getListProject().size();
	}

	class JuryProjectAdapterViewHolder extends RecyclerView.ViewHolder {

		private final View view;

		private final TextView projectIDTextView;
		private final TextView projectNameTextView;

		private final TextView supervisierIDTextView;
		private final TextView supervisierNameTextView;

		private final ImageButton rateProjectImageButton;

		JuryProjectAdapterViewHolder(View view) {
			super(view);
			Log.d("JuryViewHolder", "JuryViewHolder()");
			this.view = view;

			projectIDTextView = view.findViewById(R.id.projectIDTextView);
			projectNameTextView = view.findViewById(R.id.projectNameTextView);

			supervisierIDTextView = view.findViewById(R.id.supervisierIDTextView);
			supervisierNameTextView = view.findViewById(R.id.supervisierNameTextView);

			rateProjectImageButton = view.findViewById(R.id.rateProjectImageButton);
		}
	}
}
