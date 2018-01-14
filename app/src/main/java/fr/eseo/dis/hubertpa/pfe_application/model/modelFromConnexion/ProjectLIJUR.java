package fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion;

import android.os.Parcel;
import android.os.Parcelable;

import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Project;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class ProjectLIJUR implements Parcelable {


	@Getter @Setter @NonNull
	Project project;

	@Getter @Setter
	boolean poster;

	@Getter @Setter @NonNull
	User supervisor;


	private ProjectLIJUR(Parcel in) {
		this.project = new Project();

		this.project.setIdProject(in.readInt());
		this.project.setTitle(in.readString());
		this.project.setDescription(in.readString());

		this.project.setConfidentiality(in.readInt());

		this.supervisor = new User();
		this.supervisor.setForename(in.readString());
		this.supervisor.setSurname(in.readString());


	}

	public static final Creator<ProjectLIJUR> CREATOR = new Creator<ProjectLIJUR>() {
		@Override
		public ProjectLIJUR createFromParcel(Parcel in) {
			return new ProjectLIJUR(in);
		}

		@Override
		public ProjectLIJUR[] newArray(int size) {
			return new ProjectLIJUR[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.project.getIdProject());
		dest.writeString(this.project.getTitle());
		dest.writeString(this.project.getDescription());

		dest.writeInt(this.project.getConfidentiality());

		dest.writeString(this.supervisor.getForename());
		dest.writeString(this.supervisor.getSurname());

//		dest.writeList(this.getSupervisor());
	}

}
