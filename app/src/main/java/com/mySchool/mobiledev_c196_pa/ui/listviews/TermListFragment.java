package com.mySchool.mobiledev_c196_pa.ui.listviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.adapters.TermListAdapter;
import com.mySchool.mobiledev_c196_pa.ui.detailviews.DetailActivity;
import com.mySchool.mobiledev_c196_pa.viewmodels.TermViewModel;

public class TermListFragment extends Fragment {

    public TermListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list_view, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TermListAdapter adapter = new TermListAdapter(v.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        TermViewModel termViewModel = new ViewModelProvider(requireActivity()).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(getViewLifecycleOwner(), adapter::setTerms);
        adapter.setOnTermClickListener(term -> {
            Intent intent = DetailActivity.intentLoader(
                    getActivity(),1,term.getId());
            getActivity().startActivity(intent);
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("My Terms");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_list_add) {
            Intent intent = DetailActivity.intentLoader(getActivity(), -1,-1);
            getActivity().startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}