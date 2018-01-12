package fr.eseo.dis.hubertpa.pfe_application.model.basicModel;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

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
public class User implements Parcelable {

	@Getter @Setter
	private int idUser;

	@Getter @Setter @NonNull
	private String username;

	@Getter @Setter
	private String salt;

	@Getter @Setter @NonNull
	private String password;

	@Getter @Setter @NonNull
	private String forename;

	@Getter @Setter @NonNull
	private String surname;

	protected User(Parcel in) {
		idUser = in.readInt();
		forename = in.readString();
		surname = in.readString();
	}

	public static final Creator<User> CREATOR = new Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};

	public static User fromJson(JSONObject jsonObject) throws JSONException {
		User user = new User();

		if(jsonObject.has("userId")) {
			user.setIdUser(jsonObject.getInt("userId"));
		}

		if (jsonObject.has("forename")) {
			try {
				user.setForename(new String(jsonObject.getString("forename").getBytes("ISO-8859-1"), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		if (jsonObject.has("surname")) {
			try {
				user.setSurname(new String(jsonObject.getString("surname").getBytes("ISO-8859-1"), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return user;
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(this.idUser);
		parcel.writeString(this.getForename());
		parcel.writeString(this.getSurname());
	}
}
