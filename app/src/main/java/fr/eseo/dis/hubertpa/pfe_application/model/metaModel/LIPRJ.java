package fr.eseo.dis.hubertpa.pfe_application.model.metaModel;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by paulhubert on 30/12/17.
 */

@AllArgsConstructor
@RequiredArgsConstructor
public class LIPRJ {


	@Getter @Setter @NonNull
	List<ProjectLIPRJ> projectList;

	public LIPRJ () {
		projectList = new ArrayList<ProjectLIPRJ>();
	}

}
