package com.example.foodies.userDatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.foodies.tabs.CartModel;

import java.util.ArrayList;

public class DBLogin extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DB_NAME="MyCanteenApp";

    //Table 1
    private static final String TABLE_NAME="users";
    private static final String KEY_NAME="name";
    private static final String KEY_EMAIL="email";  // Primary Key
    private static final String KEY_PASSWORD="password";

    //Table 2

    private static final String TABLE_NAME2="users_details";
    private static final String KEY_FNAME="first_name";
    private static final String KEY_LNAME="last_name";
    private static final String KEY_EMAIL_DETAILS="email"; // Primary Key
    private static final String KEY_NUMBER="phone_number";
    private static final String KEY_DOB="dob";
    private static final String KEY_ADDRESS = "address";

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
                KEY_EMAIL_DETAILS + " TEXT, " +
                KEY_FNAME + " TEXT, " +
                KEY_LNAME + " TEXT, " +
                KEY_NUMBER + " TEXT  PRIMARY KEY, " +
                KEY_ADDRESS + " TEXT, " +
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


        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_USERS_DETAILS_TABLE);
        db.execSQL(CREATE_PRODUCTS_TABLE);
        db.execSQL(CREATE_CART_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
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
        ContentValues cv = new ContentValues();
        Cursor cursor = db.query(TABLE_NAME2, new String[]{KEY_EMAIL_DETAILS}, KEY_EMAIL_DETAILS + "=?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return; 
        }

        cv.put(KEY_EMAIL_DETAILS, email);
        cv.put(KEY_FNAME, fn);
        cv.put(KEY_LNAME, ln);
        cv.put(KEY_ADDRESS, add);
        cv.put(KEY_NUMBER, num);
        cv.put(KEY_DOB, dob);

        try {
            long rowId = db.insert(TABLE_NAME2, null, cv);
            if (rowId != -1) {
                Log.d("TABLE VALUE", "Product added with ID = " + rowId);
            } else {
                Log.d("TABLE VALUE", "Failed to add product");
            }
        } catch (SQLException e) {
            Log.e("TABLE ERROR", "Error inserting data: " + e.getMessage());
        } finally {
            db.close(); // Close the database connection after use
        }
    }


   @SuppressLint("Range")
    public void printTableDetails2() {
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME2, null)) {
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
        }
    }

    public void deleteTable2() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
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
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, email);
        values.put(KEY_F_NAME, itemName);
        values.put(KEY_F_PRICE, itemPrice);
        values.put(KEY_IMAGE, itemImage);
        values.put(KEY_QUANTITY, 1);

        db.insert(TABLE_NAME4, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public ArrayList<CartModel> getAllCartItems(String email) {
        ArrayList<CartModel> cartItemsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {KEY_F_NAME, KEY_F_PRICE, KEY_IMAGE, KEY_QUANTITY};
        String selection = KEY_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_NAME4, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String itemName = cursor.getString(cursor.getColumnIndex(KEY_F_NAME));
                String itemPrice = cursor.getString(cursor.getColumnIndex(KEY_F_PRICE));
                byte[] itemImage = cursor.getBlob(cursor.getColumnIndex(KEY_IMAGE));
                int itemQuantity = cursor.getInt(cursor.getColumnIndex(KEY_QUANTITY));

                CartModel cartItem = new CartModel(itemImage, itemName, itemPrice, itemQuantity);
                cartItemsList.add(cartItem);
            } while (cursor.moveToNext());
            cursor.close();
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
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

}


