package com.example.jack.loginpage;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SuccessVerify extends AppCompatActivity {
//    public static final int CONNECTION_TIMEOUT=10000;
//    public static final int READ_TIMEOUT=15000;
    TextView guestview;
    TextView guestcp;
    TextView guesticview;
    private ProgressDialog pd;
    String newpin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_verify);

        pd = new ProgressDialog(SuccessVerify.this);
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);

        guestview = (TextView)findViewById(R.id.guestview);
        guestcp = (TextView)findViewById(R.id.guestcp);
        guesticview = (TextView)findViewById(R.id.guestic);

        //String newpin;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newpin= null;
            } else {
                newpin = extras.getString("GETPIN");
            }
        } else {
            newpin = (String) savedInstanceState.getSerializable("GETPIN");
        }


        getGuestData();





    }

    private void getGuestData(){
        String url= "https://projecttc.000webhostapp.com/FetchGuestData.php?OTP="+newpin;
        pd.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pd.hide();


                        try {

                            JSONArray jsonarray = new JSONArray(response);

                            for(int i=0; i < jsonarray.length(); i++) {

                                JSONObject jsonobject = jsonarray.getJSONObject(i);


                                String name = jsonobject.getString("guest_name");
                                String plate = jsonobject.getString("car_plate");
                                String ic = jsonobject.getString("guest_ic");


                                guestview.setText(name);
                                guestcp.setText(plate);
                                guesticview.setText(ic);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();


                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error != null){

                            Toast.makeText(getApplicationContext(), "Something went wrong.", Toast.LENGTH_LONG).show();
                        }
                    }
                }

        );

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }



//    class Connection extends AsyncTask<String,String,String>{
//        HttpURLConnection conn;
//        URL url = null;
//        @Override
//        protected String doInBackground(String...params){
//
//            try {
//
//                // Enter URL address where your php file resides
//                url = new URL("https://projecttc.000webhostapp.com/FetchGuestData.php");
//
//            } catch (MalformedURLException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                return "exception";
//            }
//
//            try {
//                // Setup HttpURLConnection class to send and receive data from php and mysql
//                conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(READ_TIMEOUT);
//                conn.setConnectTimeout(CONNECTION_TIMEOUT);
//                conn.setRequestMethod("POST");
//
//                // setDoInput and setDoOutput method depict handling of both send and receive
//                conn.setDoInput(true);
//                conn.setDoOutput(true);
//               return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void result){
//
//
//        }
//    }




}
