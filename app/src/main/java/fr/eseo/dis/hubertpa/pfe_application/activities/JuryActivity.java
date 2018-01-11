package fr.eseo.dis.hubertpa.pfe_application.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.controller.adapters.JuryAdapter;
import fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley.VolleyCallbackListJury;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.JsonParserAPI;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.partials.BottomNavigationViewHelper;
import fr.eseo.dis.hubertpa.pfe_application.partials.NavigationBottom;
import lombok.Getter;
import lombok.Setter;

public class JuryActivity  extends AppCompatActivity {

	// The object contains a list of all the juries
	@Getter @Setter
	LIJUR lijur;

	// The following list is use to be a buffer, the list to be display.
	// This list can be modified if the user filter the result, ask to only display the favorits one
	@Getter @Setter
	List<JuryLIJUR> juryListBuffer;

	public static int NEW_CARD_COUNTER;

	boolean stoppingBuffering = false;

	private JuryAdapter juryAdapter;

	private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationBottom(this, NavigationBottom.PAGES.JURIES);

	VolleyCallbackListJury callback;

	RecyclerView recycler;

	SwipeRefreshLayout mySwipeRefreshLayout;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_juries);

		mySwipeRefreshLayout = findViewById(R.id.swip_to_refresh);

		BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
		BottomNavigationViewHelper.disableShiftMode(navigation);
		navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

		navigation.getMenu().getItem(1).setChecked(true);

		lijur = new LIJUR();

		juryListBuffer = new ArrayList<JuryLIJUR>();

		NEW_CARD_COUNTER = 0;

		setRecycler();

		setCallback();

		setActionOnRefrech();

		processGetJuries();
	}


	private void processGetJuriesFake() {
		String jsonObjectString = "{\"result\":\"OK\",\"api\":\"LIJUR\",\"juries\":[{\"idJury\":0,\"date\":\"2017-01-13\",\"info\":{\"members\":[{\"forename\":\"Olivier\",\"surname\":\"CAMP\"},{\"forename\":\"Fabien\",\"surname\":\"CHHEL\"}],\"projects\":[{\"projectId\":1,\"title\":\"Reconnaissancelanguedessignes\",\"confid\":0,\"poster\":true,\"supervisor\":{\"forename\":\"Patrick\",\"surname\":\"ALBERS\"}},{\"projectId\":3,\"title\":\"OPAL-Sécudesréseauxetdev.outilswin\",\"confid\":0,\"poster\":true,\"supervisor\":{\"forename\":\"Olivier\",\"surname\":\"BEAUDOUX\"}},{\"projectId\":5,\"title\":\"ServiceProBooking\",\"confid\":0,\"poster\":false,\"supervisor\":{\"forename\":\"Matthias\",\"surname\":\"BRUN\"}},{\"projectId\":27,\"title\":\"IndustrialisationLogicielle\",\"confid\":2,\"poster\":true,\"supervisor\":{\"forename\":\"Richard\",\"surname\":\"WOODWARD\"}}]}},{\"idJury\":1,\"date\":\"2017-01-13\",\"info\":{\"members\":[{\"forename\":\"Sebastien\",\"surname\":\"AUBIN\"},{\"forename\":\"Mickael\",\"surname\":\"CLAVREUL\"}],\"projects\":[{\"projectId\":8,\"title\":\"AWS\",\"confid\":0,\"poster\":false,\"supervisor\":{\"forename\":\"Jerome\",\"surname\":\"CHAVIN\"}},{\"projectId\":21,\"title\":\"DemonstratorforIoTinindustry\",\"confid\":0,\"poster\":true,\"supervisor\":{\"forename\":\"Jonathon\",\"surname\":\"ILIAS-PILLET\"}},{\"projectId\":25,\"title\":\"BtoBrowse-BusinessAnalyticsWebApp\",\"confid\":0,\"poster\":false,\"supervisor\":{\"forename\":\"Daniel\",\"surname\":\"SCHANG\"}},{\"projectId\":26,\"title\":\"BtoBrowse-MachineLearning\",\"confid\":0,\"poster\":true,\"supervisor\":{\"forename\":\"Daniel\",\"surname\":\"SCHANG\"}}]}},{\"idJury\":2,\"date\":\"2017-01-13\",\"info\":{\"members\":[{\"forename\":\"Jerome\",\"surname\":\"DELATOUR\"},{\"forename\":\"Daniel\",\"surname\":\"SCHANG\"}],\"projects\":[{\"projectId\":11,\"title\":\"Jeumultijoueuretmultiplatforme\",\"confid\":0,\"poster\":true,\"supervisor\":{\"forename\":\"Mickael\",\"surname\":\"CLAVREUL\"}},{\"projectId\":14,\"title\":\"Bancdetestdomotique\",\"confid\":0,\"poster\":false,\"supervisor\":{\"forename\":\"Jerome\",\"surname\":\"DELATOUR\"}},{\"projectId\":15,\"title\":\"Devd'unagentI/O\",\"confid\":0,\"poster\":false,\"supervisor\":{\"forename\":\"Jerome\",\"surname\":\"DELATOUR\"}},{\"projectId\":16,\"title\":\"ProjetRecifalX\",\"confid\":0,\"poster\":false,\"supervisor\":{\"forename\":\"Jerome\",\"surname\":\"DELATOUR\"}}]}},{\"idJury\":3,\"date\":\"2017-01-19\",\"info\":{\"members\":[{\"forename\":\"Nicolas\",\"surname\":\"GUTOWSKI\"},{\"forename\":\"Jonathon\",\"surname\":\"ILIAS-PILLET\"}],\"projects\":[{\"projectId\":4,\"title\":\"Gyropode\",\"confid\":0,\"poster\":true,\"supervisor\":{\"forename\":\"Matthias\",\"surname\":\"BRUN\"}},{\"projectId\":7,\"title\":\"Sécuritéparlapratique\",\"confid\":0,\"poster\":true,\"supervisor\":{\"forename\":\"Jerome\",\"surname\":\"CHAVIN\"}},{\"projectId\":10,\"title\":\"Plateformedefictioninteractive\",\"confid\":1,\"poster\":true,\"supervisor\":{\"forename\":\"Mickael\",\"surname\":\"CLAVREUL\"}},{\"projectId\":18,\"title\":\"UrbanBoard\",\"confid\":0,\"poster\":false,\"supervisor\":{\"forename\":\"Nicolas\",\"surname\":\"GUTOWSKI\"}}]}},{\"idJury\":4,\"date\":\"2017-01-20\",\"info\":{\"members\":[{\"forename\":\"Olivier\",\"surname\":\"BEAUDOUX\"},{\"forename\":\"Richard\",\"surname\":\"WOODWARD\"}],\"projects\":[{\"projectId\":0,\"title\":\"Gestionmain-libretabletteandroid\",\"confid\":0,\"poster\":true,\"supervisor\":{\"forename\":\"Patrick\",\"surname\":\"ALBERS\"}},{\"projectId\":6,\"title\":\"PlatformeTwitter\",\"confid\":0,\"poster\":true,\"supervisor\":{\"forename\":\"Olivier\",\"surname\":\"CAMP\"}},{\"projectId\":9,\"title\":\"ChatBot\",\"confid\":0,\"poster\":true,\"supervisor\":{\"forename\":\"Fabien\",\"surname\":\"CHHEL\"}},{\"projectId\":17,\"title\":\"AnalyseetVisualisationmobilitéurbaine\",\"confid\":0,\"poster\":true,\"supervisor\":{\"forename\":\"Nicolas\",\"surname\":\"GUTOWSKI\"}},{\"projectId\":24,\"title\":\"AppyFiz\",\"confid\":0,\"poster\":true,\"supervisor\":{\"forename\":\"Sophie\",\"surname\":\"ROUSSEAU\"}}]}},{\"idJury\":5,\"date\":\"2017-01-20\",\"info\":{\"members\":[{\"forename\":\"Jerome\",\"surname\":\"CHAVIN\"},{\"forename\":\"Samuel\",\"surname\":\"POIRAUD\"},{\"forename\":\"Sophie\",\"surname\":\"ROUSSEAU\"}],\"projects\":[{\"projectId\":2,\"title\":\"Greenygrass\",\"confid\":0,\"poster\":true,\"supervisor\":{\"forename\":\"Sebastien\",\"surname\":\"AUBIN\"}},{\"projectId\":20,\"title\":\"Fiabilisationetportagebootloader\",\"confid\":0,\"poster\":true,\"supervisor\":{\"forename\":\"Jonathon\",\"surname\":\"ILIAS-PILLET\"}},{\"projectId\":22,\"title\":\"BriqueslogiciellessurNucleoF103\",\"confid\":0,\"poster\":true,\"supervisor\":{\"forename\":\"Samuel\",\"surname\":\"POIRAUD\"}},{\"projectId\":23,\"title\":\"LunettesàRéalitéAugmentée\",\"confid\":0,\"poster\":false,\"supervisor\":{\"forename\":\"Sophie\",\"surname\":\"ROUSSEAU\"}}]}},{\"idJury\":6,\"date\":\"2017-02-02\",\"info\":{\"members\":[],\"projects\":[]}},{\"idJury\":7,\"date\":\"2017-02-03\",\"info\":{\"members\":[],\"projects\":[]}},{\"idJury\":8,\"date\":\"2017-02-02\",\"info\":{\"members\":[],\"projects\":[]}},{\"idJury\":9,\"date\":\"2017-02-03\",\"info\":{\"members\":[],\"projects\":[]}},{\"idJury\":10,\"date\":\"2017-02-02\",\"info\":{\"members\":[],\"projects\":[]}},{\"idJury\":11,\"date\":\"2017-02-02\",\"info\":{\"members\":[],\"projects\":[]}},{\"idJury\":12,\"date\":\"2017-01-20\",\"info\":{\"members\":[{\"forename\":\"Matthias\",\"surname\":\"BRUN\"},{\"forename\":\"Mickael\",\"surname\":\"CLAVREUL\"}],\"projects\":[{\"projectId\":12,\"title\":\"HMIMapperMachineLearningA\",\"confid\":2,\"poster\":false,\"supervisor\":{\"forename\":\"Camille\",\"surname\":\"CONSTANT\"}},{\"projectId\":13,\"title\":\"HMIMapperMachineLearningB\",\"confid\":2,\"poster\":false,\"supervisor\":{\"forename\":\"Camille\",\"surname\":\"CONSTANT\"}}]}}]}";

		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonObjectString);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		LIJUR lijur = JsonParserAPI.parseLIJUR(jsonObject);

		callback.onSuccess(lijur);
	}

	/**
	 * Set the recycler. Basic setter
	 */
	private void setRecycler() {
		recycler = findViewById(R.id.card_list_juries);
		recycler.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recycler.setLayoutManager(llm);

	}

	/**
	 * The class and the two functions called when the Volley get a response
	 */
	public void setCallback() {
		this.callback =  new VolleyCallbackListJury() {
			@Override
			public void onSuccess(LIJUR lijur) {
				//Log.d("JuryActivity", String.valueOf(lijur.getListJuries().size()));

				// Set the list of juries
				JuryActivity.this.setJuryListBuffer(lijur.getListJuries());
				JuryActivity.this.setLijur(lijur);

				for(int i=0; i < lijur.getListJuries().size(); i++){
					juryAdapter = new JuryAdapter(JuryActivity.this);

					recycler.setAdapter(juryAdapter);
				}

			}

			@Override
			public void onError(String errorMessage) {
				Toast.makeText(JuryActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

			}
		};
	}

	private void processGetJuries() {
		mySwipeRefreshLayout.setRefreshing(true);

		// Get the token from the saved data
		String token = WebServiceConnexion.getToken(this);
		String login = WebServiceConnexion.getLogin(this);

		stoppingBuffering = true;
		stopBuffering();

		// Get the good url with the good variables
		String url = WebServiceConnexion.getLIJUR(login, token);

		RequestQueue queue = Volley.newRequestQueue(this, new HurlStack(null, WebServiceConnexion.newSslSocketFactory(this)));
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				stoppingBuffering = false;
				try {

					JSONObject jsonObject = new JSONObject(response);
					String result = jsonObject.getString("result");

					if (result.equals("OK")) {

						LIJUR lijur = JsonParserAPI.parseLIJUR(jsonObject);

						callback.onSuccess(lijur);
					} else {
						String error = jsonObject.getString("error");
						callback.onError(jsonObject.getString(error));
					}
					mySwipeRefreshLayout.setRefreshing(false);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			}
		});
		queue.add(stringRequest);
	}

	// After 10 sec, the activity hind the annoying spining stuff and display an error
	public void stopBuffering() {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (stoppingBuffering) {
					Toast.makeText(JuryActivity.this, "Connexion error", Toast.LENGTH_SHORT).show();
					mySwipeRefreshLayout.setRefreshing(false);
				}
			}
		}, 10000);

	}

	/**
	 * If the user swipe down, this listener is trigger.
	 * The action is to ask to recive the list of the projects
	 */
	private void setActionOnRefrech() {
		mySwipeRefreshLayout.setOnRefreshListener(
				new SwipeRefreshLayout.OnRefreshListener() {
					@Override
					public void onRefresh() {
						processGetJuries();
					}
				}
		);
	}


	public void clickItem(JuryLIJUR juryLIJUR) {
		Intent intent = new Intent(this, DetailJuryActivity.class);
		intent.putExtra("selected_jury", juryLIJUR);
		startActivity(intent);
	}


}