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
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.statussaver.R;

import java.io.File;
import java.io.IOException;

public class Video extends AppCompatActivity {

    // Intent strings
    String uri;
    String destination;
    String path;
    String folderName;

    // xml initialization
    ImageView status,download,share;

    // Video initialization
    VideoView videoView;
    Intent intent;

    // file declaration
    File file,destinationFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        initViews();

        //Glide.with(this).load(uri).into(videoView);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        Uri uri1 = Uri.parse(uri);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri1);
        videoView.requestFocus();
        videoView.start();

        download.setOnClickListener(v -> {
            try {
                org.apache.commons.io.FileUtils.copyFileToDirectory(file,destinationFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(this,
                    new String[]{destinationFile + folderName},
                    new String[]{"*/*"},
                    new MediaScannerConnection.MediaScannerConnectionClient() {
                        @Override
                        public void onMediaScannerConnected() {

                        }

                        @Override
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    });
            Dialog dialog = new Dialog(Video.this);
            dialog.setContentView(R.layout.item_custom_dialogue);
            dialog.show();
            Button button = dialog.findViewById(R.id.customDialoguOk);
            button.setOnClickListener(v1 -> dialog.dismiss());
        });
        share.setOnClickListener(v -> {
            Intent send = new Intent();
            send.setAction(Intent.ACTION_SEND);
            send.setType("Share Via");
            startActivity(intent);
        });
    }

    private void initViews() {
        videoView = findViewById(R.id.videoView1);

        // Getting Intent Strings from previous activity
        intent = getIntent();
        uri = intent.getStringExtra("VEDIO_URI");
        destination = intent.getStringExtra("VEDIO_DESTINATION_PATH");
        path = intent.getStringExtra("VEDIO_CURRENT_FOLDER");
        folderName = intent.getStringExtra("FILENAME_VIDEO");

        //xml view initialization
        status = findViewById(R.id.statusSaverLogoVideo);
        download = findViewById(R.id.downloadLogoVideo);
        share = findViewById(R.id.shareLogoVideo);

        // File declaration
        file = new File(path);
        destinationFile = new File(destination);
    }
}