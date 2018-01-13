package fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion;

import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Student;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;
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
@NoArgsConstructor
@RequiredArgsConstructor
public class NotesNOTES {

	@Getter @Setter @NonNull
	User student;

	@Getter @Setter
	int mynote;

	@Getter @Setter
	int avgnote;

	@Getter @Setter
	boolean noteSet;
}
