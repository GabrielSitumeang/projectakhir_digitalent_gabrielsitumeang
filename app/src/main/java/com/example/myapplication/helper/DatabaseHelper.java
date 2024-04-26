package com.example.myapplication.helper; // tambahkan package statement

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Nama database
    private static final String DATABASE_NAME = "Projectakhir.db";
    private static final int DATABASE_VERSION = 1; // tambahkan konstanta DATABASE_VERSION

    // Tabel dan kolom untuk Database 1
    private static final String USERS_TABLE = "users";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    // Tabel dan kolom untuk Database 2
    private static final String WISATA_TABLE = "wisata";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LOKASI = "lokasi";
    private static final String COLUMN_HARGATIKET = "hargaTiket";
    private static final String COLUMN_DESKRIPSI = "deskripsi";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method onCreate untuk membuat database dan tabel
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Membuat tabel untuk Database 1
        String CREATE_USERS_TABLE = "CREATE TABLE " + USERS_TABLE +
                "(" +
                COLUMN_EMAIL + " TEXT PRIMARY KEY," +
                COLUMN_PASSWORD + " TEXT" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Membuat tabel untuk Database 2
        final String SQL_CREATE_WISATA_TABLE = "CREATE TABLE " + WISATA_TABLE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_LOKASI + " TEXT NOT NULL, " +
                COLUMN_HARGATIKET + " TEXT NOT NULL, " +
                COLUMN_DESKRIPSI + " TEXT NOT NULL " +
                ")";
        db.execSQL(SQL_CREATE_WISATA_TABLE);
    }

    // Method onUpgrade untuk upgrade database jika diperlukan
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop tabel lama jika ada
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WISATA_TABLE);
        // Membuat tabel baru
        onCreate(db);
    }

    // Metode untuk Database 1
    public boolean insertUserData(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        long result = db.insert(USERS_TABLE, null, contentValues);
        return result != -1;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + COLUMN_EMAIL + "=?", new String[]{email});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USERS_TABLE + " WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?", new String[]{email, password});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Metode untuk Database 2
    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + WISATA_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put(COLUMN_ID, cursor.getString(0));
                map.put(COLUMN_NAME, cursor.getString(1));
                map.put(COLUMN_LOKASI, cursor.getString(2));
                map.put(COLUMN_HARGATIKET, cursor.getString(3));
                map.put(COLUMN_DESKRIPSI, cursor.getString(4));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return wordList;
    }

    public void insertData(String name, String lokasi, String hargaTiket, String deskripsi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_LOKASI, lokasi);
        contentValues.put(COLUMN_HARGATIKET, hargaTiket);
        contentValues.put(COLUMN_DESKRIPSI, deskripsi);
        db.insert(WISATA_TABLE, null, contentValues);
        db.close();
    }

    public void updateData(int id, String name, String lokasi, String hargaTiket, String deskripsi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_LOKASI, lokasi);
        contentValues.put(COLUMN_HARGATIKET, hargaTiket);
        contentValues.put(COLUMN_DESKRIPSI, deskripsi);
        db.update(WISATA_TABLE, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteData(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(WISATA_TABLE, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}
