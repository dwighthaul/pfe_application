package fr.eseo.dis.hubertpa.pfe_application.model.basicModel;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@NoArgsConstructor
public class Project implements Parcelable {

	@Getter @Setter
	private int idProject;

	@Getter @Setter @NonNull
	private String title;

	@Getter @Setter
	private String description;

	@Getter @Setter
	private int confidentiality;



	public static Project fromJson(JSONObject jsonObject) throws JSONException {
		Project project = new Project();

		if(jsonObject.has("projectId"))
			project.setIdProject(jsonObject.getInt("projectId"));

		if(jsonObject.has("title"))
		project.setTitle(jsonObject.getString("title"));

		if(jsonObject.has("descrip"))
			project.setDescription(jsonObject.getString("descrip"));

		if(jsonObject.has("confid"))
			project.setConfidentiality(jsonObject.getInt("confid"));

		return project;
	}

	public static final Creator<Project> CREATOR = new Creator<Project>() {
		@Override
		public Project createFromParcel(Parcel in) {
			return new Project(in);
		}

		@Override
		public Project[] newArray(int size) {
			return new Project[size];
		}
	};

	private Project(Parcel in) {
		setIdProject(in.readInt());
		setTitle(in.readString());
		setDescription(in.readString());
		setConfidentiality(in.readInt());
	}


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int i) {
		dest.writeInt(this.getIdProject());
		dest.writeString(this.getTitle());
		dest.writeString(this.getDescription());
		dest.writeInt(this.getConfidentiality());
	}
}

