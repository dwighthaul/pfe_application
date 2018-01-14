package fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley;

import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIJUR;

public interface VolleyCallbackListJury {
	void onSuccess(LIJUR lijur);

	void onError(String errorMessage);

}
