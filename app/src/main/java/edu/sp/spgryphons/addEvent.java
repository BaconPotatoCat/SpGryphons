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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        TextView textTitle = findViewById(R.id.textTitle);
    }

    public void submitEvent(View v) {
        Log.d("tag","Event submitted.");

        String title;
        String date;
        String time;
        String desc;
        double lat;
        double longi;

        text = findViewById(R.id.editTitle);
        title = text.getText().toString();
        text = findViewById(R.id.editDate);
        date = text.getText().toString();
        text = findViewById(R.id.editTime);
        time = text.getText().toString();
        text = findViewById(R.id.editDesc);
        desc = text.getText().toString();
        text = findViewById(R.id.editLat);
        lat = Double.parseDouble(text.getText().toString());
        text = findViewById(R.id.editLong);
        longi = Double.parseDouble(text.getText().toString());

        eventObj e = new eventObj(title, date, time, desc ,lat ,longi);

        eventDB b = new eventDB();
        b.submitEvent(e,this);

        Toast.makeText(getApplicationContext(),"Event has been added", Toast.LENGTH_SHORT).show();

        Intent i = new Intent(addEvent.this, events.class);
        startActivity(i);

        Log.d("tag","Content: ");
    }
}
