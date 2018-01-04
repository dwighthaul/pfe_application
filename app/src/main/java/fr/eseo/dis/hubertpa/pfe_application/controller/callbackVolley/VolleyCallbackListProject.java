package fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley;

import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIPRJ;

/**
 * Created by paulhubert on 03/01/18.
 */

public interface VolleyCallbackListProject {
	void onSuccess(LIPRJ liprj);

	void onError(String errorMessage);

}
