package fr.eseo.dis.hubertpa.pfe_application.controller.jpoDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import fr.eseo.dis.hubertpa.pfe_application.databases.DatabaseHandler;
import fr.eseo.dis.hubertpa.pfe_application.model.jpoModel.MemoPosterJPO;

public class MemoPosterDAO{

    // Database schema
    private SQLiteDatabase database;
    private DatabaseHandler dbHelper;
    private String[] allColumns = { DatabaseHandler.COLUMN_NAME_ID_MEMO,
            DatabaseHandler.COLUMN_NAME_ID_PROJECT, DatabaseHandler.COLUMN_NAME_TEXT };

    public MemoPosterDAO(Context context) {
        dbHelper = new DatabaseHandler(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createMemoPoster(MemoPosterJPO memoPosterJPO) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.COLUMN_NAME_ID_MEMO, memoPosterJPO.getIdMemo());
        values.put(DatabaseHandler.COLUMN_NAME_ID_PROJECT, memoPosterJPO.getIdProject());
        values.put(DatabaseHandler.COLUMN_NAME_TEXT, memoPosterJPO.getText());
        database.insert(DatabaseHandler.MEMO_POSTER_TABLE_NAME, null, values);
    }

    public void edit(JuryDAO juryJPO) {
        // CODE
    }

    /*public List<MemoPosterJPO> getAllMemos() {
        List<MemoPosterJPO> memos = new ArrayList<MemoPosterJPO>();

        Cursor cursor = database.query(DatabaseHandler.MEMO_POSTER_TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            MemoPosterJPO MemoPosterJPO = cursorToMemo(cursor);
            memos.add(MemoPosterJPO);
            cursor.moveToNext();
        }

        cursor.close();
        return memos;
    }*/
}
