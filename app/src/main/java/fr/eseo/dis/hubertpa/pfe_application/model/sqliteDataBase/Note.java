package fr.eseo.dis.hubertpa.pfe_application.model.sqliteDataBase;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by paulhubert on 17/01/18.
 */

public class Note {

	@Getter
	@Setter
	private long id;

	@Getter @Setter
	private long idProject;

	@Getter @Setter
	private long note;

	@Override
	public String toString() {
		return this.getId() + " "
				+ this.getIdProject() + " "
				+  this.getNote();
	}
}
