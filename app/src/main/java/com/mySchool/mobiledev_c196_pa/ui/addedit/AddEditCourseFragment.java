package com.mySchool.mobiledev_c196_pa.ui.addedit;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.adapters.AssessmentListAdapter;
import com.mySchool.mobiledev_c196_pa.adapters.InstructorsListAdapter;
import com.mySchool.mobiledev_c196_pa.data.entities.Course;
import com.mySchool.mobiledev_c196_pa.data.entities.Status;
import com.mySchool.mobiledev_c196_pa.ui.detailviews.DetailedAssessmentFragment;
import com.mySchool.mobiledev_c196_pa.ui.detailviews.DetailedInstructorFragment;
import com.mySchool.mobiledev_c196_pa.utilities.DateFormFiller;
import com.mySchool.mobiledev_c196_pa.utilities.DateTimeConv;
import com.mySchool.mobiledev_c196_pa.utilities.FormValidators;
import com.mySchool.mobiledev_c196_pa.viewmodels.AssessmentViewModel;
import com.mySchool.mobiledev_c196_pa.viewmodels.CourseInstructorViewModel;
import com.mySchool.mobiledev_c196_pa.viewmodels.CourseViewModel;
import com.mySchool.mobiledev_c196_pa.viewmodels.InstructorViewModel;

import java.time.ZonedDateTime;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEditCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditCourseFragment extends Fragment {
    private static final String COURSE_ID = "courseId";
    private static final String TERM_ID = "termId";
    private long id;
    private Long termId;
    private boolean edit;
    private CourseViewModel courseViewModel;
    private InstructorViewModel instructorViewModel;
    private AssessmentViewModel assessmentViewModel;
    private CourseInstructorViewModel courseInstructorViewModel;
    private Course course;
    private EditText title;
    private EditText start;
    private EditText end;
    private RadioGroup radioGroup;
    private RadioButton planToTake;
    private RadioButton inProgress;
    private RadioButton complete;
    private RadioButton dropped;
    private EditText note;
    private Button addInstructor;
    private Button addAssessment;
    private TextView noInstructors;
    private TextView noAssessments;

    /**
     * Required empty constructor.
     */
    public AddEditCourseFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id     Course ID.
     * @param termId Term ID.
     * @return A new instance of fragment AddEditCourseFragment.
     */
    public static AddEditCourseFragment newInstance(long id, Long termId) {
        AddEditCourseFragment fragment = new AddEditCourseFragment();
        Bundle args = new Bundle();
        args.putLong(COURSE_ID, id);
        args.putLong(TERM_ID, termId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            id = getArguments().getLong(COURSE_ID);
            termId = getArguments().getLong(TERM_ID);
            edit = id > 0;
            if (termId < 0 ) { termId = null; }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_course, container, false);
        title = v.findViewById(R.id.course_title);
        start = v.findViewById(R.id.course_start);
        end = v.findViewById(R.id.course_end);
        radioGroup = v.findViewById(R.id.course_status_radio_group);
        planToTake = v.findViewById(R.id.course_status_planToTake_radio);
        inProgress = v.findViewById(R.id.course_status_inProgress_radio);
        complete = v.findViewById(R.id.course_status_complete_radio);
        dropped = v.findViewById(R.id.course_status_dropped_radio);
        note = v.findViewById(R.id.course_note);
        addInstructor = v.findViewById(R.id.course_addInstructor_button);
        addAssessment = v.findViewById(R.id.course_addAssessment_button);
        noInstructors = v.findViewById(R.id.course_noInstructors);
        noAssessments = v.findViewById(R.id.course_noAssessments);
        DateFormFiller.dateOnClickDatePicker(start,null);
        DateFormFiller.dateOnClickDatePicker(end,null);
        RecyclerView instructorRecycler = v.findViewById(R.id.course_instructor_recyclerView);
        RecyclerView assessmentRecycler = v.findViewById(R.id.course_assessment_recyclerView);
        instructorRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        assessmentRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        InstructorsListAdapter iAdapter = new InstructorsListAdapter(v.getContext());
        instructorRecycler.setAdapter(iAdapter);
        AssessmentListAdapter aAdapter = new AssessmentListAdapter(v.getContext());
        assessmentRecycler.setAdapter(aAdapter);
        courseViewModel = new ViewModelProvider(requireActivity()).get(CourseViewModel.class);
        instructorViewModel = new ViewModelProvider(requireActivity()).get(InstructorViewModel.class);
        assessmentViewModel = new ViewModelProvider(requireActivity()).get(AssessmentViewModel.class);
        // Set field values as appropriate with edit or add.
        if (edit) { //all methods that use the courseID
            courseViewModel.getCourseById(this.id).observe(getViewLifecycleOwner(), courses -> {
                if (!courses.isEmpty()) {
                    title.setText(courses.get(0).getTitle());
                    start.setText(DateTimeConv.dateToStringLocal(courses.get(0).getStart()));
                    end.setText(DateTimeConv.dateToStringLocal(courses.get(0).getEnd()));
                    DateFormFiller.dateOnClickDatePicker(start,courses.get(0).getStart());
                    DateFormFiller.dateOnClickDatePicker(end,courses.get(0).getEnd());
                    selectStatus(courses.get(0).getStatus());
                    note.setText(courses.get(0).getNote());
                    this.course = courses.get(0);
                }
            });
            //initialize working lists with current values.
            courseViewModel.getAssociatedInstructors(this.id).observe(getViewLifecycleOwner(), instructors -> {
                if (!instructors.isEmpty() && instructorViewModel.getWorkingList().getValue().isEmpty()) {
                    instructorViewModel.setWorkingList(instructors);
                }
            });
            courseViewModel.getAssociatedAssessments(this.id).observe(getViewLifecycleOwner(), assessments -> {
                if (!assessments.isEmpty() && assessmentViewModel.getWorkingList().getValue().isEmpty()) {
                    assessmentViewModel.addAllToWorkingList(assessments);
                }
            });
        }
        //Use ViewModel to display info until complete.
        instructorViewModel.getWorkingList().observe(getViewLifecycleOwner(), instructors -> {
            if (!instructors.isEmpty()) {
                iAdapter.setInstructors(instructors);
                noInstructors.setVisibility(View.GONE);
            }
        });
        assessmentViewModel.getWorkingList().observe(getViewLifecycleOwner(), assessments -> {
            if (!assessments.isEmpty()) {
                aAdapter.setAssessments(assessments);
                noAssessments.setVisibility(View.GONE);
            }
        });
        // RecyclerView adapter click listeners
        iAdapter.setOnInstructorClickListener(instructor -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.detail_view_host, DetailedInstructorFragment.newInstance(instructor.getInstructorID()))
                    .addToBackStack("AddEditCourse")
                    .commit();
        });
        aAdapter.setOnAssessmentClickListener(assessment -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.detail_view_host, DetailedAssessmentFragment.newInstance(assessment.getId()))
                    .addToBackStack("AddEditCourse")
                    .commit();
        });
        // Button click listeners
        addInstructor.setOnClickListener(v1 -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.detail_view_host, AddInstructorFragment.newInstance(this.id))
                    .addToBackStack("AddEditCourse")
                    .commit();
        });
        addAssessment.setOnClickListener(v1 -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.detail_view_host,AddEditAssessmentFragment.newInstance(-1,this.id))
                    .addToBackStack("AddEditCourse")
                    .commit();
        });
        return v;
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_edit_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int option = item.getItemId();
        if (option == R.id.menu_addedit_save) {
            if (formValidation()) {
                buildCourse();
                if (edit) {
                    courseViewModel.update(course);
                    //TODO update relationships by returning courseId;
                } else {
                    courseViewModel.insert(course);
                    //TODO insert relationships by returning courseID;
                }
            }
            return true;
        } else if (option == R.id.menu_addedit_delete) {
            //TODO:Delete
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (edit) {
            getActivity().setTitle("Edit Course");
        } else {
            getActivity().setTitle("Add Course");
        }
    }

    private boolean formValidation() {
        if (!FormValidators.nameValidation(title)) {
            title.setError("Please enter a title.");
        }
        if (!FormValidators.startDateValidation(start)) {
            start.setError("Please enter a start date.");
        }
        if (!FormValidators.endDateValidation(start,end)) {
            end.setError("Please enter a valid end date.");
        }
        if (!FormValidators.radioGroupValidation(radioGroup)) {
            planToTake.setError("Please select a status.");
        }
        if (noInstructors.getVisibility() != View.GONE) {
            noInstructors.setBackgroundColor(getResources().getColor(R.color.errorBackground, getActivity().getTheme()));
            noInstructors.setTextColor(getResources().getColor(R.color.errorText, getActivity().getTheme()));
        }
        if (noAssessments.getVisibility() != View.GONE) {
            noAssessments.setBackgroundColor(getResources().getColor(R.color.errorBackground, getActivity().getTheme()));
            noAssessments.setTextColor(getResources().getColor(R.color.errorText, getActivity().getTheme()));
        }
        return FormValidators.nameValidation(title)
                && FormValidators.startDateValidation(start)
                && FormValidators.endDateValidation(start,end)
                && FormValidators.radioGroupValidation(radioGroup)
                && noInstructors.getVisibility() == View.GONE
                && noAssessments.getVisibility() == View.GONE;
    }

    private void buildCourse() {
        String newTitle = title.getText().toString();
        ZonedDateTime newStart = DateTimeConv.stringToDateLocalWithoutTime(start.getText().toString());
        ZonedDateTime newEnd = DateTimeConv.stringToDateLocalWithoutTime(end.getText().toString());
        Status newStatus = getCourseStatus();
        String newNote = note.getText().toString();
        if (!edit) {
            this.course = new Course(newTitle,newStatus,newStart,newEnd,newNote,this.termId);
        } else {
            this.course.setTitle(newTitle);
            this.course.setStatus(newStatus);
            this.course.setStart(newStart);
            this.course.setEnd(newEnd);
            this.course.setNote(newNote);
        }
    }

    private Status getCourseStatus() {
        int option = radioGroup.getCheckedRadioButtonId();
        if (option == R.id.course_status_planToTake_radio) {
            return Status.PLAN_TO_TAKE;
        } else if (option == R.id.course_status_inProgress_radio) {
            return Status.IN_PROGRESS;
        } else if (option == R.id.course_status_complete_radio) {
            return Status.COMPLETE;
        } else {
            return Status.DROPPED;
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