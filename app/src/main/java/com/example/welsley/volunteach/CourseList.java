package com.example.welsley.volunteach;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.welsley.volunteach.adapter.RecycleAdapter;
import com.example.welsley.volunteach.model.Course;

import java.util.List;

public class CourseList extends AppCompatActivity {

    DatabaseHelper db;
    Session session;
    Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Course> courseList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        db = DatabaseHelper.getInstance(this);

        session = new Session(this);
        if(session.checkLogin()){
            finish();
        }

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.rvCourses);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        courseList = db.getAllCourses();
        adapter = new RecycleAdapter(courseList, this);
        recyclerView.setAdapter(adapter);
    }
}
