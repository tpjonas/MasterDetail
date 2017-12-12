package cimdata.android.dez2017.masterdetail.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import cimdata.android.dez2017.masterdetail.R;
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
        displayMasterFragment();


    }

    private void displayMasterFragment() {

        MasterFragment fragment = MasterFragment.newInstance("", "");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.ly_acmaster_container_master, fragment)
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

        DetailFragment fragment = DetailFragment.newInstance("", "");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.ly_acmaster_container_detail, fragment)
                .commit();

    }

    private void displayDetailActivity(int pos) {
    }


}
