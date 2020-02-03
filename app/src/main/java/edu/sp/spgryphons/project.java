package edu.sp.spgryphons;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class project extends AppCompatActivity {

    Button settingbtn;
    Button Eventsbtn;
    Button Projectsbtn;
    Button Attendancebtn;
    Button Logout;
    Toolbar mToolbar;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        mToolbar =(Toolbar)findViewById(R.id.toolbar);
        Logout = findViewById(R.id.logout);

        //Initialize and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.projectbtn);

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
                        Toast.makeText(project.this,"You are already in Project Page!",Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.eventsbtn:
                        startActivity(new Intent(getApplicationContext(), events.class));
                        overridePendingTransition(0, 0);
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
                Intent IntToMain = new Intent (project.this, RealLoginPage.class);
                startActivity(IntToMain);
            }
        });

    }
    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences mSharedPreferences = getSharedPreferences("ToolbarColor", MODE_PRIVATE);
        int m = mSharedPreferences.getInt("color", getResources().getColor(R.color.colorPrimary));
        Log.d("tag", "hello " + m);
        //if(getColor() != getResources().getColor(R.color.colorPrimary)){
        //    mToolbar.setBackgroundColor(getColor());
        //}
        mToolbar.setBackgroundColor(m);
        Log.d("dd","123");
    }
}
