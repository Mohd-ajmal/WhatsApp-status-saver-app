package com.example.statussaver.VideoAndPicture;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.statussaver.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Picture extends AppCompatActivity {

    // Intent strings
    String uri;
    String destination;
    String path;
    String folderName;

    // xml initialization
    ImageView status,download,share;

    // image view picture
    ImageView picture;
    Intent intent;

    // File
    File file,destination_path_file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        initViews();
        Glide.with(this).load(uri).into(picture);

        download.setOnClickListener(v -> {
            try {
                org.apache.commons.io.FileUtils.copyFileToDirectory(file,destination_path_file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(this,
                    new String[]{destination_path_file + folderName},
                    new String[]{"*/*"},
                    new MediaScannerConnection.MediaScannerConnectionClient() {
                @Override
                public void onMediaScannerConnected() {

                }

                @Override
                public void onScanCompleted(String path, Uri uri) {

                }
            });
            Dialog dialog = new Dialog(Picture.this);
            dialog.setContentView(R.layout.item_custom_dialogue);
            dialog.show();
            Button button = dialog.findViewById(R.id.customDialoguOk);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        });
    }

    private void initViews() {
        picture = findViewById(R.id.imageViewPicture);

        // Getting Intent Strings from previous activity
        intent = getIntent();
        uri = intent.getStringExtra("URI");
        destination = intent.getStringExtra("DESTINATION_PATH");
        path = intent.getStringExtra("CURRENT_FOLDER");
        folderName = intent.getStringExtra("FILENAME");

        //xml view initialization
        status = findViewById(R.id.statusSaverLogoPicture);
        download = findViewById(R.id.downloadLogoPicture);
        share = findViewById(R.id.shareLogoPicture);

        // File declaration
        file = new File(path);
        destination_path_file = new File(destination);

    }
}