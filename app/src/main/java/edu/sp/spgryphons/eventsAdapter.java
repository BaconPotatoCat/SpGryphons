package edu.sp.spgryphons;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

public class eventsAdapter extends
        RecyclerView.Adapter<eventsAdapter.EventsViewHolder> {

    public final ArrayList<eventObj> mEventList;
    private LayoutInflater mInflater;
    private final Context mContext;

    public eventsAdapter(Context context, ArrayList<eventObj> eventlist) {
        mInflater = LayoutInflater.from(context);
        this.mEventList = eventlist;
        mContext = context;
    }

    class EventsViewHolder extends RecyclerView.ViewHolder {
        public final TextView eventItemView;
        final eventsAdapter mAdapter;
        Button map_button;

        public EventsViewHolder(@NonNull View itemView, eventsAdapter adapter) {
            super(itemView);
            eventItemView = (TextView) itemView.findViewById(R.id.eventListing);
            this.mAdapter = adapter;
            map_button = (Button)itemView.findViewById(R.id.map_button);
        }
    }

    @NonNull
    @Override
    public eventsAdapter.EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mEventView = mInflater.inflate(R.layout.event_item, parent, false);
        return new EventsViewHolder(mEventView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull eventsAdapter.EventsViewHolder holder, int position) {
        eventObj mCurrent = mEventList.get(position);
        String eventDetails = "Event: "+mCurrent.getTitle()+"\nDate: "+mCurrent.getDate()+"\nTime: "+mCurrent.getTime()+
                "\nDescription: "+mCurrent.getDescription();
        holder.eventItemView.setText(eventDetails);

        final EventsViewHolder h = holder;
        holder.map_button.setOnClickListener(
            new eventMapOnClickListener(mCurrent.getLat(),mCurrent.getLong()) {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext,eventMapsActivity.class);
                    mContext.startActivity(i);
                }
            }
        );
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }
}
