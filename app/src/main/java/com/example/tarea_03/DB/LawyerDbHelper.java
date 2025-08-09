package com.example.tarea_03.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tarea_03.model.Lawyer;

public class LawyerDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Lawyers.db";
    private static final int DATABASE_VERSION = 1;
    public LawyerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_LAWYER_TABLE = "CREATE TABLE lawyer (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "id TEXT NOT NULL," +
                "name TEXT NOT NULL," +
                "specialty TEXT NOT NULL," +
                "phone TEXT NOT NULL," +
                "bio TEXT," +
                "avatarUri TEXT);";
        db.execSQL(SQL_CREATE_LAWYER_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS lawyer");
        onCreate(db);
    }
    // Insertar datos
    public long addLawyer(String id, String name, String specialty, String phone, String bio, String avatarUri) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("specialty", specialty);
        values.put("phone", phone);
        values.put("bio", bio);
        values.put("avatarUri", avatarUri);
        return db.insert("lawyer", null, values);
    }


    // Leer Datos
    public Cursor getAllLawyers() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query("lawyer", null, null, null, null, null, null);
    }

    public Lawyer getLawyerById(String lawyerId) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                "id",
                "name",
                "specialty",
                "phone",
                "bio",
                "avatarUri"
        };

        String selection = "id = ?";
        String[] selectionArgs = { lawyerId };

        Cursor cursor = db.query(
                "lawyer",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Lawyer lawyer = null;

        if (cursor != null && cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String specialty = cursor.getString(cursor.getColumnIndexOrThrow("specialty"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("phone"));
            String bio = cursor.getString(cursor.getColumnIndexOrThrow("bio"));
            String avatarUri = cursor.getString(cursor.getColumnIndexOrThrow("avatarUri"));

            lawyer = new Lawyer(id, name, specialty, phone, bio, avatarUri);
            cursor.close();
        }

        return lawyer;
    }


    // Actualizar Datos
    public int updateLawyer(String id, String name, String specialty, String phone, String bio, String avatarUri) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("specialty", specialty);
        values.put("phone", phone);
        values.put("bio", bio);
        values.put("avatarUri", avatarUri);
        return db.update("lawyer", values, "id = ?", new String[]{id});
    }


    // Eliminar datos
    public int deleteLawyer(String lawyerId) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("lawyer", "id = ?", new String[]{lawyerId});
    }
}

