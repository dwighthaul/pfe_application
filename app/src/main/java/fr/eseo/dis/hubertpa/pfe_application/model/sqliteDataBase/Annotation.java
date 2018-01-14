package fr.eseo.dis.hubertpa.pfe_application.model.sqliteDataBase;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by paulhubert on 14/01/18.
 */

public class Annotation {

	@Getter @Setter @NonNull
	private long id;

	@Getter @Setter @NonNull
	private long idProject;

	@Getter @Setter @NonNull
	private String annotation;

	@Override
	public String toString() {
		return this.getId() + " "
		+ this.getIdProject() + " "
		+  this.getAnnotation();
	}
}
