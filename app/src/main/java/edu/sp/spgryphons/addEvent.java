package edu.sp.spgryphons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class addEvent extends AppCompatActivity {

    Toolbar mToolbar;
    private Button Logout;
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
        Logout = findViewById(R.id.logout);
        mToolbar =(androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
        getPref();
        getState();

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent IntToMain = new Intent (addEvent.this, RealLoginPage.class);
                startActivity(IntToMain);
            }
        });
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
        getPref();
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

        Pattern p1 = Pattern.compile("^[a-zA-Z0-9]{1,20}+( +[a-zA-Z0-9]{1,20}+){0,6}");
        Matcher m1 = p1.matcher(title);

        text = findViewById(R.id.editDate);
        date = text.getText().toString();

        Pattern p2 = Pattern.compile("[0-9]{2} [a-zA-Z]{3} [0-9]{4}");
        Matcher m2 = p2.matcher(date);

        text = findViewById(R.id.editTime);
        time = text.getText().toString();

        Pattern p3 = Pattern.compile("[0-9]{4}");
        Matcher m3 = p3.matcher(time);

        text = findViewById(R.id.editDesc);
        desc = text.getText().toString();

        Pattern p4 = Pattern.compile("^[a-zA-Z0-9,.&]{1,20}+([ \\n]+[a-zA-Z0-9,.&\\n]{1,20}+){0,100}");
        Matcher m4 = p4.matcher(desc);

        if (!m1.matches()) {
            Toast.makeText(getApplicationContext(),"The title can have a maximum of 7 words and must not contain any special characters.",Toast.LENGTH_LONG).show();
            return;
        }

        if (!m2.matches()) {
            Toast.makeText(getApplicationContext(),"The date must be in this format: 11 Sep 2001.",Toast.LENGTH_LONG).show();
            return;
        }

        if (!m3.matches()) {
            Toast.makeText(getApplicationContext(),"The time must be in the 24 hour format (e.g. 1500).",Toast.LENGTH_LONG).show();
            return;
        }

        if (!m4.matches()) {
            Toast.makeText(getApplicationContext(),"The description can have a maximum of 101 words and cannot contain forbidden characters.",Toast.LENGTH_LONG).show();
            return;
        }
        if (title.length()<1 || date.length()<1 || time.length()<1 || desc.length()<1) {
            Toast.makeText(getApplicationContext(),"Please fill in all the fields.",Toast.LENGTH_SHORT).show();
            return;
        }
        String[] coord = getIntent().getStringArrayExtra("coords");

        if (coord != null) {
            lat = Double.parseDouble(coord[0]);
            longi = Double.parseDouble(coord[1]);
        } else {
            // Setting default location for if no location is set
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

    public void getPref() {
        SharedPreferences mSharedPreferences = getSharedPreferences("ToolbarColor", MODE_PRIVATE);
        int m = mSharedPreferences.getInt("color", getResources().getColor(R.color.colorPrimary));
        Log.d("tag", "hello " + m);
        mToolbar.setBackgroundColor(m);
    }
}
