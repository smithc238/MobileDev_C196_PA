package com.mySchool.mobiledev_c196_pa.ui.addedit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.adapters.CourseListAdapter;
import com.mySchool.mobiledev_c196_pa.utilities.DateFormFiller;
import com.mySchool.mobiledev_c196_pa.utilities.DateTimeConv;
import com.mySchool.mobiledev_c196_pa.viewmodels.CourseViewModel;
import com.mySchool.mobiledev_c196_pa.viewmodels.TermViewModel;

public class AddEditTermFragment extends Fragment {
    private static final String EDIT_TERM = "edit";
    private static final String EDIT_TERM_ID = "id";
    private boolean edit = false;
    private long editTermID = -1;
    private TermViewModel termViewModel;
    private CourseViewModel courseViewModel;

    public AddEditTermFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param edit edit term = true, add term = false.
     * @param termID term ID for edit.
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
            if (edit) {
                getActivity().setTitle("Edit Term");
            } else {
                getActivity().setTitle("Add Term");
            }
        }
        setHasOptionsMenu(true);
    }

    //TODO: Finish the add edit fragment.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_edit_term, container, false);
        EditText title = v.findViewById(R.id.addEdit_term_title);
        EditText start = v.findViewById(R.id.addEdit_term_startDate);
        EditText end = v.findViewById(R.id.addEdit_term_endDate);
        TextView noCourses = v.findViewById(R.id.addEdit_term_noCourses);
        dateOnClickDatePicker(start);
        dateOnClickDatePicker(end);

        RecyclerView recyclerView = v.findViewById(R.id.addEdit_term_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CourseListAdapter adapter = new CourseListAdapter(v.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        if (edit) {
            termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
            termViewModel.getTerm(editTermID).observe(getViewLifecycleOwner(), terms -> {
                start.setText(DateTimeConv.dateToStringLocal(terms.get(0).getStart()));
                end.setText(DateTimeConv.dateToStringLocal(terms.get(0).getEnd()));
            });
            courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
            courseViewModel.getAssociatedCourses(editTermID).observe(getViewLifecycleOwner(), courses -> {
                adapter.setCourses(courses);
            });
        }
        return v;
    }

    private void dateOnClickDatePicker(EditText editText) {
        editText.setOnClickListener(v -> {
            DateFormFiller formFiller = new DateFormFiller(editText);
            formFiller.showDatePickerDialog(v);
        });
    }


}