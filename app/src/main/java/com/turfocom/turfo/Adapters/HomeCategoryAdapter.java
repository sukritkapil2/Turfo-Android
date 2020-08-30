package com.turfocom.turfo.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.turfocom.turfo.CategoryProductsActivity;
import com.turfocom.turfo.Models.Category;
import com.turfocom.turfo.R;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.CustomViewHolder> {

    private List<Category> dataList;
    private Context context;

    public HomeCategoryAdapter(Context context, List<Category> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.category_row, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.category_name.setText(dataList.get(position).getName());
        Glide.with(holder.mView.getContext())
                .asBitmap()
                .load(dataList.get(position).getPic())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC))
                .into(new BitmapImageViewTarget(holder.category_image) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        //Play with bitmap
                        super.setResource(resource);
                    }
                });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryProductsActivity.class);
                intent.putExtra("category", dataList.get(position).getName());
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
        TextView category_name;
        ImageView category_image;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mView = itemView;
            category_name = mView.findViewById(R.id.category_name);
            category_image = mView.findViewById(R.id.category_img);
        }
    }
}