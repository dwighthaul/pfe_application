package fr.eseo.dis.hubertpa.pfe_application.model.metaModel;

import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Created by paulhubert on 31/12/17.
 */

public class MYJUR {

	@Getter @Setter @NonNull
	List<JuryLIJUR> juryList;

}

/*
"juries" : [
		{
		"idJury" : <id>,
		"date" : "<date>",
		"info" : {
		"projects" : [
		{
		"projectId" : <projectId>,
		"title" : "<project title>",
		"confid" : <confidentiality>,
		"poster" : true|false,
		"supervisor" : {
		"forename" : "<forename>",
		"surname" : "<surname>",
		},
		},
		]
		}
		},
		*/