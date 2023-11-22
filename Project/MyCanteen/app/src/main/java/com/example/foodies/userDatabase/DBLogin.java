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

    //Table 1
    private static final String TABLE_NAME="users";
    private static final String KEY_NAME="name";
    private static final String KEY_EMAIL="email";
    private static final String KEY_PASSWORD="password";

    //Table 2

    private static final String TABLE_NAME2="users_details";
    private static final String KEY_FNAME="first_name";
    private static final String KEY_LNAME="last_name";
    private static final String KEY_EMAIL_DETAILS="email";
    private static final String KEY_NUMBER="phone_number";
    private static final String KEY_DOB="dob";
    private static final String KEY_ADDRESS = "address";


    public DBLogin(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + KEY_EMAIL + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_PASSWORD + " TEXT" + ")";

        String CREATE_USERS_DETAILS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + " ( " +
                KEY_EMAIL_DETAILS + " TEXT PRIMARY KEY, " +
                KEY_FNAME + " TEXT, " +
                KEY_LNAME + " TEXT, " +
                KEY_NUMBER + " TEXT, " +
                KEY_ADDRESS + " TEXT, " +
                KEY_DOB + " TEXT )";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_USERS_DETAILS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
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

    //Table - 2
    public void saveDetails(String email, String fn, String ln, String add, String num, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (!isEmailExists(email)) {
            ContentValues cv = new ContentValues();

            cv.put(KEY_EMAIL_DETAILS, email);
            cv.put(KEY_FNAME, fn);
            cv.put(KEY_LNAME, ln);
            cv.put(KEY_ADDRESS, add);
            cv.put(KEY_NUMBER, num);
            cv.put(KEY_DOB, dob);

            db.insert(TABLE_NAME2, null, cv);
        } else {
            Log.d("SaveDetails", "Email already exists");
        }
    }

    @SuppressLint("Range")
    public void printTableDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME2, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL_DETAILS));
                    String firstName = cursor.getString(cursor.getColumnIndex(KEY_FNAME));
                    String lastName = cursor.getString(cursor.getColumnIndex(KEY_LNAME));
                    String address = cursor.getString(cursor.getColumnIndex(KEY_ADDRESS));
                    String phoneNumber = cursor.getString(cursor.getColumnIndex(KEY_NUMBER));
                    String dob = cursor.getString(cursor.getColumnIndex(KEY_DOB));

                    Log.d("TableDetails", "Email: " + email +
                            ", First Name: " + firstName +
                            ", Last Name: " + lastName +
                            ", Address: " + address +
                            ", Phone Number: " + phoneNumber +
                            ", DOB: " + dob);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void deleteTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
    }

    public boolean isEmailExists(String email1) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean exists = false;


        Cursor cursor = db.query(TABLE_NAME2,new String[]{KEY_EMAIL_DETAILS},KEY_EMAIL_DETAILS + "=?",new String[]{email1},null,null,null);

        if (cursor != null) {
            exists = cursor.getCount() > 0;
            cursor.close();
        }

        return exists;
    }

}


