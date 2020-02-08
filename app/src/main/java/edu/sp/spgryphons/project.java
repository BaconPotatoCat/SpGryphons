package edu.sp.spgryphons;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    private static final String TAG = project.class.getSimpleName();
    public String l ="here";

    public static final int WORD_EDIT = 1;
    public static final int WORD_ADD = -1;

    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;
    private WordListOpenHelper mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("tag", "hello " + l);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        mToolbar = findViewById(R.id.toolbar);
        Logout = findViewById(R.id.logout);
        getPref();
        mDB = new WordListOpenHelper(this);

        // Create recycler view.
        mRecyclerView = findViewById(R.id.recyclerview);

        // Create an mAdapter and supply the data to be displayed.
        mAdapter =new WordListAdapter(this,mDB);
        // Connect the mAdapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Initialize and Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.projectbtn);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.settingsbtn:
                        startActivity(new Intent(getApplicationContext(), Setting.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.projectbtn:
                        Toast.makeText(project.this, "You are already in Project Page!", Toast.LENGTH_SHORT).show();
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

        // Add a floating action click handler for creating new entries.
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                // Start empty edit activity.
                Intent intent = new Intent(getBaseContext(), EditWordActivity.class);
                startActivityForResult(intent, WORD_EDIT);
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent IntToMain = new Intent(project.this, RealLoginPage.class);
                startActivity(IntToMain);
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Add code to update the database.
        if (requestCode == WORD_EDIT) {
            if (resultCode == RESULT_OK) {
                String word = data.getStringExtra(EditWordActivity.EXTRA_REPLY);
                String date = data.getStringExtra(EditWordActivity.wasd);
                if (!TextUtils.isEmpty(word)) {
                    int id = data.getIntExtra(WordListAdapter.EXTRA_ID, -99);
                    if (id == WORD_ADD) {
                        mDB.insert(word,date);
                    }else if (id >= 0) {
                        mDB.update(id, word, date);
                    }
                    mAdapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(
                            getApplicationContext(),
                            R.string.empty_not_saved,Toast.LENGTH_LONG).show();
                }
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getPref();
    }

    public void getPref() {
        SharedPreferences mSharedPreferences = getSharedPreferences("ToolbarColor", MODE_PRIVATE);
        int m = mSharedPreferences.getInt("color", getResources().getColor(R.color.colorPrimary));
        Log.d("tag", "hello " + m);
        mToolbar.setBackgroundColor(m);
    }
}
