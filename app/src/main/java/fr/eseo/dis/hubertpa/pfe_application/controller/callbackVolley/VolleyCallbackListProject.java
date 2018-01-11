package fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley;

import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;

/**
 * Created by paulhubert on 03/01/18.
 */

public interface VolleyCallbackListProject {
	void onSuccess(List<ProjectLIPRJ> liprjs);

	void onError(String errorMessage);

}
