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

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Project {

	@Getter @Setter  @NonNull
	private int idProject;

	@Getter @Setter @NonNull
	private String title;

	@Getter @Setter
	private String description;

	@Getter @Setter  @NonNull
	private int confidentiality;



	public static Project fromJson(JSONObject jsonObject) throws JSONException {
		Project project = new Project();

		project.setIdProject(jsonObject.getInt("projectId"));
		project.setTitle(jsonObject.getString("title"));
		project.setDescription(jsonObject.getString("descrip"));
		project.setConfidentiality(jsonObject.getInt("confid"));
		return project;

	}
}

