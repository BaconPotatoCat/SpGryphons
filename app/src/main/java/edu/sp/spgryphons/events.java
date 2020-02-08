package edu.sp.spgryphons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;

public class events extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    public static eventsAdapter mAdapter;
    Button Logout;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        mToolbar =(Toolbar)findViewById(R.id.toolbar);
        Logout = findViewById(R.id.logout);

        getPref();
        eventDB b = new eventDB();
        ArrayList<eventObj> eventsArray = b.getEventObj(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.eventsView);
        mAdapter = new eventsAdapter(this, eventsArray);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addEvent);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(events.this, addEvent.class);
                startActivity(i);
            }
        });

        //Initialize and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.eventsbtn);
        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.settingsbtn:
                        startActivity(new Intent(getApplicationContext(), Setting.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.projectbtn:
                        startActivity(new Intent(getApplicationContext(), project.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.eventsbtn:
                        Toast.makeText(events.this,"You are already in Events Page!",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.attendancebtn:
                        startActivity(new Intent(getApplicationContext(), attendance.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent IntToMain = new Intent (events.this, RealLoginPage.class);
                startActivity(IntToMain);
            }
        });
    }
    @Override
    public void onResume(){
        super.onResume();
        getPref();
        //if(getColor() != getResources().getColor(R.color.colorPrimary)){
        //    mToolbar.setBackgroundColor(getColor());
        //}
        //mToolbar.setBackgroundColor(m);
    }

    public void getPref() {
        SharedPreferences mSharedPreferences = getSharedPreferences("ToolbarColor", MODE_PRIVATE);
        int m = mSharedPreferences.getInt("color", getResources().getColor(R.color.colorPrimary));
        Log.d("tag", "hello " + m);
        mToolbar.setBackgroundColor(m);
    }

}

