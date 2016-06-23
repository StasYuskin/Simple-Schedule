package ua.pp.danielyuzek.simple_schedule.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;

import ua.pp.danielyuzek.simple_schedule.R;
import ua.pp.danielyuzek.simple_schedule.adapters.ViewPagerAdapter;
import ua.pp.danielyuzek.simple_schedule.models.Data;
import ua.pp.danielyuzek.simple_schedule.models.Group;
import ua.pp.danielyuzek.simple_schedule.models.Week;
import ua.pp.danielyuzek.simple_schedule.utilities.Logger;
import ua.pp.danielyuzek.simple_schedule.utilities.UserSettings;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private View mTextChooseGroup;
    private DrawerLayout mDrawer;

    private ViewPagerAdapter mViewPagerAdapter;

    private Data mData;
    private Integer mWeekNumber;
    private Integer mCurrentWeekNumber;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbarWithDrawer();
        initViews();

        mData = UserSettings.getData();
        if (mData == null) {
            showProgress();
        } else {
            setupView();
        }

        getDatabaseData();
        openNavigationDrawer();

    }

    private void openNavigationDrawer() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isFirstStart = UserSettings.getBoolean(UserSettings.FIRST_APP_RUN, false);
                // if it was the first app start
                if (!isFirstStart && mDrawer != null) {
                    mDrawer.openDrawer(Gravity.LEFT);
                    UserSettings.setBoolean(UserSettings.FIRST_APP_RUN, true);
                }
            }
        });
        thread.start();
    }

    private void initToolbarWithDrawer() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initViews() {
        mTextChooseGroup = findViewById(R.id.text_choose_group);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
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
                UserSettings.setData(snapshot.getValue(Data.class));
                if (mData == null) {
                    mData = UserSettings.getData();
                    setupView();
                }
                hideProgress();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Logger.e(MainActivity.class.getSimpleName(), "ERROR: " + databaseError);
                hideProgress();
            }
        });
    }

    private void setupView() {
        setCurrentWeek();

        //todo delete - set null group for test
        //UserSettings.setString(UserSettings.USER_GROUP, null);

        String userGroup = UserSettings.getString(UserSettings.USER_GROUP);
        if (userGroup !=null && !userGroup.isEmpty()) {

            for (Group group : mData.getGroups()) {

                if (group.getName().equals(userGroup)) {
                    mViewPager.setVisibility(View.VISIBLE);
                    mTextChooseGroup.setVisibility(View.GONE);
                    initViewPager(group);
                }
            }
        } else {
            mViewPager.setVisibility(View.GONE);
            mTextChooseGroup.setVisibility(View.VISIBLE);
        }
    }

    private void initViewPager(Group group) {
        Week week = group.getSchedule().getCurrentWeek();

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), week, mWeekNumber);
        mViewPager.setAdapter(mViewPagerAdapter);

        //set tablayout with viewpager
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        setCurrentDay();
    }

    private void updateViewPagerData(int weekNumber) {
        if (mViewPager != null && mViewPagerAdapter != null) {
            Week week = mData.getGroups().get(0).getSchedule().getWeek(weekNumber);
            mViewPagerAdapter.update(week, mWeekNumber);
            mViewPager.setCurrentItem(0);
        }
    }

    private void prevWeek() {
        mWeekNumber--;
        updateViewPagerData(mWeekNumber);
    }

    private void nextWeek() {
        mWeekNumber++;
        updateViewPagerData(mWeekNumber);
    }

    private void currentWeek() {
        setCurrentWeek();
        updateViewPagerData(mWeekNumber);
    }

    private void setCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        mCurrentWeekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
        mWeekNumber = calendar.get(Calendar.WEEK_OF_YEAR);
    }

    private void setCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        mViewPager.setCurrentItem(day - 2);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawer != null && mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.prev_week) {
            prevWeek();
            return true;
        } else if (id == R.id.current_week) {
            currentWeek();
            return true;
        } else if (id == R.id.next_week) {
            nextWeek();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_settings) {
            Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
            MainActivity.this.startActivityForResult(myIntent, 99);
        } else if (id == R.id.nav_info) {
            Intent myIntent = new Intent(MainActivity.this, AboutActivity.class);
            MainActivity.this.startActivity(myIntent);
        }

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawer != null) {
            mDrawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 99) {
            setupView();
        }
    }
}
