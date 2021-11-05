package com.mySchool.mobiledev_c196_pa.ui.detailviews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Database;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.data.entities.ExamType;
import com.mySchool.mobiledev_c196_pa.utilities.DateTimeConv;
import com.mySchool.mobiledev_c196_pa.viewmodels.AssessmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailedAssessmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedAssessmentFragment extends Fragment {
    private static final String ASSESSMENT_ID = "id";
    private static final String COURSE_ID = "courseId";
    private long id;
    private Long courseId;
    AssessmentViewModel assessmentViewModel;
    private EditText title;
    private RadioButton objective;
    private RadioButton performance;
    private EditText start;
    private EditText end;
    private EditText description;

    public DetailedAssessmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Assessment ID.
     * @param courseId Course ID.
     * @return A new instance of fragment DetailedAssessmentFragment.
     */
    public static DetailedAssessmentFragment newInstance(long id, Long courseId) {
        DetailedAssessmentFragment fragment = new DetailedAssessmentFragment();
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
        View v = inflater.inflate(R.layout.fragment_assessment, container, false);
        prepareFields(v);

        assessmentViewModel = new ViewModelProvider(requireActivity()).get(AssessmentViewModel.class);
        assessmentViewModel.getAssessmentById(this.id).observe(getViewLifecycleOwner(),assessments -> {
            if (!assessments.isEmpty()) {
                title.setText(assessments.get(0).getTitle());
                prepareTypeRadio(assessments.get(0).getType());
                start.setText(DateTimeConv.dateToStringLocal(assessments.get(0).getStart()));
                end.setText(DateTimeConv.dateToStringLocal(assessments.get(0).getEnd()));
                description.setText(assessments.get(0).getDescription());
            }
        });
        return v;
    }

    private void prepareFields(View v) {
        title = v.findViewById(R.id.assessment_title);
        objective = v.findViewById(R.id.assessment_type_objective_radio);
        performance = v.findViewById(R.id.assessment_type_performance_radio);
        start = v.findViewById(R.id.assessment_start);
        end = v.findViewById(R.id.assessment_end);
        description = v.findViewById(R.id.assessment_description);
        title.setClickable(false);
        title.setCursorVisible(false);
        title.setFocusable(false);
        title.setFocusableInTouchMode(false);
        title.setBackground(null);
        start.setBackground(null);
        end.setBackground(null);
        description.setBackground(null);
    }

    private void prepareTypeRadio(ExamType examType) {
        if (examType == ExamType.OBJECTIVE) {
            objective.toggle();
        } else {
            performance.toggle();
        }
    }
}