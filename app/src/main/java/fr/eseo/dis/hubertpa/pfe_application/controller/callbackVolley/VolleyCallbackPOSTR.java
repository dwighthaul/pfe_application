package fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley;

import android.graphics.Bitmap;

import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;

/**
 * Created by paulhubert on 10/01/18.
 */

public interface VolleyCallbackPOSTR {
	void onSuccess(Bitmap bitmap);

	void onError(String errorMessage);

}
