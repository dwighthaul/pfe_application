package fr.eseo.dis.hubertpa.pfe_application.controller.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.ProjectActivity;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackListProject;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackPOSTR;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.JsonParserAPI;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.StyleProject;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Project;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIPRJ;
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

		String desc = "";
		int limit = 75;
		if(projectLIPRJ.getProject().getDescription().length() > limit) {
			desc = projectLIPRJ.getProject().getDescription().substring(0, limit);
		}

		holder.projectDecription.setText(desc);
		holder.projectConfidentialiy.setText(String.format("%d", projectLIPRJ.getProject().getConfidentiality()));
		holder.projectPoster.setText((projectLIPRJ.isPoster()) ? "Poster disponible" : "Poster non Disponible");



		if (projectLIPRJ.isPoster()) {
			VolleyCallbackPOSTR callback = getCallback();
			setImage(activity, projectLIPRJ, callback);
		}

		holder.projectSuperviseur.setText(String.format("%s %s", projectLIPRJ.getSupervisor().getForename(), projectLIPRJ.getSupervisor().getSurname()));

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
		private final ImageView projectPosterView;
		private final TextView projectSuperviseur;
		private final TextView projectConfidentialiy;

		public ProjectViewHolder(View view) {
			super(view);
			Log.d("ProjectViewHolder","ProjetViewHolder()");
			this.view = view;

			projectId = (TextView) view.findViewById(R.id.idtextView);
			projectTitre = (TextView) view.findViewById(R.id.titletextView);
			projectDecription = (TextView) view.findViewById(R.id.descriptiontextView);
			projectPoster = (TextView) view.findViewById(R.id.postertextView);
			projectPosterView = (ImageView) view.findViewById(R.id.posterViewProject);
			projectSuperviseur = (TextView) view.findViewById(R.id.supervisortextViewValue);
			projectConfidentialiy = (TextView) view.findViewById(R.id.confidentialitytextViewValue);

		}
	}

	private void setImage(AppCompatActivity activity, ProjectLIPRJ projectLIPRJ, VolleyCallbackPOSTR callbackPOSTR){

		// Get the token from the saved data
		final String _token = WebServiceConnexion.getToken(activity);
		final String _login = WebServiceConnexion.getLogin(activity);
		final VolleyCallbackPOSTR _callbackPOSTR = callbackPOSTR;
		AppCompatActivity _activity = activity;

		// Get the good url with the good variables
		String url = WebServiceConnexion.getPOSTR(_login, _token, projectLIPRJ.getProject().getIdProject(), StyleProject.FULL);
		RequestQueue queue = Volley.newRequestQueue(activity, new HurlStack(null, WebServiceConnexion.newSslSocketFactory(_activity)));
//
//		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<Bitmap>() {
//
//			@Override
//			public void onResponse(Bitmap response) {
//
//			}
//
//		}, new Response.ErrorListener() {
//
//			@Override
//			public void onErrorResponse(VolleyError error) {
//			}
//		});

//		queue.add(stringRequest);
	}

	public VolleyCallbackPOSTR getCallback() {


		VolleyCallbackPOSTR callback =  new VolleyCallbackPOSTR() {

			@Override
			public void onSuccess(ProjectLIPRJ project, Bitmap bitmap) {
				String urlString = project.getPosterPath();
				Log.d("POSTERPATH", project.getPosterPath());
				URL url;
				try {
					url = new URL(urlString);
					Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//					holder.projectPosterView.setImageBitmap(bmp);
				} catch (Exception e) {
					e.printStackTrace();
				}


			}

			@Override
			public void onError(String errorMessage) {
			}
		};

		return callback;
	}




}
