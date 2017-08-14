package com.example.welsley.volunteach;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.welsley.volunteach.model.Branch;
import com.example.welsley.volunteach.model.Course;

import java.util.HashMap;

import static com.example.welsley.volunteach.Session.KEY_EMAIL;
import static com.example.welsley.volunteach.Session.KEY_NAME;

public class popCourse extends AppCompatActivity {
    Session session;
    private DatabaseHelper db;
    private Button bookCourse;
    private TextView CourseHeading, CourseDesc, CourseBranchName, CourseBranchAddress, CourseTime, CourseLength, CourseInst;
    private Course oneCourse;
    private Branch oneBranch;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_course);
        db = DatabaseHelper.getInstance(this);
        session = new Session(this);
        if(session.checkLogin()){
            finish();
        }
        setWindow();

        final int courseId = (getIntent().getIntExtra("courseId", 0));
        oneCourse = db.getCourse(courseId);
        oneBranch = db.getBranch(oneCourse.getBranchId());
        name = db.getUserFullname(oneCourse.getInstId());

        CourseHeading = (TextView)findViewById(R.id.tvCourseHeading);
        CourseDesc = (TextView)findViewById(R.id.tvCourseDesc);
        CourseTime = (TextView)findViewById(R.id.tvCourseTime);
        CourseLength = (TextView)findViewById(R.id.tvCourseLength);
        CourseInst = (TextView)findViewById(R.id.tvCourseInst);
        CourseBranchName = (TextView)findViewById(R.id.tvCourseBranchName);
        CourseBranchAddress = (TextView)findViewById(R.id.tvCourseBranchAddress);

        CourseHeading.setText(oneCourse.getName());
        CourseDesc.setText(oneCourse.getDescription());
        CourseTime.setText("Time: " + oneCourse.getTime());
        CourseLength.setText("Duration: " + oneCourse.getDuration() + " hours");
        CourseInst.setText("Instructor: " + name);
        CourseBranchName.setText(oneBranch.getBranchName());
        CourseBranchAddress.setText(oneBranch.getAddress());

        bookCourse = (Button)findViewById(R.id.btnBook);
        bookCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = session.getUserDetails();
                String value1 = map.get(KEY_NAME);
                String value2 = map.get(KEY_EMAIL);
                int userId = db.getUserId(value1, value2);
                db.bookCourse(userId, courseId);
                Toast.makeText(getApplicationContext(), "Course has been booked", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setWindow(){
        //window sizing
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .8), (int)(height * .8));
    }
}
