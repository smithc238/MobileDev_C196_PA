package com.mySchool.mobiledev_c196_pa.ui.detailviews;

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

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;
import com.mySchool.mobiledev_c196_pa.ui.addedit.AddEditInstructorFragment;
import com.mySchool.mobiledev_c196_pa.viewmodels.InstructorViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailedInstructorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedInstructorFragment extends Fragment {
    private static final String ID = "id";
    private long id;
    private InstructorViewModel instructorViewModel;
    private Instructor instructor;
    private EditText name;
    private EditText phone;
    private EditText email;

    /**
     * Required empty public constructor
     */
    public DetailedInstructorFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Instructor ID.
     * @return A new instance of fragment DetailedInstructorFragment.
     */
    public static DetailedInstructorFragment newInstance(long id) {
        DetailedInstructorFragment fragment = new DetailedInstructorFragment();
        Bundle args = new Bundle();
        args.putLong(ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Instructor Details");
        if (getArguments() != null) {
            id = getArguments().getLong(ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_instructor, container, false);
        name = v.findViewById(R.id.instructor_name);
        phone = v.findViewById(R.id.instructor_phone);
        email = v.findViewById(R.id.instructor_email);
        setEditTextViewOnly();
        instructorViewModel = new ViewModelProvider(requireActivity()).get(InstructorViewModel.class);
        instructorViewModel.getInstructorById(this.id).observe(getViewLifecycleOwner(), instructors -> {
            if (!instructors.isEmpty()) {
                    populateFields(instructors.get(0));
                    this.instructor = instructors.get(0);
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.detail_menu, menu);
        menu.removeItem(R.id.menu_detail_setNotification);
        menu.removeItem(R.id.menu_detail_cancelNotification);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_detail_edit) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.detail_view_host,
                            AddEditInstructorFragment.newInstance(this.id))
                    .addToBackStack("DetailedInstructor")
                    .commit();
        } else if (id == R.id.menu_detail_delete) {
            instructorViewModel.removeFromWorkingList(instructor);
            instructorViewModel.delete(instructor);
            nextScreen();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEditTextViewOnly() {
        name.setClickable(false);
        name.setCursorVisible(false);
        name.setFocusable(false);
        name.setFocusableInTouchMode(false);
        name.setBackground(null);
        phone.setClickable(false);
        phone.setCursorVisible(false);
        phone.setFocusable(false);
        phone.setFocusableInTouchMode(false);
        phone.setBackground(null);
        email.setClickable(false);
        email.setCursorVisible(false);
        email.setFocusable(false);
        email.setFocusableInTouchMode(false);
        email.setBackground(null);
    }

    private void populateFields(Instructor instructor) {
        name.setText(instructor.getName());
        phone.setText(instructor.getPhone());
        email.setText(instructor.getEmail());
    }

    private void nextScreen() {
        if (getParentFragmentManager().getBackStackEntryCount() == 0) {
            getActivity().finish();
        } else {
            getParentFragmentManager().popBackStack();
        }
    }
}