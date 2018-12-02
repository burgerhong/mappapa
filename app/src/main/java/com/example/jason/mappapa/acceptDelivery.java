package com.example.jason.mappapa;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class acceptDelivery extends AppCompatActivity {

    String userIDD = "Jason";

    //a list to store all the products
    List<temporaryDetails> acceptList= new ArrayList<>();

    //the recyclerview
    RecyclerView recyclerView1;

    public String userName2;
    double gloLatitude, gloLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_delivery);

        //getting the recyclerview from xml
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView1);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        final acceptAdapter adapter1 = new acceptAdapter(this, acceptList);

        DatabaseReference acceptSlot = FirebaseDatabase.getInstance().getReference("delivery").child("timeSlot").child("slotList");
        acceptSlot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()){
                    if (dataSnapshot2.child("user").getValue().toString().equals(userIDD)) {
                    //    String slotIden = dataSnapshot2.child("slotID").toString();
                        for (DataSnapshot hohoho : dataSnapshot2.child("temporaryList").getChildren()) {
                            temporaryDetails accept = hohoho.getValue(temporaryDetails.class);
                            String use = accept.getRequester();
                            double lat = accept.getLatitude();
                            double lon = accept.getLongitude();
                            String slot = dataSnapshot2.child("slotID").getValue().toString();
                            Log.d("alalala", use + lat + lon + slot);
                            acceptList.add(new temporaryDetails(use, lat, lon, slot));
                        }
                    }
                }
                adapter1.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView1.setAdapter(adapter1);


    }
}
