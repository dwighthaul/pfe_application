package fr.eseo.dis.hubertpa.pfe_application.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {


    // The database name
    public static final String DATABASE_NAME = "pfe_database.db";

    // The database version
    public static final int DATABASE_VERSION = 1;

    //Jury Table
    public static final String JURY_TABLE_NAME = "juriesjpo";

    public static final String COLUMN_NAME_ID_JURY = "idJury";
    public static final String COLUMN_NAME_ID_PROJECT = "idProject";
    public static final String COLUMN_NAME_DATE = "date";

    private static final String JURY_TABLE_CREATE =
            "CREATE TABLE " + JURY_TABLE_NAME + " (" +
                    COLUMN_NAME_ID_JURY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_ID_PROJECT + " INTEGER, " +
                    COLUMN_NAME_DATE + " REAL);";

    //Mark Table
    public static final String MARK_TABLE_NAME = "marksjpo";

    public static final String COLUMN_NAME_ID_NOTE = "idNote";
    //public static final String COLUMN_NAME_ID_PROJECT = "idProject";
    public static final String COLUMN_NAME_VALUE_NOTE = "valueNote";

    private static final String MARK_TABLE_CREATE =
            "CREATE TABLE " + MARK_TABLE_NAME + " (" +
                    COLUMN_NAME_ID_NOTE + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_ID_PROJECT + " INTEGER," +
                    COLUMN_NAME_VALUE_NOTE + " INTEGER)";

    //Memo Poster Table
    /* Inner class that defines the table contents */
    public static final String MEMO_POSTER_TABLE_NAME = "memoposterjpo";

    public static final String COLUMN_NAME_ID_MEMO = "idMemo";
    //public static final String COLUMN_NAME_ID_PROJECT = "idProject";
    public static final String COLUMN_NAME_TEXT = "text";

    private static final String MEMO_POSTER_TABLE_CREATE =
            "CREATE TABLE " + MEMO_POSTER_TABLE_NAME + " (" +
                    COLUMN_NAME_ID_MEMO + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_ID_PROJECT + " INTEGER," +
                    COLUMN_NAME_TEXT + " TEXT)";




    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(JURY_TABLE_CREATE);
        db.execSQL(MARK_TABLE_CREATE);
        db.execSQL(MEMO_POSTER_TABLE_CREATE);
    }

    private static final String SQL_DELETE_JURY_TABLE = "DROP TABLE IF EXISTS " + JURY_TABLE_NAME + ";";
    private static final String SQL_DELETE_MARK_TABLE = "DROP TABLE IF EXISTS " + MARK_TABLE_NAME + ";";
    private static final String SQL_DELETE_MEMO_POSTER_TABLE = "DROP TABLE IF EXISTS " + MEMO_POSTER_TABLE_NAME + ";";

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_JURY_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_MARK_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_MEMO_POSTER_TABLE);
        onCreate(sqLiteDatabase);
    }
}
