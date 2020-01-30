package edu.sp.spgryphons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

public class Setting extends AppCompatActivity {


    Toolbar mToolbar;
    Button mRedColor;
    Button mGreenColor;
    Button mYellowColor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mToolbar =(Toolbar)findViewById(R.id.toolbar);
        mRedColor=(Button)findViewById(R.id.btnred);
        mGreenColor=(Button)findViewById(R.id.btngreen);
        mYellowColor=(Button)findViewById(R.id.btnyellow);

        if(getColor() != getResources().getColor(R.color.colorPrimary)){
            mToolbar.setBackgroundColor(getColor());
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                getWindow().setStatusBarColor(getColor());
            }
        }

        mRedColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorRed));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorRed));
                }
                storeColor(getResources().getColor(R.color.colorRed));
            }
        });

        mGreenColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorGreen));
                }
                storeColor(getResources().getColor(R.color.colorGreen));
            }
        });

        mYellowColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mToolbar.setBackgroundColor(getResources().getColor(R.color.colorYello));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setStatusBarColor(getResources().getColor(R.color.colorYello));
                }
                storeColor(getResources().getColor(R.color.colorYello));
            }
        });


    }

    public void storeColor(int color){
        SharedPreferences mSharedPreferences = getSharedPreferences("ToolbarColor",MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putInt("color",color);
        mEditor.apply();
    }

    public int getColor(){
        SharedPreferences mSharedPreferences = getSharedPreferences("ToolbarColor",MODE_PRIVATE);
        int selectedcolor = mSharedPreferences.getInt("color",getResources().getColor(R.color.colorPrimary));
        return selectedcolor;
    }
}
