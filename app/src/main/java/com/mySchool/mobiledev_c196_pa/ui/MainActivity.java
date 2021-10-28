package com.mySchool.mobiledev_c196_pa.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mySchool.mobiledev_c196_pa.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginListener(View view) {
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        startActivity(intent);
    }
}