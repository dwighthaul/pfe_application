package fr.eseo.dis.hubertpa.pfe_application.model.metaModel;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by paulhubert on 31/12/17.
 */

@AllArgsConstructor
@RequiredArgsConstructor
public class LIJUR {

	@Getter @Setter @NonNull
	List<JuryLIJUR> listJuries;

	public LIJUR () {
		listJuries = new ArrayList<JuryLIJUR>();
	}


}
