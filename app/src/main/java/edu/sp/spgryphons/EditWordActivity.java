package edu.sp.spgryphons;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


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

        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, word);
        replyIntent.putExtra(wasd,date);
        replyIntent.putExtra(WordListAdapter.EXTRA_ID, mId);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}

