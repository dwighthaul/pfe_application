package fr.eseo.dis.hubertpa.pfe_application.databases;

public class MemoPosterJPOdb {
    //Memo Poster Table
    /* Inner class that defines the table contents */
    public static final String MEMO_POSTER_TABLE_NAME = "memoposterjpo";

    public static final String COLUMN_NAME_ID_MEMO = "idMemo";
    public static final String COLUMN_NAME_ID_PROJECT = "idProject";
    public static final String COLUMN_NAME_TEXT = "text";

    public static final String MEMO_POSTER_TABLE_CREATE =
            "CREATE TABLE " + MEMO_POSTER_TABLE_NAME + " (" +
                    COLUMN_NAME_ID_MEMO + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_ID_PROJECT + " INTEGER," +
                    COLUMN_NAME_TEXT + " TEXT)";
}
