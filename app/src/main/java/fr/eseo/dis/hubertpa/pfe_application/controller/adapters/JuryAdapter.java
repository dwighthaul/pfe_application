package fr.eseo.dis.hubertpa.pfe_application.controller.adapters;

import android.app.Activity;
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
import fr.eseo.dis.hubertpa.pfe_application.activities.JuryActivity;
import fr.eseo.dis.hubertpa.pfe_application.activities.JuryActivity;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;

/**
 * Created by Morgan on 05/01/2018.
 */

public class JuryAdapter extends RecyclerView.Adapter<JuryAdapter.JuryViewHolder> {

    private JuryActivity activity;
    private List<Integer> positionsExpanded;

    public JuryAdapter(JuryActivity juryActivity) {
        this.activity = juryActivity;
        positionsExpanded = new ArrayList<Integer>();
    }

    @Override
    public JuryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View juryView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jury_cards, parent, false);
        Log.d("FilmographyAdaper","onCreateViewHolder()");
        return new JuryViewHolder(juryView);
    }

    @Override
    public void onBindViewHolder(JuryViewHolder holder, final int position) {
        Log.d("JuryViewHolder","onBindViewHolder()");

        final JuryLIJUR juryLIJUR = activity.getJuryListBuffer().get(position);
        holder.juryId.setText(String.format("%d", juryLIJUR.getJury().getIdJury()));
        holder.juryDate.setText(String.format("%d", juryLIJUR.getJury().getDate()));

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

        public JuryViewHolder(View viewi) {
            super(viewi);
            Log.d("JuryViewHolder","JuryViewHolder()");
            this.view = viewi;

            juryId = (TextView) view.findViewById(R.id.idtextView);
            juryDate = (TextView) view.findViewById(R.id.datetextView);

        }
    }

}
