package fr.eseo.dis.hubertpa.pfe_application.model;

import lombok.Getter;

public abstract class BasicSettings {


	public enum Roles {
		VISITOR("Visitor", 1),
		STUDENT("Student", 2),
		TEACHER("Teacher", 3),
		;

		@Getter
		int roleId;

		@Getter
		String roleName;

		Roles(String role, int i) {
			roleName = role;
			roleId = i;
		}
	}



	public final static String saveFilenameShared = "user_settings";
	public final static String sharedLogin = "StringUserLogin";
	public final static String sharedPassword = "StringUserPassword";
	public final static String sharedToken = "StringUserToken";
	public final static String sharedRole = "intUserRole";


	public final static String sharedLoginDefault = "default";
	public final static String sharedPasswordDefault = "default";
	public final static String sharedTokenDefault = "0";
	public final static int sharedRoleDefault = 1;
}
