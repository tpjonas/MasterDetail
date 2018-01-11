package cimdata.android.dez2017.notesappproject.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.GregorianCalendar;

import cimdata.android.dez2017.notesappproject.R;
import cimdata.android.dez2017.notesappproject.db.NotesContract;
import cimdata.android.dez2017.notesappproject.db.NotesDataSource;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    EditText titleText;
    EditText bodyText;
    Button saveButton;
    DatePicker datePicker;

    private NotesDataSource dataSource;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        titleText = findViewById(R.id.id_add_text_title);
        bodyText = findViewById(R.id.id_add_text_body);
        saveButton = findViewById(R.id.id_add_button_save);
        datePicker = findViewById(R.id.id_datepicker);
        saveButton.setOnClickListener(this);

        // get data
        Intent intent = getIntent();
        id = intent.getIntExtra(DetailActivity.EXTRA_INT_POSITION, -1);

        dataSource = new NotesDataSource(this);

        // set date picker's minDate to today
        datePicker.setMinDate(System.currentTimeMillis() - 1000);

    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();

        this.setTitle(getString(R.string.add_note));

        // if in edit mode, preset values
        if (id != -1) {
            ContentValues row = dataSource.fetchNote(id);
            this.setTitle(getString(R.string.edit_note));
            titleText.setText(row.getAsString(NotesContract.NotesEntry.COLUMN_TITLE_NAME));
            bodyText.setText(row.getAsString(NotesContract.NotesEntry.COLUMN_BODY_NAME));
            datePicker.updateDate(
                    row.getAsInteger(DetailActivity.DB_YEAR),
                    row.getAsInteger(DetailActivity.DB_MONTH),
                    row.getAsInteger(DetailActivity.DB_DAY)
            );
        }

    }

    @Override
    public void onClick(View v) {

        String title = String.valueOf(titleText.getText());
        String body = String.valueOf(bodyText.getText());

        if (title.equals("") || (body.equals(""))) {
            Toast.makeText(this, "Title and body must not be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        // get due date
        GregorianCalendar gregorianCalendar = new GregorianCalendar(
                datePicker.getYear(),
                //2016,
                datePicker.getMonth(),
                datePicker.getDayOfMonth()
        );
        Date dueDate = gregorianCalendar.getTime();

        // save data
        if(id != -1) {
            dataSource.updateNote(id, title, body, dueDate);
        } else {
            dataSource.insertNote(title, body, dueDate);
        }
        this.finish();
        overridePendingTransition(0, 0);

    }
}
