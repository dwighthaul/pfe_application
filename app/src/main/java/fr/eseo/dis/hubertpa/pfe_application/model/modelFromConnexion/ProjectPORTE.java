package fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import fr.eseo.dis.hubertpa.pfe_application.model.basicModel.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
public class ProjectPORTE implements Parcelable {


	@Getter @Setter @NonNull
	Project project;

	@Getter @Setter @NonNull
	Bitmap bitmap;


	private ProjectPORTE(Parcel in) {
		this.project = new Project();

		this.project.setIdProject(in.readInt());
		this.project.setTitle(in.readString());
		this.project.setDescription(in.readString());
		this.project.setConfidentiality(in.readInt());

	}

	public static final Creator<ProjectPORTE> CREATOR = new Creator<ProjectPORTE>() {
		@Override
		public ProjectPORTE createFromParcel(Parcel in) {
			return new ProjectPORTE(in);
		}

		@Override
		public ProjectPORTE[] newArray(int size) {
			return new ProjectPORTE[size];
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

	}

}
