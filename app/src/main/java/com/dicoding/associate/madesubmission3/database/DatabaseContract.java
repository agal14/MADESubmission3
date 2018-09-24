package com.dicoding.associate.madesubmission3.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_ENGLISHINDO = "table_englishindo";
    static String TABLE_INDOENGLISH  = "table_indoenglish";

    static final class KamusColumns implements BaseColumns {

        // Mahasiswa nama
        static String WORDS = "words";
        // Mahasiswa nim
        static String TRANSLATE = "translate";

    }
}
