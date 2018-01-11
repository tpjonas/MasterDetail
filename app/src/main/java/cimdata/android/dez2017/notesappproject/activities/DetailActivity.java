package cimdata.android.dez2017.notesappproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import cimdata.android.dez2017.notesappproject.R;
import cimdata.android.dez2017.notesappproject.db.NotesDataSource;
import cimdata.android.dez2017.notesappproject.fragments.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_INT_POSITION = "extra.int.id";

    public NotesDataSource dataSource;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Daten holen
        Intent intent = getIntent();
        position = intent.getIntExtra(EXTRA_INT_POSITION, -1);

        //Toast.makeText(this, "POS: " + id, Toast.LENGTH_SHORT).show();

        DetailFragment fragment = DetailFragment.newInstance(position);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.ly_acdetail_container_detail, fragment)
                .commit();

        dataSource = new NotesDataSource(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.id_delete:
                Toast.makeText(this, "DELETE", Toast.LENGTH_SHORT).show();
                dataSource.deleteNote(position);
                this.finish();
                overridePendingTransition(0, 0);
                break;
            case R.id.id_edit:
                Toast.makeText(this, "EDIT", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra(EXTRA_INT_POSITION, position);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "NO", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }
}
