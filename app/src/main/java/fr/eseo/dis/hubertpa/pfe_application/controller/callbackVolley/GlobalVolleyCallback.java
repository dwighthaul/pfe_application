package fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley;

import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIJUR;

/**
 * Created by paulhubert on 12/01/18.
 */

public interface GlobalVolleyCallback {
	void onSuccess();

	void onError(String errorMessage);
}
