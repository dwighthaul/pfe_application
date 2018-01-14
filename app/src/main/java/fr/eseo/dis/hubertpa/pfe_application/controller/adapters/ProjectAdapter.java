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
import fr.eseo.dis.hubertpa.pfe_application.activities.ProjectActivity;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

	private ProjectActivity activity;

	public ProjectAdapter(ProjectActivity projectActivity) {
		this.activity = projectActivity;
	}

	@Override
	public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View projectView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.project_cards, parent, false);
		Log.d("FilmographyAdaper","onCreateViewHolder()");
		return new ProjectViewHolder(projectView);
	}

	@Override
	public void onBindViewHolder(ProjectViewHolder holder, final int position) {
		Log.d("ProjectViewHolder","onBindViewHolder()");

		final ProjectLIPRJ projectLIPRJ = activity.getProjectListBuffer().get(position);
		holder.projectId.setText(String.format("%d", projectLIPRJ.getProject().getIdProject()));
		holder.projectTitre.setText(projectLIPRJ.getProject().getTitle());

		String desc = projectLIPRJ.getProject().getDescription();
		int limit = 75;
		if(projectLIPRJ.getProject().getDescription().length() > limit) {
			desc = projectLIPRJ.getProject().getDescription().substring(0, limit) + " [ ... ]";
		}

		holder.projectDecription.setText(desc);
		holder.projectConfidentialiy.setText(String.format("%d", projectLIPRJ.getProject().getConfidentiality()));
		holder.projectPoster.setText((projectLIPRJ.isPoster()) ? "Poster disponible" : "Poster non Disponible");


		if (projectLIPRJ.isPoster()) {
			holder.projectPoster.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
			holder.buttonDisplayPoster.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0));
		} else {
			holder.projectPoster.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0));
			holder.buttonDisplayPoster.setLayoutParams(new TableLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
		}

		holder.projectSuperviseur.setText(String.format("%s %s", projectLIPRJ.getSupervisor().getForename(), projectLIPRJ.getSupervisor().getSurname()));

		holder.view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("ProjectAdapter","Item 'clicked'");
				activity.clickItem(projectLIPRJ);
			}
		});


		holder.buttonDisplayPoster.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("ProjectAdapter","Item 'clicked'");
				activity.seePoster(projectLIPRJ);
			}
		});

		holder.takeNoteButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("ProjectAdapter","Item 'clicked'");
				activity.takeNotes(projectLIPRJ);
			}
		});


	}


	@Override
	public int getItemCount() {
		Log.d("ProjectAdapter","getItemCount()");
		return activity.getProjectListBuffer().size();
	}

	class ProjectViewHolder extends RecyclerView.ViewHolder {

		private final View view;

		private final TextView projectId;
		private final TextView projectTitre;
		private final TextView projectDecription;
		private final TextView projectPoster;
		private final TextView projectSuperviseur;
		private final TextView projectConfidentialiy;
		private final Button buttonDisplayPoster;
		private final Button takeNoteButton;

		ProjectViewHolder(View view) {
			super(view);
			Log.d("ProjectViewHolder","ProjetViewHolder()");
			this.view = view;

			projectId = view.findViewById(R.id.idtextView);
			projectTitre = view.findViewById(R.id.titletextView);
			projectDecription = view.findViewById(R.id.descriptiontextView);
			projectPoster = view.findViewById(R.id.postertextView);
			projectSuperviseur = view.findViewById(R.id.supervisortextViewValue);
			projectConfidentialiy = view.findViewById(R.id.confidentialitytextViewValue);

			buttonDisplayPoster = view.findViewById(R.id.buttonDisplayPoster);
			takeNoteButton = view.findViewById(R.id.takeNoteButton);
		}
	}





}
