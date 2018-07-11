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
import android.widget.Toast;

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
        listEventsToDate();
        addEvent();
        help();
    }

    private void initialize() {
        calendar = findViewById(R.id.calendar);
        event = findViewById(R.id.events);
        events = new ArrayList<>();
        addEvent = findViewById(R.id.addevent);
        help = findViewById(R.id.help);
    }

    private void listEventsToDate() {
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                // query(i2,i1,i)
                // it returns a list of events for selected date
                // events = result;
                eventsAdapter = new EventsAdapter(events, getApplicationContext(), MainActivity.this);
                event.setAdapter(eventsAdapter);
            }
        });
    }

    private void help() {
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = getLayoutInflater();
                view = layoutInflater.inflate(R.layout.help, null);

                Button exit = view.findViewById(R.id.exit_help);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(view);
                builder.setCancelable(true);

                final AlertDialog dialog = builder.create();

                dialog.show();

                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });
    }

    private void addEvent() {
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, final int i, final int i1, final int i2) {
                addEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LayoutInflater layoutInflater = getLayoutInflater();
                        view = layoutInflater.inflate(R.layout.add_event, null);

                        Button add = view.findViewById(R.id.add);
                        Button cancel = view.findViewById(R.id.cancel);

                        final String title = view.findViewById(R.id.title).toString();
                        final String description = view.findViewById(R.id.description).toString();
                        TextView date = view.findViewById(R.id.date);

                        date.setText(i2+"/"+i1+"/"+i);



                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setView(view);
                        builder.setCancelable(true);

                        final AlertDialog dialog = builder.create();

                        dialog.show();

                        add.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //query(title, description, i2+"/"+i1+"/"+i")
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "Appointment created", Toast.LENGTH_SHORT).show();
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                            }
                        });
                    }
                });
            }
        });
    }

}
