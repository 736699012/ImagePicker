package com.example.imagepackge.adapter;

import android.graphics.Point;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.imagepackge.R;
import com.example.imagepackge.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.InnerHolder> {

    List<Uri> list = new ArrayList<>();
    private int horNum;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);
        view.findViewById(R.id.select).setVisibility(View.GONE);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        View itemView = holder.itemView;
        ImageView iv = itemView.findViewById(R.id.iv);
        Point point = SizeUtils.getScreenSize(iv.getContext());
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(point.x / horNum, point.x / horNum);
        itemView.setLayoutParams(layoutParams);
        Glide.with(itemView.getContext()).load(list.get(position)).into(iv);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<Uri> list,int horNum){
        this.list.clear();
        this.list.addAll(list);
        this.horNum =horNum;
        notifyDataSetChanged();
    }


    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
