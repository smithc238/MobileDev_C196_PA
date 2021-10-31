package com.mySchool.mobiledev_c196_pa.ui.listviews;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentOnAttachListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.adapters.InstructorsListAdapter;
import com.mySchool.mobiledev_c196_pa.data.entities.Instructor;
import com.mySchool.mobiledev_c196_pa.ui.addedit.AddEditInstructorFragment;
import com.mySchool.mobiledev_c196_pa.ui.detailviews.DetailActivity;
import com.mySchool.mobiledev_c196_pa.viewmodels.InstructorViewModel;

public class InstructorListFragment extends Fragment {
    private static final String INSTRUCTOR_ID = "id";
    private InstructorViewModel instructorViewModel;
    private long id;

    public InstructorListFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Instructor ID.
     * @return A new instance of fragment InstructorListFragment.
     */
    public static InstructorListFragment newInstance(long id) {
        InstructorListFragment fragment = new InstructorListFragment();
        Bundle args = new Bundle();
        args.putLong(INSTRUCTOR_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Instructors");
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            id = getArguments().getLong(INSTRUCTOR_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_instructor_list, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.instructor_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        InstructorsListAdapter adapter = new InstructorsListAdapter(v.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        instructorViewModel = new ViewModelProvider(this).get(InstructorViewModel.class);
        instructorViewModel.getAllInstructors().observe(getViewLifecycleOwner(), instructors -> {
            adapter.setInstructors(instructors);
        });

        adapter.setOnInstructorClickListener(instructor -> {
            Intent intent = DetailActivity.intentLoader(
                    getActivity(), 4, instructor.getName(), instructor.getId());
            getActivity().startActivity(intent);
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.list_view_add) {
            Intent intent = DetailActivity.intentLoader(getActivity(),
                    -4,"new",-4);
            getActivity().startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}