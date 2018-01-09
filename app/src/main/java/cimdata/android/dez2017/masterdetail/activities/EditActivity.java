package cimdata.android.dez2017.masterdetail.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        titleText = findViewById(R.id.id_add_text_title);
        bodyText = findViewById(R.id.id_add_text_body);
        saveButton = findViewById(R.id.id_add_button_save);

        saveButton.setOnClickListener(this);

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
    }

    @Override
    public void onClick(View v) {

        String title = String.valueOf(titleText.getText());
        String body = String.valueOf(bodyText.getText());

        if (title.equals("") || (body.equals(""))) {
            Toast.makeText(this, "Couldn't save: Title and body must not be empty!", Toast.LENGTH_SHORT).show();
        } else {
            dataSource.insertNote(title, body);
            this.finish();
            overridePendingTransition(0, 0);
        }

    }
}
