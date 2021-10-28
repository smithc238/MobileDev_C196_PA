package com.mySchool.mobiledev_c196_pa.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.mySchool.mobiledev_c196_pa.R;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.list_view_host);

        if (fragment == null) {
            fragment = new MyTermsListFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.list_view_host,fragment)
                    .commit();
        }
    }
}