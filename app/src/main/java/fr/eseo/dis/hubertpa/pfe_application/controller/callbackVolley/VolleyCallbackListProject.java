package fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley;

import java.util.List;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;


public interface VolleyCallbackListProject {
	void onSuccess(List<ProjectLIPRJ> liprjs);

	void onError(String errorMessage);

}
