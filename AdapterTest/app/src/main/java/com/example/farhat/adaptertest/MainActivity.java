package com.example.farhat.adaptertest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView list;
    Button addApptBtn;
    Appointment_Adapter adapter;
    boolean appt_created;
    String title, time, desq, location;

    // TODO: when using this class in NewAppointment_Activity (Line 66), it made me
    // TODO: (continue) declare this class as STATIC

    // TODO: maybe the best way is to declare this class in NewAppointment_Activity
    public class Appointment{
        public String appt_Title;
        public String appt_Time;
        public String appt_desq;
        public String appt_Location;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.apptListView);
        addApptBtn = findViewById(R.id.newAppt);
        Context context = this;


        // launch new appointment activity
        addApptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NewAppointment_Activity.class);
                startActivity(i);
            }
        });

        // Check if appt was created
        appt_created = getIntent().getBooleanExtra("apptBool", false);

        if(appt_created){
            createAppointmentBlock(title, time, desq, location);
            appt_created = false;
        }

    }


    // TODO: attempt 2 -> get the values to the adapter
    public void createAppointmentBlock(String title, String time, String desq, String location){

        Appointment appointment = new Appointment();
        ArrayList<Appointment> apptList = new ArrayList<>();

        // get the values from the TextViews
        title = NewAppointment_Activity.getApptTitle();
        time = NewAppointment_Activity.getApptTime();
        desq = NewAppointment_Activity.getApptDesq();
        location = NewAppointment_Activity.getApptLoc();

        // Assign values to appointment object
        appointment.appt_Title = title;
        appointment.appt_Time = time;
        appointment.appt_desq = desq;
        appointment.appt_Location = location;

        // Assign to adapter
        adapter = new Appointment_Adapter(this, apptList);
        list.setAdapter(adapter);
        apptList.add(appointment);
    }
}
