package com.example.jason.mappapa;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class confirmDelivery extends AppCompatActivity {

    int locationCounter;

    public static class Data1{
        public int locationCount;

         public int getLocationCount(){return locationCount;}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_delivery);

        Button accept = (Button)findViewById(R.id.acceptButton);
        Button reject = (Button) findViewById(R.id.rejectButton);

        String slotID = null;
        Double latitude = null;
        Double longitude =null;

        String address = null;
        String requester = null;

        Intent iin= getIntent();
        Bundle b = iin.getExtras();

        if(b!=null)
        {
            address = (String) b.get("address");
            latitude = (Double) b.get("latitude");
            longitude = (Double) b.get("longitude");
            slotID = (String) b.get("slotID");
            requester =(String)b.get("user");
        }
        final String finaladdress = address;
        final Double finallatitude = latitude;
        final Double finallongitude = longitude;
        final String finalslotID = slotID;
        final String finalrequester = requester;

        TextView nearLocation = findViewById(R.id.textView7);

      /*  Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(finallatitude,finallongitude,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size()>0){
            String addressLine = addresses.get(0).getAddressLine(0);
            nearLocation.setText(addressLine);
        }*/

        nearLocation.setText(address);


        DatabaseReference currentLocationCounter =FirebaseDatabase.getInstance().getReference().child("delivery").child("timeSlot").child("slotList").child(finalslotID);
        currentLocationCounter.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Data1 data = dataSnapshot.getValue(Data1.class);
                Log.d("locationkaopeh", String.valueOf(data.getLocationCount()));
                locationCounter = data.getLocationCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("lilala",finalslotID);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference addDBlatitude = databaseReference.child("delivery").child("timeSlot").child("slotList").child(finalslotID).child("deliverList").child("Location_" + locationCounter + "").child("latitude");
                DatabaseReference addDBlongitude = databaseReference.child("delivery").child("timeSlot").child("slotList").child(finalslotID).child("deliverList").child("Location_" + locationCounter + "").child("longitude");
                DatabaseReference addDBrequester = databaseReference.child("delivery").child("timeSlot").child("slotList").child(finalslotID).child("deliverList").child("Location_" + locationCounter + "").child("requester");
                addDBlatitude.setValue(finallatitude);
                addDBlongitude.setValue(finallongitude);
                addDBrequester.setValue(finalrequester);

                // RMB TO DELETE TEMPORARY LIST

                DatabaseReference dbcurrentLocationCount = databaseReference.child("delivery").child("timeSlot").child("slotList").child(finalslotID).child("locationCount");
                locationCounter++;
                dbcurrentLocationCount.setValue(locationCounter);

            }
        });
    }
}
