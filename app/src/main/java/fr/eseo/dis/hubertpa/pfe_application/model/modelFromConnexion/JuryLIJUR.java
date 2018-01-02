package fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Jury;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by paulhubert on 31/12/17.
 */

@AllArgsConstructor
public class JuryLIJUR {

	@Getter @Setter @NonNull
	Jury jury;

	@Getter @Setter @NonNull
	List<ProjectLIJUR> listProject;


	JuryLIJUR() {
		listProject = new ArrayList<ProjectLIJUR>();
	}
}
