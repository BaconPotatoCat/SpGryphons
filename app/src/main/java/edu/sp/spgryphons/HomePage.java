package edu.sp.spgryphons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {
    ImageButton setting;
    ImageView attendance;
    ImageView project;
    ImageView events;
    Button Logout;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Toast.makeText(HomePage.this,currentuser,Toast.LENGTH_SHORT).show();

        Logout = findViewById(R.id.BtnLogout);


        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent IntToMain = new Intent (HomePage.this, LoginPage.class);
                startActivity(IntToMain);
            }
        });

        setting = (ImageButton) findViewById(R.id.settingsbtn);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this,Setting.class);
                startActivity(i);
            }
        });

        attendance = (ImageButton) findViewById(R.id.attendancebtn);
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this,attendance.class);
                startActivity(i);
            }
        });

        events = (ImageButton) findViewById(R.id.eventsbtn);
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this,events.class);
                startActivity(i);
            }
        });
        project = (ImageButton) findViewById(R.id.projectbtn);
        project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePage.this,project.class);
                startActivity(i);
            }
        });

    }
    //SharedPreferences mSharedPreferences = getSharedPreferences("ToolbarColor",MODE_PRIVATE);
    //int selectedcolor = mSharedPreferences.getInt("color",getResources().getColor(R.color.colorPrimary));



}
