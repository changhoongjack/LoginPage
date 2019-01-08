package com.example.jack.loginpage;

// to establish a connection for POST


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class EstablishConnection {

    public static HttpURLConnection connect (String urlAddress){

        try
        {
            URL url=new URL(urlAddress);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();

            //SET PROPERTIES
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(20000);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            return conn;


        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


        }


    }

