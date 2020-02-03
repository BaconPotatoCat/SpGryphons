package edu.sp.spgryphons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class addEvent extends AppCompatActivity {

    private EditText text;
    private String title;
    private String date;
    private String time;
    private String desc;
    private double lat;
    private double longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        TextView textTitle = findViewById(R.id.textTitle);


    }

    public void setLocation(View v){
        Intent i = new Intent(this,addEventMapActivity.class);
        startActivity(i);
    }
    public void submitEvent(View v) {
        Log.d("tag","Event submitted.");



        text = findViewById(R.id.editTitle);
        title = text.getText().toString();
        text = findViewById(R.id.editDate);
        date = text.getText().toString();
        text = findViewById(R.id.editTime);
        time = text.getText().toString();
        text = findViewById(R.id.editDesc);
        desc = text.getText().toString();


        String[] coord = getIntent().getStringArrayExtra("coords");

        if (coord != null) {
            lat = Double.parseDouble(coord[0]);
            longi = Double.parseDouble(coord[1]);
            Log.d("tag","coord is not null");
        } else {
            // Setting default location for if no location is set.
            lat = 1.3116252;
            longi = 103.774457;
            Log.d("tag","coord is null");
        }

        Log.d("tag","FINAL COORD:"+Double.toString(lat)+","+Double.toString(longi));
        eventObj e = new eventObj(title, date, time, desc ,lat ,longi);

        eventDB b = new eventDB();
        b.submitEvent(e,this);

        Toast.makeText(getApplicationContext(),"Event has been added", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(addEvent.this, events.class);
        startActivity(i);

        Log.d("tag","Content: ");
    }
}
