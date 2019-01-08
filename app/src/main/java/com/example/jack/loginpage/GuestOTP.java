package com.example.jack.loginpage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

import javax.microedition.khronos.egl.EGLDisplay;

public class GuestOTP extends AppCompatActivity {

    String urlAddress="https://projecttc.000webhostapp.com/GuestInfo.php";

    EditText mytext;


    EditText guestnameTxt;
    EditText carplateTxt;
    EditText guesticTxt;
    EditText editText;
    Button GenerateBtn;
    Button generate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_otp);

        guestnameTxt = (EditText) findViewById(R.id.guestname);
        carplateTxt = (EditText) findViewById(R.id.carplate);
        guesticTxt = (EditText) findViewById(R.id.guestic);
        GenerateBtn= (Button) findViewById(R.id.OTPgenerate);
        generate = (Button) findViewById(R.id.btnPIN);

        mytext = (EditText) findViewById(R.id.PINview);
        EditText mEdit = (EditText) findViewById(R.id.PINview);
        mEdit.setEnabled(false);



        generate.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {




                Random rnd = new Random();
                int number = rnd.nextInt(999999);
                mytext.setText(String.format("%06d", number));


            }
        });

//        generate.postDelayed(new Runnable() {  //delay button
//            public void run() {
//                generate.setEnabled(true);
//                generate.invalidate();
//                //any other associated action
//            }
//        }, 1000);



        GenerateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //START ASYNC TASK
                DataSender s=new DataSender(GuestOTP.this,urlAddress,guestnameTxt,carplateTxt,guesticTxt, mytext);
                s.execute();
            }

            });
        }

//
//    public void  generate(View view){
//
//        Random rand =  new Random();
//        int number = rand.nextInt(999999);
//        mytext = (TextView)findViewById(R.id.PINview);
//        String myString = String.valueOf(number);
//
//        mytext.setText(myString);
//
//
//    }



}
