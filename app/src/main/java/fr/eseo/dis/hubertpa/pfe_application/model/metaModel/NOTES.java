package fr.eseo.dis.hubertpa.pfe_application.model.metaModel;

import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListNote;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class NOTES {

	@Getter @Setter @NonNull
	ListNote notesList;



	public NOTES() {
		notesList = new ListNote();
	}
}
