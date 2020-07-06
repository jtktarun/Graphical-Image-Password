package com.example.grimpro;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Context mContext;
    ListView listView;
    ArrayList<String> lockedApps = new ArrayList<>();
    ArrayList<String> lockedApps1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        lockedApps1 = new ArrayList<>();
        lockedApps1=getArrayList("key");
        SharedPreferences prefs = getSharedPreferences("ImagePath", MODE_PRIVATE);
        String unlockpack = prefs.getString("unlockpack", "none");
        //lockedApps.addAll(lockedApps1);
        listView = findViewById(R.id.listView);
        final ArrayList<AppList> installedApps;
        installedApps = getAppInfoList(false);

        if(lockedApps1!=null) {
            lockedApps = lockedApps1;
            for(int k=0;k<installedApps.size();k++){
                if(installedApps.get(k).getPackageName().equalsIgnoreCase(unlockpack)){
                    lockedApps.remove(unlockpack);
                    SharedPreferences.Editor imageSave = getSharedPreferences("ImagePath",MODE_PRIVATE).edit();
                    imageSave.putString("unlockpack","none");
                    imageSave.apply();
                }
            }
            int i,j;
            for(i=0;i<lockedApps.size();i++){
                for(j=0;j<installedApps.size();j++){
                    if(lockedApps.get(i).equals(installedApps.get(j).getPackageName()))
                        installedApps.get(j).setLock();
                }
            }
        }

        final customAdapter adapter = new customAdapter(MainActivity.this,installedApps);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView temp = view.findViewById(R.id.lock_symbol);
                AppList appList = installedApps.get(position);
                String packageName = appList.getPackageName();
                Intent i = new Intent(MainActivity.this,MainService.class);
                stopService(i);
                if(!appList.getLockStatus()){
                    appList.setLock();
                    lockedApps.add(packageName);
                    Log.i("Locked","Locked");
                }
                else{
                    SharedPreferences prefs = getSharedPreferences("ImagePath", MODE_PRIVATE);
                    String flagset = prefs.getString("islockset", "no");
                    if(flagset.equalsIgnoreCase("yes"))
                    {
                        String type=prefs.getString("type",null);

                        if(type.equalsIgnoreCase("touch"))
                        {
                            Intent ip=new Intent(MainActivity.this,LockScreen.class);
                            ip.putExtra("Package Name",packageName);
                            SharedPreferences.Editor imageSave = getSharedPreferences("ImagePath",MODE_PRIVATE).edit();
                            imageSave.putString("frommain","true");
                            imageSave.apply();
                            startActivity(ip);
                        }
                        else
                        {
                            Intent ip=new Intent(MainActivity.this,gridlock.class);
                            ip.putExtra("Package Name", packageName);
                            SharedPreferences.Editor imageSave = getSharedPreferences("ImagePath",MODE_PRIVATE).edit();
                            imageSave.putString("frommain","true");
                            imageSave.apply();
                            startActivity(ip);
                        }

                    }
                    else {
                        appList.setLock();
                        lockedApps.remove(appList.getPackageName());
                        Log.i("Locked", "Unlocked");
                    }
                }
                if(appList.getLockStatus()){
                    temp.setVisibility(View.VISIBLE);
                }
                else
                    temp.setVisibility(View.INVISIBLE);
                i = new Intent(MainActivity.this,MainService.class);
                SharedPreferences prefs1 = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = prefs1.edit();
                Gson gson = new Gson();
                String json = gson.toJson(lockedApps);
                editor.putString("key", json);
                editor.apply();

                i.putExtra("App List", lockedApps);
                startService(i);
            }
        });

        storagePermission();
        if (!isAccessGranted()) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
            while (!isAccessGranted());
        }

        Intent i = new Intent(MainActivity.this,MainService.class);
        i.putExtra("App List", lockedApps);
        startService(i);
    }
    public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void storagePermission(){
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean isAccessGranted() {
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);

            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT) {
                mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        applicationInfo.uid, applicationInfo.packageName);
            }
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Intent restartService = new Intent();
        restartService.setAction("com.example.Broadcast");
        restartService.putExtra("App List", lockedApps);
        //sendBroadcast(restartService);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.three_dot_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.set_lock){
            Intent lockIntent = new Intent(MainActivity.this,LockActivity.class);
            startActivity(lockIntent);
        }

        return true;
    }

    private ArrayList<AppList> getAppInfoList(boolean getSysPackages) {
        ArrayList<AppList> appInfoList = new ArrayList<>();
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);

        for (PackageInfo packageInfo : packages) {
            if ((!getSysPackages) && (packageInfo.versionName == null)) {
                continue ;
            }
            if(mContext.getPackageManager().getLaunchIntentForPackage(packageInfo.applicationInfo.packageName) != null){
                AppList newInfo = new AppList();
                newInfo.setAppName(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());
                newInfo.setIcon(packageInfo.applicationInfo.loadIcon(getPackageManager()));
                newInfo.setPackageName(packageInfo.applicationInfo.packageName);
                appInfoList.add(newInfo);
            }
        }
        return appInfoList;
    }
}