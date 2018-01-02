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
public class User {

	@Getter @Setter
	private int idUser;

	@Getter @Setter @NonNull
	private String username;

	@Getter @Setter
	private String salt;

	@Getter @Setter @NonNull
	private String password;

	@Getter @Setter
	private String forename;

	@Getter @Setter
	private String surname;


	public static User fromJson(JSONObject jsonObject) throws JSONException {
		User user = new User();

		if (jsonObject.getString("userId") != null)
			user.setIdUser(jsonObject.getInt("userId"));

		if (jsonObject.getString("forename") != null)
			user.setForename(jsonObject.getString("forename"));

		if (jsonObject.getString("surname") != null)
			user.setSurname(jsonObject.getString("surname"));

		return user;
	}


}
