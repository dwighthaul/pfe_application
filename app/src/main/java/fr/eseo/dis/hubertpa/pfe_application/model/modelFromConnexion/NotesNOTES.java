package fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion;

import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by paulhubert on 31/12/17.
 */


@AllArgsConstructor
public class NotesNOTES {

	@Getter @Setter
	Student student;

	@Getter @Setter
	int mynote;

	@Getter @Setter
	int avgnote;

}
