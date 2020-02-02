package edu.sp.spgryphons;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class addEvent extends AppCompatActivity {

    private EditText text;
    public static final String EXTRA_MESSAGE = "edu.sp.spgryphons.extra.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        TextView textTitle = findViewById(R.id.textTitle);
    }

    public void submitEvent(View v) {
        Log.d("tag","Event submitted.");

        text = findViewById(R.id.editTitle);
        String content = text.getText().toString()+"\n";
        text = findViewById(R.id.editDate);
        content += text.getText().toString()+"\n";
        text = findViewById(R.id.editTime);
        content += text.getText().toString()+"\n";
        text = findViewById(R.id.editDesc);
        content += text.getText().toString();

        Log.d("tag","Content: "+content);
    }
}
