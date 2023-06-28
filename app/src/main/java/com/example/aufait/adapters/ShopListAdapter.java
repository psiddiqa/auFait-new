package com.example.aufait.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aufait.R;
import com.example.aufait.model.ShopModel;

import java.util.List;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.MyViewHolder> {

    private List<ShopModel> shopModelList;
    private ShopListClickListener clickListener;

    public ShopListAdapter(List<ShopModel> shopModelList, ShopListClickListener clickListener) {
        this.shopModelList = shopModelList;
        this.clickListener = clickListener;
    }

    public void updateData(List<ShopModel> shopModelList) {
        this.shopModelList = shopModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShopListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListAdapter.MyViewHolder holder, int position) {
        holder.shopName.setText(shopModelList.get(position).getName());
        holder.shopAddress.setText("Address: "+shopModelList.get(position).getAddress());
        holder.shopHours.setText("Today's hours: " + shopModelList.get(position).getHours().getTodaysHours());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(shopModelList.get(position));
            }
        });
        Glide.with(holder.thumbImage)
                .load(shopModelList.get(position).getImage())
                .into(holder.thumbImage);

    }

    @Override
    public int getItemCount() {
        return shopModelList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  shopName;
        TextView  shopAddress;
        TextView  shopHours;
        ImageView thumbImage;

        public MyViewHolder(View view) {
            super(view);
            shopName = view.findViewById(R.id.shopName);
            shopAddress = view.findViewById(R.id.shopAddress);
            shopHours = view.findViewById(R.id.shopHours);
            thumbImage = view.findViewById(R.id.thumbImage);

        }
    }

    public interface ShopListClickListener {
        public void onItemClick(ShopModel shopModel);
    }
}
