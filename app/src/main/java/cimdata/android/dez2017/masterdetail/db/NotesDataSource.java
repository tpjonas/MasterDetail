package cimdata.android.dez2017.masterdetail.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Jonas on 14.12.2017.
 */

public class NotesDataSource {

    private static final String TAG = "*** NotesDataSource";

    private SQLiteDatabase database;
    private DatabaseSQLiteOpenHelper dbHelper;

    public NotesDataSource(Context context) {

        this.dbHelper = new DatabaseSQLiteOpenHelper(context);
    }

    public void open() {

        // Hier holen wir uns die Datenbank aus unserem Helper.
        this.database = dbHelper.getWritableDatabase();

        Log.d(TAG, "DB open!");
    }

    public void close() {
        database.close();

        Log.d(TAG, "DB close!");
    }

    public Cursor fetchAllNotes() {

        Cursor cursor = database.rawQuery(
                "SELECT " +
                        NotesContract.NotesEntry._ID + " || '.' AS " + NotesContract.NotesEntry._ID + ", " +
                        NotesContract.NotesEntry.COLUMN_TITLE_NAME + ", " + NotesContract.NotesEntry.COLUMN_BODY_NAME +
                        " FROM " +
                        NotesContract.NotesEntry.TABLE_NAME,
                null);


        return cursor;

    }

    public Cursor fetchNotesTitleContains(String startChar) {

        Cursor cursor = database.query(
                NotesContract.NotesEntry.TABLE_NAME,
                new String[] {
                        NotesContract.NotesEntry._ID,
                        NotesContract.NotesEntry.COLUMN_TITLE_NAME,
                        NotesContract.NotesEntry.COLUMN_BODY_NAME
                },
                NotesContract.NotesEntry.COLUMN_TITLE_NAME + " LIKE ? ",
                new String[] {
                        "%" + startChar + "%"
                },
                null,
                null,
                null,
                null
        );

        return cursor;

    }

    public ContentValues fetchNote(int id) {

        ContentValues row = new ContentValues();

        Cursor cursor = database.rawQuery(
                "SELECT " +
                        NotesContract.NotesEntry._ID + " || '.' AS " + NotesContract.NotesEntry._ID + ", " +
                        NotesContract.NotesEntry.COLUMN_TITLE_NAME + ", " + NotesContract.NotesEntry.COLUMN_BODY_NAME +
                        " FROM " + NotesContract.NotesEntry.TABLE_NAME +
                        " WHERE _id = ?",
                new String[] {String.valueOf(id)});


        if (cursor.moveToNext()) {
            row.put("title", cursor.getString(1));
            row.put("body", cursor.getString(2));
        }
        cursor.close();
        return row;

    }

    public void insertNote(String title, String body) {

        ContentValues values = new ContentValues();

        values.put(NotesContract.NotesEntry.COLUMN_TITLE_NAME, title);
        values.put(NotesContract.NotesEntry.COLUMN_BODY_NAME, body);

        database.insert(
                NotesContract.NotesEntry.TABLE_NAME,null, values
        );

    }

}

