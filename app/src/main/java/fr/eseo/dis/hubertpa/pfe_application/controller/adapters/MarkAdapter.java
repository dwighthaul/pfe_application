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
import fr.eseo.dis.hubertpa.pfe_application.activities.MarkActivity;
import fr.eseo.dis.hubertpa.pfe_application.activities.ProjectActivity;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListNote;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.NotesNOTES;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;

public class MarkAdapter extends RecyclerView.Adapter<MarkAdapter.ProjectViewHolder> {

	private MarkActivity activity;

	public MarkAdapter(MarkActivity markActivity) {
		this.activity = markActivity;
	}

	@Override
	public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View projectView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.note_cards, parent, false);
		Log.d("FilmographyAdaper","onCreateViewHolder()");
		return new ProjectViewHolder(projectView);
	}

	@Override
	public void onBindViewHolder(ProjectViewHolder holder, final int position) {
		Log.d("ProjectViewHolder","onBindViewHolder()");

		final NotesNOTES note = activity.getListNoteBuffer().get(position);
		holder.textViewIdStudent.setText(String.format("%d", note.getStudent().getIdUser()));
		holder.textViewNameStudent.setText(note.getStudent().getFormatedString());
		holder.textViewMyMark.setText(note.getMynote());
		holder.textViewAvgMark.setText(note.getAvgnote());
	}


	@Override
	public int getItemCount() {
		Log.d("ProjectAdapter","getItemCount()");
		return activity.getListNoteBuffer().size();
	}

	class ProjectViewHolder extends RecyclerView.ViewHolder {

		private final View view;

		private final TextView textViewIdStudent;
		private final TextView textViewNameStudent;
		private final TextView textViewMyMark;
		private final TextView textViewAvgMark;

		ProjectViewHolder(View view) {
			super(view);
			Log.d("ProjectViewHolder","ProjetViewHolder()");
			this.view = view;

			textViewIdStudent = view.findViewById(R.id.textViewIdStudent);
			textViewNameStudent = view.findViewById(R.id.textViewNameStudent);
			textViewMyMark = view.findViewById(R.id.textViewMyMark);
			textViewAvgMark = view.findViewById(R.id.textViewAvgMark);
		}
	}





}
