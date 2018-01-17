package fr.eseo.dis.hubertpa.pfe_application.activities.jpo_activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;

import fr.eseo.dis.hubertpa.pfe_application.R;

public class JPODisplayImageActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jpodisplay_image);

		Intent intent = getIntent();
		String string_poster = intent.getStringExtra("idProject");

		ImageView imageView = findViewById(R.id.imageView);

		final String pureBase64Encoded = string_poster.substring(string_poster.indexOf(",")  + 1);

		final byte[] decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT);

		Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
		imageView.setImageBitmap(decodedBitmap);

	}
}
