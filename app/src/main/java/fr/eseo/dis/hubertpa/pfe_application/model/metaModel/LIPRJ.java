package fr.eseo.dis.hubertpa.pfe_application.model.metaModel;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class LIPRJ {


	@Getter @Setter @NonNull
	List<ProjectLIPRJ> projectList;

	public LIPRJ () {
		projectList = new ArrayList<>();
	}

}
