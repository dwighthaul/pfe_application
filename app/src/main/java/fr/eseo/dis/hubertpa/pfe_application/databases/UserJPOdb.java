package fr.eseo.dis.hubertpa.pfe_application.databases;

public class UserJPOdb {

    //Jury Table
    public static final String USER_TABLE_NAME = "usersjpo";

    public static final String COLUMN_NAME_ID_USER = "idUser";
    public static final String COLUMN_NAME_FORENAME = "forname";
    public static final String COLUMN_NAME_SURNAME = "surname";

    public static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    COLUMN_NAME_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_FORENAME + " TEXT, " +
                    COLUMN_NAME_SURNAME + " REAL);";
}
