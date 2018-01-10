package cimdata.android.dez2017.masterdetail.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import cimdata.android.dez2017.masterdetail.R;
import cimdata.android.dez2017.masterdetail.db.NotesContract;
import cimdata.android.dez2017.masterdetail.db.NotesDataSource;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    EditText titleText;
    EditText bodyText;
    Button saveButton;
    DatePicker datePicker;

    private NotesDataSource dataSource;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        titleText = findViewById(R.id.id_add_text_title);
        bodyText = findViewById(R.id.id_add_text_body);
        saveButton = findViewById(R.id.id_add_button_save);
        datePicker = findViewById(R.id.id_datepicker);
        saveButton.setOnClickListener(this);

        // Daten holen
        Intent intent = getIntent();
        position = intent.getIntExtra(DetailActivity.EXTRA_INT_POSITION, -1);

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

        if (position != -1) {
            ContentValues row = dataSource.fetchNote(position);
            titleText.setText(row.getAsString(NotesContract.NotesEntry.COLUMN_TITLE_NAME));
            bodyText.setText(row.getAsString(NotesContract.NotesEntry.COLUMN_BODY_NAME));
            datePicker.updateDate(
                    row.getAsInteger("year"),
                    row.getAsInteger("month"),
                    row.getAsInteger("day")
            );
            Toast.makeText(this, row.getAsInteger("year") + "/" + row.getAsInteger("month") + "/" + row.getAsInteger("day") + "/" , Toast.LENGTH_SHORT).show();
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

        // Get date
        GregorianCalendar gregorianCalendar = new GregorianCalendar(
                datePicker.getYear(),
                //2016,
                datePicker.getMonth(),
                datePicker.getDayOfMonth()
        );
        Date dueDate = gregorianCalendar.getTime();

        /*
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormatter.format(dueDate);

        Toast.makeText(this, strDate, Toast.LENGTH_SHORT).show();
        */

        if(position != -1) {
            Toast.makeText(this, "UPDATE", Toast.LENGTH_SHORT).show();
            dataSource.updateNote(position, title, body, dueDate);
        } else {
            Toast.makeText(this, "INSERT", Toast.LENGTH_SHORT).show();
            dataSource.insertNote(title, body, dueDate);
        }

        this.finish();
        /*
        TODO: @Andreas, bei einem Update würde ich mit this.finish() zur vorherigen Activity (DetailActivity)
        TODO: ich möchte aber in die davor (MasterActivity). Das geht mit einem Intent, erscheint mir aber wenig
        TODO: elegant. Kann ich auch die vorherige finish()en?
        Intent intent = new Intent(this, MasterActivity.class);
        startActivity(intent);
        */
        overridePendingTransition(0, 0);

    }
}
