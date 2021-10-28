package com.mySchool.mobiledev_c196_pa.ui.terms;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mySchool.mobiledev_c196_pa.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEditTermFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditTermFragment extends Fragment {
    private static final String EDIT_TERM = "edit";
    private static final String EDIT_TERM_ID = "id";
    private boolean edit;
    private long editTermID;

    public AddEditTermFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param edit edit term = true, add term = false.
     * @param termID term ID to edit, null for add.
     * @return A new instance of fragment AddEditTermFragment.
     */
    public static AddEditTermFragment newInstance(boolean edit, long termID) {
        AddEditTermFragment fragment = new AddEditTermFragment();
        Bundle args = new Bundle();
        args.putBoolean(EDIT_TERM, edit);
        args.putLong(EDIT_TERM_ID, termID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            edit = getArguments().getBoolean(EDIT_TERM);
            editTermID = getArguments().getLong(EDIT_TERM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_edit_term, container, false);
        TextView textView = v.findViewById(R.id.hello_blank);
        if (getArguments() != null) {
            textView.setText(edit + " " + editTermID);
//            if (edit) {
//                getActivity().getActionBar().setTitle("Edit Term");
//            } else {
//                getActivity().getActionBar().setTitle("Add Term");
//            }
        }
        return v;
    }


}