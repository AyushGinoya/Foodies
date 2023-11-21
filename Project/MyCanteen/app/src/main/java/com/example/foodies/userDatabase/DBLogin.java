package com.example.foodies.userDatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBLogin extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DB_NAME="MyCanteenApp";
    private static final String TABLE_NAME="users";
    private static final String KEY_NAME="name";
    private static final String KEY_EMAIL="email";
    private static final String KEY_PASSWORD="password";
    private static final int KEY_Count=0;

    public DBLogin(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_EMAIL + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_PASSWORD + " TEXT" + ")";

        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(User user) {
        long result = -1;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues cv = new ContentValues();

            cv.put(KEY_EMAIL, user.getEmail());
            cv.put(KEY_NAME, user.getName());
            cv.put(KEY_PASSWORD, user.getPassword());

            result = db.insert(TABLE_NAME, null, cv);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result != -1;
    }
    @SuppressLint("Range")
    public boolean isUserExists(String email, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean userExists = false;

        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{KEY_EMAIL, KEY_PASSWORD},
                KEY_EMAIL + "=?",
                new String[]{email},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            String storedEmail = cursor.getString(cursor.getColumnIndex(KEY_EMAIL));
            String storedPass = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));

            if (storedPass.equals(pass) && storedEmail.equals(email)) {
                userExists = true;
            }

            cursor.close();
        }

        return userExists;
    }




    public void logAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                String email = cursor.getString(1);
                String name = cursor.getString(2);
                String password = cursor.getString(3);

                Log.d("DBLogin",   "Email: " + email + ", Name: " + name + ", Password: " + password);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }


    public void clearAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null); // This deletes all rows
        db.close();
    }
}


