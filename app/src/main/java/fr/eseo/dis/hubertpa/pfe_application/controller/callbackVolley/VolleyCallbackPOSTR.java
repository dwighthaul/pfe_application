package fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley;

import android.graphics.Bitmap;

public interface VolleyCallbackPOSTR {
	void onSuccess(Bitmap bitmap);

	void onError(String errorMessage);

}
