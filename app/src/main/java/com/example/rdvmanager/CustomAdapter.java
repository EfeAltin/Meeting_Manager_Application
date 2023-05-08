package com.example.rdvmanager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private Context context;
    Activity activity;
    private ArrayList rdv_id,rdv_title, rdv_date,rdv_time;
    private ArrayList rdv_contact;

    CustomAdapter(Activity activity,Context context, ArrayList rdv_id, ArrayList rdv_title, ArrayList rdv_date, ArrayList rdv_time,ArrayList rdv_contact){

        this.activity=activity;
        this.context=context;
        this.rdv_id=rdv_id;
        this.rdv_title=rdv_title;
        this.rdv_date=rdv_date;
        this.rdv_time=rdv_time;
        this.rdv_contact=rdv_contact;



    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
       View view = layoutInflater.inflate(R.layout.my_row,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.rdv_id_txt.setText(String.valueOf(rdv_id.get(position)));
        holder.rdv_title_txt.setText(String.valueOf(rdv_title.get(position)));
        holder.rdv_date_txt.setText(String.valueOf(rdv_date.get(position)));
        holder.rdv_time_txt.setText(String.valueOf(rdv_time.get(position)));
        holder.rdv_contact.setText(String.valueOf(rdv_contact.get(position)));





        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(context,UpdateActivity.class);
              intent.putExtra("id",String.valueOf(rdv_id.get(position)));
                intent.putExtra("title",String.valueOf(rdv_title.get(position)));
                intent.putExtra("date",String.valueOf(rdv_date.get(position)));
                intent.putExtra("time",String.valueOf(rdv_time.get(position)));
                intent.putExtra("contact",String.valueOf(rdv_contact.get(position)));


              activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rdv_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView rdv_id_txt,rdv_title_txt,rdv_date_txt,rdv_time_txt,rdv_contact;
        LinearLayout mainLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rdv_id_txt=itemView.findViewById(R.id.rdv_id_txt);
            rdv_title_txt=itemView.findViewById(R.id.rdv_title_txt);
            rdv_date_txt=itemView.findViewById(R.id.rdv_date_txt);
            rdv_time_txt=itemView.findViewById(R.id.rdv_time_txt);
            rdv_contact=itemView.findViewById(R.id.rdv_contact_txt);

            mainLayout=itemView.findViewById(R.id.mainLayout);

        }
    }


}
