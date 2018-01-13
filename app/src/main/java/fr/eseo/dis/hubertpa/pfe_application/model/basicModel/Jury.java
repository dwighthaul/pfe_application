package fr.eseo.dis.hubertpa.pfe_application.model.basicModel;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by paulhubert on 31/12/17.
 */

@NoArgsConstructor
public class Jury {

	@Getter @Setter
	private int idJury;

	@Getter @Setter
	private String description;

	@Getter @Setter
	private String date;


	public static Jury fromJson(JSONObject jsonObject) throws JSONException {
		Jury jury = new Jury();


		if (jsonObject.has("idJury"))
			jury.setIdJury(jsonObject.getInt("idJury"));

		if (jsonObject.has("date"))
			jury.setDate(jsonObject.getString("date"));


		if (jsonObject.has("description"))
			jury.setDescription(jsonObject.getString("description"));

		return jury;
	}
}
