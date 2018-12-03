package com.example.jason.mappapa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class locationAdapter extends RecyclerView.Adapter<locationAdapter.LocationViewHolder>{


    //this context we will use to inflate the layout
    private Context mCtx;



    //we are storing all the products in a list
    private List<locationDetails> locationList = new ArrayList<>();

    //getting the context and product list with constructor
    public locationAdapter(Context mCtx, List<locationDetails> locationList) {
        this.mCtx = mCtx;
        this.locationList = locationList;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.location_row,parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        //getting the product of the specified position
        locationDetails location = locationList.get(position);



        //binding the data with the viewholder views
        holder.textViewTitle.setText(location.getPickUpTime());
        holder.textViewShortDesc.setText(location.getUser());
        holder.textViewRating.setText(String.valueOf(location.getSlotID()));

      //  holder.textViewPrice.setText(String.valueOf(location.getPrice()));

    }


    @Override
    public int getItemCount() {
        return locationList.size();
    }


    class LocationViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle, textViewShortDesc, textViewRating, textViewPrice;
        ImageView imageView;

        public LocationViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, pinDeliveryLocation.class);
                        intent.putExtra("pickupTime",textViewTitle.getText());
                        intent.putExtra("userID",textViewShortDesc.getText());
                        intent.putExtra("slotID",textViewRating.getText());
                        context.startActivity(intent);

                }
            });

            textViewTitle = itemView.findViewById(R.id.poptextViewAddress);
            textViewShortDesc = itemView.findViewById(R.id.poptextViewUserName);
            textViewRating = itemView.findViewById(R.id.poptextViewSlot);
          //  textViewPrice = itemView.findViewById(R.id.textViewPrice);
          //  imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
