package com.omer.user.calendarapp;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    CalendarView calendar;
    ListView event;
    ArrayList<Event> events;
    EventsAdapter eventsAdapter;
    Button addEvent, help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initialize();
        dateChange();
        addEvent();
    }

    private void initialize(){
        calendar = findViewById(R.id.calendar);
        event = findViewById(R.id.events);
        events = new ArrayList<>();
        addEvent = findViewById(R.id.addevent);
        help = findViewById(R.id.help);
    }

    private void dateChange(){
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                // query(i2,i1,i)
                // it returns a list of events for selected date
                // events = result;
                eventsAdapter = new EventsAdapter(events, getApplicationContext());
                event.setAdapter(eventsAdapter);
            }
        });
    }

    private void help() {
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void addEvent() {
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = getLayoutInflater();
                view = layoutInflater.inflate(R.layout.add_event, null);

                Button add = view.findViewById(R.id.add);
                Button cancel = view.findViewById(R.id.cancel);
                final TextView date = view.findViewById(R.id.date);

                date.setText(Epoch2DateString(calendar.getDate(), "yyyy-MM-dd hh:mm:ss a"));

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(view);
                builder.setCancelable(true);

                final AlertDialog dialog = builder.create();

                dialog.show();

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

            }
        });

    }

    public static String Epoch2DateString(long epochSeconds, String formatString) {
        Date updatedate = new Date(epochSeconds * 1000);
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        return format.format(updatedate);
    }
}
