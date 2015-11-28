package com.ascentbrezie.brezie.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ascentbrezie.brezie.R;
import com.ascentbrezie.brezie.data.TimelineData;
import com.ascentbrezie.brezie.utils.Constants;

import java.util.ArrayList;

/**
 * Created by ADMIN on 24-10-2015.
 */
public class TimelineRecyclerAdapter extends RecyclerView.Adapter<TimelineRecyclerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TimelineData> timelineData;

    public TimelineRecyclerAdapter(Context context) {
        this.context = context;
    }

    public TimelineRecyclerAdapter(Context context, ArrayList<TimelineData> timelineData) {
        this.context = context;
        this.timelineData = timelineData;

        Log.d(Constants.LOG_TAG,Constants.TIMELINE_RECYCLER_ADAPTER);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        public ViewHolder(View v) {
            super(v);
            view = v;

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                                    .inflate(R.layout.row_timeline,viewGroup,false);

        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        findViews(viewHolder);
        setViews(position);
    }

    public void findViews(ViewHolder viewHolder){


    }

    public void setViews(int position){



    }

    @Override
    public int getItemCount() {
        return 3;
//        return timelinedata.size();
    }

}
