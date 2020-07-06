package com.example.grimpro;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.grimpro.MainService;

import java.util.ArrayList;

public class RestartService extends BroadcastReceiver {

    ArrayList<String> installedApps;
    @Override
    public void onReceive(Context context, Intent intent) {
        //Bundle bundle = intent.getExtras();
        //installedApps = bundle.getStringArrayList("App List");
        //Intent i=new Intent(context, MainService.class);
        //i.putExtra("App List", installedApps);
        //context.startService(i);
    }
}
