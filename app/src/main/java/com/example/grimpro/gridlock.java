package com.example.grimpro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.Random;

public class gridlock extends AppCompatActivity {
    String path,packageName;
    Context mContext;
    String q="";
    Button b1,b2;
    TextView t1;
    Bitmap[] imgs = new Bitmap[9];
    Bitmap scaledBitmap;
    Bitmap bitmap;
    int i1=-1,i2=-1,i3=-1,i4=-1,i5=-1,i6=-1,i7=-1,i8=-1,i9=-1;
    int[] count = new int[9];
    int[] rand1 = new int[9];
    int flag=0;
    private ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9;
    String fromlock;
    String frommain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridlock);
        mContext = getApplicationContext();
        Intent intent = getIntent();
        packageName = intent.getStringExtra("Package Name");
        b1=findViewById(R.id.button);
        b2=findViewById(R.id.button1);
      //  t1=findViewById(R.id.txt);
        img1= findViewById(R.id.imageview1);
        img2= findViewById(R.id.imageview2);
        img3= findViewById(R.id.imageview3);
        img4= findViewById(R.id.imageview4);
        img5= findViewById(R.id.imageview5);
        img6= findViewById(R.id.imageview6);
        img7= findViewById(R.id.imageview7);
        img8= findViewById(R.id.imageview8);
        img9= findViewById(R.id.imageview9);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //t1.setText(q);

                if(frommain.equalsIgnoreCase("true"))
                {
                    if (q.equalsIgnoreCase("012345678")) {
                        Intent i=new Intent(gridlock.this,MainActivity.class);
                        SharedPreferences.Editor imageSave = getSharedPreferences("ImagePath",MODE_PRIVATE).edit();
                        imageSave.putString("unlockpack",packageName);
                        imageSave.putString("frommain","false");
                        imageSave.apply();
                        startActivity(i);

                    } else {
                        Intent i=new Intent(gridlock.this,MainActivity.class);
                        SharedPreferences.Editor imageSave = getSharedPreferences("ImagePath",MODE_PRIVATE).edit();
                        imageSave.putString("unlockpack","none");
                        imageSave.putString("frommain","false");
                        imageSave.apply();
                        startActivity(i);
                    }
                }


               else if(fromlock.equalsIgnoreCase("true"))
                {
                    if (q.equalsIgnoreCase("012345678")) {
                        Intent i=new Intent(gridlock.this, LockActivity.class);
                        SharedPreferences.Editor imageSave = getSharedPreferences("ImagePath",MODE_PRIVATE).edit();
                        imageSave.putString("islockset","no");
                        imageSave.apply();
                        startActivity(i);

                    } else {
                        Intent i=new Intent(gridlock.this, MainActivity.class);
                        startActivity(i);
                    }
                }
                else {
                    if (q.equalsIgnoreCase("012345678")) {
                        SharedPreferences.Editor unlockedApp = getSharedPreferences("unlockedApp", MODE_PRIVATE).edit();
                        unlockedApp.putString("Unlocked Package", packageName);
                        unlockedApp.apply();
                        launchApp(packageName);
                    } else {
                        Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
                        startHomescreen.addCategory(Intent.CATEGORY_HOME);
                        startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(startHomescreen);
                    }
                }



            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable highlight = getResources().getDrawable( R.drawable.title_logo);
                img1.setBackground(highlight);
                String p=img1.getTag().toString();
                if(i1==-1) {
                    i1=0;
                    q = q + p;
                }
                if(q.length() == 9)
                    b1.setVisibility(View.VISIBLE);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=img2.getTag().toString();
                if(i2==-1) {
                    i2=0;
                    q = q + p;
                }
                if(q.length() == 9)
                    b1.setVisibility(View.VISIBLE);
                Drawable highlight = getResources().getDrawable( R.drawable.title_logo);
                img2.setBackground(highlight);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=img3.getTag().toString();
                if(i3==-1) {
                    i3=0;
                    q = q + p;
                }
                if(q.length() == 9)
                    b1.setVisibility(View.VISIBLE);
                Drawable highlight = getResources().getDrawable( R.drawable.title_logo);
                img3.setBackground(highlight);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=img4.getTag().toString();
                if(i4==-1) {
                    i4=0;
                    q = q + p;
                }
                if(q.length() == 9)
                    b1.setVisibility(View.VISIBLE);
                Drawable highlight = getResources().getDrawable( R.drawable.title_logo);
                img4.setBackground(highlight);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=img5.getTag().toString();
                if(i5==-1) {
                    i5=0;
                    q = q + p;
                }
                if(q.length() == 9)
                    b1.setVisibility(View.VISIBLE);
                Drawable highlight = getResources().getDrawable( R.drawable.title_logo);
                img5.setBackground(highlight);

            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=img6.getTag().toString();
                if(i6==-1) {
                    i6=0;
                    q = q + p;
                }
                if(q.length() == 9)
                    b1.setVisibility(View.VISIBLE);
                Drawable highlight = getResources().getDrawable( R.drawable.title_logo);
                img6.setBackground(highlight);
            }
        });
        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=img7.getTag().toString();
                if(i7==-1) {
                    i7=0;
                    q = q + p;
                }
                if(q.length() == 9)
                    b1.setVisibility(View.VISIBLE);
                Drawable highlight = getResources().getDrawable( R.drawable.title_logo);
                img7.setBackground(highlight);
            }
        });
        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=img8.getTag().toString();
                if(i8==-1) {
                    i8=0;
                    q = q + p;
                }
                if(q.length() == 9)
                    b1.setVisibility(View.VISIBLE);
                Drawable highlight = getResources().getDrawable( R.drawable.title_logo);
                img8.setBackground(highlight);
            }
        });
        img9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=img9.getTag().toString();
                if(i9==-1) {
                    i9=0;
                    q = q + p;
                }
                if(q.length() == 9)
                    b1.setVisibility(View.VISIBLE);
                Drawable highlight = getResources().getDrawable( R.drawable.title_logo);
                img9.setBackground(highlight);
            }
        });
        SharedPreferences prefs = getSharedPreferences("ImagePath", MODE_PRIVATE);
        path = prefs.getString("Path Key", "null");
        fromlock=prefs.getString("fromlock", "false");
        frommain=prefs.getString("frommain", "false");
         bitmap = null;
        while(bitmap == null)
            bitmap = BitmapFactory.decodeFile(path);

         scaledBitmap = Bitmap.createScaledBitmap(bitmap, 240, 240, true);
        imgs[0] = Bitmap.createBitmap(scaledBitmap, 0, 0, 80 , 80);
        imgs[1] = Bitmap.createBitmap(scaledBitmap, 80, 0, 80, 80);
        imgs[2] = Bitmap.createBitmap(scaledBitmap,160, 0, 80,80);
        imgs[3] = Bitmap.createBitmap(scaledBitmap, 0, 80, 80, 80);
        imgs[4] = Bitmap.createBitmap(scaledBitmap, 80, 80, 80,80);
        imgs[5] = Bitmap.createBitmap(scaledBitmap, 160, 80,80,80);
        imgs[6] = Bitmap.createBitmap(scaledBitmap, 0, 160, 80,80);
        imgs[7] = Bitmap.createBitmap(scaledBitmap, 80, 160,80,80);
        imgs[8] = Bitmap.createBitmap(scaledBitmap, 160,160,80,80);




        int i;
        for(i=0;i<9;i++)
            count[i]=0;
        while(flag<9)
        {
            Random rand = new Random();
            int random = rand.nextInt(9);
            if(count[random]==0)
            {
                count[random]=1;
                rand1[flag]=random;
                flag++;
            }
        }

        img1.setImageBitmap(imgs[rand1[0]]);
        img1.setTag(Integer.toString(rand1[0]));
        img2.setImageBitmap(imgs[rand1[1]]);
        img2.setTag(Integer.toString(rand1[1]));
        img3.setImageBitmap(imgs[rand1[2]]);
        img3.setTag(Integer.toString(rand1[2]));
        img4.setImageBitmap(imgs[rand1[3]]);
        img4.setTag(Integer.toString(rand1[3]));
        img5.setImageBitmap(imgs[rand1[4]]);
        img5.setTag(Integer.toString(rand1[4]));
        img6.setImageBitmap(imgs[rand1[5]]);
        img6.setTag(Integer.toString(rand1[5]));
        img7.setImageBitmap(imgs[rand1[6]]);
        img7.setTag(Integer.toString(rand1[6]));
        img8.setImageBitmap(imgs[rand1[7]]);
        img8.setTag(Integer.toString(rand1[7]));
        img9.setImageBitmap(imgs[rand1[8]]);
        img9.setTag(Integer.toString(rand1[8]));





        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                b1.setVisibility(View.INVISIBLE);

                q="";
                i1=-1;
                i2=-1;i3=-1;i4=-1;i5=-1;i6=-1;i7=-1;i8=-1;i9=-1;
                Drawable highlight = getResources().getDrawable( R.drawable.title_logo1);
                img1.setBackground(highlight);
                img2.setBackground(highlight);
                img3.setBackground(highlight);
                img4.setBackground(highlight);
                img5.setBackground(highlight);
                img6.setBackground(highlight);
                img7.setBackground(highlight);
                img8.setBackground(highlight);
                img9.setBackground(highlight);
                flag=0;
                int i;
                for(i=0;i<9;i++)
                    count[i]=0;
                while(flag<9) {
                    Random rand = new Random();
                    int random = rand.nextInt(9);
                    if(count[random]==0) {
                        count[random]=1;
                        rand1[flag]=random;
                        flag++;
                    }
                }

                img1.setImageBitmap(imgs[rand1[0]]);
                img1.setTag(Integer.toString(rand1[0]));
                img2.setImageBitmap(imgs[rand1[1]]);
                img2.setTag(Integer.toString(rand1[1]));
                img3.setImageBitmap(imgs[rand1[2]]);
                img3.setTag(Integer.toString(rand1[2]));
                img4.setImageBitmap(imgs[rand1[3]]);
                img4.setTag(Integer.toString(rand1[3]));
                img5.setImageBitmap(imgs[rand1[4]]);
                img5.setTag(Integer.toString(rand1[4]));
                img6.setImageBitmap(imgs[rand1[5]]);
                img6.setTag(Integer.toString(rand1[5]));
                img7.setImageBitmap(imgs[rand1[6]]);
                img7.setTag(Integer.toString(rand1[6]));
                img8.setImageBitmap(imgs[rand1[7]]);
                img8.setTag(Integer.toString(rand1[7]));
                img9.setImageBitmap(imgs[rand1[8]]);
                img9.setTag(Integer.toString(rand1[8]));


            }
        });

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
