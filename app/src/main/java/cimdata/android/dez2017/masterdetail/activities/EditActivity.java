package cimdata.android.dez2017.masterdetail.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cimdata.android.dez2017.masterdetail.R;
import cimdata.android.dez2017.masterdetail.db.NotesContract;
import cimdata.android.dez2017.masterdetail.db.NotesDataSource;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    EditText titleText;
    EditText bodyText;
    Button saveButton;

    private NotesDataSource dataSource;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        titleText = findViewById(R.id.id_add_text_title);
        bodyText = findViewById(R.id.id_add_text_body);
        saveButton = findViewById(R.id.id_add_button_save);
        saveButton.setOnClickListener(this);

        // Daten holen
        Intent intent = getIntent();
        position = intent.getIntExtra(DetailActivity.EXTRA_INT_POSITION, -1);

        dataSource = new NotesDataSource(this);

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

        if(position != -1) {
            Toast.makeText(this, "UPDATE", Toast.LENGTH_SHORT).show();
            dataSource.updateNote(position, title, body);
        } else {
            Toast.makeText(this, "INSERT", Toast.LENGTH_SHORT).show();
            dataSource.insertNote(title, body);
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
