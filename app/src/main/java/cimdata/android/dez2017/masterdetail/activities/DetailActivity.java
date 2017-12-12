package cimdata.android.dez2017.masterdetail.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import cimdata.android.dez2017.masterdetail.R;
import cimdata.android.dez2017.masterdetail.fragments.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_INT_POSITION = "extra.int.position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Daten holen
        Intent intent = getIntent();
        int position = intent.getIntExtra(EXTRA_INT_POSITION, -1);

        //Toast.makeText(this, "POS: " + position, Toast.LENGTH_SHORT).show();

        DetailFragment fragment = DetailFragment.newInstance(position);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.ly_acdetail_container_detail, fragment)
                .commit();

    }
}
