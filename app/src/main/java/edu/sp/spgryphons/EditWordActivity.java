package edu.sp.spgryphons;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Activity to edit an existing or create a new word.
 */
public class EditWordActivity extends AppCompatActivity {

    private static final String TAG = EditWordActivity.class.getSimpleName();

    private static final int NO_ID = -99;
    private static final String NO_WORD = "";

    private EditText mEditWordView;
    private EditText mEditDateView;

    // Unique tag for the intent reply.
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    public static final String wasd = "com.example.android.wordlistsql.REPLY2";

    int mId = project.WORD_ADD;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);

        mEditWordView = (EditText) findViewById(R.id.edit_word);
        mEditDateView = (EditText) findViewById(R.id.edit_date);


        // Get data sent from calling activity.
        Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            int id = extras.getInt(WordListAdapter.EXTRA_ID, NO_ID);
            String word = extras.getString(WordListAdapter.EXTRA_WORD, NO_WORD);
            String date = extras.getString(WordListAdapter.EXTRA_DATE, NO_WORD);
            if (id != NO_ID && word != NO_WORD) {
                mId = id;
                mEditWordView.setText(word);
                mEditDateView.setText(date);
            }
        } // Otherwise, start with empty fields.
    }

    /**
     *  Click handler for the Save button.
     *  Creates a new intent for the reply, adds the reply message to it as an extra,
     *  sets the intent result, and closes the activity.
     */
    public void returnReply(View view) {
        String word = ((EditText) findViewById(R.id.edit_word)).getText().toString();
        String date = ((EditText) findViewById(R.id.edit_date)).getText().toString();

        Pattern p1 = Pattern.compile("^[a-zA-Z]{1,20}+( +[a-zA-Z]{1,20}+){0,6}");
        Matcher m1 = p1.matcher(word);

        Pattern p2 = Pattern.compile("[0-9]{2} [a-zA-Z]{3} [0-9]{4}");
        Matcher m2 = p2.matcher(date);

        if (!m1.matches()) {
            Toast.makeText(getApplicationContext(),"The title can have a maximum of 7 words and must not contain any special characters.",Toast.LENGTH_LONG).show();
            return;
        }

        if (!m2.matches()) {
            Toast.makeText(getApplicationContext(),"The Deadline must be in this format: 11 Sep 2001.",Toast.LENGTH_LONG).show();
            return;
        }
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, word);
        replyIntent.putExtra(wasd,date);
        replyIntent.putExtra(WordListAdapter.EXTRA_ID, mId);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}

