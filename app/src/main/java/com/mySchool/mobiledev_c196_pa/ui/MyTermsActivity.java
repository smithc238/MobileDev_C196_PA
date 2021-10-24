package com.mySchool.mobiledev_c196_pa.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.data.entities.Term;
import com.mySchool.mobiledev_c196_pa.viewModel.TermViewModel;

import java.util.List;

public class MyTermsActivity extends AppCompatActivity {
    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_terms);

        RecyclerView recyclerView = findViewById(R.id.term_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final MyTermsAdapter adapter = new MyTermsAdapter();
        recyclerView.setAdapter(adapter);

        termViewModel = new ViewModelProvider(this).get(TermViewModel.class);
        termViewModel.getAllTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                adapter.setTerms(terms);
            }
        });
    }
}