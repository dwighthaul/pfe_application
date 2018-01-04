package fr.eseo.dis.hubertpa.pfe_application.controller.requestApi;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LOGON;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by paulhubert on 20/12/17.
 */

public abstract class WebServiceConnexion  {

	/**
	 * Portes ouvertes values
	 */
	public static final String DEFAULT_SURNAME = "Journee";
	public static final String DEFAULT_LASTNAME = "Portes Ouvertes";
	public static final String DEFAULT_LOGIN = "jpo";
	public static final String DEFAULT_PASSWORD = "w872o32HkYAO";

	private static final String URL_PERSO_PAUL = "http://192.168.1.12/api_rest_project/android_project/?q=";
	private static final String URL_PERSO_MORGAN = "http://192.168.1.34/api_rest_project/android_project/?q=";

	private static final String URL_ESEO = "https://192.168.4.10/www/pfe/webservice.php?q=";

	public static final String URL = URL_PERSO_PAUL;

	//Initial validation of users credentials
	private static final String LOGON = URL + "LOGON";

	//List information on all projects
	private static final String LIPRJ = URL + "LIPRJ";

	//List information on projects where the user is the supervisor
	private static final String MYPRJ = URL + "MYPRJ";

	//List information on all the juries
	private static final String LIJUR = URL + "LIJUR";

	//List information on the juries where the user is a member of the jury
	private static final String MYJUR = URL + "MYJUR";

	//Provide detailed information on projects in a given jury
	private static final String JYINF = URL + "JYINF";

	//PRecuperate full information for a given project
	private static final String PROJE = URL + "PROJE";

	//Recuperate a poster for a given project
	private static final String POSTR = URL + "POSTR";

	//List the ’notes’ of all the team members for a given project
	private static final String NOTES = URL + "NOTES";

	//Add or update a note for a given student
	private static final String NEWNT = URL + "NEWNT";

	//Recuperate a range of non condential projects and posters for a demonstration “portes ouvertes”?
	private static final String PORTE = URL + "PORTE";


	public static String getLOGON(String username, String password) {
		return LOGON + "&user=" + username + "&pass=" + password;
	}

	public static String getLIPRJ(String username, String token) {
		return LIPRJ + addUsernameAndToken(username, token);
	}

	public static String getMYPRJ(String username, String token) {
		return MYPRJ + addUsernameAndToken(username, token);
	}

	public static String getLIJUR(String username, String token) {
		return LIJUR + addUsernameAndToken(username, token);
	}

	public static String getMYJUR(String username, String token) {
		return MYJUR + addUsernameAndToken(username, token);
	}

	public static String getJYINF(String username, String token, int idJury) {
		return JYINF + "&jury=" + idJury + addUsernameAndToken(username, token);
	}

	// TODO : See the parameters to give;
	public static String getPROJE(String username, String token) {
		return PROJE + addUsernameAndToken(username, token);
	}

	public static String getPOSTR(String username, String token, int idProject, StyleProject style) {
		return POSTR + "&proj="  + idProject + "&style=" + style + addUsernameAndToken(username, token);
	}

	public static String getNOTES(String username, String token, int idProject) {
		return NOTES + "&proj="  + idProject + addUsernameAndToken(username, token);
	}

	//&user=<username>&proj=<project id>&student=<student id>&note=<note>token=<token data>
	public static String getNEWNT(String username, String token, int idProject, int idStudent, int noteValue) {
		return NEWNT + "&proj=" + idProject + "&student=" + idStudent + "&note=" + noteValue + addUsernameAndToken(username, token);
	}
	//&user=<username>&seed=<seed>token=<token data>
	public static String getPORTE(String username, String token, String seed) {
		return PORTE + "&seed=" + seed + addUsernameAndToken(username, token);
	}

	private static String addUsernameAndToken(String username, String token) {
		return "&user=" + username + "&token=" + token;
	}




	public static void getConnected(String userName, String password, AppCompatActivity activity, final VolleyCallbackLOGON callback) {

		final AppCompatActivity _activity = activity;
		String url = WebServiceConnexion.getLOGON(userName, password);

		RequestQueue queue = Volley.newRequestQueue(activity);
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {


					JSONObject jsonObject = new JSONObject(response);
					String result = jsonObject.getString("result");
					Log.d("WebServiceConnexion", result);

					if (result.equals("OK")) {
						callback.onSuccess(true);

						LOGON logon = JsonParserAPI.parseLOGON(jsonObject);
						String tokenValue =logon.getToken();
						makeToster(_activity, "Connected");
					} else {
						callback.onSuccess(false);

						makeToster(_activity, "Error to connect");
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				makeToster(_activity, "Error to connected");
			}
		});

		queue.add(stringRequest);

	}


	private static void makeToster(AppCompatActivity activity, String message) {
		Context context = activity.getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}



}
