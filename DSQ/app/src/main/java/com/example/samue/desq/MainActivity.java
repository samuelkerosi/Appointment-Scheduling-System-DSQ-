package com.example.samue.desq;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    Appointment_Adapter adapter;
    Context context;
    ListView listView;

    public class apptBox{
        public String boxTitle;
        public String boxTime;
        public String boxDesq;
        public String boxLoc;
    }

    DatabaseReference databaseUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiating
        databaseUsers = FirebaseDatabase.getInstance().getReference("User");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
                Intent intent = new Intent(context, appointmentActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //TODO: please work
        getApptList();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            // Handle the search action
            Intent search = new Intent(this, searchActivity.class);
            this.startActivity(search);

        } else if (id == R.id.nav_viewsaved) {

            Intent view = new Intent(this, viewActivity.class);
            this.startActivity(view);

        } else if (id == R.id.nav_appointment) {
            Intent appointment  = new Intent(this, appointmentActivity.class);
            this.startActivity(appointment);

        } else if (id == R.id.nav_settings) {
            Intent settings = new Intent(this, settingsActivity.class);
            this.startActivity(settings);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // function to populate listview
    public void getApptList(){


        // get appointments associated with the current user's ID
        DatabaseReference userAppointments = FirebaseDatabase.getInstance().getReference("Appointments");
        userAppointments.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // while (appointment != null) { add to adapter and display; } ??
                // get current user id
                String currentUserID = CurrentUserData.User.getId();
                //String test = dataSnapshot.getKey();
                //String title, time, desq, location;
                String desq = "Omar";
                String location = "ECC 238";

                ArrayList<AppointmentData> apptList = new ArrayList<>();

                // add to adapter
                adapter = new Appointment_Adapter(context, apptList);
                listView = findViewById(R.id.apptListView);
                listView.setAdapter(adapter);


                // Loop through appointments
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    AppointmentData apptData = snapshot.getValue(AppointmentData.class);
                    String date = apptData.date;
                    String startTime = apptData.startTime;
                    String title = apptData.title;

                    //user id stuff
                    String visitorID = apptData.visitorId;

                    if(visitorID.equals(currentUserID)){
                        apptList.add(apptData);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
