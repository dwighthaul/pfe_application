package fr.eseo.dis.hubertpa.pfe_application.controller.sqliteDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_COMMENTS = "annotations";
	public static final String COLUMN_ID = "_id";
	public static final String PROJECT_ID = "id_project";
	public static final String COLUMN_TEXT = "annotation";

	public static final String TABLE_NOTE = "annotations";
	public static final String NOTE_COLUMN_ID = "_id";
	public static final String NOTE_PROJECT_ID = "id_project";
	public static final String NOTE_COLUMN_VALUE = "note";

	private static final String DATABASE_NAME = "pfe_database.db";
	private static final int DATABASE_VERSION = 1;

	private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_COMMENTS + "("
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ PROJECT_ID + " integer not null, "
			+ COLUMN_TEXT + " text not null);";

	private static final String DATABASE_CREATE_NOTE_JPO = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NOTE + " ( "
			+ NOTE_COLUMN_ID + " integer primary key autoincrement, "
			+ NOTE_PROJECT_ID + " integer not null, "
			+ NOTE_COLUMN_VALUE + " integer not null);";


	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		Log.d("TakeNotes", DATABASE_CREATE);
		database.execSQL(DATABASE_CREATE);
		database.execSQL(DATABASE_CREATE_NOTE_JPO);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
		onCreate(db);
	}

	public void resetDatabaseAnnotation() {
		SQLiteDatabase database = getWritableDatabase();
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
		database.execSQL(DATABASE_CREATE);
		database.close();
	}

	public void resetDatabaseNotes() {
		SQLiteDatabase database = getWritableDatabase();
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
		database.execSQL(DATABASE_CREATE_NOTE_JPO);
		database.close();
	}

}