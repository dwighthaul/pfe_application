package fr.eseo.dis.hubertpa.pfe_application.controller.connexionAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.APIAndroid;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Jury;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Project;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Student;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Supervisor;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.NOTES;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.POSTR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.NotesNOTES;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.JYINF;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.MYJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.MYPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;

/**
 * Created by paulhubert on 30/12/17.
 */

public class JsonParserAPI {
	public static User parseUser(JSONObject jsonObject) {
		User user = new User();

		try {
			user.setIdUser(0);
			user.setForename(jsonObject.getString("current_user_url"));
			user.setPassword(jsonObject.getString("emails_url"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}

	public static APIAndroid parseTest(JSONObject jsonObject) {
		APIAndroid api = new APIAndroid();

		try {

			api.set_current_user_url(jsonObject.getString("current_user_url"));
			api.set_current_user_authorizations_html_url(jsonObject.getString("current_user_authorizations_html_url"));
			api.set_authorizations_url(jsonObject.getString("authorizations_url"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return api;
	}


	public static APIAndroid parseTest2(JSONObject jsonObject) {
		APIAndroid api = new APIAndroid();

		try {
			api.set_current_user_url(jsonObject.getString("current_user_url"));
			api.set_current_user_authorizations_html_url(jsonObject.getString("current_user_authorizations_html_url"));
			api.set_authorizations_url(jsonObject.getString("authorizations_url"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return api;
	}

	public static User parseLOGON(JSONObject jsonObject) {
		User user = new User();

		try {
			user.setIdUser(jsonObject.getInt("current_user_url"));


		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}


	public static LIPRJ parseLIPRJ(JSONObject jsonObject) {
		LIPRJ lirpj = new LIPRJ();
		try {
			JSONArray listProjectJson = jsonObject.getJSONArray("projects");

			for (int i = 0; i < listProjectJson.length(); i++) {

				JSONObject jsonObjectProject = listProjectJson.getJSONObject(i);
				Project project = Project.fromJson(jsonObjectProject);

				JSONObject supervisorJson = jsonObjectProject.getJSONObject("supervisor");
				Supervisor supervisor = (Supervisor) Supervisor.fromJson(supervisorJson);

				boolean poster = jsonObjectProject.getBoolean("poster");

				JSONArray studentsJsonArray = jsonObjectProject.getJSONArray("students");
				List<Student> listStudents = new ArrayList<Student>();

				for (int j = 0; j < studentsJsonArray.length(); j++) {
					JSONObject studentJson = studentsJsonArray.getJSONObject(j);

					Student student = (Student) Student.fromJson(studentJson);

					listStudents.add(student);
				}

				ProjectLIPRJ projectLIPRJ = new ProjectLIPRJ(project, poster, supervisor, listStudents);
				lirpj.getProjectList().add(projectLIPRJ);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return lirpj;
	}

	public static MYPRJ parseMYPRJ(JSONObject jsonObject) {
		return (MYPRJ) parseLIPRJ(jsonObject);
	}


	public static LIJUR parseLIJUR(JSONObject jsonObject) {
		LIJUR lirpj = new LIJUR();
		try {
			JSONArray listJuryJson = jsonObject.getJSONArray("juries");
			List<JuryLIJUR> listJury = new ArrayList<JuryLIJUR>();


			for (int i = 0; i < listJuryJson.length(); i++) {

				JSONObject jsonObjectJury = listJuryJson.getJSONObject(i);

				Jury jury = Jury.fromJson(jsonObjectJury);

				JSONObject infoJson = jsonObjectJury.getJSONObject("info");
				JSONArray projectsJson = infoJson.getJSONArray("projects");

				List<ProjectLIJUR> listProject = new ArrayList<ProjectLIJUR>();

				for (int j = 0; j < projectsJson.length(); j++) {

					JSONObject projectJson = projectsJson.getJSONObject(j);
					Project project = Project.fromJson(projectJson);

					boolean poster = projectJson.getBoolean("poster");

					JSONObject supervisorJson = projectJson.getJSONObject("supervisor");
					Supervisor supervisor = (Supervisor) Supervisor.fromJson(supervisorJson);

					ProjectLIJUR projectLIJUR = new ProjectLIJUR(project, poster, supervisor);
					listProject.add(projectLIJUR);

				}

				JuryLIJUR juryLIJUR = new JuryLIJUR(jury, listProject);
				lirpj.getListJuries().add(juryLIJUR);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return lirpj;
	}

	public static MYJUR parseMYJUR(JSONObject jsonObject) {
		return (MYJUR) parseMYJUR(jsonObject);
	}

	public static JYINF parseJYINF(JSONObject jsonObject) {
		return (JYINF) parseLIJUR(jsonObject);

	}


	// TODO : Update to match the result
	public static POSTR parsePOSTR(JSONObject jsonObject) {
		return null;

	}

	public static NOTES parseNOTES(JSONObject jsonObject) {
		NOTES notes = new NOTES();
		try {
			JSONArray listNotesJson = jsonObject.getJSONArray("notes");
			List<JuryLIJUR> listJury = new ArrayList<JuryLIJUR>();


			for (int i = 0; i < listNotesJson.length(); i++) {

				JSONObject noteJSON = listNotesJson.getJSONObject(i);

				Student student = (Student) Student.fromJson(noteJSON);

				int mynote = noteJSON.getInt("mynote");

				int avgnote = noteJSON.getInt("avgnote");

				NotesNOTES notesNOTES = new NotesNOTES(student, mynote , avgnote);

				notes.getNotesList().add(notesNOTES);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return notes;
	}













}