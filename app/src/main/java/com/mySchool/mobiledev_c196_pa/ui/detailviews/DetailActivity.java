package com.mySchool.mobiledev_c196_pa.ui.detailviews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.mySchool.mobiledev_c196_pa.R;
import com.mySchool.mobiledev_c196_pa.ui.addedit.AddEditInstructorFragment;
import com.mySchool.mobiledev_c196_pa.ui.addedit.AddEditTermFragment;
import com.mySchool.mobiledev_c196_pa.viewmodels.InstructorViewModel;

public class DetailActivity extends AppCompatActivity {
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private InstructorViewModel instructorViewModel;
    private static final String TYPE = "type";
    private static final String TITLE = "title";
    private static final String ID = "id";
    private int type;
    private String title;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        instructorViewModel = new ViewModelProvider(this).get(InstructorViewModel.class);

        Intent intent = getIntent();
        this.type = intent.getIntExtra(TYPE, 0);
        this.title = intent.getStringExtra(TITLE);
        this.id = intent.getLongExtra(ID,-1);

        if (savedInstanceState == null) {
            fragmentLauncher(fragmentSwitch(this.type));
        }
    }

    /**
     * Prepares the Intent for launching DetailActivity with correct fragment.
     * @param context Context.
     * @param type 1=Term, 2=Course, 3=Assessment, 4=Instructor, negatives are Add.
     * @param title Name of the Entity.
     * @param id Primary Key of the Entity.
     * @return Intent to send to Detail Activity.
     */
    public static Intent intentLoader(Context context, int type, String title, long id) {
        Intent intent = new Intent(context,DetailActivity.class);
        intent.putExtra(TYPE,type);
        intent.putExtra(TITLE,title);
        intent.putExtra(ID,id);
        return intent;
    }

    private void fragmentLauncher(Fragment fragment) {
        fragmentManager.beginTransaction()
                .add(R.id.detail_view_host,fragment)
                .commit();
    }

    private Fragment fragmentSwitch(int type) {
        switch (type) {
            default:
                return DetailedTermFragment.newInstance(this.title,this.id);
            case 2:
                //return CourseDetail
            case 3:
                //return AssessmentDetail
            case 4:
                return DetailedInstructorFragment.newInstance(this.id);
            case -1:
                return AddEditTermFragment.newInstance(this.id);
            case -2:
                //add Course
            case -3:
                //add Assessment
            case -4:
                return AddEditInstructorFragment.newInstance(this.id);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}