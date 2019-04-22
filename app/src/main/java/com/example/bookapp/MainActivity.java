package com.example.bookapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bookapp.Adapter.TabPagerAdapter;
import com.example.bookapp.Fragments.Free;
import com.example.bookapp.Fragments.Paid;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static final String baseurl = "";
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.tbm);
        toolbar.setTitle("Book App");
        setSupportActionBar(toolbar);


        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);

        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());

        //Add Fragments
        tabPagerAdapter.AddFragment(new Free(), "Free");
        tabPagerAdapter.AddFragment(new Paid(), "Paid");

        //adapter setup
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

//        //set on menu item click listener for menu
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//
//                switch (item.getItemId()){
//                    case R.id.searchicon:
//                        Toast.makeText(getApplicationContext(), "It works", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//
//                return true;
//            }
//        });
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
    }
}