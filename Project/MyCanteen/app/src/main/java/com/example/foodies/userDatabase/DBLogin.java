package com.example.foodies.userDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(KEY_EMAIL,user.getEmail());
        cv.put(KEY_NAME,user.getName());
        cv.put(KEY_PASSWORD,user.getPassword());

        db.insert(TABLE_NAME,null,cv);
        db.close();
    }

    public boolean isUserExists(String email,String pass) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        boolean userExists = cursor.moveToFirst();

        cursor.close();
        return userExists;
    }

//    public void clearAllData() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME, null, null); // This deletes all rows
//        db.close();
//    }
}
