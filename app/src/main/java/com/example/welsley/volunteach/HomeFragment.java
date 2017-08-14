package com.example.welsley.volunteach;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.welsley.volunteach.adapter.RecycleAdapter;
import com.example.welsley.volunteach.model.Course;

import java.util.HashMap;
import java.util.List;

import static com.example.welsley.volunteach.Session.KEY_EMAIL;
import static com.example.welsley.volunteach.Session.KEY_NAME;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    DatabaseHelper db;
    Session session;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Course> courseList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = DatabaseHelper.getInstance(getActivity());
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        session = new Session(getActivity());

        recyclerView = (RecyclerView)view.findViewById(R.id.rvUserCourses);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        HashMap<String, String> map = session.getUserDetails();
        String value1 = map.get(KEY_NAME);
        String value2 = map.get(KEY_EMAIL);
        int userId = db.getUserId(value1, value2);
        courseList = db.getAllUserCourses(userId);

        adapter = new RecycleAdapter(courseList, getActivity());
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

}
