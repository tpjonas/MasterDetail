package cimdata.android.dez2017.notesappproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        return database.rawQuery(
                "SELECT " +
                        NotesContract.NotesEntry._ID + " || '.' AS " + NotesContract.NotesEntry._ID
                        + ", " + NotesContract.NotesEntry.COLUMN_TITLE_NAME
                        + ", " + NotesContract.NotesEntry.COLUMN_BODY_NAME
                        + ", CASE"
                        +  " WHEN date(" + NotesContract.NotesEntry.COLUMN_DUEDATE_NAME + ") <= date('now')"
                        +  " THEN 1 ELSE 0"
                        +  " END"
                        + " AS " + NotesContract.NotesEntry.COLUMN_IS_DUE
                        + " FROM "
                        + NotesContract.NotesEntry.TABLE_NAME
                        + " ORDER BY " + NotesContract.NotesEntry.COLUMN_DUEDATE_NAME + " ASC",
                null);
    }

    public Cursor searchNotes(String needle) {

        return database.rawQuery(
                "SELECT " +
                        NotesContract.NotesEntry._ID + " || '.' AS " + NotesContract.NotesEntry._ID
                        + ", " + NotesContract.NotesEntry.COLUMN_TITLE_NAME
                        + ", " + NotesContract.NotesEntry.COLUMN_BODY_NAME
                        + ", CASE"
                        +  " WHEN date(" + NotesContract.NotesEntry.COLUMN_DUEDATE_NAME + ") <= date('now')"
                        +  " THEN 1 ELSE 0"
                        +  " END"
                        + " AS " + NotesContract.NotesEntry.COLUMN_IS_DUE
                        + " FROM " + NotesContract.NotesEntry.TABLE_NAME
                        + " WHERE " + NotesContract.NotesEntry.COLUMN_TITLE_NAME + " LIKE ?"
                        +    " OR " + NotesContract.NotesEntry.COLUMN_BODY_NAME + " LIKE ?"
                        + " ORDER BY " + NotesContract.NotesEntry.COLUMN_DUEDATE_NAME + " ASC",
                new String[] {
                        "%" + needle + "%",
                        "%" + needle + "%"
                }
        );
    }

    public ContentValues fetchNote(int id) {

        ContentValues row = new ContentValues();

        Cursor cursor = database.rawQuery(
                "SELECT " +
                        NotesContract.NotesEntry._ID + " || '.' AS " + NotesContract.NotesEntry._ID
                        + ", " + NotesContract.NotesEntry.COLUMN_TITLE_NAME
                        + ", " + NotesContract.NotesEntry.COLUMN_BODY_NAME
                        + ", " + NotesContract.NotesEntry.COLUMN_DUEDATE_NAME
                        + " FROM " + NotesContract.NotesEntry.TABLE_NAME
                        + " WHERE _id = ?",
                new String[] {String.valueOf(id)});


        if (cursor.moveToNext()) {
            row.put("title", cursor.getString(1));
            row.put("body", cursor.getString(2));
            ContentValues values = string2contentValues(cursor.getString(3));
            row.put("day", values.getAsInteger("day"));
            row.put("month", values.getAsInteger("month"));
            row.put("year", values.getAsInteger("year"));
        }
        cursor.close();
        return row;

    }

    public void insertNote(String title, String body, Date duedate) {

        ContentValues values = new ContentValues();

        values.put(NotesContract.NotesEntry.COLUMN_TITLE_NAME, title);
        values.put(NotesContract.NotesEntry.COLUMN_BODY_NAME, body);
        values.put(NotesContract.NotesEntry.COLUMN_DUEDATE_NAME, date2string(duedate));

        database.insert(
                NotesContract.NotesEntry.TABLE_NAME,
                null,
                values
        );

    }

    public void updateNote(int id, String title, String body, Date duedate) {

        ContentValues values = new ContentValues();

        values.put(NotesContract.NotesEntry.COLUMN_TITLE_NAME, title);
        values.put(NotesContract.NotesEntry.COLUMN_BODY_NAME, body);
        values.put(NotesContract.NotesEntry.COLUMN_DUEDATE_NAME, date2string(duedate));

        database.update(
                NotesContract.NotesEntry.TABLE_NAME,
                values,
                NotesContract.NotesEntry._ID + "=" + id,
                null
        );

    }

    public void deleteNote(int id) {
        database.delete(
                NotesContract.NotesEntry.TABLE_NAME,
                NotesContract.NotesEntry._ID + "=?",
                new String[]{String.valueOf(id)});
    }

    private String date2string(Date date) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return dateFormatter.format(date);
    }

    private ContentValues string2contentValues(String strDate) {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormatter.parse(strDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        values.put("day", calendar.get(Calendar.DAY_OF_MONTH));
        values.put("month", calendar.get(Calendar.MONTH));
        values.put("year", calendar.get(Calendar.YEAR));

        return values;
    }

}

