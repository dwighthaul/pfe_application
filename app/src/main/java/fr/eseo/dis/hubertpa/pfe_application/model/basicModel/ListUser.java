package fr.eseo.dis.hubertpa.pfe_application.model.basicModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ListUser extends ArrayList<User> implements Parcelable {


	protected ListUser(Parcel in) {
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		for(int i=0; i < this.size(); i++)
		{
			User user = this.get(i);
			dest.writeInt(user.getIdUser());
			dest.writeString(user.getSurname());
			dest.writeString(user.getForename());
		}
	}

	public void getFromParcel(Parcel in)
	{
		// On vide la liste avant tout remplissage
		this.clear();

		//Récupération du nombre d'objet
		int size = in.readInt();

		//On repeuple la liste avec de nouveau objet
		for(int i = 0; i < size; i++)
		{
			User user = new User();
			user.setIdUser(in.readInt());
			user.setSurname(in.readString());
			user.setForename(in.readString());
			this.add(user);
		}

	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ListUser> CREATOR = new Creator<ListUser>() {
		@Override
		public ListUser createFromParcel(Parcel in) {
			return new ListUser(in);
		}

		@Override
		public ListUser[] newArray(int size) {
			return new ListUser[size];
		}
	};
}
