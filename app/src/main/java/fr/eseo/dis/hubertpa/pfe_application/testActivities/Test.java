package fr.eseo.dis.hubertpa.pfe_application.testActivities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Scanner;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import fr.eseo.dis.hubertpa.pfe_application.R;
import fr.eseo.dis.hubertpa.pfe_application.activities.WelcomeActivity;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.TestWebservice;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.JsonParserAPI;
import fr.eseo.dis.hubertpa.pfe_application.controller.requestApi.WebServiceConnexion;
import fr.eseo.dis.hubertpa.pfe_application.model.APIAndroid;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LOGON;
import lombok.Getter;
import lombok.Setter;

public class Test extends AppCompatActivity {



		private TextView mTextView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);

		mTextView = (TextView) findViewById(R.id.token_value);
		RequestQueue queue = Volley.newRequestQueue(this, new HurlStack(null, newSslSocketFactory()));

		String url = WebServiceConnexion.getLOGON(WebServiceConnexion.DEFAULT_LOGIN, WebServiceConnexion.DEFAULT_PASSWORD);

		// Request a string response from the provided URL.
		StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					try {

						JSONObject jsonObject = new JSONObject(response);

						String result = jsonObject.getString("result");

						if (result.equals("OK")) {
							LOGON logon = JsonParserAPI.parseLOGON(jsonObject);
							String tokenValue =logon.getToken();
							mTextView.setText(tokenValue);

						} else {

						}

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d("WebServiceConnexion", error.getMessage());

			}
		});
		// Add the request to the RequestQueue.
		queue.add(stringRequest);

	}


	private SSLSocketFactory newSslSocketFactory() {
		try {
			CertificateFactory cf = CertificateFactory.getInstance("x.509");
			InputStream caInput = new BufferedInputStream(this.getResources().openRawResource(R.raw.root));
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
			InputStream in = getApplicationContext().getResources().openRawResource(R.raw.root); //.bks file in raw folder
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
