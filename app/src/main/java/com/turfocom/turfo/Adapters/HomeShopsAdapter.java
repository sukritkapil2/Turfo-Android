package com.turfocom.turfo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.turfocom.turfo.R;
import com.turfocom.turfo.Models.Shop;
import com.turfocom.turfo.ShopVisitActivity;

import java.util.List;

public class HomeShopsAdapter extends RecyclerView.Adapter<HomeShopsAdapter.CustomViewHolder> {

    private List<Shop> dataList;
    private Context context;

    public HomeShopsAdapter(Context context, List<Shop> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.shop_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.shopName.setText(dataList.get(position).getName());
        holder.shopAddress.setText(dataList.get(position).getAddress());
        holder.shopCategory.setText(dataList.get(position).getCategory().toUpperCase());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShopVisitActivity.class);
                intent.putExtra("shopId", dataList.get(position).get_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        TextView shopName,shopAddress, shopCategory;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mView = itemView;
            shopName = mView.findViewById(R.id.shopName);
            shopAddress = mView.findViewById(R.id.Address);
            shopCategory = mView.findViewById(R.id.Category);
        }
    }
}