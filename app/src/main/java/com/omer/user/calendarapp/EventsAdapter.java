package com.omer.user.calendarapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EventsAdapter extends BaseAdapter {

    ArrayList<Event> events;
    Context context;
    Activity activity;

    public EventsAdapter(ArrayList<Event> events, Context context, Activity activity) {
        this.events = events;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View layout = LayoutInflater.from(context).inflate(R.layout.event_element, viewGroup, false);
        TextView element_title = layout.findViewById(R.id.event_title);
        element_title.setText(events.get(i).getEvent_title());

        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                openDeleteAppointmentDialog(i);
                return false;
            }
        });

        return layout;
    }


    private void openDeleteAppointmentDialog(final int i) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.delete_event, null);

        Button delete = view.findViewById(R.id.delete);
        Button cancel = view.findViewById(R.id.cancel2);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(view);
        builder.setCancelable(true);

        final AlertDialog dialog = builder.create();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // necessary query
                dialog.cancel();
            }
        });

        dialog.show();

    }
}
