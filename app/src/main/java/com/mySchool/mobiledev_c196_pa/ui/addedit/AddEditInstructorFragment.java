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
import com.mySchool.mobiledev_c196_pa.viewmodels.InstructorViewModel;

/**
 * Class to add or edit an Instructor
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
     * @param id Instructor ID.
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
     * @param savedInstanceState
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
        View v = inflater.inflate(R.layout.fragment_detailed_instructor, container, false);
        instructorViewModel = new ViewModelProvider(requireActivity()).get(InstructorViewModel.class);
        name = v.findViewById(R.id.detailed_instructor_name);
        phone = v.findViewById(R.id.detailed_instructor_phone);
        email = v.findViewById(R.id.detailed_instructor_email);
        if (edit) {
            instructorViewModel.getInstructor().observe(getViewLifecycleOwner(), instructor -> {
                if (instructor != null) {
                    name.setText(instructor.getName());
                    phone.setText(instructor.getPhone());
                    email.setText(instructor.getEmail());
                }
            });
            this.instructor = instructorViewModel.getInstructor().getValue();
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
        int id = item.getItemId();
        if (id == R.id.menu_addedit_save) {
           if (nameValidation(name)
           && phoneValidation(phone)
           && emailValidation(email)) {
               buildInstructor();
               if (edit) {
                   instructorViewModel.update(instructor);
               } else {
                   instructorViewModel.insert(instructor);
               }
               nextScreen();
               return true;
           }
        }
        if (id == R.id.menu_addedit_delete) {
            instructorViewModel.delete(instructor);
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean nameValidation(EditText editText) {
        String name = editText.getText().toString().trim();
        if (!name.isEmpty()) {
            return true;
        }
        editText.setError("Please enter a name");
        return false;
    }

    private boolean phoneValidation(EditText editText) {
       String phone = editText.getText().toString().trim();
       if (phone.length() < 10) {
           editText.setError("Please enter a valid phone number.");
           return false;
       }
       return true;
    }

    private boolean emailValidation(EditText editText) {
        String email = editText.getText().toString().trim();
        if (!email.isEmpty()) {
            return true;
        }
        editText.setError("Please enter an email.");
        return false;
    }

    private void buildInstructor() {
        if (!edit) {
            this.instructor = new Instructor("John Smith","111-111-1111","john.smith@myschool.com");
        }
        this.instructor.setName(this.name.getText().toString());
        this.instructor.setPhone(this.phone.getText().toString());
        this.instructor.setEmail(this.email.getText().toString());
    }

    private void nextScreen() {
        if (getParentFragmentManager().getBackStackEntryCount() == 0) {
            getActivity().finish();
        } else {
            getParentFragmentManager().popBackStack();
        }
    }
}