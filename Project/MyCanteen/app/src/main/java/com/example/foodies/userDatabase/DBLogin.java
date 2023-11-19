package com.example.foodies.userDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBLogin extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DB_NAME="MyCanteenApp";
    private static final String TABLE_NAME="users";
    private static final String KEY_NAME="name";
    private static final String KEY_EMAIL="email";
    private static final String KEY_PASSWORD="password";
    private static final String KEY_ID="id";


    public DBLogin(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_EMAIL + " TEXT,"
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


    public boolean isUserExists(String email, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean userExists = false;

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            int passwordColumnIndex = cursor.getColumnIndex(KEY_PASSWORD);

            if (passwordColumnIndex != -1) {
                String storedPassword = cursor.getString(passwordColumnIndex);

                if (storedPassword.equals(pass)) {
                    userExists = true;
                }
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        return userExists;
    }



    public void clearAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null); // This deletes all rows
        db.close();
    }
}


