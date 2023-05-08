package com.example.rdvmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MediaPlayer player;

    MyDBHelper myDB;

    ArrayList<String> rdv_id,rdv_title, rdv_date, rdv_time, rdv_contact;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView =findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddRDV.class);
                startActivity(intent);
            }
        });

        myDB = new MyDBHelper(MainActivity.this);
        rdv_id = new ArrayList<>();
        rdv_title = new ArrayList<>();
        rdv_date = new ArrayList<>();
        rdv_time = new ArrayList<>();
        rdv_contact = new ArrayList<>();

        StoreDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this,this, rdv_id,rdv_title,rdv_date,rdv_time,rdv_contact);
        recyclerView.setAdapter(customAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            recreate();
        }

    }

    void StoreDataInArrays(){

        Cursor cursor = myDB.readAllData();
        if(cursor.getCount()==0){
            Toast.makeText(this,"No Data", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                rdv_id.add(cursor.getString(0));
                rdv_title.add(cursor.getString(1));
                rdv_date.add(cursor.getString(2));
                rdv_time.add(cursor.getString(3));
                rdv_contact.add(cursor.getString(4));

            }
        }

    }

    public void play(View v) {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.song);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlayer();
                }
            });
        }

        player.start();
    }

    public void pause(View v) {
        if (player != null) {
            player.pause();
        }
    }

    public void stop(View v) {
        stopPlayer();
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }



}