package cimdata.android.dez2017.masterdetail.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import cimdata.android.dez2017.masterdetail.R;
import cimdata.android.dez2017.masterdetail.fragments.DetailFragment;
import cimdata.android.dez2017.masterdetail.fragments.MasterFragment;


public class MasterActivity extends AppCompatActivity implements MasterFragment.FragmentListener {

    ViewGroup masterContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        masterContainer = findViewById(R.id.ly_acmaster_container_master);

        // Fill master container with master fragment
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
