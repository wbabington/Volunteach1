package com.example.welsley.volunteach;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;
    private Session session;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = DatabaseHelper.getInstance(this);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mainContainer, new HomeFragment());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Your Courses");

        session = new Session(this);
        if(session.checkLogin()){
            finish();
        }

        navigationView = (NavigationView)findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.home_id:
                        //fragment home goes here
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.library_id:
                        Intent library = new Intent(MainActivity.this, LibraryList.class);
                        startActivity(library);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.course_id:
                        Intent course = new Intent(MainActivity.this, CourseList.class);
                        startActivity(course);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.createCourse_id:
                        //implement activity
                        Toast.makeText(getApplicationContext(), "Not implemented", Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout_id:
                        session.logout();
                        finish();
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }

                return false;
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
}
