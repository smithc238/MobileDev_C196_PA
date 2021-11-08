package com.mySchool.mobiledev_c196_pa.ui.detailviews;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.adapters.AssessmentListAdapter;
import com.mySchool.mobiledev_c196_pa.adapters.InstructorsListAdapter;
import com.mySchool.mobiledev_c196_pa.data.entities.Course;
import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;
import com.mySchool.mobiledev_c196_pa.data.entities.Status;
import com.mySchool.mobiledev_c196_pa.ui.addedit.AddEditCourseFragment;
import com.mySchool.mobiledev_c196_pa.utilities.DateTimeConv;
import com.mySchool.mobiledev_c196_pa.viewmodels.CourseViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailedCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedCourseFragment extends Fragment {
    private static final String COURSE_ID = "id";
    private long id;
    private CourseViewModel courseViewModel;
    private Course course;
    private EditText title;
    private EditText start;
    private EditText end;
    private RadioButton planToTake;
    private RadioButton inProgress;
    private RadioButton complete;
    private RadioButton dropped;
    private TextView noteHeader;
    private EditText note;


    /**
     * Required empty public constructor.
     */
    public DetailedCourseFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Course ID.
     * @return A new instance of fragment DetailedCourseFragment.
     */
    public static DetailedCourseFragment newInstance(long id) {
        DetailedCourseFragment fragment = new DetailedCourseFragment();
        Bundle args = new Bundle();
        args.putLong(COURSE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            this.id = getArguments().getLong(COURSE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_course, container, false);
        prepareFields(v);
        RecyclerView instructorRecycler = v.findViewById(R.id.course_instructor_recyclerView);
        RecyclerView assessmentRecycler = v.findViewById(R.id.course_assessment_recyclerView);
        instructorRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        instructorRecycler.setHasFixedSize(true);
        assessmentRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        assessmentRecycler.setHasFixedSize(true);
        InstructorsListAdapter iAdapter = new InstructorsListAdapter(v.getContext());
        instructorRecycler.setAdapter(iAdapter);
        AssessmentListAdapter aAdapter = new AssessmentListAdapter(v.getContext());
        assessmentRecycler.setAdapter(aAdapter);
        courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        courseViewModel.getCourseById(this.id).observe(getViewLifecycleOwner(), course -> {
            if (!course.isEmpty()) {
                this.course = course.get(0);
                title.setText(course.get(0).getTitle());
                start.setText(DateTimeConv.dateToStringLocal(course.get(0).getStart()));
                end.setText(DateTimeConv.dateToStringLocal(course.get(0).getEnd()));
                selectStatus(course.get(0).getStatus());
                if (course.get(0).getNote() != null) {
                    note.setText(course.get(0).getNote());
                } else {
                    note.setVisibility(View.GONE);
                    noteHeader.setVisibility(View.GONE);
                }
            }
        });
        courseViewModel.getAssociatedInstructors(this.id).observe(getViewLifecycleOwner(), iAdapter::setInstructors);
        courseViewModel.getAssociatedAssessments(this.id).observe(getViewLifecycleOwner(),assessments -> {
            if (!assessments.isEmpty()) {
                aAdapter.setAssessments(assessments);
            }
        });
        iAdapter.setOnInstructorClickListener(instructor -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.detail_view_host,DetailedInstructorFragment.newInstance(instructor.getInstructorID()))
                    .addToBackStack("DetailedCourse")
                    .commit();
        });
        aAdapter.setOnAssessmentClickListener(assessment -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.detail_view_host,DetailedAssessmentFragment.newInstance(assessment.getId()))
                    .addToBackStack("DetailedCourse")
                    .commit();
        });
        return v;
    }

    private void prepareFields(View v) {
        title = v.findViewById(R.id.course_title);
        start = v.findViewById(R.id.course_start);
        end = v.findViewById(R.id.course_end);
        planToTake = v.findViewById(R.id.course_status_planToTake_radio);
        inProgress = v.findViewById(R.id.course_status_inProgress_radio);
        complete = v.findViewById(R.id.course_status_complete_radio);
        dropped = v.findViewById(R.id.course_status_dropped_radio);
        noteHeader = v.findViewById(R.id.course_note_header);
        note = v.findViewById(R.id.course_note);
        Button addInstructor = v.findViewById(R.id.course_addInstructor_button);
        Button addAssessment = v.findViewById(R.id.course_addAssessment_button);
        TextView noInstructors = v.findViewById(R.id.course_noInstructors);
        TextView noAssessments = v.findViewById(R.id.course_noAssessments);
        title.setClickable(false);
        title.setCursorVisible(false);
        title.setFocusable(false);
        title.setFocusableInTouchMode(false);
        title.setBackground(null);
        start.setBackground(null);
        end.setBackground(null);
        note.setBackground(null);
        addInstructor.setVisibility(View.GONE);
        addAssessment.setVisibility(View.GONE);
        noInstructors.setVisibility(View.GONE);
        noAssessments.setVisibility(View.GONE);
    }

    private void selectStatus(Status status) {
        switch (status) {
            case PLAN_TO_TAKE:
                planToTake.toggle();
                break;
            case IN_PROGRESS:
                inProgress.toggle();
                break;
            case COMPLETE:
                complete.toggle();
                break;
            case DROPPED:
                dropped.toggle();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Course Details");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int option = item.getItemId();
        if (option == R.id.menu_detail_edit) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.detail_view_host, AddEditCourseFragment.newInstance(this.id,this.course.getTermID()))
                    .addToBackStack("DetailedCourse")
                    .commit();
            return true;
        } else if (option == R.id.menu_detail_delete) {
            courseViewModel.delete(course);
            nextScreen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void nextScreen() {
        if (getParentFragmentManager().getBackStackEntryCount() == 0) {
            getActivity().finish();
        } else {
            getParentFragmentManager().popBackStack();
        }
    }
}