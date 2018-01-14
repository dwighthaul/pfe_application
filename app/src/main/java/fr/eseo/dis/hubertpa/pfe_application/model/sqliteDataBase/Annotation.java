package fr.eseo.dis.hubertpa.pfe_application.model.sqliteDataBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by paulhubert on 14/01/18.
 */

@AllArgsConstructor
@NoArgsConstructor
public class Annotation {

	@Getter @Setter
	private long id;

	@Getter @Setter
	private long idProject;

	@Getter @Setter
	private String annotation;

	@Override
	public String toString() {
		return this.getId() + " "
		+ this.getIdProject() + " "
		+  this.getAnnotation();
	}
}
