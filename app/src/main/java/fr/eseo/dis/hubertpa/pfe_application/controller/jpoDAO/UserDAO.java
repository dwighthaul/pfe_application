package fr.eseo.dis.hubertpa.pfe_application.controller.jpoDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.hubertpa.pfe_application.controller.sqliteDataBase.MySQLiteHelper;
import fr.eseo.dis.hubertpa.pfe_application.databases.DatabaseHandler;
import fr.eseo.dis.hubertpa.pfe_application.databases.UserJPOdb;
import fr.eseo.dis.hubertpa.pfe_application.model.jpoModel.UserJPO;
import fr.eseo.dis.hubertpa.pfe_application.model.sqliteDataBase.Annotation;

public class UserDAO{

    // Champs de la base de données
    private SQLiteDatabase database;
    private DatabaseHandler dbHelper;
    private String[] allColumns = { UserJPOdb.COLUMN_NAME_ID_USER, UserJPOdb.COLUMN_NAME_FORENAME,
            UserJPOdb.COLUMN_NAME_SURNAME };

    public UserDAO(Context context, boolean resetDataBase) {
        dbHelper = new DatabaseHandler(context);
        if(resetDataBase) {
            dbHelper.resetDatabaseUser();
        }
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public UserJPO createUser(String forename, String surname) {
        ContentValues values = new ContentValues();
        values.put(UserJPOdb.COLUMN_NAME_FORENAME, forename);
        values.put(UserJPOdb.COLUMN_NAME_SURNAME, surname);
        long insertId = database.insert(UserJPOdb.USER_TABLE_NAME, null,
                values);
        Cursor cursor = database.query(UserJPOdb.USER_TABLE_NAME,
                allColumns, UserJPOdb.COLUMN_NAME_ID_USER + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        UserJPO userJPO = cursorToUserJPO(cursor);
        cursor.close();
        return userJPO;
    }

    public List<UserJPO> getAllUsers() {
        List<UserJPO> users = new ArrayList<>();

        Cursor cursor = database.query(UserJPOdb.USER_TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserJPO user = cursorToUserJPO(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        return users;
    }

    private UserJPO cursorToUserJPO(Cursor cursor) {
        UserJPO userJPO = new UserJPO();
        userJPO.setIdUser(cursor.getLong(0));
        userJPO.setForename(cursor.getString(1));
        userJPO.setSurname(cursor.getString(2));
        return userJPO;
    }
}
