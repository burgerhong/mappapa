package com.example.jason.mappapa;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class acceptAdapter extends RecyclerView.Adapter<acceptAdapter.LocationViewHolder> {

    int locationCounter;

    public static class Data1{
        public int locationCount;

        public int getLocationCount(){return locationCount;}
    }

    private Context mCtx;

    String slotID ;
    String address ;
    String requester ;

    private List<temporaryDetails> tempList = new ArrayList<>();

    double finallatitude, finallongitude;

    public acceptAdapter(Context mCtx, List<temporaryDetails> tempList) {
        this.mCtx = mCtx;
        this.tempList = tempList;
    }

    @Override
    public acceptAdapter.LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.temp_row,parent, false);
        return new acceptAdapter.LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(acceptAdapter.LocationViewHolder holder, int position) {
        //getting the product of the specified position
        temporaryDetails accept = tempList.get(position);

        finallatitude = accept.getLatitude();
        finallongitude = accept.getLongitude();
        Geocoder gcd = new Geocoder(mCtx,Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = gcd.getFromLocation(finallatitude,finallongitude,1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses.size()>0){
            String addressLine = addresses.get(0).getAddressLine(0);
            Log.d("o0o",addressLine);
           holder.textViewAddress.setText(addressLine);
        }

        //binding the data with the viewholder views
        holder.textViewUser.setText(accept.getRequester());
        holder.textViewSlot.setText(String.valueOf(accept.getSlotID()));

        slotID = (String) holder.textViewSlot.getText();
        address = (String) holder.textViewAddress.getText();
        requester = (String) holder.textViewUser.getText();
    }

    @Override
    public int getItemCount() {
        return tempList.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder {

        TextView textViewAddress, textViewUser, textViewSlot, textViewPrice;
        ImageView imageView;

        public LocationViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseReference currentLocationCounter =FirebaseDatabase.getInstance().getReference().child("delivery").child("timeSlot").child("slotList").child(slotID);
                    currentLocationCounter.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            confirmDelivery.Data1 data = dataSnapshot.getValue(confirmDelivery.Data1.class);
                            Log.d("locationkaopeh", String.valueOf(data.getLocationCount()));
                            locationCounter = data.getLocationCount();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                    Context context = v.getContext();


                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(context);
                    }
                    builder.setTitle("Willing to deliver?")
                            .setMessage("Requester information?\n"+"Requester:"+requester+"\n"+"Deliver Location:\n"+address)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                    DatabaseReference addDBlatitude = databaseReference.child("delivery").child("timeSlot").child("slotList").child(slotID).child("deliverList").child("Location_" + locationCounter + "").child("latitude");
                                    DatabaseReference addDBlongitude = databaseReference.child("delivery").child("timeSlot").child("slotList").child(slotID).child("deliverList").child("Location_" + locationCounter + "").child("longitude");
                                    DatabaseReference addDBrequester = databaseReference.child("delivery").child("timeSlot").child("slotList").child(slotID).child("deliverList").child("Location_" + locationCounter + "").child("requester");
                                    addDBlatitude.setValue(finallatitude);
                                    addDBlongitude.setValue(finallongitude);
                                    addDBrequester.setValue(requester);

                                    // RMB TO DELETE TEMPORARY LIST

                                    DatabaseReference dbcurrentLocationCount = databaseReference.child("delivery").child("timeSlot").child("slotList").child(slotID).child("locationCount");
                                    locationCounter++;
                                    dbcurrentLocationCount.setValue(locationCounter);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();


                 /*   Intent intent = new Intent(context, confirmDelivery.class);
                    intent.putExtra("address",textViewAddress.getText());
                    intent.putExtra("user",textViewUser.getText());
                    intent.putExtra("latitude",finallatitude);
                    intent.putExtra("longitude",finallongitude);
                    intent.putExtra("slotID",textViewSlot.getText());
                    context.startActivity(intent); */

                }
            });

            textViewAddress = itemView.findViewById(R.id.poptextViewAddress);
            textViewUser = itemView.findViewById(R.id.poptextViewUserName);
            textViewSlot = itemView.findViewById(R.id.poptextViewSlot);
            //  textViewPrice = itemView.findViewById(R.id.textViewPrice);
            //  imageView = itemView.findViewById(R.id.imageView);
        }
    }

}

