package com.example.library_planner;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class CreateEvent extends AppCompatActivity {

    EditText etName, etStart, etDuration, etDate;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        etName = findViewById(R.id.etCreateName);
        etDuration = findViewById(R.id.etCreateDuration);

        //Display a Time Picker Dialog upon clicking the EditText
        etStart = findViewById(R.id.etCreateStart);
        etStart.setOnClickListener(v -> {

            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {

                @SuppressLint("DefaultLocale") String fix_00 = String.format("%02d", minute1);
                String current_date = hourOfDay + ":" + fix_00;
                etStart.setText(current_date);

            }, hour, minute, true);

            timePickerDialog.show();

        });

        //Display a Date Picker Dialog upon clicking the EditText
        etDate = findViewById(R.id.etCreateDate);
        etDate.setOnClickListener(v -> {

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year1, month1, dayOfMonth) -> {
                        String current_date = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        etDate.setText(current_date);
                    },
                    year, month, day);

            datePickerDialog.show();

        });

        // Cancel button to go back to MainActivity
        findViewById(R.id.btnBack).setOnClickListener(v -> go_home());

        findViewById(R.id.btnCreate).setOnClickListener(v -> {

            String name_ = etName.getText().toString();
            String duration_ = etDuration.getText().toString();
            String start_ = etStart.getText().toString();
            String date_ = etDate.getText().toString();

            LibEvent event = new LibEvent();
            event.setEventName(name_);
            event.setEventDuration(duration_);
            event.setEventStart(start_);
            event.setEventDate(date_);

            try (DB_Helper dbHelper = new DB_Helper(CreateEvent.this)) {
                dbHelper.create_event(event);
            }

            CreateEvent.this.go_home();

        });

    }


    private void go_home() {

        Intent mainMenu = new Intent(CreateEvent.this, MainActivity.class);
        startActivity(mainMenu);

    }
}
