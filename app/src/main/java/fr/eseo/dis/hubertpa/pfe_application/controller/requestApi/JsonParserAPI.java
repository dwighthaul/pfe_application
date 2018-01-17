package fr.eseo.dis.hubertpa.pfe_application.controller.requestApi;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Jury;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListProject;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListUser;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Project;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Student;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LOGON;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.NOTES;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.PORTE;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.POSTR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.NotesNOTES;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.metaModel.LIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.JuryLIJUR;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIPRJ;
import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectPORTE;

public class JsonParserAPI {

	public static LOGON parseLOGON(JSONObject jsonObject) {

		LOGON logon = new LOGON();

		try {
			logon.setToken(jsonObject.getString("token"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return logon;
	}

	public static LIPRJ parseLIPRJ(JSONObject jsonObject) {
		LIPRJ lirpj = new LIPRJ();
		try {
			JSONArray listProjectJson = jsonObject.getJSONArray("projects");

			for (int i = 0; i < listProjectJson.length(); i++) {

				JSONObject jsonObjectProject = listProjectJson.getJSONObject(i);
				Project project = Project.fromJson(jsonObjectProject);

				JSONObject supervisorJson = jsonObjectProject.getJSONObject("supervisor");
				User supervisor = User.fromJson(supervisorJson);
				boolean poster = jsonObjectProject.getBoolean("poster");

				JSONArray studentsJsonArray = jsonObjectProject.getJSONArray("students");
				ListUser listStudents = new ListUser();

				for (int j = 0; j < studentsJsonArray.length(); j++) {
					JSONObject studentJson = studentsJsonArray.getJSONObject(j);

					User student = User.fromJson(studentJson);

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

	public static LIPRJ parseMYPRJ(JSONObject jsonObject) {
		return parseLIPRJ(jsonObject);
	}


	public static LIJUR parseLIJUR(JSONObject jsonObject) {
		LIJUR lijur = new LIJUR();
		try {
			JSONArray listJuryJson = jsonObject.getJSONArray("juries");

			for (int i = 0; i < listJuryJson.length(); i++) {

				JSONObject jsonObjectJury = listJuryJson.getJSONObject(i);

				Jury jury = Jury.fromJson(jsonObjectJury);

				JSONObject infoJson = jsonObjectJury.getJSONObject("info");

				JSONArray membersJson = infoJson.getJSONArray("members");

				ListUser listMembers = new ListUser();

				for (int j = 0; j < membersJson.length(); j++) {

					JSONObject supervisorJson = membersJson.getJSONObject(j);
					User supervisor = User.fromJson(supervisorJson);

					listMembers.add(supervisor);
				}


				JSONArray projectsJson = infoJson.getJSONArray("projects");

				ListProject listProject = new ListProject();

				for (int j = 0; j < projectsJson.length(); j++) {

					JSONObject projectJson = projectsJson.getJSONObject(j);
					Project project = Project.fromJson(projectJson);

					boolean poster = projectJson.getBoolean("poster");

					JSONObject supervisorJson = projectJson.getJSONObject("supervisor");
					User supervisor = User.fromJson(supervisorJson);

					ProjectLIJUR projectLIJUR = new ProjectLIJUR(project, poster, supervisor);
					listProject.add(projectLIJUR);

				}

				JuryLIJUR juryLIJUR = new JuryLIJUR(jury, listMembers, listProject);
				lijur.getListJuries().add(juryLIJUR);

			}


		} catch (JSONException e) {

		}
		return lijur;
	}

	public static LIJUR parseMYJUR(JSONObject jsonObject) {
		return parseLIJUR(jsonObject);
	}

	public static LIPRJ parseJYINF(JSONObject jsonObject) {
		return parseLIPRJ(jsonObject);

	}


	/**
	 * 	Useless nothing to parse since it send a bitmap of the actual image
	 * 	There is nothing to parse
	 * 	Return null.
	 *
	 */
	@Deprecated
	public static POSTR parsePOSTR(JSONObject jsonObject) {
		return null;
	}

	public static NOTES parseNOTES(JSONObject jsonObject) {
		NOTES notes = new NOTES();
		try {
			JSONArray listNotesJson = jsonObject.getJSONArray("notes");

			for (int i = 0; i < listNotesJson.length(); i++) {

				JSONObject noteJSON = listNotesJson.getJSONObject(i);

				User student = User.fromJson(noteJSON);

				int mynote = noteJSON.getInt("mynote");
				int avgnote = noteJSON.getInt("avgNote");

				NotesNOTES notesNOTES = new NotesNOTES(student);
				notesNOTES.setMynote(mynote);
				notesNOTES.setAvgnote(avgnote);

				notes.getNotesList().add(notesNOTES);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return notes;
	}

	public static PORTE parsePORTE(JSONObject jsonObject) {
		PORTE porte = new PORTE();

		try {
			JSONArray listNotesJson = jsonObject.getJSONArray("projects");
			List<ProjectPORTE> listProjectPORTE = new ArrayList<>();

			for (int i = 0; i < listNotesJson.length(); i++) {

				JSONObject jsonObjectProject = listNotesJson.getJSONObject(i);
				jsonObjectProject.put("projectId", jsonObjectProject.getString("idProject"));
				jsonObjectProject.put("descrip", jsonObjectProject.getString("description"));

				Project project = Project.fromJson(jsonObjectProject);

				String bitmap = jsonObjectProject.getString("poster");

				ProjectPORTE projectPORTE = new ProjectPORTE(project, bitmap);
				listProjectPORTE.add(projectPORTE);
			}

			porte.setListProject(listProjectPORTE);
		} catch (JSONException e) {
			Log.d("TEST", "" + e.toString());
			e.printStackTrace();
		}
		return porte;
	}



}