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

@RequiredArgsConstructor
public class NotesNOTES {

	@Getter @Setter @NonNull
	Student student;

	@Getter @Setter @NonNull
	int mynote;

	@Getter @Setter @NonNull
	int avgnote;

}
