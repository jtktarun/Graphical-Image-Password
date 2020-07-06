package com.example.grimpro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class LockActivity extends AppCompatActivity {
    ImageView image;
    Timer timer  =  new Timer();
    int index = 0;
    int x,y;
    String path;
    int[] touchX = new int[3];
    int[] touchY = new int[3];
    Button b1;
    private final int REQUEST_IMAGE_GALLERY=2;
    private final int REQUEST_IMAGE_GALLERY1=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        SharedPreferences prefs = getSharedPreferences("ImagePath", MODE_PRIVATE);
        String flagset = prefs.getString("islockset", "no");

        if(flagset.equalsIgnoreCase("yes"))
        {
                 String type=prefs.getString("type",null);
                 if(type.equalsIgnoreCase("touch"))
                 {
                     Intent i=new Intent(LockActivity.this,LockScreen.class);
                     i.putExtra("Package Name", "hellooo");
                     SharedPreferences.Editor imageSave = getSharedPreferences("ImagePath",MODE_PRIVATE).edit();
                     imageSave.putString("fromlock","true");
                     imageSave.apply();
                     startActivity(i);
                 }
                 else
                 {
                     Intent i=new Intent(LockActivity.this,gridlock.class);
                     i.putExtra("Package Name", "hellooo");
                     SharedPreferences.Editor imageSave = getSharedPreferences("ImagePath",MODE_PRIVATE).edit();
                     imageSave.putString("fromlock","true");
                     imageSave.apply();
                     startActivity(i);
                 }
        }

        image = findViewById(R.id.pic_password);
        b1=findViewById(R.id.gridbutton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent igallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                igallery.setType("image/*");
                startActivityForResult(igallery,REQUEST_IMAGE_GALLERY1);
            }
        });
    }

    public void selectgal(View view) {
        Intent igallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        igallery.setType("image/*");
        startActivityForResult(igallery,REQUEST_IMAGE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data) {

        super.onActivityResult(requestCode,resultCode,data);
        if (resultCode==RESULT_OK) {
            if(requestCode==REQUEST_IMAGE_GALLERY) {
                Uri uri=data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    path = getRealPathFromURI(uri);


                    final Toast toast = Toast.makeText(this,"Select 1st Position",Toast.LENGTH_SHORT);
                    final Toast t1 = Toast.makeText(this,"Set Successfully",Toast.LENGTH_SHORT);
                    toast.show();
                    image.setImageBitmap(bitmap);
                    image.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            x = (int)event.getX();
                            y = (int)event.getY();
                            if(index < 3) {
                                touchX[index] = x;
                                touchY[index] = y;
                                Log.i("x", Integer.toString(x));
                                Log.i("y", Integer.toString(y));
                                index++;
                                toast.setText("Select " + Integer.toString(index + 1) + " position");
                                if(index <= 2)
                                 toast.show();
                                if(index == 3){
                                    t1.show();
                                    Intent intent = new Intent(LockActivity.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                            return false;
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            else if(requestCode==REQUEST_IMAGE_GALLERY1)
            {
                Uri uri=data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    path = getRealPathFromURI(uri);
                    image.setImageBitmap(bitmap);

                    SharedPreferences.Editor imageSave = getSharedPreferences("ImagePath",MODE_PRIVATE).edit();

                    imageSave.putString("Path Key",path);
                    imageSave.putString("type","grid");
                    imageSave.putString("islockset","yes");
                    imageSave.apply();
                    //Intent i=new Intent(LockActivity.this,gridlock.class);
                    //startActivity(i);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }




        }
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(index ==3) {

                    SharedPreferences.Editor imageSave = getSharedPreferences("ImagePath",MODE_PRIVATE).edit();

                    imageSave.putString("Path Key",path);
                    imageSave.putString("type","touch");
                    imageSave.putInt("x1",touchX[0]);
                    imageSave.putInt("y1",touchY[0]);
                    imageSave.putInt("x2",touchX[1]);
                    imageSave.putInt("y2",touchY[1]);
                    imageSave.putInt("x3",touchX[2]);
                    imageSave.putInt("y3",touchY[2]);
                    imageSave.putString("islockset","yes");
                    imageSave.apply();
                    index++;
                    timer.cancel();
                }

            }
        }, 1000, 500);


    }
    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }



}
