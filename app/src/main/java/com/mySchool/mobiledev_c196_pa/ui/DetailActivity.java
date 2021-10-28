package com.mySchool.mobiledev_c196_pa.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mySchool.mobiledev_c196_pa.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra("title"));
        long id = intent.getLongExtra("id",-1);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.detail_view_host);

        if (fragment == null) {
            Log.i("DetailActivity Term ID",String.valueOf(id));
            fragment = DetailedTermFragment.newInstance(id);
            fragmentManager.beginTransaction()
                    .add(R.id.detail_view_host,fragment)
                    .commit();
        }
    }
}