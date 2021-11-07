package com.mySchool.mobiledev_c196_pa.ui.addedit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.data.entities.Assessment;
import com.mySchool.mobiledev_c196_pa.data.entities.ExamType;
import com.mySchool.mobiledev_c196_pa.utilities.DateFormFiller;
import com.mySchool.mobiledev_c196_pa.utilities.DateTimeConv;
import com.mySchool.mobiledev_c196_pa.utilities.FormValidators;
import com.mySchool.mobiledev_c196_pa.viewmodels.AssessmentViewModel;

import java.time.ZonedDateTime;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEditAssessmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditAssessmentFragment extends Fragment {
    private static final String ASSESSMENT_ID = "id";
    private static final String COURSE_ID = "courseId";
    private long id;
    private long courseId;
    AssessmentViewModel assessmentViewModel;
    Assessment assessment;
    private EditText title;
    private RadioGroup radioGroup;
    private RadioButton objective;
    private RadioButton performance;
    private EditText start;
    private EditText end;
    private EditText description;
    private boolean edit;

    /**
     * Required empty public constructor
     */
    public AddEditAssessmentFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Assessment ID.
     * @param courseId Course ID.
     * @return A new instance of fragment AddEditAssessmentFragment.
     */
    public static AddEditAssessmentFragment newInstance(long id, long courseId) {
        AddEditAssessmentFragment fragment = new AddEditAssessmentFragment();
        Bundle args = new Bundle();
        args.putLong(ASSESSMENT_ID, id);
        args.putLong(COURSE_ID, courseId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            id = getArguments().getLong(ASSESSMENT_ID);
            courseId = getArguments().getLong(COURSE_ID);
            edit = id > 0;
            if (edit) {
                getActivity().setTitle("Edit Assessment");
            } else {
                //TODO: Test add assessment and delete new
                getActivity().setTitle("Add Assessment");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_assessment, container, false);
        prepareFields(v);
        assessmentViewModel = new ViewModelProvider(requireActivity()).get(AssessmentViewModel.class);
        if (edit) {
            assessmentViewModel.getAssessmentById(this.id).observe(getViewLifecycleOwner(),assessments -> {
                if (!assessments.isEmpty()) {
                    title.setText(assessments.get(0).getTitle());
                    selectExamType(assessments.get(0).getType());
                    start.setText(DateTimeConv.dateToStringLocal(assessments.get(0).getStart()));
                    end.setText(DateTimeConv.dateToStringLocal(assessments.get(0).getEnd()));
                    DateFormFiller.dateOnClickDatePicker(start,assessments.get(0).getStart());
                    DateFormFiller.dateOnClickDatePicker(end,assessments.get(0).getEnd());
                    description.setText(assessments.get(0).getDescription());
                    this.assessment = assessments.get(0);
                }
            });
        } else {
            DateFormFiller.dateOnClickDatePicker(start,null);
            DateFormFiller.dateOnClickDatePicker(end,null);
        }
        return v;
    }

    private void prepareFields(View v) {
        title = v.findViewById(R.id.assessment_title);
        radioGroup = v.findViewById(R.id.assessment_type_radioGroup);
        objective = v.findViewById(R.id.assessment_type_objective_radio);
        performance = v.findViewById(R.id.assessment_type_performance_radio);
        start = v.findViewById(R.id.assessment_start);
        end = v.findViewById(R.id.assessment_end);
        description = v.findViewById(R.id.assessment_description);
    }

    private void selectExamType(ExamType examType) {
        if (examType == ExamType.OBJECTIVE) {
            objective.toggle();
        } else {
            performance.toggle();
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
                buildAssessment();
                if (edit) {
                    assessmentViewModel.update(assessment);
                } else {
                    assessmentViewModel.insert(assessment);
                }
                nextScreen();
                return true;
            }
        } else if (option == R.id.menu_addedit_delete) {
            if (edit) { assessmentViewModel.delete(assessment); }
            nextScreen();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean formValidation() {
        if (!FormValidators.nameValidation(title)) {
            title.setError("Please enter a title.");
        }
        if (!FormValidators.radioGroupValidation(radioGroup)) {
            objective.setError("Please select a type.");
        }
        if (!FormValidators.startDateValidation(start)) {
            start.setError("Please select a start date.");
        }
        if (!FormValidators.endDateValidation(start,end)) {
            end.setError("Please select an end date.");
        }
        if (!FormValidators.nameValidation(description)) {
            description.setError("Please write a description.");
        }
        return FormValidators.nameValidation(title)
                && FormValidators.radioGroupValidation(radioGroup)
                && FormValidators.startDateValidation(start)
                && FormValidators.endDateValidation(start,end)
                && FormValidators.nameValidation(description);
    }

    private void buildAssessment() {
        String newTitle = this.title.getText().toString();
        ExamType newType = getExamType();
        ZonedDateTime newStart = DateTimeConv.stringToDateLocalWithoutTime(this.start.getText().toString());
        ZonedDateTime newEnd = DateTimeConv.stringToDateLocalWithoutTime(this.end.getText().toString());
        String newDesc = this.description.getText().toString();
        if (!edit) {
            this.assessment = new Assessment(newTitle,newStart,newEnd,newDesc,newType,this.courseId);
        } else {
            this.assessment.setTitle(newTitle);
            this.assessment.setType(newType);
            this.assessment.setStart(newStart);
            this.assessment.setEnd(newEnd);
            this.assessment.setDescription(newDesc);
            this.assessment.setCourseId(this.courseId);
        }
    }

    private ExamType getExamType() {
        int option = radioGroup.getCheckedRadioButtonId();
        if (option == objective.getId()) {
            return ExamType.OBJECTIVE;
        } else {
            return ExamType.PERFORMANCE;
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