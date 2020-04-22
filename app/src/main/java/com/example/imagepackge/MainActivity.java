package com.example.imagepackge;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.imagepackge.adapter.MainAdapter;
import com.example.imagepackge.utils.PickerConfig;

import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {
    private static final int READ_PERMISSION_CORD = 1;
    private static final String TAG = "MainActivity";
    private static final int MAX_SELECTED_PIC = 9;
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.showPicM);
        mainAdapter = new MainAdapter();
        recyclerView.setAdapter(mainAdapter);
        checkReadPermission();
        initConfig();
    }

    private void initConfig() {
        PickerConfig pickerConfig = PickerConfig.getInstance();
        pickerConfig.setMax_selected_pic(MAX_SELECTED_PIC);
        pickerConfig.setOnItemSelectedFinished(new PickerConfig.OnItemSelectedFinished() {
            @Override
            public void onItemSelectedFinished(List<Uri> list) {
                int horNum =1;
                if(list.size()<3){
                    horNum =list.size();
                }else{
                    horNum =3;
                }

                GridLayoutManager gridLayoutManager =new GridLayoutManager(MainActivity.this,horNum);
                recyclerView.setLayoutManager(gridLayoutManager);
                mainAdapter.setData(list,horNum);
            }
        });
    }

    public void pickImages(View view) {
        Intent intent = new Intent(MainActivity.this, PackageImage.class);
        startActivity(intent);
    }


    private void checkReadPermission() {
        int readPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (readPermission != PackageManager.PERMISSION_GRANTED && writePermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_PERMISSION_CORD);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == READ_PERMISSION_CORD) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "拥有权限");
            }
        }
    }
}
