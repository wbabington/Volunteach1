package com.example.welsley.volunteach;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.welsley.volunteach.adapter.CustomAdapter;
import com.example.welsley.volunteach.model.Branch;

import java.util.List;

public class LibraryList extends AppCompatActivity {
    DatabaseHelper db;
    private Session session;
    private ListView listLib;
    private CustomAdapter adapter;
    private List<Branch> branchList;
    private Branch branch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_list);
        db = DatabaseHelper.getInstance(this);

        session = new Session(this);
        if(session.checkLogin()){
            finish();
        }

        listLib = (ListView)findViewById(R.id.libListView);
        //Get list of library branches from database
        branchList = db.getListContents();
        //Initialize the adapter
        adapter = new CustomAdapter(this, branchList);
        //Set the adapter for the listView
        listLib.setAdapter(adapter);
        listLib.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        branch = (Branch)listLib.getItemAtPosition(i);
                        int value = branch.getId();
                        Intent intent = new Intent(LibraryList.this, BranchInfo.class);
                        intent.putExtra("branchId", value);
                        startActivity(intent);
                    }
                }
        );
    }
}
