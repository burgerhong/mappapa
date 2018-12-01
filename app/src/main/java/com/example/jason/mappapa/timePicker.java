package com.example.jason.mappapa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;

public class timePicker extends AppCompatActivity {
    private TimePicker timePicker1;
    int hour, minute;

    int slotCounter;

    public static class Data2{
        public int slotCount;

        public int getSlotCount(){return slotCount;}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);
        timePicker1 =    (TimePicker) findViewById(R.id.timePicker1);

        Button confirmButton =  (Button) findViewById(R.id.confirmButton2);
        Button returnButton = (Button) findViewById(R.id.returnButton2);


        DatabaseReference currentLocationCounter =FirebaseDatabase.getInstance().getReference().child("delivery").child("timeSlot");
        currentLocationCounter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                timePicker.Data2 data = dataSnapshot.getValue(timePicker.Data2.class);
                Log.d("slotkaopeh", String.valueOf(data.getSlotCount()));
                slotCounter = data.getSlotCount();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 hour = timePicker1.getCurrentHour();
                 minute = timePicker1.getCurrentMinute();
                 int hourMax;
                 if(hour==23) {
                     hourMax = 0;
                 }
                 else{
                     hourMax = hour +1;
                 }
                 DatabaseReference dbAddTimeSlot = FirebaseDatabase.getInstance().getReference();
                 DatabaseReference dbAddTime = dbAddTimeSlot.child("delivery").child("timeSlot").child("slotList").child("slot_"+slotCounter).child("pickUpTime");
                 DatabaseReference dbAddSlotID = dbAddTimeSlot.child("delivery").child("timeSlot").child("slotList").child("slot_"+slotCounter).child("slotID");
                 dbAddSlotID.setValue("slot_"+slotCounter);
                 dbAddTime.setValue(String.valueOf(hour)+String.valueOf(minute)+"-"+String.valueOf(hourMax)+String.valueOf(minute));
                 slotCounter++;
                 DatabaseReference dbAddSlotCounter = dbAddTimeSlot.child("delivery").child("timeSlot").child("slotCount");
                 dbAddSlotCounter.setValue(slotCounter);
                Log.d("timepuki", String.valueOf(hour)+String.valueOf(minute));
            }
        });

    }

}
