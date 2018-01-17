package fr.eseo.dis.hubertpa.pfe_application.controller.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.jpo_activities.JPOProjectActivity;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectPORTE;

public class JPOProjectAdapter extends RecyclerView.Adapter<JPOProjectAdapter.ProjectViewHolder> {

	private JPOProjectActivity activity;

	public JPOProjectAdapter(JPOProjectActivity projectActivity) {
		this.activity = projectActivity;
	}

	@Override
	public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View projectView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.jpo_project_cards, parent, false);
		return new ProjectViewHolder(projectView);
	}

	@Override
	public void onBindViewHolder(ProjectViewHolder holder, final int position) {
		Log.d("ProjectViewHolder","onBindViewHolder()");

		final ProjectPORTE projectPORTE = activity.getProjectListBuffer().get(position);
		holder.idProjectTextView.setText(String.format("%d", projectPORTE.getProject().getIdProject()));
		holder.nameProjectTextView.setText(projectPORTE.getProject().getTitle());

		String desc = projectPORTE.getProject().getDescription();
		int limit = 75;
		if(projectPORTE.getProject().getDescription().length() > limit) {
			desc = projectPORTE.getProject().getDescription().substring(0, limit) + " [ ... ]";
		}

		holder.descriptionProjectTextView.setText(desc);

//		holder.buttonDisplayPoster.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0));

		holder.view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("ProjectAdapter","Item 'clicked'");
				activity.clickItem(projectPORTE);
			}
		});

//		holder.buttonDisplayPoster.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Log.d("ProjectAdapter","Item 'clicked'");
//				activity.seePoster(projectPORTE.getProject().getIdProject());
//			}
//		});



	}


	@Override
	public int getItemCount() {
		Log.d("ProjectAdapter","getItemCount()");
		return activity.getProjectListBuffer().size();
	}

	class ProjectViewHolder extends RecyclerView.ViewHolder {

		private final View view;

		private final TextView idProjectTextView;
		private final TextView nameProjectTextView;
		private final TextView descriptionProjectTextView;

		ProjectViewHolder(View view) {
			super(view);
			Log.d("ProjectViewHolder","ProjetViewHolder()");
			this.view = view;

			idProjectTextView = view.findViewById(R.id.idProjectTextView);
			nameProjectTextView = view.findViewById(R.id.nameProjectTextView);
			descriptionProjectTextView = view.findViewById(R.id.descriptionProjectTextView);

		}
	}





}
