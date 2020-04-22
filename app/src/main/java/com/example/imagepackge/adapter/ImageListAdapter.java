package com.example.imagepackge.adapter;

import android.graphics.Point;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.imagepackge.R;
import com.example.imagepackge.utils.PickerConfig;
import com.example.imagepackge.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.InnerHolder> {

    private List<Uri> mimageItems = new ArrayList<>();
    private List<Uri> mselect = new ArrayList<>();
    private static final int MAX_SELECTED_PIC = 9;
    private onItemSelectedNum mOnItemSelectedNum = null;

    public List<Uri> getMselect() {
        return mselect;
    }

    public void setMselect(List<Uri> mselect) {
        this.mselect = mselect;
    }

    public int getMaxselected() {
        return maxselected;
    }

    public void setMaxselected(int maxselected) {
        this.maxselected = maxselected;
    }

    public int maxselected  =MAX_SELECTED_PIC;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        Point point = SizeUtils.getScreenSize(view.getContext());
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(point.x / 3, point.y / 3);
        view.setLayoutParams(layoutParams);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        View view = holder.itemView;
        ImageView imageView = view.findViewById(R.id.iv);
        final View visibility = view.findViewById(R.id.visibility);
        final CheckBox select = view.findViewById(R.id.select);
        final Uri uri = mimageItems.get(position);
        Glide.with(imageView.getContext()).load(uri).into(imageView);
//      先根据状态来渲染，防止紊乱
        if (mselect.contains(uri)) {
            select.setButtonDrawable(R.drawable.image1);
            visibility.setVisibility(View.VISIBLE);
        } else {
            select.setButtonDrawable(R.drawable.image2);
            visibility.setVisibility(View.INVISIBLE);
        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                如果已经没被选择，添加
                if (!mselect.contains(uri)) {
                    if(mselect.size()>=9){
                        Toast.makeText(select.getContext(),"最多选择"+MAX_SELECTED_PIC+"张图片",Toast.LENGTH_SHORT).show();

                    }else{
                        mselect.add(uri);
                        select.setButtonDrawable(R.drawable.image1);
                        visibility.setVisibility(View.VISIBLE);
                    }

                } else {
//                    如果已经被选择，删除，更改可见度
                    mselect.remove(uri);
                    select.setButtonDrawable(R.drawable.image2);
                    visibility.setVisibility(View.INVISIBLE);
                }
                mOnItemSelectedNum.onItemSelectedChange(mselect);
            }
        });
    }

    public void setOnItemSelectedNum(onItemSelectedNum onItemSelectedNum){
        this.mOnItemSelectedNum = onItemSelectedNum;
    }

    public interface onItemSelectedNum{
        void onItemSelectedChange(List<Uri> list);
    }


    @Override
    public int getItemCount() {
        return mimageItems.size();
    }

    public void setData(List<Uri> imageItems) {
        mimageItems.clear();
        mimageItems.addAll(imageItems);
        notifyDataSetChanged();
    }


    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
