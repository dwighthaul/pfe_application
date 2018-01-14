package fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley;

import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.NOTES;

public interface VolleyCallbackNOTES {
	void onSuccess(NOTES notes);

	void onError(String errorMessage);
}
