package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackPOSTR;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.StyleProject;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;

public class DislayPosterActivity extends AppCompatActivity {

	ImageView posterImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_poster);

		posterImageView = findViewById(R.id.posterImageView);

		Bundle data = getIntent().getExtras();
		assert data != null;
		ProjectLIPRJ projectLIPRJ = data.getParcelable("selected_project");

		VolleyCallbackPOSTR callback = getCallback();
		setImage(this, projectLIPRJ, callback);


	}


	private void setImage(AppCompatActivity activity, ProjectLIPRJ projectLIPRJ, final VolleyCallbackPOSTR callbackPOSTR){

		// Get the token from the saved data
		final String _token = WebServiceConnexion.getToken(activity);
		final String _login = WebServiceConnexion.getLogin(activity);

		// Get the good url with the good variables
		String url = WebServiceConnexion.getPOSTR(_login, _token, projectLIPRJ.getProject().getIdProject(), StyleProject.FULL);
		RequestQueue queue = Volley.newRequestQueue(activity, new HurlStack(null, WebServiceConnexion.newSslSocketFactory(activity)));


		ImageRequest stringRequest = new ImageRequest(url,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap bitmap) {
						callbackPOSTR.onSuccess(bitmap);
					}
				}, 0, 0, null,
				new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
						posterImageView.setImageResource(R.drawable.eseo_groupe);
					}
				});


		queue.add(stringRequest);
	}

	public VolleyCallbackPOSTR getCallback() {


		return new VolleyCallbackPOSTR() {

			@Override
			public void onSuccess(Bitmap bitmap) {
				try {
					posterImageView.setImageBitmap(bitmap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(String errorMessage) {
				posterImageView.setImageResource(R.drawable.eseo_groupe);
			}
		};
	}

}
