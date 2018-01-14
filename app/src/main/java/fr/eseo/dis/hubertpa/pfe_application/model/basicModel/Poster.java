package fr.eseo.dis.hubertpa.pfe_application.model.basicModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


@NoArgsConstructor
public class Poster {

	@Getter @Setter
	private int idPoster;

	@Getter @Setter
	private int project;

	@Getter @Setter @NonNull
	private String filepathPDF;



}
