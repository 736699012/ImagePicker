package com.example.imagepackge.utils;

import android.net.Uri;

import java.util.List;

public class PickerConfig {
    private int max_selected_pic = 1;
    private OnItemSelectedFinished monItemSelectedFinished = null;

    public OnItemSelectedFinished getMonItemSelectedFinished() {
        return monItemSelectedFinished;
    }

    private PickerConfig(){}
    private static PickerConfig pickerConfig;

    public static PickerConfig getInstance(){
        if(pickerConfig==null){
            pickerConfig =new PickerConfig();
        }
        return pickerConfig;
    }

    public void setOnItemSelectedFinished(OnItemSelectedFinished onItemSelectedFinished){
        this.monItemSelectedFinished = onItemSelectedFinished;
    }


    public interface OnItemSelectedFinished{
        void onItemSelectedFinished(List<Uri> list);
    }

    public int getMax_selected_pic() {
        return max_selected_pic;
    }

    public void setMax_selected_pic(int max_selected_pic) {
        this.max_selected_pic = max_selected_pic;
    }
}
