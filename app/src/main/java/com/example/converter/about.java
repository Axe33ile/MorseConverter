package com.example.converter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;
import java.io.File;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked_btn("https://www.facebook.com/BarPupco/");
            }
        });

        findViewById(R.id.facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked_btn("https://www.facebook.com/BarPupco/");
            }
        });

        findViewById(R.id.instagram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked_btn("https://www.instagram.com/barpupco/");
            }
        });


        findViewById(R.id.linkedin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicked_btn("https://www.linkedin.com/in/bar-popko-11b1981aa/");
            }


        });

//send text and share

        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


    }

        public void buttonShareFile(View view){

            Intent intentShare = new Intent(Intent.ACTION_SEND);
            intentShare.setType("text/plain");
            intentShare.putExtra(Intent.EXTRA_SUBJECT,"Installation File of MorseCode APPLICATION");
            intentShare.putExtra(Intent.EXTRA_TEXT , "Hi , this is the link to install the application : shorturl.at/nCNQS ");

            startActivity(Intent.createChooser(intentShare,"Share the text."));


    }

    public void buttonShareText(View view){

        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_EMAIL,new String[]{"barpupco@gmail.com"});
        intentShare.putExtra(Intent.EXTRA_SUBJECT,"Your Subject");
        intentShare.putExtra(Intent.EXTRA_TEXT , "Hi , i will be awesome to hear from you , enter your text here :)");

        startActivity(Intent.createChooser(intentShare,"Share the text."));

    }

    public void clicked_btn(String url) {
        Intent intent = new Intent((Intent.ACTION_VIEW));
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}