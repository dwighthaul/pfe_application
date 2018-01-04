package fr.eseo.dis.hubertpa.pfe_application.controller;

/**
 * Created by paulhubert on 03/01/18.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import fr.eseo.dis.hubertpa.pfe_application.activities.ProjectActivity;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.VolleyCallbackLOGON;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;

public class UserLoginTask extends AsyncTask<Void, Void, Void> {

	private final String mLogin;
	private final String mPassword;
	@SuppressLint("StaticFieldLeak")
	private final AppCompatActivity mActivity;

	private final VolleyCallbackLOGON mVolleyCallback;

	public UserLoginTask(String login, String password, AppCompatActivity activity, VolleyCallbackLOGON callback) {
		mLogin = login;
		mPassword = password;
		mActivity = activity;
		mVolleyCallback = callback;
		
	}

	@Override
	public Void doInBackground(Void... params) {
		WebServiceConnexion.getConnected(mLogin, mPassword, mActivity, mVolleyCallback);
		return null;
	}


}