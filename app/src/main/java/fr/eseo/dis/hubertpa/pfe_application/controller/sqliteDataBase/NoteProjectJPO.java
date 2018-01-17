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
import fr.eseo.dis.hubertpa.pfe_application.model.sqliteDataBase.Note;

public class NoteProjectJPO {

	// Champs de la base de données
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private String[] allColumns = { MySQLiteHelper.NOTE_COLUMN_ID, MySQLiteHelper.NOTE_PROJECT_ID,
			MySQLiteHelper.NOTE_COLUMN_VALUE };

	public NoteProjectJPO(Context context, boolean resetDataBase) {
		dbHelper = new MySQLiteHelper(context);
		if(resetDataBase) {
			dbHelper.resetDatabaseNotes();
		}
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();

	}

	public void close() {
		dbHelper.close();
	}


	public Note updateNote(int noteValue, int idProject) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.NOTE_COLUMN_VALUE, noteValue);
		values.put(MySQLiteHelper.NOTE_PROJECT_ID, idProject);

		long insertId = database.update(MySQLiteHelper.TABLE_NOTE, values, MySQLiteHelper.NOTE_COLUMN_ID + " = " + idProject, null);

		Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTE,
				allColumns, MySQLiteHelper.NOTE_COLUMN_ID + " = " + insertId, null,
				null, null, null);

		cursor.moveToFirst();
		Note note = cursorToNote(cursor);
		cursor.close();
		return note;
	}

	public Note createAnnotation(int mark, int idProject) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.NOTE_PROJECT_ID, mark);
		values.put(MySQLiteHelper.NOTE_COLUMN_VALUE, idProject);
		long insertId = database.insert(MySQLiteHelper.TABLE_NOTE, null,
				values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTE,
				allColumns, MySQLiteHelper.NOTE_COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Note note = cursorToNote(cursor);
		cursor.close();
		return note;
	}

	public void deleteNote(Annotation annotation) {
		long id = annotation.getId();
		Log.d("DAO", "deleted with id: " + id);

		database.delete(MySQLiteHelper.TABLE_NOTE, MySQLiteHelper.NOTE_COLUMN_ID
				+ " = " + id, null);
	}

	public List<Note> findNoteFromProject(int idProject) {
		List<Note> notes = new ArrayList<>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTE,
				allColumns, MySQLiteHelper.NOTE_PROJECT_ID + " = " + idProject, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Note note = cursorToNote(cursor);
			notes.add(note);
			cursor.moveToNext();
		}
		cursor.close();
		return notes;
	}


	public List<Note> getAllNotes() {
		List<Note> notes = new ArrayList<>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_NOTE,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Note note = cursorToNote(cursor);
			notes.add(note);
			cursor.moveToNext();
		}
		cursor.close();
		return notes;
	}

	private Note cursorToNote(Cursor cursor) {
		Note note = new Note();
		note.setId(cursor.getLong(0));
		note.setIdProject(cursor.getLong(1));
		note.setNote(cursor.getLong(2));
		return note;
	}
}
