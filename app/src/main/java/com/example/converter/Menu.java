package com.example.converter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends AppCompatActivity {
    Button convertor;
    Button video;
    Button morseTable;
    Button about;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        convertor = (Button) findViewById(R.id.morseConvertor);
        convertor.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Converter();
            }
        });

        morseTable = (Button) findViewById(R.id.table);
        morseTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morseTable();
            }
        });

        about = (Button) findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                about();
            }
        });
        showNotification();
/*
        checkConnection();
*/

    }

    //screen to converter
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Converter() {
        Intent intent = new Intent(this, Converter.class);
        startActivity(intent);
    }


    public void morseTable() {
        Intent intent = new Intent(this, MorseTable.class);
        startActivity(intent);
    }

    public void about() {
        Intent intent = new Intent(this, about.class);
        startActivity(intent);
    }

   /* public void checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetwork();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                Toast.makeText(this, "Wifi Enabled", Toast.LENGTH_SHORT).show();

            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                Toast.makeText(this, "Data network Enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
            }
        }

    }//checkConnection*/

    //notification
    String name="";
    String message=" .-.  ..-  -. ";


    public void showNotification(){
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("channelId","channelName",NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"channelId")
                .setContentTitle("Hi "+name)
                .setContentText("Last code you send was " )
                .setSmallIcon(R.mipmap.ic_launcher);

        //nomination last until user erase it.
        Intent intent = new Intent(this,Menu.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);


        manager.notify(1,builder.build());


    }

}
