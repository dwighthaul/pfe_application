package fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion;

import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Project;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Supervisor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by paulhubert on 31/12/17.
 */

@AllArgsConstructor
@RequiredArgsConstructor
public class ProjectLIJUR {


	@Getter @Setter @NonNull
	Project project;

	boolean poster;

	@Getter @Setter @NonNull
	Supervisor supervisor;

}
