package fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Jury;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Supervisor;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by paulhubert on 31/12/17.
 */

@RequiredArgsConstructor
public class JuryLIJUR {

	@Getter @Setter @NonNull
	Jury jury;

	@Getter @Setter @NonNull
	User supervisor;

	@Getter @Setter @NonNull
	List<ProjectLIJUR> listProject;

	public JuryLIJUR() {
		listProject = new ArrayList<ProjectLIJUR>();
	}

	protected JuryLIJUR(Parcel in) {
		this.jury = new Jury();

		this.jury.setIdJury(in.readInt());
		this.jury.setDate(in.readString());
		//this.project.setDescription(in.readString());

		//this.project.setConfidentiality(in.readInt());

		this.supervisor = new User();

		this.supervisor.setForename(in.readString());
		this.supervisor.setSurname(in.readString());


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

	/*@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(this.jury.getIdJury());
	}*/


}
