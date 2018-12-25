package com.example.samue.desq;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PhoneVerification extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private static String phoneVerificationMessage = "DSQ verification code: ";
    private String currentUserId;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String verificationCode;
    EditText verificationCodeEditText;
    DatabaseReference databaseUsers;
    MyFirebaseMessagingService myFMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        //Notification channel
        myFMS = new MyFirebaseMessagingService();
        myFMS.createNotificationChannel(this, "Channel ID", "Channel Name", "Channel Description", MyFirebaseMessagingService.NotificationChannelImportance.Urgent);


        Intent intent = getIntent();
        phoneNumber = intent.getStringExtra("PhoneNumber");
        firstName = intent.getStringExtra("FirstName");
        lastName = intent.getStringExtra("LastName");

        //Check if phone has sms capability
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY))
        {
            sendVerificationCode(phoneNumber); //Send code, device has sms capability
        }
        else
        {
            generateVerificationCode(); //The code is static right now
        }

        verificationCodeEditText = findViewById(R.id.verificationCode);

        FirebaseDatabase.getInstance().getReference("Users");
    }

    private void sendVerificationCode(String phone)
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            //Don't have sms permission yet
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS))
            {
                //Toast.makeText(getApplicationContext(), "Don't have permission yet.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else
        {
            //Toast.makeText(getApplicationContext(), "Have permissions, sms sending", Toast.LENGTH_LONG).show();
            sendSMS(phone, phoneVerificationMessage + generateVerificationCode());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSMS(phoneNumber, phoneVerificationMessage + generateVerificationCode());
                } else {
                    Toast.makeText(getApplicationContext(), "SMS failed, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

    private void sendSMS(String number, String message)
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, message, null, null);
        Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
    }

    private String generateVerificationCode()
    {
        verificationCode = "123456"; //This would need to be randomly generated and stored in a db

        //Store code in db here

        return verificationCode;
    }

    //Currently checking a verification code variable, will need to check a db value down the road
    public void checkVerificationCode(View view)
    {
        String enteredCode = verificationCodeEditText.getText().toString();

        //Retrieve verification code from db here

        //Check entered code against stored code in db (currently checking a global variable)
        if (enteredCode.equals(verificationCode))
        {
            Toast.makeText(getApplicationContext(), "Phone number verified.", Toast.LENGTH_LONG).show();

            Boolean userExists = false;

            if (!userExists)
            {
                myFMS.generateNotificationToken(this);
                //createNewUser(phoneNumber, firstName, lastName);
            }

            goToMainActivity();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Invalid code.", Toast.LENGTH_LONG).show();
        }
    }

    public void createNewUser(String notificationToken)
    {
        //Store user data in db
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        currentUserId = databaseUsers.push().getKey();    //used to generate unique id
        String fullName = firstName + ' ' + lastName;
        //String notificationToken = myFMS.getNotificationToken();

        UserData user = new UserData(currentUserId, fullName, phoneNumber, notificationToken);  //adding new user to the database

        databaseUsers.child(currentUserId).setValue(user);
        CurrentUserData.User.userData = user;

        String toastMessage = "User account created";
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
    }

    public String getCurrentUserId() { return currentUserId; }

    private void goToMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}