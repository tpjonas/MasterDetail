package cimdata.android.dez2017.notesappproject.fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import cimdata.android.dez2017.notesappproject.R;
import cimdata.android.dez2017.notesappproject.activities.MasterActivity;
import cimdata.android.dez2017.notesappproject.db.NotesContract;
import cimdata.android.dez2017.notesappproject.db.NotesDataSource;

public class MasterFragment extends Fragment {

    // Notes List
    private ListView dataList;

    // Search box and contents
    private ConstraintLayout searchBox;
    private Button clearButton;
    private EditText searchField;

    private SimpleCursorAdapter adapter;

    public NotesDataSource dataSource;

    private FragmentListener listener;
    private Cursor cursor;

    public MasterFragment() {
        // Required empty public constructor
    }

    public static MasterFragment newInstance() {
        return new MasterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataSource = new NotesDataSource(getActivity());

    }

    @Override
    public void onResume() {
        super.onResume();
        changeCursor(dataSource.fetchAllNotes());

    }

    public void changeCursor(Cursor cursor) {
        adapter.changeCursor(cursor);
        //adapter.notifyDataSetChanged(); // not needed
    }

    public void toggleSearchBoxVisibility() {
        int visibility = searchBox.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
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
        clearButton = v.findViewById(R.id.id_button_clear);
        searchField = v.findViewById(R.id.id_text_search);

        // open database
        dataSource.open();

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            // run search on text change
            @Override
            public void afterTextChanged(Editable s) {
                String needle = String.valueOf(searchField.getText());
                changeCursor(dataSource.searchNotes(needle));
            }
        });

        // clear search on Button click
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchField.setText("");
                hideKeyboardFrom(getContext(), v);
                toggleSearchBoxVisibility();
            }
        });

        // create empty adapter
        adapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_list_item_2,
                null, // null for now, we assign the cursor later
                new String[] {
                        NotesContract.NotesEntry.COLUMN_TITLE_NAME,
                        NotesContract.NotesEntry.COLUMN_BODY_NAME
                },
                new int[] {
                        android.R.id.text1,
                        android.R.id.text2

                },
                0
        ){

            // highlight due notes
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view = super.getView(position, convertView, parent);

                Cursor cursor = getCursor();
                cursor.moveToPosition(position);
                boolean isDue = cursor.getInt(cursor.getColumnIndex(NotesContract.NotesEntry.COLUMN_IS_DUE)) == 1;

                if(isDue) {
                    int hightlightedColor = getResources().getColor(R.color.colorAccent, null);
                    ((TextView) view.findViewById(android.R.id.text1)).setTextColor(hightlightedColor);
                    ((TextView) view.findViewById(android.R.id.text2)).setTextColor(hightlightedColor);
                }
                return view;
            }
        };


        dataList.setAdapter(adapter);

        // Callback on click of any item in the list
        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onFragmentItemClick((int) id);
            }
        });

        return v;
    }

    private static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(MasterActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof FragmentListener)) {
            throw new AssertionError("Activity must implement Interface FragmentListener.");
        }

        listener = (FragmentListener) context;
    }

    // internal interface for callbacks
    public interface FragmentListener {
        void onFragmentItemClick (int id);
    }


}
