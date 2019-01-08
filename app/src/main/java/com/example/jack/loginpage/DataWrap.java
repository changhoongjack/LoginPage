package com.example.jack.loginpage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

public class DataWrap {

    String guestname, carplate, guestic,otp;

    //Receive all data we wanted to send
    public DataWrap(String guestname, String carplate, String guestic,String otp){
        this.guestname = guestname;
        this.carplate = carplate;
        this.guestic = guestic;
        this.otp = otp;
    }

    //Wrap into a Json Object
    public String WrapData(){

        JSONObject JO = new JSONObject();
        StringBuffer dataWrap= new StringBuffer();


        try{

            JO.put("guestname", guestname);
            JO.put("carplate",carplate);
            JO.put("guestic",guestic);
            JO.put("otp",otp);

            Boolean firstValue = true;

            Iterator it=JO.keys();

            do {
                String key=it.next().toString();
                String value=JO.get(key).toString();

                if(firstValue)
                {
                    firstValue=false;
                }else
                {
                    dataWrap.append("&");
                }

                dataWrap.append(URLEncoder.encode(key,"UTF-8"));
                dataWrap.append("=");
                dataWrap.append(URLEncoder.encode(value,"UTF-8"));

            }while (it.hasNext());

            return dataWrap.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;

        }
    }


