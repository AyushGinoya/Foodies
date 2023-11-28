package com.example.foodies.userDatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.foodies.tabs.CartModel;

import java.util.ArrayList;
import java.util.HashMap;

public class DBLogin extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DB_NAME="MyCanteenApp";

    //Table 1
    private static final String TABLE_NAME="users";
    private static final String KEY_NAME="name";
    private static final String KEY_EMAIL="email";  // Primary Key
    private static final String KEY_PASSWORD="password";

    //Table 2

    private static final String TABLE_NAME2 = "users_details";
    private static final String KEY_STUDENT_ID = "student_id";
    private static final String KEY_EMAIL_DETAILS = "email";// Primary Key
    private static final String KEY_NUMBER = "phone_number";
    private static final String KEY_DOB = "dob";

    // Table 3
    private static final String TABLE_NAME3="product";
    private static final String KEY_FOOD_NAME="food_name"; // Primary Key
    private static final String KEY_FOOD_PRICE = "price";

    //Table 4
    private static final String TABLE_NAME4 = "cart_products";
    private static final String KEY_ID = "id"; // Primary Key
    private static final String KEY_CEMAIL = "email"; // Foreign Key
    private static final String KEY_F_NAME = "food";
    private static final String KEY_F_PRICE = "f_price";
    private static final String KEY_IMAGE = "img";
    private static final String KEY_QUANTITY = "quantity";

    //Table - 5
    private static final String TABLE_NAME5 = "profile";
    private static final String KEY_P_EMAIL = "email";
    private static final String KEY_P_IMAGE = "image";


    public DBLogin(Context context) {
        super(context, DB_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + KEY_EMAIL + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_PASSWORD + " TEXT" + ")";

        String CREATE_USERS_DETAILS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + " ( " +
                KEY_STUDENT_ID + " TEXT PRIMARY KEY, " +
                KEY_EMAIL_DETAILS + " TEXT, " +
                KEY_NUMBER + " TEXT, " +
                KEY_DOB + " TEXT )";


        String CREATE_PRODUCTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME3 + " ( "+
                KEY_FOOD_NAME + " TEXT PRIMARY KEY, " +
                KEY_FOOD_PRICE + " TEXT )";

        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_NAME4 + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_EMAIL + " TEXT," +
                KEY_F_NAME + " TEXT," +
                KEY_F_PRICE + " REAL," +
                KEY_IMAGE + " BLOB," +
                KEY_QUANTITY + " INTEGER," +
                "FOREIGN KEY (" + KEY_EMAIL + ") REFERENCES " + TABLE_NAME + "(" + KEY_EMAIL + ")" +
                ")";

        String CREATE_PROFILE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME5 + " ( " +
                KEY_P_EMAIL + " TEXT PRIMARY KEY, " +
                KEY_P_IMAGE + " BLOB )";


        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_USERS_DETAILS_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_CART_TABLE);
        db.execSQL(CREATE_PROFILE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME5);
            onCreate(db);
    }

    String email;


    public boolean addUser(User user) {
        long result = -1;
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues cv = new ContentValues();

            email = user.getEmail();
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
    public void saveDetails(String email, String studentId, String num, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        Cursor cursor = db.query(TABLE_NAME2, new String[]{KEY_EMAIL_DETAILS}, KEY_EMAIL_DETAILS + "=?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return;
        }

        cv.put(KEY_EMAIL_DETAILS, email);
        cv.put(KEY_STUDENT_ID, studentId);
        cv.put(KEY_NUMBER, num);
        cv.put(KEY_DOB, dob);

        try {
            long rowId = db.insert(TABLE_NAME2, null, cv);
            if (rowId != -1) {
                Log.d("TABLE VALUE", "Details added with ID = " + rowId);
            } else {
                Log.d("TABLE VALUE", "Failed to add details");
            }
        } catch (SQLException e) {
            Log.e("TABLE ERROR", "Error inserting data: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    public HashMap<String, String> retrieveDetails(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        HashMap<String, String> detailsMap = new HashMap<>();

        Cursor cursor = db.query(TABLE_NAME2, null, KEY_EMAIL_DETAILS + "=?", new String[]{email}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String[] columnNames = cursor.getColumnNames();

            for (String columnName : columnNames) {
                int columnIndex = cursor.getColumnIndex(columnName);
                String columnValue = cursor.getString(columnIndex);
                detailsMap.put(columnName, columnValue);
            }
            cursor.close();
        }

        db.close();
        return detailsMap;
    }

    public void updateDetails(String email, String studentId, String num,String newEmail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_STUDENT_ID, studentId);
        cv.put(KEY_NUMBER, num);
        cv.put(KEY_EMAIL_DETAILS, newEmail);

        try {
            int affectedRows = db.update(TABLE_NAME2, cv, KEY_EMAIL_DETAILS + "=?", new String[]{email});
            if (affectedRows > 0) {
                Log.d("TABLE VALUE", "Details updated for email: " + email);
            } else {
                Log.d("TABLE VALUE", "Failed to update details");
            }
        } catch (SQLException e) {
            Log.e("TABLE ERROR", "Error updating data: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    public void deleteTable2() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
    }

    public boolean isEmailExists(String email1) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean exists = false;
        Cursor cursor = db.query(TABLE_NAME2, new String[]{KEY_EMAIL_DETAILS}, KEY_EMAIL_DETAILS + "=?", new String[]{email1}, null, null, null);

        if (cursor != null) {
            exists = cursor.getCount() > 0;
            cursor.close();
        }
        return exists;
    }


    // Table - 3
    public void addProduct(){
        String[] foodName = {"Chole Bhature", "Khaman", "Puff", "Vada Pav", "Panjabi"};
        String[] foodPrice = {"50 ₹", "40 ₹", "25 ₹", "60 ₹", "45 ₹"};
        SQLiteDatabase db = this.getWritableDatabase();

        for (int i=0;i<foodName.length;i++){
            ContentValues values = new ContentValues();
            values.put(KEY_FOOD_NAME,foodName[i]);
            values.put(KEY_FOOD_PRICE,foodPrice[i]);

           long a = db.insert(TABLE_NAME3,null,values);
           Log.d("TABLE VALUE" , "Product added = " + a);
        }
    }

    public boolean isTableExists(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='" + tableName + "'", null);
        boolean exists = false;

        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            exists = count > 0;
            cursor.close();
        }

        return exists;
    }


    // Table 4
    @SuppressLint({"Range", "NotifyDataSetChanged"})
    public void addToCart(String email, String itemName, String itemPrice, byte[] itemImage) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME4 + " WHERE " +
                KEY_EMAIL + " = '" + email + "' AND " +
                KEY_F_NAME + " = '" + itemName + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int currentQuantity = cursor.getInt(cursor.getColumnIndex(KEY_QUANTITY));
            int newQuantity = currentQuantity + 1;

            ContentValues updateValues = new ContentValues();
            updateValues.put(KEY_QUANTITY, newQuantity);

            db.update(TABLE_NAME4, updateValues,
                    KEY_EMAIL + " = ? AND " + KEY_F_NAME + " = ?",
                    new String[]{email, itemName});
        } else {
            ContentValues values = new ContentValues();
            values.put(KEY_EMAIL, email);
            values.put(KEY_F_NAME, itemName);
            values.put(KEY_F_PRICE, itemPrice);
            values.put(KEY_IMAGE, itemImage);
            values.put(KEY_QUANTITY, 1);

            db.insert(TABLE_NAME4, null, values);
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
    }
    @SuppressLint("Range")
    public ArrayList<CartModel> getAllCartItems(String email) {
        ArrayList<CartModel> cartItemsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {KEY_F_NAME, KEY_F_PRICE, KEY_IMAGE, KEY_QUANTITY};
        String selection = KEY_EMAIL + " = ?";
        String[] selectionArgs = {email};

        try (Cursor cursor = db.query(TABLE_NAME4, columns, selection, selectionArgs, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String itemName = cursor.getString(cursor.getColumnIndex(KEY_F_NAME));
                    String itemPrice = cursor.getString(cursor.getColumnIndex(KEY_F_PRICE));
                    byte[] itemImage = cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE));
                    int itemQuantity = cursor.getInt(cursor.getColumnIndex(KEY_QUANTITY));

                    CartModel cartItem = new CartModel(itemImage, itemName, itemPrice, itemQuantity);
                    Log.d("Cart Item : ","OK: " + cartItem);
                    cartItemsList.add(cartItem);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DBLogin", "Error getting cart items: " + e.getMessage());
        }

        return cartItemsList;
    }

    public void removeCartItem(String email, String itemName, String itemPrice) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = KEY_EMAIL + " = ? AND " + KEY_F_NAME + " = ? AND " + KEY_F_PRICE + " = ?";
        String[] selectionArgs = {email, itemName, itemPrice};

        try {
            int deletedRows = db.delete(TABLE_NAME4, selection, selectionArgs);

            if (deletedRows > 0) {
                Log.d("DBLogin", "Item removed from cart: " + itemName);
            } else {
                Log.d("DBLogin", "Item not found in the cart: " + itemName);
            }
        } catch (Exception e) {
            Log.e("DBLogin", "Error removing item from cart: " + e.getMessage());
            e.printStackTrace();
        } finally {
            db.close();
        }
    }
    public void updateQuantity(String email, String itemName, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put(KEY_QUANTITY, quantity);

            int updatedRows = db.update(TABLE_NAME4, values,
                    KEY_EMAIL + " = ? AND " + KEY_F_NAME + " = ?",
                    new String[]{email, itemName});
            if (updatedRows > 0) {
                Log.d("DBLogin", "Quantity updated for item: " + itemName);
            } else {
                Log.d("DBLogin", "Item not found in cart while updating quantity: " + itemName);
            }
        } catch (NumberFormatException e) {
            Log.e("DBLogin", "Invalid quantity format: " + e.getMessage());
        } finally {
            db.close();
        }
    }

    //Table - 5
    public void addImageToProfile(String email, byte[] imageBytes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_P_EMAIL, email);
        values.put(KEY_P_IMAGE, imageBytes);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public Bitmap getImageFroProfile(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Bitmap bitmap = null;

        String selectQuery = "SELECT " + KEY_P_IMAGE + " FROM " + TABLE_NAME5 + " WHERE " + KEY_P_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});

        if (cursor != null && cursor.moveToFirst()) {
            byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(KEY_P_IMAGE));
            cursor.close();

            if (imageBytes != null) {
                bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            }
        }

        return bitmap;
    }

}