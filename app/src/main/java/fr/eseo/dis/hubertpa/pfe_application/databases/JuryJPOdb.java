package fr.eseo.dis.hubertpa.pfe_application.databases;

class JuryJPOdb {
    //Jury Table
    public static final String JURY_TABLE_NAME = "juriesjpo";

    public static final String COLUMN_NAME_ID_JURY = "idJury";
    public static final String COLUMN_NAME_ID_PROJECT = "idProject";
    public static final String COLUMN_NAME_DATE = "date";

    public static final String JURY_TABLE_CREATE =
            "CREATE TABLE " + JURY_TABLE_NAME + " (" +
                    COLUMN_NAME_ID_JURY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_ID_PROJECT + " INTEGER, " +
                    COLUMN_NAME_DATE + " REAL);";
}
