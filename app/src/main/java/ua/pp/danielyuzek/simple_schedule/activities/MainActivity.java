package ua.pp.danielyuzek.simple_schedule.activities;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ua.pp.danielyuzek.simple_schedule.R;
import ua.pp.danielyuzek.simple_schedule.fragments.DayFragment;
import ua.pp.danielyuzek.simple_schedule.models.Data;
import ua.pp.danielyuzek.simple_schedule.models.Day;
import ua.pp.danielyuzek.simple_schedule.models.Week;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private Data mData;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //showProgress();

        getDatabaseData();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

    }

    private void showProgress() {
        mProgress = new ProgressDialog(this);
        mProgress.setMessage("Loading...");
        mProgress.setCancelable(false);
        mProgress.show();
    }

    private void hideProgress() {
        if (mProgress != null) {
            mProgress.dismiss();
        }
    }

    private void getDatabaseData() {
        // Get a reference to our posts
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mDatabase.getRoot().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //Getting the data from snapshot
                mData = snapshot.getValue(Data.class);
                setupViewPager(viewPager);
                setCurrentDay();
                //hideProgress();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("ERROR: " + databaseError);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {

        //todo get true week
        Week week = mData.getGroups().get(0).getSchedule().getWeeks().get(0);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (Day day : week.getDays()) {
            adapter.addFragment(DayFragment.newInstance(day), day.getName());
        }
        viewPager.setAdapter(adapter);

        //set tablayout with viewpager
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    private void setCurrentDay() {

        //todo check not saturday or sunday
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.WEEK_OF_YEAR);
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        viewPager.setCurrentItem(day-1);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
