package fr.eseo.dis.hubertpa.pfe_application.model.basicModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Presentation {


	@Getter @Setter
	private int idPresentation;

	@Getter @Setter
	private int project;

	@Getter @Setter
	private String filepathPDF;

}
