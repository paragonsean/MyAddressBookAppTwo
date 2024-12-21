package com.sbake021.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBContactListHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "new_contactlist.db";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_CONTACTS = "contacts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_ZIP = "zipcode";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_BIRTH = "birthday";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    private static final String CREATE_TABLE_CONTACTS =
            "CREATE TABLE " + TABLE_CONTACTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_ADDRESS + " TEXT NOT NULL, " +
                    COLUMN_CITY + " TEXT, " +
                    COLUMN_STATE + " TEXT, " +
                    COLUMN_ZIP + " TEXT, " +
                    COLUMN_PHONE + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_BIRTH + " TEXT, " +
                    COLUMN_LATITUDE + " REAL, " +
                    COLUMN_LONGITUDE + " REAL);";

    public DBContactListHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DBContactListHelper", "Upgrading database from version " + oldVersion + " to " + newVersion);

        if (oldVersion < 2) {
            try {
                db.execSQL("ALTER TABLE " + TABLE_CONTACTS + " ADD COLUMN " + COLUMN_LATITUDE + " REAL;");
                db.execSQL("ALTER TABLE " + TABLE_CONTACTS + " ADD COLUMN " + COLUMN_LONGITUDE + " REAL;");
            } catch (Exception e) {
                Log.e("DBContactListHelper", "Error upgrading database", e);
            }
        }
    }
}
