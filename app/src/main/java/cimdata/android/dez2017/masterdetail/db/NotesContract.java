package cimdata.android.dez2017.masterdetail.db;



// Diese Klasse stellt die Tabellen-Informationen unserer Datenbank als
// Konstanten in statischen Unterklassen zur Verfügung.

import android.provider.BaseColumns;

public class NotesContract {

    public static final String CREATE_NOTES_TABLE =
            "CREATE TABLE IF NOT EXISTS " + NotesEntry.TABLE_NAME +
            "(" +
            NotesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            NotesEntry.COLUMN_TITLE_NAME + " TEXT, " +
            NotesEntry.COLUMN_BODY_NAME + " TEXT " +
            ")";

    public static final class NotesEntry implements BaseColumns {

        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_TITLE_NAME = "title";
        public static final String COLUMN_BODY_NAME = "body";

    }

}
