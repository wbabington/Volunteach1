package com.example.welsley.volunteach.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.welsley.volunteach.R;
import com.example.welsley.volunteach.model.Branch;

import java.util.List;

/**
 * Created by Welsley on 8/11/2017.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    private List<Branch> branchList;

    public CustomAdapter(Context context, List<Branch> branchList) {
        this.context = context;
        this.branchList = branchList;
    }

    @Override
    public int getCount() {
        return branchList.size();
    }

    @Override
    public Object getItem(int i) {
        return branchList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return branchList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.customlibrarylist, null);
        TextView name = (TextView)v.findViewById(R.id.tv_Name);
        TextView location = (TextView)v.findViewById(R.id.tv_Location);

        name.setText(branchList.get(i).getBranchName());
        location.setText(branchList.get(i).getAddress());

        return v;
    }
}
