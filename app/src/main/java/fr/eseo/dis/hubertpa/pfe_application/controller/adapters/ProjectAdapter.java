package fr.eseo.dis.hubertpa.pfe_application.controller.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.ProjectActivity;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Project;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;

/**
 * Created by paulhubert on 03/01/18.
 */

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder> {

	private ProjectActivity activity;
	private List<Integer> positionsExpanded;

	public ProjectAdapter(ProjectActivity projectActivity) {
		this.activity = projectActivity;
		positionsExpanded = new ArrayList<Integer>();
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
		holder.projectDecription.setText(projectLIPRJ.getProject().getDescription());
		holder.projectConfidentialiy.setText(String.format("%d", projectLIPRJ.getProject().getConfidentiality()));
		holder.projectPoster.setText((projectLIPRJ.isPoster()) ? "Poster disponible" : "Poster non Disponible");

		holder.projectSuperviseur.setText(String.format("%s%s", projectLIPRJ.getSupervisor().getSurname(), projectLIPRJ.getSupervisor().getForename()));

		holder.view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("ProjectAdapter","Item 'clicked'");
				activity.clickItem(projectLIPRJ);
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

		public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			Log.d("ProjectAdapter","onCreateViewHolder()");
			View projectView = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.project_cards, parent, false);

			CardView projectCardView = (CardView)projectView;

			projectCardView.setCardElevation(3*ProjectActivity.NEW_CARD_COUNTER++);
			return new ProjectViewHolder(projectView);
		}

		public void onBindViewHolder(ProjectViewHolder holder, final int position) {
			Log.d("ProjectAdapter", "onBindViewHolder()");
			final ProjectLIPRJ projectLIPRJ = activity.getProjectListBuffer().get(position);
			holder.projectId.setText(String.format("%d", projectLIPRJ.getProject().getIdProject()));
			holder.projectTitre.setText(projectLIPRJ.getProject().getTitle());
			holder.projectDecription.setText(projectLIPRJ.getProject().getDescription());
			holder.projectPoster.setText(String.format("%s", projectLIPRJ.isPoster()));
			holder.projectConfidentialiy.setText(projectLIPRJ.getProject().getConfidentiality());
			holder.projectSuperviseur.setText(String.format("%s %s", projectLIPRJ.getSupervisor().getSurname(), projectLIPRJ.getSupervisor().getForename()));

			holder.view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d("FilmographyAdapter", "Item 'clicked'");
					activity.clickItem(projectLIPRJ);
				}
			});
		}

		public ProjectViewHolder(View view) {
			super(view);
			Log.d("ProjectViewHolder","ProjetViewHolder()");
			this.view = view;


			projectId = (TextView) view.findViewById(R.id.idtextView);
			projectTitre = (TextView) view.findViewById(R.id.titletextView);
			projectDecription = (TextView) view.findViewById(R.id.descriptiontextView);
			projectPoster = (TextView) view.findViewById(R.id.postertextView);
			projectSuperviseur = (TextView) view.findViewById(R.id.supervisortextViewValue);
			projectConfidentialiy = (TextView) view.findViewById(R.id.confidentialitytextViewValue);

		}
	}


}
