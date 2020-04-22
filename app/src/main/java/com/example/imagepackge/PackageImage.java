package com.example.imagepackge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import com.example.imagepackge.adapter.ImageListAdapter;
import com.example.imagepackge.utils.PickerConfig;

import java.util.ArrayList;
import java.util.List;

public class PackageImage extends AppCompatActivity {


    private static final String TAG = "PackageImage";
    private static final int LOADER_ID = 1;
    private List<Uri> imageItemList = new ArrayList<>();
    private RecyclerView showImages;
    private ImageListAdapter adapter;
    private TextView finishView;
    private PickerConfig mpickerConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_image);
        initView();
        initAdapter();
        initLoaderManager();
        initConfig();
    }

    private void initConfig() {
        mpickerConfig = PickerConfig.getInstance();
        adapter.setMaxselected(mpickerConfig.getMax_selected_pic());
    }

    public void finishedPic(View view){

        //需要获取数据
        List<Uri> list = adapter.getMselect();
        //把数据通知给其他地方
        PickerConfig.OnItemSelectedFinished monItemSelectedFinished = mpickerConfig.getMonItemSelectedFinished();
        if(monItemSelectedFinished!=null){
            monItemSelectedFinished.onItemSelectedFinished(list);
        }

        //结束
        finish();
    }

    private void initView() {
        finishView = findViewById(R.id.finished_pic);
        showImages = findViewById(R.id.showImages);
    }

    private void initAdapter() {
        adapter = new ImageListAdapter();
        adapter.setOnItemSelectedNum(new ImageListAdapter.onItemSelectedNum() {
            @Override
            public void onItemSelectedChange(List<Uri> list) {
                finishView.setText("("+list.size()+"/"+adapter.getMaxselected()+")完成");
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
        showImages.setLayoutManager(gridLayoutManager);
        showImages.setAdapter(adapter);
    }

    private void initLoaderManager() {
        imageItemList.clear();
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @NonNull
            @Override
            public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

                if (id == LOADER_ID) {
                    return new CursorLoader(PackageImage.this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            new String[]{MediaStore.Images.Media._ID}, null, null, "date_added DESC");

                }
                return null;
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
                if (cursor != null) {

                    while (cursor.moveToNext()) {

                        String id = cursor.getString(0);
                        Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id);
                        imageItemList.add(uri);
                        adapter.setData(imageItemList);
                    }


                }
            }

            @Override
            public void onLoaderReset(@NonNull Loader<Cursor> loader) {

            }
        });
    }



}
