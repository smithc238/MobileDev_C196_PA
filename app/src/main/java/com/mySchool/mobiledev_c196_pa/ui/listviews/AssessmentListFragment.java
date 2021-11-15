package com.mySchool.mobiledev_c196_pa.ui.listviews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mySchool.mobiledev_c196_pa.R;

/**
 * A simple {@link Fragment} subclass. Not Used.
 * Use the {@link AssessmentListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssessmentListFragment extends Fragment {
    private static final String ASSESSMENT_ID = "id";
    public static final String COURSE_ID = "courseId";
    private long id;
    private Long courseId;

    public AssessmentListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Assessment ID.
     * @param courseId Parameter 2.
     * @return A new instance of fragment AssessmentListFragment.
     */
    public static AssessmentListFragment newInstance(long id, Long courseId) {
        AssessmentListFragment fragment = new AssessmentListFragment();
        Bundle args = new Bundle();
        args.putLong(ASSESSMENT_ID, id);
        args.putLong(COURSE_ID, courseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong(ASSESSMENT_ID);
            courseId = getArguments().getLong(COURSE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        return v;
    }
}