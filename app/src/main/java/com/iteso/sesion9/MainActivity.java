package com.iteso.sesion9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.iteso.sesion9.beans.ItemProduct;
import com.iteso.sesion9.tools.Constant;

public class MainActivity extends AppCompatActivity {

    private static final int TOTAL_PAGES = 3;
    private FragmentTechnology fragmentTechnology;
    private FragmentHome fragmentHome;
    private FragmentElectronics fragmentElectronics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        //Relate tabs with view pager content
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton floatingActionButton = findViewById(R.id.activity_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityItem.class);
                startActivityForResult(intent, Constant.ACTIVITY_DETAIL);
            }
        });

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case Constant.FRAGMENT_TECHNOLOGY:
                    if (fragmentTechnology == null)
                        fragmentTechnology = new FragmentTechnology();
                    return fragmentTechnology;
                case Constant.FRAGMENT_HOME:
                    if (fragmentHome == null)
                        fragmentHome = new FragmentHome();
                    return fragmentHome;
                case Constant.FRAGMENT_ELECTRONICS:
                    if (fragmentElectronics == null)
                        fragmentElectronics = new FragmentElectronics();
                    return fragmentElectronics;
                default:
                    return new FragmentTechnology();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return TOTAL_PAGES;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case Constant.FRAGMENT_TECHNOLOGY:
                    return getString(R.string.section1);
                case Constant.FRAGMENT_HOME:
                    return getString(R.string.section2);
                case Constant.FRAGMENT_ELECTRONICS:
                    return getString(R.string.section3);
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case R.id.action_logout:
                logout();
                break;
            case R.id.action_privacy:
                Intent intent = new Intent(this,
                        ActivityPrivacyPolicy.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout(){
        SharedPreferences sharedPreferences = getSharedPreferences(
                "com.iteso.USER_PREFERENCES", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(this,
                ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ItemProduct itemProduct;
        switch (requestCode){
            case Constant.ACTIVITY_DETAIL:
                if(resultCode == RESULT_OK){
              //      if(data.getExtras() != null) {
                        int fragment = data.getExtras().getInt(Constant.EXTRA_FRAGMENT);
                        switch (fragment) {
                            case Constant.FRAGMENT_TECHNOLOGY:
                                fragmentTechnology.onActivityResult(requestCode, resultCode, data);
                                if(data != null){
                                    itemProduct = data.getParcelableExtra("ITEM");
                                    if(itemProduct.getCategory().getName().equalsIgnoreCase("TECHNOLOGY")) {
                                        fragmentTechnology.notifyDataSetChanged(itemProduct);
                                    }
                                }
                                break;

                            case Constant.FRAGMENT_HOME:
                                fragmentHome.onActivityResult(requestCode, resultCode, data);
                                if(data != null){
                                    itemProduct = data.getParcelableExtra("ITEM");
                                    if(itemProduct.getCategory().getName().equalsIgnoreCase("HOME")) {
                                        fragmentHome.notifyDataSetChanged(itemProduct);
                                    }
                                }
                                break;
                            case Constant.FRAGMENT_ELECTRONICS:
                                fragmentElectronics.onActivityResult(requestCode, resultCode, data);
                                if(data != null){
                                    itemProduct = data.getParcelableExtra("ITEM");
                                    if(itemProduct.getCategory().getName().equalsIgnoreCase("ELECTRONICS")) {
                                        fragmentElectronics.notifyDataSetChanged(itemProduct);
                                    }
                                }
                                break;
                        }
              //      }
                }
                break;
        }
    }

}












