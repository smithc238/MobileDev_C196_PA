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

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;
import com.mySchool.mobiledev_c196_pa.utilities.FormValidators;
import com.mySchool.mobiledev_c196_pa.viewmodels.InstructorViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEditInstructorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditInstructorFragment extends Fragment {
    private static final String INSTRUCTOR_ID = "id";
    private InstructorViewModel instructorViewModel;
    private long id;
    private boolean edit = false;
    private EditText name;
    private EditText phone;
    private EditText email;
    private Instructor instructor;

    /**
     * Required empty constructor
     */
    public AddEditInstructorFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Instructor ID > 0 for edit and < 0 for add.
     * @return A new instance of fragment AddEditInstructorFragment.
     */
    public static AddEditInstructorFragment newInstance(long id) {
        AddEditInstructorFragment fragment = new AddEditInstructorFragment();
        Bundle args = new Bundle();
        args.putLong(INSTRUCTOR_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Method determines if instance is add or edit by the id in the bundle. Positive = edit, negative = add.
     * Sets title appropriately.
     * @param savedInstanceState savedInstanceState.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            id = getArguments().getLong(INSTRUCTOR_ID);
            if (id > 0) {
                edit = true;
                getActivity().setTitle("Edit Instructor");
            } else {
                getActivity().setTitle("Add Instructor");
            }
        }

    }

    /**
     * Method sets view for add or edit. Edit also sets the model instructor for save function.
     * @param inflater inflater.
     * @param container container.
     * @param savedInstanceState savedInstanceState.
     * @return view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_instructor, container, false);
        instructorViewModel = new ViewModelProvider(requireActivity()).get(InstructorViewModel.class);
        name = v.findViewById(R.id.instructor_name);
        phone = v.findViewById(R.id.instructor_phone);
        email = v.findViewById(R.id.instructor_email);
        if (edit) {
            instructorViewModel.getInstructorById(this.id).observe(getViewLifecycleOwner(), instructors -> {
                if (!instructors.isEmpty()) {
                    name.setText(instructors.get(0).getName());
                    phone.setText(instructors.get(0).getPhone());
                    email.setText(instructors.get(0).getEmail());
                    this.instructor = instructors.get(0);
                }
            });
        }
        return v;
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
               buildInstructor();
               if (edit) {
                   instructorViewModel.update(instructor);
               } else {
                   instructorViewModel.insert(instructor);
               }
               nextScreen();
               return true;
           }
        } else if (option == R.id.menu_addedit_delete) {
            if (edit) { instructorViewModel.delete(instructor); }
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean formValidation() {
        if (!FormValidators.nameValidation(name)) {
            name.setError("Please enter a name");
        }
        if (!FormValidators.phoneValidation(phone)) {
            phone.setError("Please enter a valid phone number.");
        }
        if (!FormValidators.emailValidation(email)) {
            email.setError("Please enter an email.");
        }
       return FormValidators.nameValidation(name)
               && FormValidators.phoneValidation(phone)
               && FormValidators.emailValidation(email);
    }

    private void buildInstructor() {
        String newName = this.name.getText().toString();
        String newPhone = this.phone.getText().toString();
        String newEmail = this.email.getText().toString();
        if (!edit) {
            this.instructor = new Instructor(newName,newPhone,newEmail);
        } else {
            this.instructor.setName(newName);
            this.instructor.setPhone(newPhone);
            this.instructor.setEmail(newEmail);
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