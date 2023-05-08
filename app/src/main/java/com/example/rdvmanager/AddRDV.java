package com.example.rdvmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Calendar;

public class AddRDV extends AppCompatActivity {

    EditText title_input,contact_input;
    TextView time_input,date_input;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rdv);

        title_input=findViewById(R.id.title_input);
        date_input=findViewById(R.id.date_input);
        time_input=findViewById(R.id.time_input);
        contact_input=findViewById(R.id.contact_input);

        button=findViewById(R.id.button);

        final Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final  int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);

        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(AddRDV.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        date_input.setText(date);

                    }
                },year,month,day);
                dialog.show();
            }
        });

        time_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddRDV.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0,0,0, hourOfDay, minute);

                        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
                        time_input.setText("Time: "+sdf.format(calendar.getTime()));

                    }
                },12,0,false);
                timePickerDialog.show();

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDBHelper myDB = new MyDBHelper(AddRDV.this);
                myDB.addRDV(title_input.getText().toString().trim(),
                        date_input.getText().toString().trim(),
                        time_input.getText().toString().trim(),
                        contact_input.getText().toString().trim());

            }
        });

    }
}