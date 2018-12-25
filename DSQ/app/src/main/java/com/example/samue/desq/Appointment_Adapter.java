package com.example.samue.desq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Appointment_Adapter extends BaseAdapter {

    ArrayList<AppointmentData> apptList;
    Context context;
    LayoutInflater inflater;

    // just a general default constructor I THINK ??
    public Appointment_Adapter(Context context, ArrayList<AppointmentData> apptList){
        //super(context, 0, apptList);
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

    // this is where the magic happens (assigning values from the xml to the appointment adapter)
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.appointment_box, viewGroup, false);
        }

        TextView apptTitle =  view.findViewById(R.id.titleText);
        apptTitle.setText(apptList.get(position).title);

        TextView timeFrame = view.findViewById(R.id.timeFrameText);
        timeFrame.setText(apptList.get(position).startTime);

        TextView DesqID = view.findViewById(R.id.desqIDText);
        DesqID.setText("Omar");
        //DesqID.setText(apptList.get(position).appt_desq);

        TextView Location = view.findViewById(R.id.locationText);
        Location.setText("ECC 238");
        //Location.setText(apptList.get(position).appt_Location);


        return view;
    }
}