package com.example.farhat.backendtests2;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    EditText nameText, lnameText;
    Button sendBtn;
    String fName, lName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameText = (EditText)findViewById(R.id.nameInput);
        lnameText = (EditText)findViewById(R.id.lnameInput);
        sendBtn = (Button) findViewById(R.id.sendBtn);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(nameText, InputMethodManager.SHOW_IMPLICIT);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendDataToServer(view);
                Toast.makeText(MainActivity.this, "Information sent", Toast.LENGTH_LONG).show();
            }

        });
    }

    // Hide keyboard function
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


    public void sendDataToServer(View v) {

        JSONObject json = new JSONObject();

        fName = nameText.getText().toString();
        lName = lnameText.getText().toString();

        try {
            json.put("fname", fName); // POST First name
            json.put("lname", lName); // POST Last name
        } catch (Exception e) {
            e.printStackTrace();
        }


        class sendJsonToServer extends AsyncTask<String, String, String>{

            @Override
            protected String doInBackground(String... params) {

                String JsonResponse = null;
                String JsonDATA = params[0];

                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL("localhost:3001/api/checkin");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);

                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("Accept", "application/json");

                    //Set header and methods
                    Writer writer =
                            new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(),
                                    "UTF-8"));

                    writer.write(JsonDATA);

                    //json data
                    writer.close();
                    InputStream is = connection.getInputStream();

                    StringBuffer buffer = new StringBuffer();
                    if(is == null){
                        return null;
                    }

                    reader = new BufferedReader(new InputStreamReader(is));

                    String inputLine;
                    while((inputLine = reader.readLine()) != null) {
                        buffer.append(inputLine + "\n");
                    }
                    if(buffer.length() == 0) {
                        return null;
                    }
                    JsonResponse = buffer.toString();
                    //Log.i(TAG, JsonResponse);

                    /*try{
                        return JsonResponse;
                    } catch(JSONException e) {
                        e.printStackTrace();
                    }*/



                } catch (IOException e){
                    e.printStackTrace();
                }

                return null;
            }
        }


        // Send the JSON object if it has something.
        if(json.length() > 0){
            new sendJsonToServer().execute(String.valueOf(json));
        }
    }


}
