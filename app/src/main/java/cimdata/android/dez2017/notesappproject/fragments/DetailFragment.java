package cimdata.android.dez2017.notesappproject.fragments;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cimdata.android.dez2017.notesappproject.R;
import cimdata.android.dez2017.notesappproject.activities.DetailActivity;
import cimdata.android.dez2017.notesappproject.db.NotesDataSource;

public class DetailFragment extends Fragment {

    private static final String ARG_INT_ID = "arg.int.id";

    private TextView bodyText;
    private TextView dateText;

    private int id;

    public NotesDataSource dataSource;


    public DetailFragment() {}

    public static DetailFragment newInstance(int position) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INT_ID, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ARG_INT_ID, -1);
        }
        dataSource = new NotesDataSource(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        dataSource.open();

        // get data
        ContentValues row = dataSource.fetchNote(id);
        String title = row.getAsString(DetailActivity.DB_TITLE);
        String body = row.getAsString(DetailActivity.DB_BODY);
        int day = row.getAsInteger(DetailActivity.DB_DAY);
        int month = row.getAsInteger(DetailActivity.DB_MONTH)+1;
        int year = row.getAsInteger(DetailActivity.DB_YEAR);



        bodyText = v.findViewById(R.id.id_txt_detail_body);
        dateText = v.findViewById(R.id.id_txt_detail_date);

        getActivity().setTitle(title);
        bodyText.setText(body);
        dateText.setText(String.format(getString(R.string.date), day, month, year));

        return v;
    }

}
