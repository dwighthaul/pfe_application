package fr.eseo.dis.hubertpa.pfe_application.controller.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.jpo_activities.JPOSettingActivity;

public class UserJPOAdapter{

    private JPOSettingActivity activity;

    public UserJPOAdapter(JPOSettingActivity settingActivity) {
        this.activity = settingActivity;
    }

    public void onBindViewHolder(UserJPOViewHolder holder, final int position) {
        Log.d("ProjectViewHolder","onBindViewHolder()");

    }

    class UserJPOViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        private final TextView userId;
        private final TextView projectTitre;
        private final TextView projectDecription;

        UserJPOViewHolder(View view) {
            super(view);
            Log.d("ProjectViewHolder","ProjetViewHolder()");
            this.view = view;

            userId = view.findViewById(R.id.idtextView);
            projectTitre = view.findViewById(R.id.surname1textViewLabel);
            projectDecription = view.findViewById(R.id.descriptiontextView);
        }
    }

}
