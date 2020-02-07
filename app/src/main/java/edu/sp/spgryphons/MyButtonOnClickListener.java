package edu.sp.spgryphons;

import android.view.View;

/**
 * Instantiated for the Edit and Delete buttons in WordListAdapter.
 */
public class MyButtonOnClickListener implements View.OnClickListener {
    private static final String TAG = View.OnClickListener.class.getSimpleName();

    int id;
    String word;
    String date;

    public MyButtonOnClickListener(int id) {
        this.id=id;
    }
    public MyButtonOnClickListener(int id, String word, String date) {
        this.id = id;
        this.word = word;
        this.date = date;
    }

    public void onClick(View v) {
        // Implemented in WordListAdapter
    }
}
