package com.mySchool.mobiledev_c196_pa.ui.terms;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.data.entities.Course;
import com.mySchool.mobiledev_c196_pa.utilities.DateTimeConv;
import com.mySchool.mobiledev_c196_pa.viewModel.CourseViewModel;
import com.mySchool.mobiledev_c196_pa.viewModel.TermViewModel;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observer;

public class DetailedTermActivity extends AppCompatActivity {
    private TermViewModel termViewModel;
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_term);
        TextView startDate = findViewById(R.id.detailed_term_start);
        TextView endDate = findViewById(R.id.detailed_term_end);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra("title"));
        long id = intent.getLongExtra("id",-1);
        startDate.setText(DateTimeConv.dateToStringLocal((ZonedDateTime) intent.getSerializableExtra("start")));
        endDate.setText(DateTimeConv.dateToStringLocal((ZonedDateTime) intent.getSerializableExtra("end")));

        RecyclerView recyclerView = findViewById(R.id.detailed_term_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final DetailedTermAdapter adapter = new DetailedTermAdapter(this);
        recyclerView.setAdapter(adapter);

        courseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);
        courseViewModel.getAssociatedCourses(id).observe(this, courses -> adapter.setCourses(courses));
    }
}