package fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley;

import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.NOTES;

/**
 * Created by paulhubert on 12/01/18.
 */

public interface VolleyCallbackNOTES {
	public void onSuccess(NOTES notes);

	public void onError(String errorMessage);
}
