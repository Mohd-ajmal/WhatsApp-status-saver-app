package com.example.statussaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import com.example.statussaver.MainScreen.Adapter;
import com.example.statussaver.MainScreen.Contants;
import com.example.statussaver.MainScreen.PojoClass;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;

import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity {

    // RecyclerView items
    Adapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    // files
    File[] files;

    // content
    ArrayList<PojoClass> content = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        checkPermissionFirst();


        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            setUpLayout();
            {
                new Handler().postDelayed(() -> swipeRefreshLayout.setRefreshing(false),1000);
            }
        });
    }

    private void setUpLayout() {
        content.clear();
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        adapter = new Adapter(getData(),this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private ArrayList<PojoClass> getData() {
        PojoClass pojoClass;
        String targetPath = Environment.getExternalStorageDirectory().getAbsolutePath()+ Contants.FOLDER_NAME+"Media/.Statuses";
        File file = new File(targetPath);
        files = file.listFiles();
        for (File f : files) {
            pojoClass = new PojoClass();
            pojoClass.setUri(Uri.fromFile(f));
            pojoClass.setPath(f.getAbsolutePath());
            pojoClass.setFilename(f.getName());
            if (!pojoClass.getUri().toString().endsWith(".nomedia"))
                content.add(pojoClass);
        }
        return content;
    }

    private void initViews() {
        // recyclerView initialization and setup
        swipeRefreshLayout = findViewById(R.id.swipeRefereshLayout);
        recyclerView = findViewById(R.id.mainRecyclerView);

    }

    private void checkPermissionFirst() {
        if(SDK_INT>23)
        {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "warning: your device hacked", Toast.LENGTH_SHORT).show();
                setUpLayout();
            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
        else {
            Toast.makeText(this, "Permission granted already", Toast.LENGTH_SHORT).show();
            setUpLayout();
        }
    }
}