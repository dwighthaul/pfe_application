package fr.eseo.dis.hubertpa.pfe_application.model.metaModel;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectPORTE;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class PORTE {

	@Getter @Setter @NonNull
	List<ProjectPORTE> listProject;

	public PORTE () {
		listProject = new ArrayList<>();
	}


}
