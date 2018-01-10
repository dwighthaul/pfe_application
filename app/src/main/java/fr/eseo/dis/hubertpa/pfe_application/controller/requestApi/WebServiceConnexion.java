package fr.eseo.dis.hubertpa.pfe_application.controller.requestApi;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.ProjectActivity;
import fr.eseo.dis.hubertpa.pfe_application.model.BasicSettings;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LOGON;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import static fr.eseo.dis.hubertpa.pfe_application.model.BasicSettings.saveFilenameShared;
import static fr.eseo.dis.hubertpa.pfe_application.model.BasicSettings.sharedLogin;
import static fr.eseo.dis.hubertpa.pfe_application.model.BasicSettings.sharedLoginDefault;
import static fr.eseo.dis.hubertpa.pfe_application.model.BasicSettings.sharedToken;
import static fr.eseo.dis.hubertpa.pfe_application.model.BasicSettings.sharedTokenDefault;

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


	public static String getLOGON(String login, String password) {
		return LOGON + "&user=" + login + "&pass=" + password;
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




	public static void getConnected(final String login, final String password, AppCompatActivity activity, final VolleyCallbackLOGON callback) {

		final AppCompatActivity _activity = activity;
		final String _login = login;
		final String _password = password;

		// Get the URL to log into the server
		String url = WebServiceConnexion.getLOGON(login, password);

		Log.d("WebServiceConnexion", url);

		RequestQueue queue = Volley.newRequestQueue(activity, new HurlStack(null, WebServiceConnexion.newSslSocketFactory(activity)));

		StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {

					JSONObject jsonObject = new JSONObject(response);
					String result = jsonObject.getString("result");

					if (result.equals("OK")) {
						LOGON logon = JsonParserAPI.parseLOGON(jsonObject);
						String tokenValue =logon.getToken();

						saveDataValue(_activity, _login, _password, tokenValue);

						makeToster(_activity, "Connected");

						callback.onSuccess();

					} else {
						makeToster(_activity, "Error to connect");
						callback.onError();

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


	public static void makeToster(AppCompatActivity activity, String message) {
		Context context = activity.getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}


	private static void saveDataValue (AppCompatActivity activity, String login, String password, String tokenValue) {
		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
		SharedPreferences settings = activity.getSharedPreferences(BasicSettings.saveFilenameShared, activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(BasicSettings.sharedLogin, login);
		editor.putString(BasicSettings.sharedPassword, password);
		editor.putString(BasicSettings.sharedToken, tokenValue);

		editor.apply();
	}

	public static void resetDataValue (AppCompatActivity activity) {
		SharedPreferences settings = activity.getSharedPreferences(BasicSettings.saveFilenameShared, activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		// Reset the values as the default ones.
		editor.putString(BasicSettings.sharedLogin, BasicSettings.sharedLoginDefault);
		editor.putString(BasicSettings.sharedPassword, BasicSettings.sharedPasswordDefault);
		editor.putString(BasicSettings.sharedToken, BasicSettings.sharedTokenDefault);

		editor.apply();
	}

	public static String getToken(AppCompatActivity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(BasicSettings.saveFilenameShared, activity.MODE_PRIVATE);

		// Récuperation du token
		String tokenValue = prefs.getString(sharedToken, sharedTokenDefault);

		return tokenValue;
	}


	public static String getLogin(AppCompatActivity activity) {
		SharedPreferences prefs = activity.getSharedPreferences(BasicSettings.saveFilenameShared, activity.MODE_PRIVATE);

		// Récuperation de l'identifiant
		String loginValue = prefs.getString(sharedLogin, sharedLogin);

		return loginValue;
	}



	public static SSLSocketFactory newSslSocketFactory(AppCompatActivity activity) {
		try {
			CertificateFactory cf = CertificateFactory.getInstance("x.509");
			InputStream caInput = new BufferedInputStream(activity.getResources().openRawResource(R.raw.root));
			//this.getResources().getIdentifier("chain","raw",this.getPackageName())));

			Certificate ca;
			try{

				ca = cf.generateCertificate(caInput);
				Log.d("TESTTEST","ca="+((X509Certificate)ca).getSubjectDN());
			}finally{
				caInput.close();
			}
			// Get an instance of the Bouncy Castle KeyStore format
			String keyStoreType = KeyStore.getDefaultType();
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			// Get the raw resource, which contains the keystore with
			// your trusted certificates (root and any intermediate certs)
			InputStream in = activity.getApplicationContext().getResources().openRawResource(R.raw.root); //.bks file in raw folder
			keyStore.load(null,null);
			keyStore.setCertificateEntry("ca",ca);

			String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
			tmf.init(keyStore);

			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, tmf.getTrustManagers(), null);

			SSLSocketFactory sf = context.getSocketFactory();
			return sf;

		} catch (Exception e) {
			Log.d("SSL Exception",e.getMessage());
			throw new AssertionError(e);
		}
	}


}
