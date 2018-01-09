package cimdata.android.dez2017.masterdetail.fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import org.w3c.dom.Text;

import cimdata.android.dez2017.masterdetail.R;
import cimdata.android.dez2017.masterdetail.activities.MasterActivity;
import cimdata.android.dez2017.masterdetail.db.NotesContract;
import cimdata.android.dez2017.masterdetail.db.NotesDataSource;
import cimdata.android.dez2017.masterdetail.services.DataService;

public class MasterFragment extends Fragment implements View.OnClickListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Notes List
    private ListView dataList;

    // Search box and contents
    private ConstraintLayout searchBox;
    private Button searchButton;
    private EditText searchField;


    private String mParam1;
    private String mParam2;

    private SimpleCursorAdapter adapter;

    public NotesDataSource dataSource;

    // Für die Callbacks
    // Schritt 2: Wir legen ein Feld für das Objekt an,
    // das auf unsere Callbacks hört
    private FragmentListener listener;
    private Cursor cursor;

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
        dataSource = new NotesDataSource(getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();
        changeCursor(dataSource.fetchAllNotes());

    }

    public void changeCursor(Cursor cursor) {
        adapter.changeCursor(cursor);
        adapter.notifyDataSetChanged(); // CHECK IF NEEDED
    }

    public void toggleSearchBoxVisibility() {
        int visibility = searchBox.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE;
        searchBox.setVisibility(visibility);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        dataSource.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_master, container, false);

        dataList = v.findViewById(R.id.list_frmaster_data);

        searchBox = v.findViewById(R.id.id_fragment_master_search_frame);
        searchButton = v.findViewById(R.id.id_button_search);
        searchField = v.findViewById(R.id.id_text_search);

        searchButton.setOnClickListener(this);

        dataSource.open();
        adapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_list_item_2,
                null,
                new String[] {
                        NotesContract.NotesEntry.COLUMN_TITLE_NAME,
                        NotesContract.NotesEntry.COLUMN_BODY_NAME
                },
                new int[] {
                        android.R.id.text1,
                        android.R.id.text2

                },
                0
        );


        dataList.setAdapter(adapter);

        //---

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

    @Override
    public void onClick(View v) {
        String needle = String.valueOf(searchField.getText());
        changeCursor(dataSource.searchNotes(needle));
        searchField.setText("");
        toggleSearchBoxVisibility();
    }

    // Für die Callbacks
    // Schritt 1: Wir schreiben ein internes Interface
    public interface FragmentListener {
        void onFragmentItemClick (int pos);
    }


}
