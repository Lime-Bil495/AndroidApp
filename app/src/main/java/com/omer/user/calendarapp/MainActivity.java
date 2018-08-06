package com.omer.user.calendarapp;

import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.omer.user.calendarapp.RestApi.ManagerAll;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    CalendarView calendar;
    ListView appointment;
    List<Appointment> appointments;
    AppointmentAdapter appointmentAdapter;
    Button addEvent, help, searchb;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
        initialize();
        help();

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, final int i, final int i1, final int i2) {
                int i_m = i1 + 1;
                Log.i("date", i2 + "/" + i_m + "/" + i);
                listAppointments(i2 + "/" + i_m + "/" + i);
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

        searchb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initialize() {
        calendar = findViewById(R.id.calendar);
        appointment = findViewById(R.id.appointments);
        appointments = new ArrayList<>();
        addEvent = findViewById(R.id.addevent);
        help = findViewById(R.id.help);
        searchb = findViewById(R.id.searchb);
        search = findViewById(R.id.search);
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

    private void fillList() {
        appointmentAdapter = new AppointmentAdapter(appointments, getApplicationContext(), MainActivity.this);
        appointment.setAdapter(appointmentAdapter);
    }

    private void listAppointments(String date){
        Call<List<Appointment>> x = ManagerAll.getInstance().all_appointments(date);
        x.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, Response<List<Appointment>> response) {
                if(response.isSuccessful()) {
                    appointments = response.body();
                    Log.i("date", appointments.toString() + " exists");
                    fillList();
                }
            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
                Log.i("date", t.getMessage().toString());
            }
        });
    }


}