package edu.sp.spgryphons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class addEvent extends AppCompatActivity {

    private EditText text;
    private String title;
    private String date;
    private String time;
    private String desc;
    private double lat;
    private double longi;
    private SharedPreferences currText;
    private String sharedPrefFile = "edu.sp.spgryphons.addEventState";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        getState();
    }

    public void setLocation(View v){
        saveState();
        Intent i = new Intent(this,addEventMapActivity.class);
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();

        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getState();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        clearState();
    }

    public void submitEvent(View v) {
        text = findViewById(R.id.editTitle);
        title = text.getText().toString();
        text = findViewById(R.id.editDate);
        date = text.getText().toString();
        text = findViewById(R.id.editTime);
        time = text.getText().toString();
        text = findViewById(R.id.editDesc);
        desc = text.getText().toString();

        if (title.length()<1 || date.length()<1 || time.length()<1 || desc.length()<1) {
            Log.d("tag","LENGTH NIGGA");
            Toast.makeText(getApplicationContext(),"Please fill in all the fields.",Toast.LENGTH_SHORT).show();
            return;
        }
        String[] coord = getIntent().getStringArrayExtra("coords");

        if (coord != null) {
            lat = Double.parseDouble(coord[0]);
            longi = Double.parseDouble(coord[1]);
        } else {
            // Setting default location for if no location is set.
            lat = 1.3116252;
            longi = 103.774457;
        }

        eventObj e = new eventObj(title, date, time, desc ,lat ,longi);

        clearState();

        eventDB b = new eventDB();
        b.submitEvent(e,this);



        Toast.makeText(getApplicationContext(),"Event has been added", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(addEvent.this, events.class);
        startActivity(i);
    }

    public void saveState() {
        text = findViewById(R.id.editTitle);
        title = text.getText().toString();
        text = findViewById(R.id.editDate);
        date = text.getText().toString();
        text = findViewById(R.id.editTime);
        time = text.getText().toString();
        text = findViewById(R.id.editDesc);
        desc = text.getText().toString();

        SharedPreferences.Editor prefEditor = currText.edit();
        prefEditor.putString("Title", title);
        prefEditor.putString("Date", date);
        prefEditor.putString("Time", time);
        prefEditor.putString("Desc", desc);
        prefEditor.apply();
    }

    public void getState() {
        currText = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        if (currText.getString("Title","").length()>1 ||
            currText.getString("Date","").length()>1 ||
            currText.getString("Time","").length()>1 ||
            currText.getString("Desc","").length()>1) {
            Log.d("tag","IS NOT NULL");
            EditText titl = (EditText)findViewById(R.id.editTitle);
            EditText dat = (EditText)findViewById(R.id.editDate);
            EditText tim = (EditText)findViewById(R.id.editTime);
            EditText des = (EditText)findViewById(R.id.editDesc);
            titl.setText(currText.getString("Title"," "));
            dat.setText(currText.getString("Date"," "));
            tim.setText(currText.getString("Time"," "));
            des.setText((currText.getString("Desc"," ")));
        } else {
            Log.d("tag","IS NULL");
        }
    }

    public void clearState() {
        SharedPreferences.Editor currState = currText.edit();
        currState.clear().apply();

        EditText titl = (EditText)findViewById(R.id.editTitle);
        EditText dat = (EditText)findViewById(R.id.editDate);
        EditText tim = (EditText)findViewById(R.id.editTime);
        EditText des = (EditText)findViewById(R.id.editDesc);
        titl.setText("");
        dat.setText("");
        tim.setText("");
        des.setText("");
    }
}
