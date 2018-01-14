package fr.eseo.dis.hubertpa.pfe_application.model.metaModel;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class LIJUR {

	@Getter @Setter @NonNull
	List<JuryLIJUR> listJuries;

	public LIJUR () {
		listJuries = new ArrayList<JuryLIJUR>();
	}


}
