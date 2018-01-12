package cimdata.android.dez2017.notesappproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import cimdata.android.dez2017.notesappproject.R;
import cimdata.android.dez2017.notesappproject.fragments.MasterFragment;


public class MasterActivity extends AppCompatActivity implements MasterFragment.FragmentListener {

    ViewGroup masterContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        masterContainer = findViewById(R.id.ly_acmaster_container_master);

        // Fill master container with master fragment
        displayMasterFragment();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void displayMasterFragment() {

        MasterFragment fragment = (MasterFragment) getSupportFragmentManager().findFragmentById(R.id.ly_acmaster_container_master);

        if(fragment == null) {
            fragment = MasterFragment.newInstance();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.ly_acmaster_container_master, fragment, "master_fragment")
                    .commit();
        }

    }

    @Override
    public void onFragmentItemClick(int pos) {

        displayDetailActivity(pos);

    }

    private void displayDetailActivity(int pos) {

        Intent intent = new Intent(this, DetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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
                Intent intent = new Intent(this, EditActivity.class);
                startActivity(intent);
                break;
            case R.id.id_search:
                MasterFragment fragment = (MasterFragment) getSupportFragmentManager().findFragmentByTag("master_fragment");
                fragment.toggleSearchBoxVisibility();
                break;
            default:
                new RuntimeException("Unknown menu item");

        }

        return super.onOptionsItemSelected(item);
    }
}
