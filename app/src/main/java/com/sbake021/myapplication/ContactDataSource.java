package com.sbake021.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ContactDataSource {
    private SQLiteDatabase database;
    private final DBContactListHelper dbHelper;

    public ContactDataSource(Context context) {
        dbHelper = new DBContactListHelper(context);
    }

    public void open() throws SQLException {
        if (database == null || !database.isOpen()) {
            database = dbHelper.getWritableDatabase();
        }
    }

    public void close() {
        if (database != null && database.isOpen()) {
            dbHelper.close();
            database = null;
        }
    }

    public boolean insertContact(Contact contact) {
        try {
            ContentValues values = new ContentValues();
            values.put(DBContactListHelper.COLUMN_NAME, contact.getName());
            values.put(DBContactListHelper.COLUMN_ADDRESS, contact.getAddress());
            values.put(DBContactListHelper.COLUMN_CITY, contact.getCity());
            values.put(DBContactListHelper.COLUMN_STATE, contact.getState());
            values.put(DBContactListHelper.COLUMN_ZIP, contact.getZipCode());
            values.put(DBContactListHelper.COLUMN_PHONE, contact.getPhone());
            values.put(DBContactListHelper.COLUMN_EMAIL, contact.getEmail());
            values.put(DBContactListHelper.COLUMN_BIRTH, contact.getBirthday());
            values.put(DBContactListHelper.COLUMN_LATITUDE, contact.getLatitude());
            values.put(DBContactListHelper.COLUMN_LONGITUDE, contact.getLongitude());

            return database.insert(DBContactListHelper.TABLE_CONTACTS, null, values) > 0;
        } catch (Exception e) {
            Log.e("ContactDataSource", "Error inserting contact", e);
            return false;
        }
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        Cursor cursor = null;

        try {
            open();
            cursor = database.query(
                    DBContactListHelper.TABLE_CONTACTS,
                    null,
                    null,
                    null,
                    null,
                    null,
                    DBContactListHelper.COLUMN_NAME + " ASC"
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    contacts.add(cursorToContact(cursor));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e("ContactDataSource", "Error fetching contacts", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            close();
        }
        return contacts;
    }

    @SuppressLint("Range")
    private Contact cursorToContact(Cursor cursor) {
        Contact contact = new Contact();
        contact.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DBContactListHelper.COLUMN_ID)));
        contact.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBContactListHelper.COLUMN_NAME)));
        contact.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(DBContactListHelper.COLUMN_ADDRESS)));
        contact.setCity(cursor.getString(cursor.getColumnIndexOrThrow(DBContactListHelper.COLUMN_CITY)));
        contact.setState(cursor.getString(cursor.getColumnIndexOrThrow(DBContactListHelper.COLUMN_STATE)));
        contact.setZipCode(cursor.getString(cursor.getColumnIndexOrThrow(DBContactListHelper.COLUMN_ZIP)));
        contact.setPhone(cursor.getString(cursor.getColumnIndexOrThrow(DBContactListHelper.COLUMN_PHONE)));
        contact.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DBContactListHelper.COLUMN_EMAIL)));
        contact.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(DBContactListHelper.COLUMN_LATITUDE)));
        contact.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(DBContactListHelper.COLUMN_LONGITUDE)));
        contact.setBirthday(cursor.getString(cursor.getColumnIndexOrThrow(DBContactListHelper.COLUMN_BIRTH)));
        return contact;
    }

    public boolean deleteContact(long contactId) {
        try {
            return database.delete(
                    DBContactListHelper.TABLE_CONTACTS,
                    DBContactListHelper.COLUMN_ID + "=?",
                    new String[]{String.valueOf(contactId)}
            ) > 0;
        } catch (Exception e) {
            Log.e("ContactDataSource", "Error deleting contact", e);
            return false;
        }
    }
}
