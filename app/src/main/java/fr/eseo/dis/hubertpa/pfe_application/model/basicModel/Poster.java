package fr.eseo.dis.hubertpa.pfe_application.model.basicModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by paulhubert on 30/12/17.
 */

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Poster {

	@Getter @Setter @NonNull
	private int idPoster;

	@Getter @Setter @NonNull
	private int project;

	@Getter @Setter @NonNull
	private String filepathPDF;



}
