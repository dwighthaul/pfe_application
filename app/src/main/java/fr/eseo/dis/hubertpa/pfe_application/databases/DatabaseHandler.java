package fr.eseo.dis.hubertpa.pfe_application.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    // The database name
    public static final String DATABASE_NAME = "pfe_database.db";

    // The database version
    public static final int DATABASE_VERSION = 1;

    public DatabaseHandler(Context pContext, String databaseName, Context context, int databaseVersion) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(JuryJPOdb.JURY_TABLE_CREATE);
        db.execSQL(MarkJPOdb.MARK_TABLE_CREATE);
        db.execSQL(MemoPosterJPOdb.MEMO_POSTER_TABLE_CREATE);
    }

    private static final String SQL_DELETE_JURY_TABLE = "DROP TABLE IF EXISTS " + JuryJPOdb.JURY_TABLE_NAME + ";";
    private static final String SQL_DELETE_MARK_TABLE = "DROP TABLE IF EXISTS " + MarkJPOdb.MARK_TABLE_NAME + ";";
    private static final String SQL_DELETE_MEMO_POSTER_TABLE = "DROP TABLE IF EXISTS " + MemoPosterJPOdb.MEMO_POSTER_TABLE_NAME + ";";

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_JURY_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_MARK_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_MEMO_POSTER_TABLE);
        onCreate(sqLiteDatabase);
    }
}
