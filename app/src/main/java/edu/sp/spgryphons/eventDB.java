package edu.sp.spgryphons;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

public class eventDB {
    public static ArrayList<eventObj> getEventObj(Context context) {
        final ArrayList<eventObj> e = new ArrayList<eventObj>();
        try {
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "https://gryphonslogin.firebaseio.com/eventList.json";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("TAG", "Response is:" + response);
                            try {
                                JSONObject JObj = new JSONObject(response);
                                JSONArray names = JObj.names();
                                for (int i=0;i<names.length();i++) {
                                    JSONObject eve = JObj.getJSONObject(names.getString(i));
                                    Log.d("TAG", "event" + i + " eve:" + eve);
                                    eventObj ev = new eventObj(eve.getString("title"), eve.getString("date"),
                                            eve.getString("time"), eve.getString("description"),
                                            eve.getDouble("latitude"), eve.getDouble("longitude"));
                                    e.add(ev);
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

    public static void submitEvent(eventObj e, Context context) {
        final eventObj ev = e;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://gryphonslogin.firebaseio.com/eventList.json";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "SUBMIT RESPONSE:" + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG","Failure to submit event.");
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("title", ev.getTitle());
                    obj.put("date",ev.getDate());
                    obj.put("time",ev.getTime());
                    obj.put("description",ev.getDescription());
                    obj.put("latitude",ev.getLat());
                    obj.put("longitude",ev.getLong());

                    Log.d("tag","Object to submit: "+obj.toString());

                    return obj.toString().getBytes();
                } catch (Exception e) {
                    Log.e("tag", "Exception in submitting event: "+e.getMessage());
                }
                return super.getBody();
            }
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };
        queue.add(stringRequest);
    }

    public static void getEventMap(double latitude, double longitude) {
        final double lat = latitude;
        final double lon = longitude;
    }
}
