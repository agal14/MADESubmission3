package com.dicoding.associate.madesubmission3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.dicoding.associate.madesubmission3.database.DatabaseContract.KamusColumns.WORDS;
import static com.dicoding.associate.madesubmission3.database.DatabaseContract.KamusColumns.TRANSLATE;
import static com.dicoding.associate.madesubmission3.database.DatabaseContract.TABLE_ENGLISHINDO;
import static com.dicoding.associate.madesubmission3.database.DatabaseContract.TABLE_INDOENGLISH;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "dbkamus";

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_ENGLISHINDO = "create table " + TABLE_ENGLISHINDO +
            " (" + _ID + " integer primary key autoincrement, " +
            WORDS + " text not null, " +
            TRANSLATE + " text not null);";

    public static String CREATE_TABLE_INDOENGLISH = "create table " + TABLE_INDOENGLISH +
            " (" + _ID + " integer primary key autoincrement, " +
            WORDS + " text not null, " +
            TRANSLATE + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENGLISHINDO);
        db.execSQL(CREATE_TABLE_INDOENGLISH);
    }

    /*
    Method onUpgrade akan di panggil ketika terjadi perbedaan versi
    Gunakan method onUpgrade untuk melakukan proses migrasi data
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        Drop table tidak dianjurkan ketika proses migrasi terjadi dikarenakan data user akan hilang,
         */
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENGLISHINDO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INDOENGLISH);
        onCreate(db);
    }
}

