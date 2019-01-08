package com.example.jack.loginpage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

public class EventDataWrap {

    String eventname, hostby,contactno,eventcapacity,pin;

    //Receive all data we wanted to send
    public EventDataWrap(String eventname, String hostby, String contactno,String eventcapacity, String pin){
        this.eventname = eventname;
        this.hostby = hostby;
        this.contactno = contactno;
        this.eventcapacity= eventcapacity;
        this.pin = pin;
    }

    //Wrap into a Json Object

    public String WrapEventData(){

        JSONObject JO = new JSONObject();
        StringBuffer Eventdatawrap= new StringBuffer();


        try{

            JO.put("eventname", eventname);
            JO.put("hostby",hostby);
            JO.put("contactno",contactno);
            JO.put("eventcapacity",eventcapacity);
            JO.put("pin",pin);

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
                    Eventdatawrap.append("&");
                }

                Eventdatawrap.append(URLEncoder.encode(key,"UTF-8"));
                Eventdatawrap.append("=");
                Eventdatawrap.append(URLEncoder.encode(value,"UTF-8"));

            }while (it.hasNext());

            return Eventdatawrap.toString();

        } catch (JSONException e) {

        } catch (UnsupportedEncodingException e) {

        }

        return null;

    }







}
