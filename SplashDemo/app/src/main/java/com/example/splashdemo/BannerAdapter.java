package com.example.splashdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder>{
    private List<String> drawings;
    private Context mContext;
    private int Count;

    BannerAdapter(List<String> drawings, Context context) {
        this.drawings = drawings;
        mContext = context;
        Count = drawings.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //有几张图就搞多少取模
        int i = position % (Count - 1);
        Glide.with(mContext)
                .load(drawings.get(i + 1))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.banner_image);
        }
    }
}
