package com.example.jack.loginpage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class DataSender extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    EditText guestnameTxt,carplateTxt,guesticTxt;
    EditText mytext;

    String guestname,carplate,guestic,otp;

    ProgressDialog pd;

    //1.Constructor
    //2.receive data

    public DataSender (Context c, String urlAddress, EditText...editTexts){

        this.c = c;
        this.urlAddress = urlAddress;

        //INPUT EDITTEXTS
        this.guestnameTxt=editTexts[0];
        this.carplateTxt=editTexts[1];
        this.guesticTxt=editTexts[2];
        this.mytext=editTexts[3];



        //GET TEXTS FROM EDITEXTS
        guestname=guestnameTxt.getText().toString();
        carplate=carplateTxt.getText().toString();
        guestic=guesticTxt.getText().toString();
        otp=mytext.getText().toString();

    }


    /*
   1.SHOW PROGRESS DIALOG WHILE DOWNLOADING DATA
    */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Send");
        pd.setMessage("Sending..Please wait");
        pd.show();
    }

    /*
    1.WHERE WE SEND DATA TO NETWORK
    2.RETURNS FOR US A STRING
     */
    @Override
    protected String doInBackground(Void... params) {
        return this.send();
    }

    /*
  1. CALLED WHEN JOB IS OVER
  2. WE DISMISS OUR PD
  3.RECEIVE A STRING FROM DOINBACKGROUND
   */
    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        pd.dismiss();

        if(response != null)
        {
            //SUCCESS
            Toast.makeText(c,response,Toast.LENGTH_LONG).show();

            guestnameTxt.setText("");
            carplateTxt.setText("");
            guesticTxt.setText("");
            mytext.setText("");
        }else
        {
            //NO SUCCESS
            Toast.makeText(c,"Unsuccessful "+response,Toast.LENGTH_LONG).show();
        }
    }

    /*
SEND DATA OVER THE NETWORK
RECEIVE AND RETURN A RESPONSE
 */
    private String send() {
        //CONNECT
        HttpURLConnection conn = EstablishConnection.connect(urlAddress);

        if (conn == null) {
            return null;
        }

        try {
            OutputStream os = conn.getOutputStream();

            //WRITE
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bw.write(new DataWrap(guestname, carplate, guestic,otp).WrapData());

            bw.flush();

            //RELEASE RES
            bw.close();
            os.close();

            //HAS IT BEEN SUCCESSFUL?
            int responseCode = conn.getResponseCode();

            if (responseCode == conn.HTTP_OK) {
                //GET EXACT RESPONSE
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer response = new StringBuffer();

                String line;

                //READ LINE BY LINE
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }

                //RELEASE RES
                br.close();

                return response.toString();

            } else {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
