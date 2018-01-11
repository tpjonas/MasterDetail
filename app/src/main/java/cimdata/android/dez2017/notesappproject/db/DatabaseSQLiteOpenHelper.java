package cimdata.android.dez2017.notesappproject.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int VERSION_NUMBER = 1;

    DatabaseSQLiteOpenHelper(Context context) {

        super(context, DATABASE_NAME, null, VERSION_NUMBER);

    }

    // Hier erstellen wir die Tabelle
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NotesContract.CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
