package com.example.welsley.volunteach;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class popHours extends AppCompatActivity {
    private DatabaseHelper db;
    private ArrayList<String> hours;
    private ListView lv_hours;
    List<LinkedHashMap<String, String>> listItems;
    private LinkedHashMap<String, String> branchHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_hours);
        db = DatabaseHelper.getInstance(this);
        int branchId = (getIntent().getIntExtra("branchId", 0));
        hours = db.getBranchHours(branchId);

        //window sizing
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .7), (int)(height * .6));

        //hours list view
        lv_hours = (ListView)findViewById(R.id.hoursList);

        branchHours = new LinkedHashMap<>();
        branchHours.put("Monday", hours.get(0));
        branchHours.put("Tuesday", hours.get(1));
        branchHours.put("Wednesday", hours.get(2));
        branchHours.put("Thursday", hours.get(3));
        branchHours.put("Friday", hours.get(4));
        branchHours.put("Saturday", hours.get(5));
        branchHours.put("Sunday", hours.get(6));

        listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.hourslist,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.time, R.id.day});

        Iterator it = branchHours.entrySet().iterator();
        while(it.hasNext()){
            LinkedHashMap<String, String> resultMap = new LinkedHashMap<>();
            Map.Entry pair = (Map.Entry)it.next();
            resultMap.put("First Line", pair.getKey().toString());
            resultMap.put("Second Line", pair.getValue().toString());
            listItems.add(resultMap);
        }

        lv_hours.setAdapter(adapter);
    }
}
