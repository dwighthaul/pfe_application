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
public class Confidentialities {

	@Getter @Setter  @NonNull
	private int idConfid;

	@Getter @Setter
	private String description;

}
