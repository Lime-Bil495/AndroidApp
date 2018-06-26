package com.omer.user.calendarapp;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.CalendarView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CalendarView calendar;
    ListView event;
    ArrayList<Event> events;
    EventsAdapter eventsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initialize();
        dateChange();
    }

    private void initialize(){
        calendar = findViewById(R.id.calendar);
        event = findViewById(R.id.events);
        events = new ArrayList<>();
    }

    private void dateChange(){
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                // query(i2,i1,i)
                // it returns a list of events for selected date
                // events = result;
                events.add(new Event("Birthday", "Birthday of John", i2+"/"+i1+"/"+i));
                events.add(new Event("Meeting", "Erasmus student meeting", i2+"/"+i1+"/"+i));
                eventsAdapter = new EventsAdapter(events, getApplicationContext());
                event.setAdapter(eventsAdapter);
            }
        });
    }
}
