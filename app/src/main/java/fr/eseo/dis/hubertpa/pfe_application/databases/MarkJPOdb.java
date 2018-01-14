package fr.eseo.dis.hubertpa.pfe_application.databases;

public class MarkJPOdb {
    public static final String MARK_TABLE_NAME = "marksjpo";

    public static final String COLUMN_NAME_ID_NOTE = "idNote";
    public static final String COLUMN_NAME_ID_PROJECT = "idProject";
    public static final String COLUMN_NAME_VALUE_NOTE = "valueNote";

    public static final String MARK_TABLE_CREATE =
            "CREATE TABLE " + MARK_TABLE_NAME + " (" +
                    COLUMN_NAME_ID_NOTE + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_ID_PROJECT + " INTEGER," +
                    COLUMN_NAME_VALUE_NOTE + " INTEGER)";
}
