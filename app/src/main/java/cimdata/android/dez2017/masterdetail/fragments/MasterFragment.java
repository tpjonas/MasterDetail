package cimdata.android.dez2017.masterdetail.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import cimdata.android.dez2017.masterdetail.R;
import cimdata.android.dez2017.masterdetail.services.DataService;

public class MasterFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView dataList;

    private String mParam1;
    private String mParam2;

    // Für die Callbacks
    // Schritt 2: Wir legen ein Feld für das Objekt an,
    // das auf unsere Callbacks hört
    private FragmentListener listener;

    public MasterFragment() {
        // Required empty public constructor
    }

    public static MasterFragment newInstance(String param1, String param2) {
        MasterFragment fragment = new MasterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_master, container, false);

        String[] data = DataService.fetchData();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                data
        );

        dataList = v.findViewById(R.id.list_frmaster_data);
        dataList.setAdapter(adapter);

        // Wenn wir ein Item auswählen ...
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ... rufen wir die Callback-Methode auf der Activity auf
                listener.onFragmentItemClick(position);

            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof FragmentListener)) {
            throw new AssertionError("Die Activity muss das Interface FragmentListener implementieren.");
        }

        // Hier verbinden sich Activity und Fragmente automatisch, denn
        // die Methode onAttach() wird immer aufgerufen und die Activity,
        // die das Fragment anzeigt, übergibt sich selber (als context).
        listener = (FragmentListener) context;
    }

    // Für die Callbacks
    // Schritt 1: Wir schreiben ein internes Interface
    public interface FragmentListener {
        void onFragmentItemClick (int pos);
    }

}
