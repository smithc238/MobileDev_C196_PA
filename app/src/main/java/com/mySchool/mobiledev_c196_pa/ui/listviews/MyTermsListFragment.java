package com.mySchool.mobiledev_c196_pa.ui.listviews;

import android.content.Intent;
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

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.adapters.TermListAdapter;
import com.mySchool.mobiledev_c196_pa.data.entities.Term;
import com.mySchool.mobiledev_c196_pa.ui.addedit.AddEditTermFragment;
import com.mySchool.mobiledev_c196_pa.ui.detailviews.DetailActivity;
import com.mySchool.mobiledev_c196_pa.viewmodels.TermViewModel;

public class MyTermsListFragment extends Fragment {

    private TermViewModel termViewModel;

    public MyTermsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("My Terms");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_terms_list, container, false);
        RecyclerView recyclerView = v.findViewById(R.id.myTerms_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        TermListAdapter adapter = new TermListAdapter(v.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(getViewLifecycleOwner(), adapter::setTerms);

        adapter.setOnTermClickListener(new TermListAdapter.OnTermClickListener() {
            @Override
            public void onTermClick(Term term) {
                Intent intent = DetailActivity.intentLoader(
                        getActivity(),1,term.getTitle(),term.getId());
                getActivity().startActivity(intent);
            }
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
        int id = item.getItemId();
        if (id == R.id.list_view_add) {
            Intent intent = DetailActivity.intentLoader(getActivity(),
                    -1,"Add Term",-1);
            getActivity().startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}