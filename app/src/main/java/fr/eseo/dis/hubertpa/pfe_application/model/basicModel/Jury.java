package fr.eseo.dis.hubertpa.pfe_application.model.basicModel;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
