package fr.eseo.dis.hubertpa.pfe_application.controller.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.JuryActivity;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListUser;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Project;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIJUR;

/**
 * Created by Morgan on 05/01/2018.
 */

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

    @Override
    public void onBindViewHolder(JuryViewHolder holder, final int position) {
        Log.d("JuryViewHolder","onBindViewHolder()");

        final JuryLIJUR juryLIJUR = activity.getJuryListBuffer().get(position);
	    Log.d("ICI MEME", "List juries : " + activity.getJuryListBuffer());
        holder.juryId.setText("" + juryLIJUR.getJury().getIdJury());
        holder.juryDate.setText(juryLIJUR.getJury().getDate());

        List<ProjectLIJUR> listProjects = juryLIJUR.getListProject();
        Log.d("TEST", "" + listProjects.size());

        HashMap<Integer, String> listProjectsMap = new HashMap<Integer, String>();
        for (ProjectLIJUR project : listProjects) {
            String fullName = project.getProject().getTitle();
            listProjectsMap.put(project.getProject().getIdProject(), fullName);
        }

        AdaptorListProjectJury adapter = new AdaptorListProjectJury(this, listProjectsMap);
        holder.listProjectJury.setAdapter(adapter);

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
        Log.d("JuryAdapter","getItemCount()");
        return activity.getJuryListBuffer().size();
    }

    class JuryViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private final TextView juryId;
        private final TextView juryDate;
        private final ListView listProjectJury;

        public JuryViewHolder(View viewi) {
            super(viewi);
            Log.d("JuryViewHolder","JuryViewHolder()");
            this.view = viewi;

            juryId = (TextView) view.findViewById(R.id.idtextView);
            juryDate = (TextView) view.findViewById(R.id.datetextView);
            listProjectJury = (ListView) view.findViewById(R.id.list_projects_by_jury);

        }
    }

}
