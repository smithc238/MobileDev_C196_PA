package com.mySchool.mobiledev_c196_pa.ui.detailviews;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
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
import android.widget.Toast;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.data.entities.Assessment;
import com.mySchool.mobiledev_c196_pa.data.entities.ExamType;
import com.mySchool.mobiledev_c196_pa.ui.addedit.AddEditAssessmentFragment;
import com.mySchool.mobiledev_c196_pa.utilities.AppNotifications;
import com.mySchool.mobiledev_c196_pa.utilities.DateTimeConv;
import com.mySchool.mobiledev_c196_pa.viewmodels.AssessmentViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailedAssessmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedAssessmentFragment extends Fragment {
    private static final String ASSESSMENT_ID = "id";
    private long id;
    AssessmentViewModel assessmentViewModel;
    Assessment assessment;
    private EditText title;
    private RadioButton objective;
    private RadioButton performance;
    private EditText start;
    private EditText end;
    private EditText description;
    private boolean alarmIsOn;
    private Menu menu;

    /**
     * Required empty public constructor
     */
    public DetailedAssessmentFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Assessment ID.
     * @return A new instance of fragment DetailedAssessmentFragment.
     */
    public static DetailedAssessmentFragment newInstance(long id) {
        DetailedAssessmentFragment fragment = new DetailedAssessmentFragment();
        Bundle args = new Bundle();
        args.putLong(ASSESSMENT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            id = getArguments().getLong(ASSESSMENT_ID);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu,menu);
        menu.removeItem(R.id.menu_detail_share);
        this.menu = menu;
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
                this.assessment = assessments.get(0);
                checkAlarm(assessments.get(0));
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Assessment Details");
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
        description.setClickable(false);
        description.setCursorVisible(false);
        description.setFocusable(false);
        description.setFocusableInTouchMode(false);
        description.setBackground(null);
    }

    private void prepareTypeRadio(ExamType examType) {
        if (examType == ExamType.OBJECTIVE) {
            objective.toggle();
        } else {
            performance.toggle();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int option = item.getItemId();
        if (option == R.id.menu_detail_edit) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.detail_view_host,
                            AddEditAssessmentFragment.newInstance(this.id,this.assessment.getCourseId()))
                    .addToBackStack("DetailedAssessment")
                    .commit();
        } else if (option == R.id.menu_detail_delete) {
            assessmentViewModel.removeFromWorkingList(this.assessment);
            assessmentViewModel.delete(this.assessment);
            nextScreen();
        } else if (option == R.id.menu_detail_setNotification) {
            Toast.makeText(getActivity(), "Reminder Set", Toast.LENGTH_SHORT).show();
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            PendingIntent beginning = AppNotifications.pendingIntentLoader(getActivity(),
                    2, assessment.getTitle() + " begins today.",
                    (int) assessment.getId(), false);
            PendingIntent ending = AppNotifications.pendingIntentLoader(getActivity(),
                    2,assessment.getTitle() + " ends today.",
                    (int) assessment.getId(), true);
            long beginTrigger = assessment.getStart().toEpochSecond()*1000;
            long endTrigger = assessment.getEnd().toEpochSecond()*1000;
            alarmManager.set(AlarmManager.RTC_WAKEUP, beginTrigger, beginning);
            alarmManager.set(AlarmManager.RTC_WAKEUP, endTrigger, ending);
            alarmIsOn = true;
            setBellIcon();
            return true;
        } else if (option == R.id.menu_detail_cancelNotification) {
            Toast.makeText(getActivity(), "Reminder Canceled", Toast.LENGTH_SHORT).show();
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
            PendingIntent beginning = AppNotifications.pendingIntentLoader(getActivity(),
                    2, assessment.getTitle() + " begins today.",
                    (int) assessment.getId(), false);
            PendingIntent ending = AppNotifications.pendingIntentLoader(getActivity(),
                    2,assessment.getTitle() + " ends today.",
                    (int) assessment.getId(), true);
            alarmManager.cancel(beginning);
            alarmManager.cancel(ending);
            beginning.cancel();
            ending.cancel();
            alarmIsOn = false;
            setBellIcon();
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

    private void checkAlarm(Assessment assessment) {
        this.alarmIsOn = AppNotifications.checkPendingIntent(getActivity(),
                2,assessment.getTitle() + " ends today.",
                (int) assessment.getId(), true);
        setBellIcon();
    }

    private void setBellIcon() {
        MenuItem off = menu.findItem(R.id.menu_detail_setNotification);
        MenuItem on = menu.findItem(R.id.menu_detail_cancelNotification);
        if (alarmIsOn) {
            off.setVisible(false);
            on.setVisible(true);
        } else {
            off.setVisible(true);
            on.setVisible(false);
        }
    }
}