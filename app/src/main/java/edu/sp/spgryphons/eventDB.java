package edu.sp.spgryphons;

import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;

public class eventDB {

    public static ArrayList<eventObj> getEventObj() {

        ArrayList<eventObj> e = new ArrayList<eventObj>();
        try {
            //Enter code to get events from firebase database
            // Do loop to retrieve all events and append to array
            //while (rs.next()) {
            //    eventObj ev = new eventObj(rs.getString("title"),rs.getString("date"),rs.getString("time"),rs.getString("desc"),rs.getFloat("longitude"),rs.getString("latitude"));
            //    e.add(ev);
            //}
            eventObj ev = new eventObj("SPOH","7 Jan 2020","0900-1800", "Singapore Poly Open House event",1.3103459, 103.7742941);
            e.add(ev);
            ev = new eventObj("CTF","20 Jul 2020","0600-2359", "Capture the Flag event hosted by Singapore Poly",1.3103459, 103.7742941);
            e.add(ev);
            return e;
        } catch(Exception er) {
            Log.d("Error", "Failure to retrieve events from database");
        }
        return e;
    }
}
