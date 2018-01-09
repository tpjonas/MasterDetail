package cimdata.android.dez2017.masterdetail.activities;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import cimdata.android.dez2017.masterdetail.R;
import cimdata.android.dez2017.masterdetail.db.NotesDataSource;
import cimdata.android.dez2017.masterdetail.db.NotesContract;
import cimdata.android.dez2017.masterdetail.fragments.DetailFragment;
import cimdata.android.dez2017.masterdetail.fragments.MasterFragment;


public class MasterActivity extends AppCompatActivity implements MasterFragment.FragmentListener {

    ViewGroup masterContainer, detailContainer;

    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        masterContainer = findViewById(R.id.ly_acmaster_container_master);
        detailContainer = findViewById(R.id.ly_acmaster_container_detail);

        // Wenn wir uns beide Container geholt haben, entscheiden wir welche
        // Variante wir anzeigen.
        // Wenn es einen Detail-Container gibt, merken wir uns, dass wir uns ium Landscape-Modus befinden
        // Wir merken daran, welche Layout-Datei verwendet wird
        isTablet = detailContainer != null;

        // Hier füllen wir den Master-Container mit dem Master Fragment
        displayMasterFragment("");

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void displayMasterFragment(String searchString) {

        MasterFragment fragment = MasterFragment.newInstance(searchString, "");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.ly_acmaster_container_master, fragment, "master_fragment")
                .commit();

    }

    @Override
    public void onFragmentItemClick(int pos) {

        Toast.makeText(this, String.valueOf(pos), Toast.LENGTH_SHORT).show();

        if (isTablet) {
            displayDetailFragment(pos);
        } else {
            displayDetailActivity(pos);
        }

    }

    private void displayDetailFragment(int pos) {

        DetailFragment fragment = DetailFragment.newInstance(pos);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.ly_acmaster_container_detail, fragment)
                .commit();

    }

    private void displayDetailActivity(int pos) {

        Intent intent = new Intent(this, DetailActivity.class);

        intent.putExtra(DetailActivity.EXTRA_INT_POSITION, pos);

        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.id_add:
                Toast.makeText(this, "ADD", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, EditActivity.class);
                startActivity(intent);
                break;
            case R.id.id_search:
                Toast.makeText(this, "SEARCH", Toast.LENGTH_SHORT).show();
                MasterFragment fragment = (MasterFragment) getSupportFragmentManager().findFragmentByTag("master_fragment");
                fragment.toggleSearchBoxVisibility();
                break;
            default:
                Toast.makeText(this, "NO", Toast.LENGTH_SHORT).show();

        }
        
        return super.onOptionsItemSelected(item);
    }
}
