package fr.eseo.dis.hubertpa.pfe_application.model.basicModel;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by paulhubert on 30/12/17.
 */

@NoArgsConstructor
public class Project {

	@Getter @Setter
	private int idProject;

	@Getter @Setter @NonNull
	private String title;

	@Getter @Setter
	private String description;

	@Getter @Setter
	private int confidentiality;



	public static Project fromJson(JSONObject jsonObject) throws JSONException {
		Project project = new Project();

		if(jsonObject.has("projectId"))
			project.setIdProject(jsonObject.getInt("projectId"));

		if(jsonObject.has("title"))
		project.setTitle(jsonObject.getString("title"));

		if(jsonObject.has("descrip"))
			project.setDescription(jsonObject.getString("descrip"));

		if(jsonObject.has("confid"))
			project.setConfidentiality(jsonObject.getInt("confid"));

		return project;

	}
}

