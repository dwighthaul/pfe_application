package fr.eseo.dis.hubertpa.pfe_application.controller.callbackVolley;

import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListNote;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;

/**
 * Created by paulhubert on 17/01/18.
 */

public interface VolleyCallbackListNotes {
	void onSuccess(ListNote listNote);

	void onError(String errorMessage);

}
