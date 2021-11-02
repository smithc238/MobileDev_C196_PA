package com.mySchool.mobiledev_c196_pa.ui.detailviews;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.mySchool.mobiledev_c196_pa.ui.addedit.AddEditTermFragment;
import com.mySchool.mobiledev_c196_pa.utilities.DateTimeConv;
import com.mySchool.mobiledev_c196_pa.viewmodels.CourseViewModel;
import com.mySchool.mobiledev_c196_pa.viewmodels.TermViewModel;

public class DetailedTermFragment extends Fragment {
    private static final String TERM_ID = "id";
    private long id;
    private TermViewModel termViewModel;
    private CourseViewModel courseViewModel;
    private EditText title;
    private EditText start;
    private EditText end;
    private TextView noCourses;
    private FloatingActionButton addButton;

    public DetailedTermFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Term ID.
     * @return A new instance of fragment DetailedTermFragment.
     */
    public static DetailedTermFragment newInstance(long id) {
        DetailedTermFragment fragment = new DetailedTermFragment();
        Bundle args = new Bundle();
        args.putLong(TERM_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            id = getArguments().getLong(TERM_ID);
        }
        getActivity().setTitle("Term Details");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_term, container, false);
        title = v.findViewById(R.id.term_title);
        start = v.findViewById(R.id.term_start);
        end = v.findViewById(R.id.term_end);
        addButton = v.findViewById(R.id.term_floatingActionButton);
        noCourses = v.findViewById(R.id.term_noCourses);
        setReadOnly();

        RecyclerView recyclerView = v.findViewById(R.id.term_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        CourseListAdapter adapter = new CourseListAdapter(v.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getTermById(id).observe(getViewLifecycleOwner(), terms -> {
            if (terms != null) {
                title.setText(terms.get(0).getTitle());
                start.setText(DateTimeConv.dateToStringLocal(terms.get(0).getStart()));
                end.setText(DateTimeConv.dateToStringLocal(terms.get(0).getEnd()));
            }
        });
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getAssociatedCourses(id).observe(getViewLifecycleOwner(), courses -> {
            if (!courses.isEmpty()) {
                adapter.setCourses(courses);
                noCourses.setVisibility(View.GONE);
            } else {
                noCourses.setVisibility(View.VISIBLE);
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_detail_edit) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.detail_view_host,
                            AddEditTermFragment.newInstance(this.id))
                    .addToBackStack("detail")
                    .commit();
        } else if (id == R.id.menu_detail_delete) {
            // If Term has courses display alert, else delete.
            if (noCourses.getVisibility() == View.GONE) {
                AlertDialog alert = new AlertDialog.Builder(getActivity())
                        .setMessage(R.string.remove_courses)
                        .setPositiveButton(R.string.ok, (dialog, which) -> {
                            dialog.dismiss();
                        }).create();
                alert.show();
            } else {
                termViewModel.delete(termViewModel.getTermById(this.id).getValue().get(0));
                getActivity().finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setReadOnly() {
        title.setClickable(false);
        title.setCursorVisible(false);
        title.setFocusable(false);
        title.setFocusableInTouchMode(false);
        title.setBackground(null);
        start.setBackground(null);
        end.setBackground(null);
        addButton.setVisibility(View.GONE);
    }
}