package fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley;

import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectPORTE;

/**
 * Created by paulhubert on 14/01/18.
 */

public interface VolleyCallbackListProjectPORTE {
	void onSuccess(List<ProjectPORTE> liprpo);

	void onError(String errorMessage);

}
