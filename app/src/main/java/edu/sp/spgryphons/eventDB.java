package edu.sp.spgryphons;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class eventDB {
    public static ArrayList<eventObj> getEventObj(Context context) {
        final ArrayList<eventObj> e = new ArrayList<eventObj>();
        try {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "https://mapp-prac13.firebaseio.com/eventList.json";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("TAG", "Response is:" + response);
                            try {
                                JSONObject JObj = new JSONObject(response);
                                int i=1;
                                for(;;){
                                    JSONObject even = JObj.getJSONObject("events");
                                    try {
                                        JSONObject eve = even.getJSONObject("event" + i);
                                        Log.d("TAG","event"+i+" eve:"+eve);
                                        eventObj ev = new eventObj(eve.getString("title"), eve.getString("date"),
                                                eve.getString("time"), eve.getString("description"),
                                                eve.getDouble("latitude"), eve.getDouble("longitude"));
                                        e.add(ev);
                                        i++;
                                    } catch(JSONException exec) {
                                        break;
                                    }
                                }
                                events.mAdapter.notifyDataSetChanged();
                            } catch (JSONException ex) {
                                Log.d("TAG","JSON EXCEPTION:"+ex);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("TAG","Failure to retrieve events from database.");
                        }
                    });
            queue.add(stringRequest);
        } catch(Exception er) {
            Log.d("Error", "Failure to retrieve events from database");
        }
        return e;
    }
}
