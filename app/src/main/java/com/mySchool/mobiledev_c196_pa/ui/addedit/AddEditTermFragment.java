package com.mySchool.mobiledev_c196_pa.ui.addedit;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.adapters.CourseListAdapter;
import com.mySchool.mobiledev_c196_pa.data.entities.Term;
import com.mySchool.mobiledev_c196_pa.utilities.DateFormFiller;
import com.mySchool.mobiledev_c196_pa.utilities.DateTimeConv;
import com.mySchool.mobiledev_c196_pa.utilities.FormValidators;
import com.mySchool.mobiledev_c196_pa.viewmodels.CourseViewModel;
import com.mySchool.mobiledev_c196_pa.viewmodels.TermViewModel;

import java.time.ZonedDateTime;

public class AddEditTermFragment extends Fragment {
    private static final String EDIT_TERM_ID = "id";
    private boolean edit = false;
    private long id;
    private TermViewModel termViewModel;
    private CourseViewModel courseViewModel;
    private EditText title;
    private EditText start;
    private EditText end;
    private TextView noCourses;
    private FloatingActionButton addButton;
    private Term term;

    public AddEditTermFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param termID term ID > 0 for edit, < 0 is add.
     * @return A new instance of fragment AddEditTermFragment.
     */
    public static AddEditTermFragment newInstance(long termID) {
        AddEditTermFragment fragment = new AddEditTermFragment();
        Bundle args = new Bundle();
        args.putLong(EDIT_TERM_ID, termID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            id = getArguments().getLong(EDIT_TERM_ID);
            if (id > 0) {
                edit = true;
                getActivity().setTitle("Edit Term");
            } else {
                getActivity().setTitle("Add Term");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_term, container, false);
        title = v.findViewById(R.id.term_title);
        start = v.findViewById(R.id.term_start);
        end = v.findViewById(R.id.term_end);
        noCourses = v.findViewById(R.id.term_noCourses);
        addButton = v.findViewById(R.id.term_floatingActionButton);

        RecyclerView recyclerView = v.findViewById(R.id.term_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CourseListAdapter adapter = new CourseListAdapter(v.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        if (edit) {
            termViewModel = new ViewModelProvider(requireActivity()).get(TermViewModel.class);
            termViewModel.getTerm().observe(getViewLifecycleOwner(), term -> {
                if (term != null) {
                    title.setText(term.getTitle());
                    start.setText(DateTimeConv.dateToStringLocal(term.getStart()));
                    end.setText(DateTimeConv.dateToStringLocal(term.getEnd()));
                    DateFormFiller.dateOnClickDatePicker(start,term.getStart());
                    DateFormFiller.dateOnClickDatePicker(end,term.getEnd());
                }
            });
            this.term = termViewModel.getTerm().getValue();
            courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
            courseViewModel.getAssociatedCourses(id).observe(getViewLifecycleOwner(), courses -> {
                if (!courses.isEmpty()) {
                    adapter.setCourses(courses);
                    noCourses.setVisibility(View.GONE);
                }
            });
        } else {
            DateFormFiller.dateOnClickDatePicker(start,null);
            DateFormFiller.dateOnClickDatePicker(end,null);
        }
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_edit_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_addedit_save) {
            //Update or Insert term if valid and has a course.
            //TODO: add back must have course validation for saves.
            if (formValidation()) {
//                    && noCourses.getVisibility() == View.GONE) {
                buildTerm();
                if (edit) {
                    Log.i("EditTerm",this.term.getId()+this.term.getTitle()+this.term.getStart()+this.term.getEnd());
                    termViewModel.update(term);
                } else {
                    //TODO: Fix Term Insert Bug.
                    Log.i("AddTerm",this.term.getId()+this.term.getTitle()+this.term.getStart()+this.term.getEnd());
                    termViewModel.insert(term);
                }
                nextScreen();
                return true;
            }
        } else if (id == R.id.menu_addedit_delete) {
            // If Term has courses display alert, else delete.
            if (noCourses.getVisibility() == View.GONE) {
                AlertDialog alert = new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.remove_courses)
                        .setPositiveButton(R.string.ok,
                                (dialog, which) -> dialog.dismiss()).create();
                alert.show();
            } else {
                if (edit) { termViewModel.delete(term); }
                getActivity().finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO:Add validations for null date fields
    private boolean formValidation() {
        if (!FormValidators.nameValidation(title)) {
            title.setError("Please enter a title.");
        }
        if (!FormValidators.endDateValidation(start, end)) {
            end.setError("Please select a date after Start date.");
        }
        return FormValidators.nameValidation(title)
                && FormValidators.endDateValidation(start, end);
    }

    private void buildTerm() {
        String newTitle = this.title.getText().toString();
        ZonedDateTime newStart = DateTimeConv.stringToDateLocalWithoutTime(this.start.getText().toString());
        ZonedDateTime newEnd = DateTimeConv.stringToDateLocalWithoutTime(this.end.getText().toString());
        if (!edit) {
            this.term = new Term(newTitle, newStart, newEnd);
        } else {
            this.term.setTitle(newTitle);
            this.term.setStart(newStart);
            this.term.setEnd(newEnd);
        }
    }

    private void nextScreen() {
        if (getParentFragmentManager().getBackStackEntryCount() == 0) {
            getActivity().finish();
        } else {
            getParentFragmentManager().popBackStack();
        }
    }
}