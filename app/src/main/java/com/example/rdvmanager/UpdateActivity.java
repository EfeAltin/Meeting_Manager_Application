package com.example.rdvmanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input,contact_input;
    TextView time_input,date_input;
    Button update_button,delete_button,share_button;

    String id,title,date,time,contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input=findViewById(R.id.title_input2);
        date_input=findViewById(R.id.date_input2);
        time_input=findViewById(R.id.time_input2);
        contact_input=findViewById(R.id.contact_input2);
        update_button= findViewById(R.id.update_button);
        share_button=findViewById(R.id.share_button);
        delete_button=findViewById(R.id.delete_button);

        final Calendar calender = Calendar.getInstance();
        final int year = calender.get(Calendar.YEAR);
        final  int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);

        share_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareMethod(title_input);
            }
        });

        date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
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

                TimePickerDialog timePickerDialog = new TimePickerDialog(UpdateActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

        getAndSetIndentData();

        ActionBar ab= getSupportActionBar();
        if(ab!=null){
            ab.setTitle(title);
        }

        update_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MyDBHelper myDB = new MyDBHelper(UpdateActivity.this);

                title = title_input.getText().toString().trim();
                date = date_input.getText().toString().trim();
                time = time_input.getText().toString().trim();
                contact=contact_input.getText().toString().trim();

                myDB.updateData(id,title,date,time,contact);

            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });



    }
    void getAndSetIndentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title")&& getIntent().hasExtra("date")&&getIntent().hasExtra("time")){
//Getting data from Intent

            id=getIntent().getStringExtra("id");
            title=getIntent().getStringExtra("title");
            date=getIntent().getStringExtra("date");
            time=getIntent().getStringExtra("time");
            contact=getIntent().getStringExtra("contact");
//Setting intent data

           title_input.setText(title);
           date_input.setText(date);
           time_input.setText(time);
           contact_input.setText(contact);


        }else{
            Toast.makeText(this,"No Data",Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ title +" ? ");
        builder.setMessage("Are you sure you want to delete? "+title+" ?" );
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDBHelper myDB = new MyDBHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();

    }

    public void shareMethod(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,title+" / "+date+" / "+time+" / "+contact);

        sendIntent.setType("text/plain");
        //startActivity(sendIntent);
        startActivity(Intent.createChooser(sendIntent, "Share App"));
    }
}


