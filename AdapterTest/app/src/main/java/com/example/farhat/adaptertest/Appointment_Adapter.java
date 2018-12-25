package com.example.farhat.adaptertest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Appointment_Adapter extends BaseAdapter {

    List<MainActivity.Appointment> apptList;
    Context context;
    LayoutInflater inflater;

    // just a general default constructor I THINK ??
    public Appointment_Adapter(Context context, List<MainActivity.Appointment> apptList){
        this.context = context;
        this.apptList = apptList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return apptList.size();
    }

    @Override
    public Object getItem(int position) {
        return apptList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // this is where the magic happens
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.appointment_box, viewGroup, false);
        }

        TextView apptTitle =  view.findViewById(R.id.titleText);
        apptTitle.setText(apptList.get(position).appt_Title);

        TextView timeFrame = view.findViewById(R.id.timeFrameText);
        timeFrame.setText(apptList.get(position).appt_Time);

        TextView DesqID = view.findViewById(R.id.desqIDText);
        DesqID.setText(apptList.get(position).appt_desq);

        TextView Location = view.findViewById(R.id.locationText);
        Location.setText(apptList.get(position).appt_Location);


        return view;
    }
}
