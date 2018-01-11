package fr.eseo.dis.hubertpa.pfe_application.model.basicModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import fr.eseo.dis.hubertpa.pfe_application.model.modelFromConnexion.ProjectLIJUR;
import lombok.NoArgsConstructor;

/**
 * Created by paulhubert on 11/01/18.
 */

@NoArgsConstructor
public class ListProject extends ArrayList<ProjectLIJUR> implements Parcelable {

	public ListProject(Parcel in) {
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		for(int i=0; i < this.size(); i++)
		{
			ProjectLIJUR projectLIJUR = this.get(i);

			dest.writeInt(projectLIJUR.getProject().getIdProject());
			dest.writeString(projectLIJUR.getProject().getTitle());
			dest.writeString(projectLIJUR.getProject().getDescription());

			dest.writeInt(projectLIJUR.getSupervisor().getIdUser());
			dest.writeString(projectLIJUR.getSupervisor().getForename());
			dest.writeString(projectLIJUR.getSupervisor().getSurname());

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
			Project project = new Project();
			project.setIdProject(in.readInt());
			project.setTitle(in.readString());
			project.setDescription(in.readString());

			User supervisor = new User();
			supervisor.setIdUser(in.readInt());
			supervisor.setForename(in.readString());
			supervisor.setSurname(in.readString());

			ProjectLIJUR projectLIJUR = new ProjectLIJUR(project, true, supervisor);
			projectLIJUR.setProject(project);

			this.add(projectLIJUR);
		}

	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ListProject> CREATOR = new Creator<ListProject>() {
		@Override
		public ListProject createFromParcel(Parcel in) {
			return new ListProject(in);
		}

		@Override
		public ListProject[] newArray(int size) {
			return new ListProject[size];
		}
	};
}
