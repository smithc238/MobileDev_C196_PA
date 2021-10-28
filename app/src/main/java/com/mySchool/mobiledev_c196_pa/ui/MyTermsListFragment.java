package com.mySchool.mobiledev_c196_pa.ui;

import android.content.Context;
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
import android.widget.Toast;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.adapters.MyTermsAdapter;
import com.mySchool.mobiledev_c196_pa.viewmodels.TermViewModel;

public class MyTermsListFragment extends Fragment {

    private TermViewModel termViewModel;

    public MyTermsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_terms_list, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.myTerms_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MyTermsAdapter adapter = new MyTermsAdapter(v.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(getViewLifecycleOwner(), adapter::setTerms);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_view_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.list_view_add) {
            Fragment addTerm = AddEditTermFragment.newInstance(false,-1);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.list_view_host,addTerm)
                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}