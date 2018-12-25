package com.example.farhat.adaptertest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewAppointment_Activity extends AppCompatActivity {

    EditText apptTitle, apptTime, apptDesq, apptLocation;
    private static String title;
    private static String time;
    private static String desq;
    private static String location;
    Button addButton;
    boolean isApptCreated = false;
    ListView appointment_list;
    Appointment_Adapter apptAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);

        apptTitle = findViewById(R.id.apptTitle);
        apptTime = findViewById(R.id.apptTime);
        apptDesq = findViewById(R.id.apptDesq);
        apptLocation = findViewById(R.id.apptLocation);
        addButton = findViewById(R.id.addApptBtn);

        Appointment_Adapter apptAdapter;

        // TODO: attempt2 -> assign strings to values and send them with intent()




        // To send info between activities I think (?)
        Intent intent = getIntent();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //addNewAppt(apptTitle, apptTime, apptDesq, apptLocation);
                assignApptValues();

                Toast.makeText(getApplicationContext(), "Appointment Created", Toast.LENGTH_LONG).show();

                // Change apptBoolean to true, create intent to pass it to main activity
                sendBool();
            }
        });

    }

    // hide keyboard function
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        View view = getCurrentFocus();
        if(view != null && (ev.getAction() == MotionEvent.ACTION_UP ||
                ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText &&
                !view.getClass().getName().startsWith("android.webkit.")){
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];

            if(x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow((this.getWindow().getDecorView().
                                getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }


    // Send boolean to activity function
    public void sendBool(){

        isApptCreated = true;
        Intent intent = new Intent(NewAppointment_Activity.this, MainActivity.class);
        intent.putExtra("apptBool", isApptCreated);
        startActivity(intent);
    }

    public void assignApptValues(){
        title = apptTitle.getText().toString();
        time = apptTime.getText().toString();
        desq = apptDesq.getText().toString();
        location = apptLocation.getText().toString();
    }



    // TODO: attempt 2 -> getting the values to strings (for MainActivity)
    public static String getApptTitle(){
        return title;
    }

    public static String getApptTime(){
        return time;
    }

    public static String  getApptDesq(){
        return desq;
    }

    public static String getApptLoc(){
        return location;
    }
}
