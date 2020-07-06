package com.example.grimpro;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import static android.content.ContentValues.TAG;

public class MainService extends Service {
    ArrayList<String> installedApps;
    int size;
    Timer timer  =  new Timer();
    String flagPackage = "lol";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        timer.cancel();
        Log.i("Service","Service Stopped");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent !=null && intent.getExtras()!=null){
            Bundle bundle = intent.getExtras();
            installedApps = bundle.getStringArrayList("App List");
            size = installedApps.size();
        }
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {

                String activityOnTop = retrieveNewApp();
                int j;
                for(j=0;j<size;j++){
                    if(installedApps.get(j).equalsIgnoreCase(activityOnTop) )
                        break;
                }
                SharedPreferences prefs = getSharedPreferences("unlockedApp", MODE_PRIVATE);
                if(prefs == null)
                    flagPackage = "lol";
                if( j != size && !flagPackage.equalsIgnoreCase(activityOnTop)){
                    SharedPreferences prefs1 = getSharedPreferences("ImagePath", MODE_PRIVATE);
                    String type = prefs1.getString("type", "null");
                    if(type.equalsIgnoreCase("touch")) {
                        Intent i = new Intent();
                        i.setClass(MainService.this, LockScreen.class);
                        SharedPreferences.Editor imageSave = getSharedPreferences("ImagePath",MODE_PRIVATE).edit();
                        imageSave.putString("fromlock","false");
                        imageSave.apply();
                        i.putExtra("Package Name", activityOnTop);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                    else {
                        Intent i = new Intent();
                        SharedPreferences.Editor imageSave = getSharedPreferences("ImagePath",MODE_PRIVATE).edit();
                        imageSave.putString("fromlock","false");
                        imageSave.apply();
                        i.setClass(MainService.this, gridlock.class);
                        i.putExtra("Package Name", activityOnTop);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                }
                else{
                    if(!activityOnTop.equalsIgnoreCase(getApplicationInfo().packageName))
                        flagPackage = activityOnTop;
                }
                if(!activityOnTop.equalsIgnoreCase(prefs.getString("Unlocked Package", "null")))
                    flagPackage = prefs.getString("Unlocked Package", "null");

                Log.i("Flagged Package",flagPackage);
            }
        }, 5000, 10);  // every half second(s)
        return START_STICKY;
    }
    private String retrieveNewApp() {
        if (Build.VERSION.SDK_INT >= 21) {
            String currentApp = null;
            UsageStatsManager usm = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> applist = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
            if (applist != null && applist.size() > 0) {
                SortedMap<Long, UsageStats> mySortedMap = new TreeMap<>();
                for (UsageStats usageStats : applist) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }

            return currentApp;

        }
        else {

            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            String mm=(manager.getRunningTasks(1).get(0)).topActivity.getPackageName();
            Log.e(TAG, "Current App in foreground is: " + mm);
            return mm;
        }
    }
}