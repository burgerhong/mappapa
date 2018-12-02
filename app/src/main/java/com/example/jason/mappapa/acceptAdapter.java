package com.example.jason.mappapa;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class acceptAdapter extends RecyclerView.Adapter<acceptAdapter.LocationViewHolder> {

    private Context mCtx;

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
                    Context context = v.getContext();
                    Intent intent = new Intent(context, confirmDelivery.class);
                    intent.putExtra("address",textViewAddress.getText());
                    intent.putExtra("user",textViewUser.getText());
                    intent.putExtra("latitude",finallatitude);
                    intent.putExtra("longitude",finallongitude);
                    intent.putExtra("slotID",textViewSlot.getText());
                    context.startActivity(intent);

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

