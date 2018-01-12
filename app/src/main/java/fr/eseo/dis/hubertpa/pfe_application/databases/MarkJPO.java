package fr.eseo.dis.hubertpa.pfe_application.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Morgan on 12/01/2018.
 */

public class MarkJPO extends SQLiteOpenHelper {

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "marksjpo";
        public static final String COLUMN_NAME_ID_NOTE = "idNote";
        public static final String COLUMN_NAME_ID_PROJECT = "idProject";
        public static final String COLUMN_NAME_VALUE_NOTE = "valueNote";
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME_ID_NOTE + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_ID_PROJECT + " INTEGER," +
                    FeedEntry.COLUMN_NAME_VALUE_NOTE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MarkJPO.db";

    public MarkJPO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
