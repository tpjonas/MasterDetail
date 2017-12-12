package cimdata.android.dez2017.masterdetail.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cimdata.android.dez2017.masterdetail.R;
import cimdata.android.dez2017.masterdetail.services.DataService;

public class DetailFragment extends Fragment {

    private static final String ARG_INT_POSITION = "arg.int.position";

    private TextView titleText;

    private int position;


    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(int position) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_INT_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_INT_POSITION, -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        String data = DataService.fetchData()[position];

        titleText = v.findViewById(R.id.txt_frdetail_title);

        titleText.setText(data);

        return v;
    }

}
