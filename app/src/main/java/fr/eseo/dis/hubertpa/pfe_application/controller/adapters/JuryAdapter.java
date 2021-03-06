package fr.eseo.dis.hubertpa.pfe_application.controller.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.JuryActivity;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListProject;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListUser;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIJUR;

public class JuryAdapter extends RecyclerView.Adapter<JuryAdapter.JuryViewHolder> {

    private JuryActivity activity;

    public JuryAdapter(JuryActivity juryActivity) {
        this.activity = juryActivity;
    }

    @Override
    public JuryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View juryView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jury_cards, parent, false);
        return new JuryViewHolder(juryView);
    }

    public void onBindViewHolder(JuryViewHolder holder, final int position) {
        Log.d("JuryViewHolder","onBindViewHolder()");

        final JuryLIJUR juryLIJUR = activity.getJuryListBuffer().get(position);
        holder.juryId.setText(String.format("%d", juryLIJUR.getJury().getIdJury()));
        holder.juryDate.setText(juryLIJUR.getJury().getDate());

        ListProject listProjects = juryLIJUR.getListProject();
        StringBuilder stringBuilderProjects = new StringBuilder();

	    for (ProjectLIJUR project : listProjects) {
		    stringBuilderProjects.append(String.valueOf(project.getProject().getIdProject() + " - "));
		    stringBuilderProjects.append(project.getProject().getTitle());
		    stringBuilderProjects.append("\n");
	    }
	    holder.listProjectTextView.setText(stringBuilderProjects.toString());

        ListUser listMembers = juryLIJUR.getListMembers();
	    StringBuilder stringBuilderMemeber = new StringBuilder();

	    for (User member : listMembers) {
		    stringBuilderMemeber.append(member.getForename() + " " + member.getSurname());
		    stringBuilderMemeber.append("\n");
	    }

	    holder.listMemberTextView.setText(stringBuilderMemeber.toString());

	    holder.view.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    Log.d("ProjectAdapter","Item 'clicked'");
			    activity.clickItem(juryLIJUR);
		    }
	    });




//	    AdaptorListProjectJury adapter = new AdaptorListProjectJury(activity, listProjectsMap);
//        Log.d("TEST" , "" + adapter.getCount());
//	    holder.listProjectJuryListView.setAdapter(adapter);


        /*holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("JuryAdapter","Item 'clicked'");
                activity.clickItem(juryLIJUR);
            }
        });*/



    }


    @Override
    public int getItemCount() {
        return activity.getJuryListBuffer().size();
    }

    class JuryViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private final TextView juryId;
        private final TextView juryDate;
		private final TextView listProjectTextView;
	    private final TextView listMemberTextView;

        JuryViewHolder(View view) {
            super(view);
            Log.d("JuryViewHolder","JuryViewHolder()");
            this.view = view;

            juryId = view.findViewById(R.id.idtextView);
            juryDate = view.findViewById(R.id.datetextView);

	        listProjectTextView = view.findViewById(R.id.listProjectTextView);
	        listMemberTextView = view.findViewById(R.id.listMemberTextView);
        }
    }

}
