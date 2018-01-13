package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.adapters.LineStudentDetailProject;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.GlobalVolleyCallback;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackPOSTR;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.StyleProject;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListUser;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;

public class DetailProjectActivity extends AppCompatActivity {

	private TextView idView;
	private TextView titleView;
	private TextView descView;
	private Button displayPoster;
	private EditText globalMarkeditText;
	private Button buttonRateProject;
	private TextView supeView;
	private ListView studListView;
	private TextView posterView;
	private Button buttonRateProjectValider;
	private ImageView projectImageView;

	ProjectLIPRJ projectLIPRJ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_project);

		Bundle data = getIntent().getExtras();
		assert data != null;
		projectLIPRJ = (ProjectLIPRJ) data.getParcelable("selected_project");

		idView = findViewById(R.id.textViewiD);
		titleView = findViewById(R.id.textViewTitle);
		displayPoster = findViewById(R.id.displayPoster);

		buttonRateProject = findViewById(R.id.buttonRateProject);
		descView = findViewById(R.id.textViewDescriptionValue);
		descView.setMovementMethod(new ScrollingMovementMethod());

		supeView = findViewById(R.id.textViewSupervisorValue);
		globalMarkeditText = findViewById(R.id.globalMarkeditText);
		buttonRateProjectValider = findViewById(R.id.buttonRateProjectValider);
		studListView  = findViewById(R.id.textViewStudentValue);
		posterView = findViewById(R.id.TextViewPoster);
		projectImageView = findViewById(R.id.projectImageView);

		loadElements();

		dealWithLayoutIssues();

		setListeners();
	}


	private void setListeners() {
		listenerGoToRateProject();
		dealWithPosterDisplay();
		setButtonRateProjectValider();
	}


	private void listenerGoToRateProject() {
		buttonRateProject.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Log.d("GoToRate", "Connect");
				goToRatePage(projectLIPRJ);
			}
		});
	}

	private void setButtonRateProjectValider () {
		buttonRateProjectValider.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Log.d("DetailProjectActivity", "setButtonRateProjectValider");
				rateStudents(callbackValidateMark());
			}

		});

	}

	private GlobalVolleyCallback callbackValidateMark() {
		return new GlobalVolleyCallback() {
			@Override
			public void onSuccess() {
				Toast.makeText(DetailProjectActivity.this, "Note des étudiants du project mise à jour", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onError(String errorMessage) {

			}
		};
	}

	private void dealWithPosterDisplay() {
		if(projectLIPRJ.isPoster()) {
			displayPoster.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					Log.d("Welcome", "Connect");
					seePoster(projectLIPRJ);
				}

			});
		} else {
			displayPoster.setText("Poster non disponible");
			ConstraintLayout constraintLayout = findViewById(R.id.containerImage);
			constraintLayout.setVisibility(View.GONE);
		}
	}

	private void dealWithLayoutIssues() {

		ListAdapter myListAdapter = studListView.getAdapter();

		int totalHeight = 0;
		for (int size = 0; size < myListAdapter.getCount(); size++) {
			View listItem = myListAdapter.getView(size, null, studListView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = studListView.getLayoutParams();
		params.height = totalHeight + (studListView.getDividerHeight() * (studListView.getCount() - 1));
		studListView.setLayoutParams(params);
	}



	private void loadElements() {

		String idProject = String.valueOf(projectLIPRJ.getProject().getIdProject());
		idView.setText(idProject);
		titleView.setText(projectLIPRJ.getProject().getTitle());
		descView.setText(projectLIPRJ.getProject().getDescription());


		String supervisor = projectLIPRJ.getSupervisor().getForename() + " " + projectLIPRJ.getSupervisor().getSurname();
		supeView.setText(supervisor);

		String poster = (projectLIPRJ.isPoster()) ? "Poster Disponible" : "Poster non disponible";
		posterView.setText(poster);

		ListUser listStudents = projectLIPRJ.getListStudents();

		HashMap<Integer, String> listStudentsMap = new HashMap<Integer, String>();
		for (User student : listStudents) {
			String fullName = student.getForename() + " " + student.getSurname();
			listStudentsMap.put(student.getIdUser(), fullName);
		}

		LineStudentDetailProject adapter = new LineStudentDetailProject(this, listStudentsMap);
		studListView.setAdapter(adapter);

		loadPoster();
	}

	public void loadPoster() {
		if(projectLIPRJ.isPoster()) {
			posterView.setVisibility(View.GONE);
			setImage(getCallbackPoster());
		} else {
			ConstraintLayout constraintLayout = findViewById(R.id.containerImage);
			constraintLayout.setVisibility(View.GONE);
		}
	}

	public void goToRatePage(ProjectLIPRJ projectLIPRJ) {
		Intent intent = new Intent(DetailProjectActivity.this, MarkStudentsFromProjectActivity.class);
		intent.putExtra("selected_project", projectLIPRJ);
		DetailProjectActivity.this.startActivity(intent);
	}

	public void seePoster(ProjectLIPRJ projectLIPRJ) {
		Intent intent = new Intent(this, DislayPosterActivity.class);
		intent.putExtra("selected_project", projectLIPRJ);
		startActivity(intent);
	}

	private void rateStudents(GlobalVolleyCallback callback) {
		for (User student : projectLIPRJ.getListStudents()) {
			final int _idStudent = student.getIdUser();
			final String _nameStudent = student.getFormatedString();
			int noteValue = Integer.valueOf(globalMarkeditText.getText().toString());
			final GlobalVolleyCallback _callback = callback;
			sendNoteStudentProject(noteValue, _idStudent, projectLIPRJ.getProject().getIdProject(), this, callback);


		}

	}

	private void setImage(final VolleyCallbackPOSTR callbackPOSTR){

		// Get the token from the saved data
		final String _token = WebServiceConnexion.getToken(this);
		final String _login = WebServiceConnexion.getLogin(this);

		// Get the good url with the good variables
		String url = WebServiceConnexion.getPOSTR(_login, _token, projectLIPRJ.getProject().getIdProject(), StyleProject.FULL);
		RequestQueue queue = Volley.newRequestQueue(this, new HurlStack(null, WebServiceConnexion.newSslSocketFactory(this)));


		ImageRequest stringRequest = new ImageRequest(url,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap bitmap) {
						callbackPOSTR.onSuccess(bitmap);
					}
				}, 0, 0, null,
				new Response.ErrorListener() {
					public void onErrorResponse(VolleyError error) {
					}
				});


		queue.add(stringRequest);
	}

	private VolleyCallbackPOSTR getCallbackPoster() {
		return new VolleyCallbackPOSTR() {

			@Override
			public void onSuccess(Bitmap bitmap) {
				try {
					projectImageView .setImageBitmap(bitmap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(String errorMessage) {
			}
		};
	}


	public static void sendNoteStudentProject(int noteValue, int idStudent, int idProject, AppCompatActivity activity, final GlobalVolleyCallback callback) {
		// Get the token from the saved data

		final String _token = WebServiceConnexion.getToken(activity);
		final String _login = WebServiceConnexion.getLogin(activity);

		if (noteValue <= 20) {
			String url = WebServiceConnexion.getNEWNT(_login, _token, idProject, idStudent, noteValue);

			StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					try {
						JSONObject jsonObject = new JSONObject(response);
						String result = jsonObject.getString("result");

						if (result.equals("OK")) {
							callback.onSuccess();
						} else {
							callback.onError(jsonObject.getString("error"));
						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError error) {
				}
			});

			RequestQueue queue = Volley.newRequestQueue(activity, new HurlStack(null, WebServiceConnexion.newSslSocketFactory(activity)));
			queue.add(stringRequest);
		}
	}


}