package com.apps.salilgokhale.expensrapp;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.apps.salilgokhale.expensrapp.CustomTextView.ToolbarTextView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView, DatePickerFragment.OnDateSelectedListener, AddBatchDialogFragment.OnBatchCreatedListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ToolbarTextView mTitle;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private MainActivityPresenterImpl presenter;

    private int[] tabIcons = {
            R.drawable.ic_library_add_white_36dp,
            R.drawable.ic_purchase_order_96
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Butterknife
        ButterKnife.bind(this);

        // Toolbar set up

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTitle = (ToolbarTextView) findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.app_name_title);

        // Tab set up

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        // Presenter set up

        presenter = new MainActivityPresenterImpl(this);

        // Fab set up

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBatchDialogFragment batchDialogFragment = AddBatchDialogFragment.newInstance();
                batchDialogFragment.show(getSupportFragmentManager(), "aB");
                batchDialogFragment.setCancelable(true);
            }
        });

    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    private void setupViewPager(final ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AddExpenseFragment(), "ONE");
        adapter.addFragment(new BatchesFragment(), "TWO");
        viewPager.setAdapter(adapter);

        final ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 1:
                        setFloatingActionButttonVisible(true);
                        break;

                    default:
                        setFloatingActionButttonVisible(false);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        viewPager.addOnPageChangeListener(onPageChangeListener);

        /*viewPager.post(new Runnable() {
            @Override
            public void run() {
                onPageChangeListener.onPageSelected(viewPager.getCurrentItem());
            }
        }); */
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

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
            //return mFragmentTitleList.get(position);
        }
    }

    public void setFloatingActionButttonVisible(boolean visible) {
        //CoordinatorLayout fabLayout = (CoordinatorLayout) fab.getParent();
        if(visible) {
            //fabLayout.setVisibility(View.VISIBLE);
            fab.show();
            //}else if(fabLayout.getVisibility()==View.VISIBLE){
        } else {
            fab.hide();
        }
    }

    @Override
    public void onDateSelected(String userinput){

        ((AddExpenseFragment) ((ViewPagerAdapter) viewPager.getAdapter()).getItem(0)).updateDateTVs(userinput);

    }

    @Override
    public void createBatch(String batchName) {
        presenter.createBatch(batchName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    /*

    Calendar c = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("dd/M/YY");
        String date = format1.format(c.getTime());
        Expense.eType type = Expense.eType.Taxi;
        Expense expenseTest = new Expense(date, type);

        mDatabaseReference.push().setValue(expenseTest);

        // Read from the database
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Expense expense = dataSnapshot.getValue(Expense.class);
                Log.d("expense name read: ", expense.getReceiptDate());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };

        mDatabaseReference.addChildEventListener(mChildEventListener);

    */


}
