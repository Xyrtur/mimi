package com.example.kattokleanup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    DateFormat df;
    Calendar toDate;
    SharedPreferences sharedPreferences;
    Button kleanupBtn;
    Button litterChangeBtn;
    TextView kleanupDay;
    TextView litterChangeDay;
    String kleanUpDate;
    String litterChangeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        df = new SimpleDateFormat("EEEE, MMM d", Locale.CANADA);

        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);


        kleanupBtn = (Button)findViewById(R.id.kleanup_btn);
        litterChangeBtn = (Button)findViewById(R.id.litter_change_btn);

        kleanupDay = (TextView)findViewById(R.id.kleanup_date);
        litterChangeDay = (TextView)findViewById(R.id.litter_change_date);

        sharedPreferences = getSharedPreferences("MyPref", 0);
        kleanUpDate = sharedPreferences.getString("kleanupDate","None yet...");
        litterChangeDate = sharedPreferences.getString("litterChangeDay","None yet...");

        kleanupDay.setText(kleanUpDate);
        litterChangeDay.setText(litterChangeDate);

        kleanupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Calendar d = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                                if(dayOfMonth +7 > d.getActualMaximum(Calendar.DAY_OF_MONTH)){
                                    if(monthOfYear +1 > 11){
                                        year++;
                                        monthOfYear = 0;
                                        dayOfMonth = 7 - (d.getActualMaximum(Calendar.DAY_OF_MONTH) - dayOfMonth);
                                    }else {
                                        monthOfYear ++;
                                        dayOfMonth = 7 - (d.getActualMaximum(Calendar.DAY_OF_MONTH) - dayOfMonth);
                                    }

                                } else {
                                    dayOfMonth = dayOfMonth +7;
                                }

                                toDate = new GregorianCalendar(year, monthOfYear, dayOfMonth);


                                kleanupDay.setText(df.format(toDate.getTime()));
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("kleanupDate", df.format(toDate.getTime()));
                                editor.commit();

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        litterChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                Calendar d = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                                if(dayOfMonth +30 > d.getActualMaximum(Calendar.DAY_OF_MONTH)){
                                    if(monthOfYear +1 > 11){
                                        year++;
                                        monthOfYear = 0;
                                        dayOfMonth = 30 - (d.getActualMaximum(Calendar.DAY_OF_MONTH) - dayOfMonth);
                                    }else {
                                        monthOfYear ++;
                                        dayOfMonth = 30 - (d.getActualMaximum(Calendar.DAY_OF_MONTH) - dayOfMonth);
                                    }

                                } else {
                                    dayOfMonth = dayOfMonth +30;
                                }


                                toDate = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                                litterChangeDay.setText(df.format(toDate.getTime()));
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("litterChangeDay", df.format(toDate.getTime()));
                                editor.commit();

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

    }
}
