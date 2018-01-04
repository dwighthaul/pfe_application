package fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Project;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Student;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Supervisor;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Created by paulhubert on 30/12/17.
 */

@AllArgsConstructor
@RequiredArgsConstructor
public class ProjectLIPRJ implements Parcelable{

	@Getter @Setter @NonNull
	Project project;

	@Getter @Setter
	boolean poster;

	@Getter @Setter @NonNull
	User supervisor;

	@Getter @Setter @NonNull
	List<User> listStudents;

	ProjectLIPRJ () {
		listStudents = new ArrayList<User>();
	}


	protected ProjectLIPRJ(Parcel in) {
		this.project = new Project();

		this.project.setIdProject(in.readInt());
		this.project.setTitle(in.readString());
		this.project.setDescription(in.readString());

		this.project.setConfidentiality(in.readInt());

		this.supervisor = new User();

		this.supervisor.setForename(in.readString());
		this.supervisor.setSurname(in.readString());


	}

	public static final Creator<ProjectLIPRJ> CREATOR = new Creator<ProjectLIPRJ>() {
		@Override
		public ProjectLIPRJ createFromParcel(Parcel in) {
			return new ProjectLIPRJ(in);
		}

		@Override
		public ProjectLIPRJ[] newArray(int size) {
			return new ProjectLIPRJ[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(this.project.getIdProject());
		parcel.writeString(this.project.getTitle());
		parcel.writeString(this.project.getDescription());
//		parcel.writeString(String.valueOf(this.isPoster()));
		parcel.writeInt(this.project.getConfidentiality());
		parcel.writeString(this.supervisor.getForename());
		parcel.writeString(this.supervisor.getSurname());
	}
}
