package com.example.samue.desq;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.List;

public class appointmentActivity  extends AppCompatActivity implements View.OnClickListener {

    EditText editTextname, descriptionView, titleView;
    ImageButton send, cancel;
    ImageButton btnDatePicker, btnTimePicker;
    EditText txtDate, txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    TextView dateView;
    ListView listViewUsers;

    //List storing all users
    List<userInfo> users;

    //database reference object
    DatabaseReference databaseUsers;
    DatabaseReference databaseAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        //setting the reference of users that are already existing
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        databaseAppointments = FirebaseDatabase.getInstance().getReference("Appointments");

        //getting the XML views
        descriptionView = (EditText) findViewById(R.id.description);
        titleView = (EditText) findViewById(R.id.titleView);  //title
        dateView = (TextView) findViewById(R.id.dateView);
        btnDatePicker = findViewById(R.id.btn_date);
        btnTimePicker = findViewById(R.id.btn_time);
        txtDate = (EditText) findViewById(R.id.dateView);
        txtTime = (EditText) findViewById(R.id.timeView);
        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        cancel = findViewById(R.id.button_cancel);
        send = findViewById(R.id.button_send);
        send.setOnClickListener(this);
        cancel.setOnClickListener(this);

        //oncoming data changes
        Intent incoming = getIntent();
        String date = incoming.getStringExtra("date");  //pass the key (date)
        dateView.setText(date);

    } //end of onCreate


    @Override
    public void onClick(View view) {
        if (view == send){
            databaseAppointments = FirebaseDatabase.getInstance().getReference("Appointments");
            //dbCheck = databaseUsers.child("date");   //ref for checking if the date already exists in the database

            databaseAppointments.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //String dbDate = dbCheck.toString();
                    String startTime = txtTime.getText().toString().trim(); //start time
                    String title = titleView.getText().toString().trim();  //title
                    String description = descriptionView.getText().toString().trim();
                    String date = dateView.getText().toString().trim();  //date
                    String currentUserId = CurrentUserData.User.getId();

                    Boolean apptValid = true;

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        AppointmentData apptData = snapshot.getValue(AppointmentData.class);
                        String dbApptDate = apptData.date;
                        String dbApptTime = apptData.startTime;

                        if (dbApptDate.equals(date) && dbApptTime.equals(startTime)) {
                            apptValid = false;
                            Toast.makeText(appointmentActivity.this, "Appointment time not valid, please select another time.", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }

                    if (apptValid)
                    {
                        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(date) && !TextUtils.isEmpty(startTime)) {
                            String appointmentId = databaseAppointments.push().getKey();    //used to generate unique id
                            AppointmentData appointment = new AppointmentData(appointmentId, title, description, date, startTime, currentUserId);  //adding new user to the database

                            databaseAppointments.child(appointmentId).setValue(appointment);

                            Toast.makeText(appointmentActivity.this, "Appointment added to the database", Toast.LENGTH_LONG).show();

                            goToMainActivity();

                        } else {
                            Toast.makeText(appointmentActivity.this, "Please Make sure all fields are filled correctly", Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(appointmentActivity.this, "There has been an error in the database", Toast.LENGTH_SHORT).show();
                }

            });

        }
        if (view == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (view == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if (view == cancel){
            /*Intent cancel  = new Intent(this, MainActivity.class);
            this.startActivity(cancel);*/

            goToMainActivity();
            //Toast.makeText(this, " CANCELLED ", Toast.LENGTH_LONG).show();

        }
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

    private void goToMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}