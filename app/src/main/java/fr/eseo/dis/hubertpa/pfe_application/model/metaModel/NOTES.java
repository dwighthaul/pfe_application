package fr.eseo.dis.hubertpa.pfe_application.model.metaModel;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.NotesNOTES;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by paulhubert on 31/12/17.
 */

public class NOTES {

	@Getter @Setter @NonNull
	List<NotesNOTES> notesList;



	public NOTES() {
		notesList = new ArrayList<NotesNOTES>();
	}
}
