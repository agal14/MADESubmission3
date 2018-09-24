package com.dicoding.associate.madesubmission3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.dicoding.associate.madesubmission3.model.KamusModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dicoding.associate.madesubmission3.database.DatabaseContract.KamusColumns.TRANSLATE;
import static com.dicoding.associate.madesubmission3.database.DatabaseContract.KamusColumns.WORDS;
import static com.dicoding.associate.madesubmission3.database.DatabaseContract.TABLE_ENGLISHINDO;
import static com.dicoding.associate.madesubmission3.database.DatabaseContract.TABLE_INDOENGLISH;

public class KamusHelper {
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dataBaseHelper.close();
    }

    /**
     * Gunakan method ini untuk cari kata pada berdasarkan kata yang dimasukkan
     *
     * @param kata kata yang dicari
     * @return list translate
     */
    public ArrayList<KamusModel> getDataByKata(String kata, String tabel) {
        Cursor cursor = database.query(tabel, null, WORDS + " LIKE ?", new String[]{kata}, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModel;
        if (cursor.getCount() > 0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORDS)));
                kamusModel.setTranslate(cursor.getString(cursor.getColumnIndexOrThrow(TRANSLATE)));
                arrayList.add(kamusModel);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    /**
     * Gunakan method ini untuk mendapatkan semua data kamus (English-Indo atau Indo-English)
     *
     * @return hasil query kamus model di dalam arraylist
     */
    public ArrayList<KamusModel> getAllData(String tabel) {
        Cursor cursor = database.query(tabel, null, null, null, null, null, _ID + " ASC", null);
        cursor.moveToFirst();
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        KamusModel kamusModel;
        if (cursor.getCount() > 0) {
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusModel.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORDS)));
                kamusModel.setTranslate(cursor.getString(cursor.getColumnIndexOrThrow(TRANSLATE)));
                arrayList.add(kamusModel);
                cursor.moveToNext();


            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    /**
     * Gunakan method ini untuk query insert
     *
     * @param kamusModel inputan model mahasiswa
     * @return id dari data kamus yang baru saja dimasukkan
     */
    public long insertEnglishIndo(KamusModel kamusModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(WORDS, kamusModel.getWord());
        initialValues.put(TRANSLATE, kamusModel.getTranslate());
        return database.insert(TABLE_ENGLISHINDO, null, initialValues);
    }

    /**
     * Gunakan method ini untuk query insert
     *
     * @param kamusModel inputan model mahasiswa
     * @return id dari data kamus yang baru saja dimasukkan
     */
    public long insertIndoEnglish(KamusModel kamusModel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(WORDS, kamusModel.getWord());
        initialValues.put(TRANSLATE, kamusModel.getTranslate());
        return database.insert(TABLE_INDOENGLISH, null, initialValues);
    }

    /**
     * Gunakan method ini untuk memulai sesi query transaction
     */
    public void beginTransaction() {
        database.beginTransaction();
    }

    /**
     * Gunakan method ini jika query transaction berhasil, jika error jangan panggil method ini
     */
    public void setTransactionSuccess() {
        database.setTransactionSuccessful();
    }

    /**
     * Gunakan method ini untuk mengakhiri sesi query transaction
     */
    public void endTransaction() {
        database.endTransaction();
    }

    /**
     * Gunakan method ini untuk query insert di dalam transaction
     *
     * @param kamusModel inputan model kamus
     */
    public void insertEnglishIndoTransaction(KamusModel kamusModel) {
        String sql = "INSERT INTO " + TABLE_ENGLISHINDO + " (" + WORDS + ", " + TRANSLATE
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamusModel.getWord());
        stmt.bindString(2, kamusModel.getTranslate());
        stmt.execute();
        stmt.clearBindings();

    }

    /**
     * Gunakan method ini untuk query insert di dalam transaction
     *
     * @param kamusModel inputan model kamus
     */
    public void insertIndoEnglishTransaction(KamusModel kamusModel) {
        String sql = "INSERT INTO " + TABLE_INDOENGLISH + " (" + WORDS + ", " + TRANSLATE
                + ") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamusModel.getWord());
        stmt.bindString(2, kamusModel.getTranslate());
        stmt.execute();
        stmt.clearBindings();

    }

    /**
     * Gunakan method ini untuk update data mahasiswa yang ada di dalam database
     *
     * @param kamusModel inputan model kamus English Indo
     * @return jumlah mahasiswa yang ter-update
     */
    public int updateEnglishIndo(KamusModel kamusModel) {
        ContentValues args = new ContentValues();
        args.put(WORDS, kamusModel.getWord());
        args.put(TRANSLATE, kamusModel.getTranslate());
        return database.update(TABLE_ENGLISHINDO, args, _ID + "= '" + kamusModel.getId() + "'", null);
    }

    /**
     * Gunakan method ini untuk update data mahasiswa yang ada di dalam database
     *
     * @param kamusModel inputan model kamus
     * @return jumlah mahasiswa yang ter-update
     */
    public int updateIndoEnglish(KamusModel kamusModel) {
        ContentValues args = new ContentValues();
        args.put(WORDS, kamusModel.getWord());
        args.put(TRANSLATE, kamusModel.getTranslate());
        return database.update(TABLE_INDOENGLISH, args, _ID + "= '" + kamusModel.getId() + "'", null);
    }


    /**
     * Gunakan method ini untuk menghapus data mahasiswa yang ada di dalam database
     *
     * @param id id kamus yang akan di hapus
     * @return jumlah kamus yang di-delete
     */
    public int delete(int id, String tabel) {

        return database.delete(tabel, _ID + " = '" + id + "'", null);
    }


}
