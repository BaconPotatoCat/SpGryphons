package edu.sp.spgryphons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class events extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private eventsAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        eventDB b = new eventDB();
        ArrayList<eventObj> eventsArray = b.getEventObj();

        mRecyclerView = (RecyclerView) findViewById(R.id.eventsView);
        mAdapter = new eventsAdapter(this, eventsArray);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



}
