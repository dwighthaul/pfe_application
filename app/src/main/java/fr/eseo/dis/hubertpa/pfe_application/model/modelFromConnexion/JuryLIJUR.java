package fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion;

import android.os.Parcel;
import android.os.Parcelable;

import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Jury;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListProject;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListUser;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class JuryLIJUR implements Parcelable {

	@Getter @Setter @NonNull
	Jury jury;

	@Getter @Setter @NonNull
	ListUser listMembers;

	@Getter @Setter @NonNull
	ListProject listProject;

	public JuryLIJUR() {
		listProject = new ListProject();
		listMembers = new ListUser();
	}

	private JuryLIJUR(Parcel in) {
		this.jury = new Jury();

		this.jury.setIdJury(in.readInt());
		this.jury.setDate(in.readString());

		this.listMembers = new ListUser();
		in.readList(this.listMembers, ListUser.class.getClassLoader());

		this.listProject = new ListProject();
		in.readList(this.listProject, ListProject.class.getClassLoader());
	}

	public static final Parcelable.Creator<JuryLIJUR> CREATOR = new Parcelable.Creator<JuryLIJUR>() {
		@Override
		public JuryLIJUR createFromParcel(Parcel in) {
			return new JuryLIJUR(in);
		}

		@Override
		public JuryLIJUR[] newArray(int size) {
			return new JuryLIJUR[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int i) {
		dest.writeInt(getJury().getIdJury());
		dest.writeString(getJury().getDate());

		dest.writeList(this.listMembers);

		dest.writeList(this.listProject);
	}

}
