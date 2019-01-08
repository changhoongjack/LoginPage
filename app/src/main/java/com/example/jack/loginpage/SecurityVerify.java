package com.example.jack.loginpage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SecurityVerify extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText PIN;
    //private TextView checkTest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_verify);

        PIN = (EditText)findViewById(R.id.PINinput);
        //checkTest = (TextView) findViewById(R.id.textView);
    }

    public void checkPIN(View arg0){



        //get text from pin verification field
        final String pin = PIN.getText().toString();

            //Initialize AsyncLogin() class
            new AsyncLogin().execute(pin);

        Intent i = new Intent(SecurityVerify.this, SuccessVerify.class);
        i.putExtra("GETPIN", pin);

    }

    private class AsyncLogin extends AsyncTask<String,String,String> {


        ProgressDialog pdLoading = new ProgressDialog(SecurityVerify.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //run on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
               url = new URL("https://projecttc.000webhostapp.com/PINVerification.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("pin", params[0]);

                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    //InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {

                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return result.toString();

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }
        }
            @Override
            protected void onPostExecute(String result){
                super.onPostExecute(result);
                Toast.makeText(SecurityVerify.this, result, Toast.LENGTH_LONG).show();
                //this method will be running on UI thread

                pdLoading.dismiss();
                //checkTest.setText(result);
                if (result.equalsIgnoreCase("true")) {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */
                    Toast.makeText(SecurityVerify.this, "PIN Correct", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SecurityVerify.this, SuccessVerify.class);
                    startActivity(intent);
                    SecurityVerify.this.finish();

                } else if (result.equalsIgnoreCase("false")) {

                    // If username and password does not match display a error message
                    Toast.makeText(SecurityVerify.this, "Invalid PIN", Toast.LENGTH_LONG).show();

                } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                    Toast.makeText(SecurityVerify.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

                }
            }

        }




    }



