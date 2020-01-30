package edu.sp.spgryphons;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class eventsAdapter extends
        RecyclerView.Adapter<eventsAdapter.EventsViewHolder> {

    public final ArrayList<eventObj> mEventList;
    private LayoutInflater mInflater;

    public eventsAdapter(Context context, ArrayList<eventObj> eventlist) {
        mInflater = LayoutInflater.from(context);
        this.mEventList = eventlist;
    }

    class EventsViewHolder extends RecyclerView.ViewHolder {
        public final TextView eventItemView;
        final eventsAdapter mAdapter;
        public EventsViewHolder(@NonNull View itemView, eventsAdapter adapter) {
            super(itemView);
            eventItemView = (TextView) itemView.findViewById(R.id.eventListing);
            this.mAdapter = adapter;
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
        Log.d("TAG","EVENTS:"+eventDetails);
        holder.eventItemView.setText(eventDetails);
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }
}
