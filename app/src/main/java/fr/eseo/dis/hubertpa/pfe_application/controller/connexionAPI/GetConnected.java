package fr.eseo.dis.hubertpa.pfe_application.controller.connexionAPI;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;

/**
 * Created by paulhubert on 30/12/17.
 */

public class GetConnected extends AsyncTask<AppCompatActivity, String, User> {

	private static final String URL = "https://api.github.com/";

	private URL url;
	private AppCompatActivity[] _activity;

	public GetConnected() throws MalformedURLException {
		String complemment = "";
		url = new URL(URL + complemment);

	}

	@Override
	protected User doInBackground(AppCompatActivity... activity) {
		this._activity = activity;
		User user = new User();
		HttpURLConnection conn;
		try {
			System.out.println(url);
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				Log.d("WebService", "Bad connexion");
				//				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String result = "";
			String output;
			while ((output = br.readLine()) != null) {
				result = output;
			}

			JSONObject jObject = new JSONObject(result);
			user = JsonParserAPI.parseUser(jObject);

			conn.disconnect();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}

}
