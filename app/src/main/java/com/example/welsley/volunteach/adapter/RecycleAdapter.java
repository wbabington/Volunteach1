package com.example.welsley.volunteach.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.welsley.volunteach.R;
import com.example.welsley.volunteach.model.Course;
import com.example.welsley.volunteach.popCourse;

import java.util.List;

/**
 * Created by Welsley on 8/12/2017.
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {

    private List<Course> listItems;
    private Context context;

    public RecycleAdapter(List<Course> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //Reminder:
        //all information needed for next activity can be found in the listItem
        final Course listItem = listItems.get(position);

        holder.tvHeading.setText(listItem.getName());
        holder.tvDescription.setText(listItem.getDescription());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), popCourse.class);
                intent.putExtra("courseId", listItem.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView tvHeading;
        public TextView tvDescription;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            tvHeading = (TextView)itemView.findViewById(R.id.cardHeading);
            tvDescription = (TextView)itemView.findViewById(R.id.cardDescription);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.rvLinearLayout);
        }
    }
}
