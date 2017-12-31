package fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion;

import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Project;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Student;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Supervisor;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by paulhubert on 30/12/17.
 */

@AllArgsConstructor
@NoArgsConstructor
public class ProjectLIPRJ {

	@Getter @Setter @NonNull
	Project project;

	@Getter @Setter @NonNull
	boolean poster;

	@Getter @Setter @NonNull
	Supervisor supervisor;

	@Getter @Setter @NonNull
	List<Student> listStudents;




}
