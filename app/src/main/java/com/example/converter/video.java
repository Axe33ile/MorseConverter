package com.example.converter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;

public class  video extends AppCompatActivity {
    VideoView videoView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

    videoView = (VideoView) findViewById(R.id.video);
    String videoPath="android.resource://" + getPackageName() + "/" + R.raw.video;
        MediaController mediaController = new MediaController(this);
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);


        videoView.start();

    }

}