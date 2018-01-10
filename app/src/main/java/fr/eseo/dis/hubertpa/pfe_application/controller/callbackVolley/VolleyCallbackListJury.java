package fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley;

import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIJUR;

/**
 * Created by Morgan on 05/01/18.
 */

public interface VolleyCallbackListJury {
	void onSuccess(LIJUR lijur);

	void onError(String errorMessage);

}
