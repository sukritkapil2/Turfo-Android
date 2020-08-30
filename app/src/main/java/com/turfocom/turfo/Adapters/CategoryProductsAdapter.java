package com.turfocom.turfo.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.turfocom.turfo.Models.Product;
import com.turfocom.turfo.R;

import java.util.List;

public class CategoryProductsAdapter extends RecyclerView.Adapter<CategoryProductsAdapter.CustomViewHolder> {

    private List<Product> dataList;
    private Context context;

    public CategoryProductsAdapter(Context context, List<Product> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.category_product_adapter, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.name.setText(dataList.get(position).getName());
        holder.rating.setText(dataList.get(position).getRating() + " Stars");
        holder.price.setText("₹ " + dataList.get(position).getPrice());
        if(dataList.get(position).getDetails().length() > 100) {
            holder.details.setText(dataList.get(position).getDetails().substring(0, 100));
        } else {
            holder.details.setText(dataList.get(position).getDetails());
        }
        Glide.with(holder.mView.getContext())
                .asBitmap()
                .load(dataList.get(position).getThumbnail())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .into(new BitmapImageViewTarget(holder.image) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        //Play with bitmap
                        super.setResource(resource);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        public final View mView;
        TextView name, rating, price, details;
        ImageView image;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mView = itemView;
            name = mView.findViewById(R.id.productName);
            rating = mView.findViewById(R.id.rating);
            price = mView.findViewById(R.id.price);
            image = mView.findViewById(R.id.productImage);
            details = mView.findViewById(R.id.details);
        }
    }
}