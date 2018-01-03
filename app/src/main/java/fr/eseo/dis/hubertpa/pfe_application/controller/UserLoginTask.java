package fr.eseo.dis.hubertpa.pfe_application.controller;

/**
 * Created by paulhubert on 03/01/18.
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import fr.eseo.dis.hubertpa.pfe_application.activities.DashboardActivity;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;

public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

	private final String mLogin;
	private final String mPassword;
	@SuppressLint("StaticFieldLeak")
	private final AppCompatActivity mActivity;

	public UserLoginTask(String email, String password, AppCompatActivity activity) {
		mLogin = email;
		mPassword = password;
		mActivity = activity;
	}

	@Override
	protected Boolean doInBackground(Void... params) {
		return WebServiceConnexion.getConnected(mLogin, mPassword, mActivity);
	}

	@Override
	protected void onPostExecute(final Boolean success) {

		if (success) {
			Intent intent = new Intent(mActivity, DashboardActivity.class);
			// Use to set the default app as this new activity and clean the stack
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			mActivity.startActivity(intent);
		}
	}

}