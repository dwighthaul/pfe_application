package fr.eseo.dis.hubertpa.pfe_application.controller.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.JuryActivity;
import fr.eseo.dis.hubertpa.pfe_application.activities.MarkStudentsFromProjectActivity;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListNote;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListProject;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListUser;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Project;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.NotesNOTES;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIJUR;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Morgan on 05/01/2018.
 */

public class RateStudentsAdapter extends RecyclerView.Adapter<RateStudentsAdapter.RateStudentsViewHolder> {

	private MarkStudentsFromProjectActivity activity;

	@Getter @Setter
	private ListNote listNotes;

	public RateStudentsAdapter(MarkStudentsFromProjectActivity juryActivity, ListNote listNotes) {
		this.activity = juryActivity;
		this.listNotes = listNotes;
	}

	@Override
	public RateStudentsAdapter .RateStudentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View juryView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.line_rate_student, parent, false);
		return new RateStudentsViewHolder(juryView);
	}

	public void onBindViewHolder(RateStudentsViewHolder holder, final int position) {
		Log.d("JuryViewHolder","onBindViewHolder()");

		final NotesNOTES notesNOTES = listNotes.get(position);
		holder.studentIDTextView.setText(String.format("%d", notesNOTES.getStudent().getIdUser()));

		String studentName = notesNOTES.getStudent().getForename() + " " + notesNOTES.getStudent().getSurname();
		holder.studentNameTextView.setText(studentName);

		if(notesNOTES.isNoteSet()) {
			holder.currentMarkTextView.setText(String.format("%d", notesNOTES.getMynote()));
		} else {
			holder.currentMarkTextView.setText("Non noté");
		}



		holder.rateStudentImageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("TEST", "" + notesNOTES.getAvgnote());

				activity.goToRateStudentPage(notesNOTES);
			}
		});

	}


			@Override
			public int getItemCount() {
				return this.getListNotes().size();
			}

			class RateStudentsViewHolder extends RecyclerView.ViewHolder {

				private final View view;

				private final TextView studentIDTextView;
				//		private final TextView markIsSetTextView;
				private final TextView studentNameTextView;
				private final TextView currentMarkTextView;
				private final ImageButton rateStudentImageButton;

				public RateStudentsViewHolder(View view) {
					super(view);
					Log.d("JuryViewHolder", "JuryViewHolder()");
					this.view = view;

					studentIDTextView = view.findViewById(R.id.studentIDTextView);
					studentNameTextView = view.findViewById(R.id.studentNameTextView);

					currentMarkTextView = view.findViewById(R.id.currentMarkTextView);
					rateStudentImageButton = view.findViewById(R.id.rateStudentImageButton);
				}
			}
}
