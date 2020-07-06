package com.example.grimpro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;

public class LockScreen extends AppCompatActivity {

    int y;
    int x;
    int index=0;
    String path;
    int flag1=-1;
    int[] X = new int[3];
    int[] Y = new int[3];
    Button button;
    Context mContext;
    String packageName = null;
    ImageView imageView;
    TextView textView;
    EditText editText;
    Timer timer  =  new Timer();

    String fromlock="false";
    String frommain="false";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        imageView = findViewById(R.id.lock_image);
        /*button = findViewById(R.id.submit);
        editText = findViewById(R.id.edit);*/
        mContext = getApplicationContext();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        packageName = intent.getStringExtra("Package Name");

        SharedPreferences prefs = getSharedPreferences("ImagePath", MODE_PRIVATE);
        fromlock=prefs.getString("fromlock", "false");
        frommain=prefs.getString("frommain", "false");
        path = prefs.getString("Path Key", "null");
        X[0]=prefs.getInt("x1",0);
        Y[0]=prefs.getInt("y1",0);
        X[1]=prefs.getInt("x2",0);
        Y[1]=prefs.getInt("y2",0);
        X[2]=prefs.getInt("x3",0);
        Y[2]=prefs.getInt("y3",0);
        /*textView = findViewById(R.id.test);

        textView.setText(path);*/

        Bitmap bitmap = null;
        while(bitmap == null)
            bitmap = BitmapFactory.decodeFile(path);
        if(bitmap == null){
            Toast.makeText(this,"Bitmap is null",Toast.LENGTH_SHORT).show();
        }
        imageView.setImageBitmap(bitmap);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(index<3) {
                    x = (int) event.getX();
                    y = (int) event.getY();
                    if(x>(X[index]+40) || x<(X[index]-40)) {
                        Log.i("x1", "fox");
                        flag1 = 0;
                    }
                    if(y>(Y[index]+40) || y<(Y[index]-40)) {
                        Log.i("y1", "foy");
                        flag1 = 0;
                    }
                    Log.i("x", Integer.toString(x));
                    Log.i("y", Integer.toString(y));
                    index++;
                }

                return false;
            }
        });

        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(index ==3) {
                    if(frommain.equalsIgnoreCase("true"))
                    {
                        if (flag1 == -1) {
                            Intent i=new Intent(LockScreen.this,MainActivity.class);
                            SharedPreferences.Editor imageSave = getSharedPreferences("ImagePath",MODE_PRIVATE).edit();
                            imageSave.putString("unlockpack",packageName);
                            imageSave.putString("frommain","false");
                            imageSave.apply();
                            startActivity(i);

                        } else {
                            Intent i=new Intent(LockScreen.this,MainActivity.class);
                            SharedPreferences.Editor imageSave = getSharedPreferences("ImagePath",MODE_PRIVATE).edit();
                            imageSave.putString("unlockpack","none");
                            imageSave.putString("frommain","false");
                            imageSave.apply();
                            startActivity(i);
                        }
                    }


                   else
                       if(fromlock.equalsIgnoreCase("true"))
                    {
                        if (flag1 == -1) {
                            Intent i=new Intent(LockScreen.this, LockActivity.class);
                            SharedPreferences.Editor imageSave = getSharedPreferences("ImagePath",MODE_PRIVATE).edit();
                            imageSave.putString("islockset","no");
                            imageSave.apply();
                            startActivity(i);

                        } else {
                            Intent i=new Intent(LockScreen.this, MainActivity.class);
                            startActivity(i);
                        }
                    }


                    else {

                        if (flag1 == -1) {
                            SharedPreferences.Editor unlockedApp = getSharedPreferences("unlockedApp", MODE_PRIVATE).edit();
                            unlockedApp.putString("Unlocked Package", packageName);
                            unlockedApp.apply();
                            launchApp(packageName);
                            finish();
                        } else {
                            Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
                            startHomescreen.addCategory(Intent.CATEGORY_HOME);
                            startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(startHomescreen);
                        }
                    }
                  timer.cancel();
                  index = 0;
                }
            }
        }, 1000, 500);

    }

    @Override
    public void onBackPressed() {

    }

    protected void launchApp(String packageName){
        PackageManager pm = mContext.getPackageManager();
        try{
            Intent intent = pm.getLaunchIntentForPackage(packageName);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            if(intent == null){
                throw new PackageManager.NameNotFoundException();
            }else{
                mContext.startActivity(intent);
            }
        }catch(PackageManager.NameNotFoundException e){
            Log.e("Launch",e.getMessage());
        }
    }
}
