package fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley;

import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;


public interface VolleyCallbackListJury {
	void onSuccess(List<JuryLIJUR> juryLIJURList);

	void onError(String errorMessage);

}
