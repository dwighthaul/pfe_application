package fr.eseo.dis.hubertpa.pfe_application.model.basicModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.NotesNOTES;
import lombok.NoArgsConstructor;

/**
 * Created by paulhubert on 12/01/18.
 */

@NoArgsConstructor
public class ListNote extends ArrayList<NotesNOTES>  implements Parcelable {



	protected ListNote(Parcel in) {
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		for(int i=0; i < this.size(); i++)
		{
			NotesNOTES notesNOTES = this.get(i);
			dest.writeInt(notesNOTES.getStudent().getIdUser());
			dest.writeString(notesNOTES.getStudent().getSurname());
			dest.writeString(notesNOTES.getStudent().getForename());
			dest.writeInt(notesNOTES.getAvgnote());
			dest.writeInt(notesNOTES.getMynote());
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
			int average = in.readInt();
			int myNote = in.readInt();

			NotesNOTES notesNOTES = new NotesNOTES(user);
			notesNOTES.setMynote(myNote);
			notesNOTES.setAvgnote(average);
			this.add(notesNOTES);
		}

	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ListNote> CREATOR = new Creator<ListNote>() {
		@Override
		public ListNote createFromParcel(Parcel in) {
			return new ListNote(in);
		}

		@Override
		public ListNote[] newArray(int size) {
			return new ListNote[size];
		}
	};
}

