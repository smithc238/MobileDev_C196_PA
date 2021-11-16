package com.mySchool.mobiledev_c196_pa.ui.addedit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.adapters.InstructorsListAdapter;
import com.mySchool.mobiledev_c196_pa.viewmodels.InstructorViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddInstructorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddInstructorFragment extends Fragment {
    private static final String COURSE_ID = "id";
    private long id;
    private InstructorViewModel instructorViewModel;

    /**
     * Required empty public constructor
     */
    public AddInstructorFragment() {}

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Course ID.
     * @return A new instance of fragment AddInstructorDialog.
     */
    public static AddInstructorFragment newInstance(long id){
        AddInstructorFragment fragment = new AddInstructorFragment();
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
            id = getArguments().getLong(COURSE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        InstructorsListAdapter adapter = new InstructorsListAdapter(v.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        instructorViewModel = new ViewModelProvider(requireActivity()).get(InstructorViewModel.class);
        instructorViewModel.getAllInstructors().observe(getViewLifecycleOwner(),
                instructors -> adapter.setInstructors(instructors));
        adapter.setOnInstructorClickListener(instructor -> {
            instructorViewModel.addToWorkingList(instructor);
            getParentFragmentManager().popBackStack();
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int option = item.getItemId();
        if (option == R.id.menu_list_add) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.detail_view_host,AddEditInstructorFragment.newInstance(-1))
                    .addToBackStack("AddInstructor")
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Select an Instructor");
    }
}