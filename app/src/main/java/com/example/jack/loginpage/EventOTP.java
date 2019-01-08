package com.example.jack.loginpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Random;

public class EventOTP extends AppCompatActivity {

    String urlAddress="https://projecttc.000webhostapp.com/EventInfo.php";

    EditText eventnameTxt;
    EditText hostbyTxt;
    EditText contactnoTxt;

    EditText editText;
    EditText pinText;
    Button generateOTP;
    Button submit;
    Spinner capacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_otp);


        eventnameTxt = (EditText) findViewById(R.id.eventname);
        hostbyTxt = (EditText)findViewById(R.id.hostby);
        contactnoTxt = (EditText)findViewById(R.id.contactno);
        editText = (EditText)findViewById(R.id.editText);

        pinText = (EditText)findViewById(R.id.PINview);
        generateOTP = (Button) findViewById(R.id.generatepin);
        EditText mEdit = (EditText) findViewById(R.id.PINview);
        mEdit.setEnabled(false);
        submit= (Button) findViewById(R.id.btnSubmit);
        capacity = (Spinner)findViewById(R.id.capacity);



        generateOTP.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Random rnd = new Random();
                int number = rnd.nextInt(999999);
                pinText.setText(String.format("%06d", number));

            }

        });

        //editText.setText(capacity.getSelectedItem().toString());


        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View arg0, boolean hasFocus) {
                // TODO Auto-generated method stub
                if(hasFocus){
                    capacity.setVisibility(View.VISIBLE);

                }
            }
        });

        capacity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                editText.setText(capacity.getSelectedItem().toString()); //this is taking the first value of the spinner by default.

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });





        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //START ASYNC TASK
                EventDataSender s=new EventDataSender(EventOTP.this,urlAddress,eventnameTxt,hostbyTxt,contactnoTxt,editText,pinText);
                s.execute();
            }

        });
    }


    }

