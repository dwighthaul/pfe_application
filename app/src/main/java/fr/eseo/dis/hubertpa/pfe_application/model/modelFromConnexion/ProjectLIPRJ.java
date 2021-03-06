package fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion;

import android.os.Parcel;
import android.os.Parcelable;

import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.ListUser;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Project;
import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
public class ProjectLIPRJ implements Parcelable {

	@Getter @Setter @NonNull
	Project project;

	@Getter @Setter
	boolean poster;

	@Getter @Setter @NonNull
	User supervisor;

	@Getter @Setter @NonNull
	ListUser listStudents;

	public ProjectLIPRJ () {
		listStudents = new ListUser();
	}


	private ProjectLIPRJ(Parcel in) {
		this.project = new Project();

		this.project.setIdProject(in.readInt());
		this.project.setTitle(in.readString());
		this.project.setDescription(in.readString());
		this.project.setConfidentiality(in.readInt());

		this.setPoster(in.readByte() != 0);

		this.supervisor = new User();
		this.supervisor.setForename(in.readString());
		this.supervisor.setSurname(in.readString());

		this.listStudents = new ListUser();
		in.readList(this.listStudents, ListUser.class.getClassLoader());


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
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.project.getIdProject());
		dest.writeString(this.project.getTitle());
		dest.writeString(this.project.getDescription());
		dest.writeInt(this.project.getConfidentiality());

		dest.writeByte((byte) (this.isPoster() ? 1 : 0));

		dest.writeString(this.supervisor.getForename());
		dest.writeString(this.supervisor.getSurname());

		dest.writeList(this.getListStudents());
	}



}
