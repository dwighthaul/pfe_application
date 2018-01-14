package fr.eseo.dis.hubertpa.pfe_application.controller.sqliteDataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;



import android.database.SQLException;
import android.util.Log;

import fr.eseo.dis.hubertpa.pfe_application.model.sqliteDataBase.Annotation;

public class AnnotationDAO {

	// Champs de la base de données
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.PROJECT_ID,
			MySQLiteHelper.COLUMN_TEXT };

	public AnnotationDAO(Context context, boolean resetDataBase) {
		dbHelper = new MySQLiteHelper(context);
		if(resetDataBase) {
			dbHelper.resetDatabaseAnnotation();
		}
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();

	}

	public void close() {
		dbHelper.close();
	}


	public Annotation updateAnnotation(String comment, int idProject) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_TEXT, comment);
		values.put(MySQLiteHelper.PROJECT_ID, idProject);

		long insertId = database.update(MySQLiteHelper.TABLE_COMMENTS, values, MySQLiteHelper.PROJECT_ID + " = " + idProject, null);

		Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Annotation annotation = cursorToAnnotation(cursor);
		cursor.close();
		return annotation;
	}

	public Annotation createAnnotation(String comment, int idProject) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_TEXT, comment);
		values.put(MySQLiteHelper.PROJECT_ID, idProject);
		long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
				allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Annotation annotation = cursorToAnnotation(cursor);
		cursor.close();
		return annotation;
	}

	public void deleteComment(Annotation annotation) {
		long id = annotation.getId();
		Log.d("DAO", "deleted with id: " + id);

		database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Annotation> findAnnotationFromProject(int idProject) {
		List<Annotation> annotations = new ArrayList<>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
				allColumns, MySQLiteHelper.PROJECT_ID + " = " + idProject, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Annotation annotation = cursorToAnnotation(cursor);
			annotations.add(annotation);
			cursor.moveToNext();
		}
		cursor.close();
		return annotations;
	}


	public List<Annotation> getAllComments() {
		List<Annotation> annotations = new ArrayList<>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Annotation annotation = cursorToAnnotation(cursor);
			annotations.add(annotation);
			cursor.moveToNext();
		}
		cursor.close();
		return annotations;
	}

	private Annotation cursorToAnnotation(Cursor cursor) {
		Annotation annotation = new Annotation();
		annotation.setId(cursor.getLong(0));
		annotation.setIdProject(cursor.getLong(1));
		annotation.setAnnotation(cursor.getString(2));
		return annotation;
	}
}
