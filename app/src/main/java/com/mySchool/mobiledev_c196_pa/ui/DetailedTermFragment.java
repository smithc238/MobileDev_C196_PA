package com.mySchool.mobiledev_c196_pa.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.adapters.DetailedTermAdapter;
import com.mySchool.mobiledev_c196_pa.utilities.DateTimeConv;
import com.mySchool.mobiledev_c196_pa.viewmodels.CourseViewModel;
import com.mySchool.mobiledev_c196_pa.viewmodels.TermViewModel;



public class DetailedTermFragment extends Fragment {

    private static final String TERM_ID = "id";
    private long id;
    private TermViewModel termViewModel;
    private CourseViewModel courseViewModel;

    public DetailedTermFragment() {
    }

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
        if (getArguments() != null) {
            id = getArguments().getLong(TERM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detailed_term, container, false);
        TextView startDate = v.findViewById(R.id.detailed_term_start);
        TextView endDate = v.findViewById(R.id.detailed_term_end);
        RecyclerView recyclerView = v.findViewById(R.id.detailed_term_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DetailedTermAdapter adapter = new DetailedTermAdapter(v.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getTerm(id).observe(getViewLifecycleOwner(), terms -> {
            startDate.setText(DateTimeConv.dateToStringLocal(terms.get(0).getStart()));
            endDate.setText(DateTimeConv.dateToStringLocal(terms.get(0).getEnd()));
        });
        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getAssociatedCourses(id).observe(getViewLifecycleOwner(), courses -> {
            adapter.setCourses(courses);
        });

        return v;
    }
}