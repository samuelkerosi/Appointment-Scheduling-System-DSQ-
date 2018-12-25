package com.DSQ.signup_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity{

    private EditText phoneNumberEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        phoneNumberEditText = (EditText) findViewById(R.id.phoneNumber);
        firstNameEditText = (EditText) findViewById(R.id.firstName);
        lastNameEditText = (EditText) findViewById(R.id.lastName);
    }

    public void signUp(View view)
    {
        Intent intent = new Intent(this, PhoneVerification.class);

        String phoneNumber = phoneNumberEditText.getText().toString();
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();

        //intent.putExtra(EXTRA_MESSAGE, phone);
        intent.putExtra("PhoneNumber", phoneNumber);
        intent.putExtra("FirstName", firstName);
        intent.putExtra("LastName", lastName);
        startActivity(intent);
    }

}

