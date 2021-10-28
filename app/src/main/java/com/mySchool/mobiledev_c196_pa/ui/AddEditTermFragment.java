package com.mySchool.mobiledev_c196_pa.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

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
        setHasOptionsMenu(true);
        if (edit) {
            getActivity().setTitle("Edit Term");
        } else {
            getActivity().setTitle("Add Term");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_edit_term, container, false);
        if (getArguments() != null) {
            if (edit) {

            } else {

            }
        }
        return v;
    }
}