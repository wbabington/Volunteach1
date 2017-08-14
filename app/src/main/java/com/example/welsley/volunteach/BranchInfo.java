package com.example.welsley.volunteach;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.welsley.volunteach.adapter.RecycleAdapter;
import com.example.welsley.volunteach.model.Branch;
import com.example.welsley.volunteach.model.Course;

import java.util.List;

public class BranchInfo extends AppCompatActivity {
    private TextView branchName, branchAddress, phone;
    private Button btnHours;
    private ListView lv_course;
    private DatabaseHelper db;
    private int branchId;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_info);

        db = DatabaseHelper.getInstance(this);
        branchId = (getIntent().getIntExtra("branchId", 0));
/*
        session = new Session(this);
        if(!session.Login()){
            logout();
        }
*/
        branchName = (TextView)findViewById(R.id.tv_cv_name);
        branchAddress = (TextView)findViewById(R.id.tv_cv_address);
        phone = (TextView)findViewById(R.id.tv_cv_phone);

        btnHours = (Button)findViewById(R.id.pop_hours);
        btnHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BranchInfo.this, popHours.class);
                intent.putExtra("branchId", branchId);
                startActivity(intent);
            }
        });
//        lv_course = (ListView)findViewById(R.id.listCourses);

        //get single Branch information from database
        Branch branchInfo = db.getBranch(branchId);
        if(branchInfo != null){
            branchName.setText(branchInfo.getBranchName());
            branchAddress.setText(branchInfo.getAddress());
            phone.setText(branchInfo.getTelephone());
        }

        courseList = db.getCourseByBranch(branchId);

        recyclerView = (RecyclerView) findViewById(R.id.rvBranchCourses);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecycleAdapter(courseList, this);
        recyclerView.setAdapter(adapter);
    }

/*
    private void logout(){
        session.setLogin(false);
        finish();
        startActivity(new Intent(LibraryList.this, LoginActivity.class));
    }
*/
}
