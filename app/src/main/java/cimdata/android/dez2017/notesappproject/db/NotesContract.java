package cimdata.android.dez2017.notesappproject.db;

import android.provider.BaseColumns;

public class NotesContract {

    public static final String CREATE_NOTES_TABLE =
            "CREATE TABLE IF NOT EXISTS " + NotesEntry.TABLE_NAME +
            "(" +
                NotesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NotesEntry.COLUMN_TITLE_NAME + " TEXT, " +
                NotesEntry.COLUMN_BODY_NAME + " TEXT, " +
                NotesEntry.COLUMN_DUEDATE_NAME + " TEXT " +
            ")";

    public static final class NotesEntry implements BaseColumns {

        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_TITLE_NAME = "title";
        public static final String COLUMN_BODY_NAME = "body";
        public static final String COLUMN_DUEDATE_NAME = "duedate";
        // not a real column, but computed in case statement in query
        public static final String COLUMN_IS_DUE = "is_due";

    }

}
