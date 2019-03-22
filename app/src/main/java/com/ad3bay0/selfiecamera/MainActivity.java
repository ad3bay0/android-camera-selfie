package com.ad3bay0.selfiecamera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static int SELFIE_REQUEST=0;
    private final int REQUEST_ALL_PERMISSIONS = 3;
    private ImageView imgView;
    private Bitmap bitmap;
    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgView = findViewById(R.id.imgView);
        checkPermissions();
    }

    /**
     * action called on click of "take selfie" button
     * @param view
     */
    public void launchSelfieCam(View view){

        int id = getFrontCameraId();
        Log.i(TAG,"Camera ID: "+id);

        if(id==-1){


        }else {

            Intent intent = new Intent();
            intent.setClass(this,CameraActivity.class);
            startActivityForResult(intent,SELFIE_REQUEST);
        }


    }

    /**
     * retrive front camera id
     * @return int
     */
    int getFrontCameraId(){

        Camera.CameraInfo ci = new Camera.CameraInfo();

        for(int i =0; i<Camera.getNumberOfCameras();i++){

            Camera.getCameraInfo(i,ci);

            if(ci.facing== Camera.CameraInfo.CAMERA_FACING_FRONT)
                return i;
        }
        return -1;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==SELFIE_REQUEST){

            if(resultCode==RESULT_OK){

                bitmap = data.getParcelableExtra("img");
                imgView.setImageBitmap(bitmap);
            }



        }else if(requestCode==RESULT_CANCELED){


        }
    }
    public void checkPermissions() {

        if (Build.VERSION.SDK_INT > 21) {


            int checkCameraPermision = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);


            List<String> listPermissionsNeeded = new ArrayList<>();

            if(checkCameraPermision!=PackageManager.PERMISSION_GRANTED){

                listPermissionsNeeded.add(Manifest.permission.CAMERA);


            }



            if (!listPermissionsNeeded.isEmpty()) {

                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ALL_PERMISSIONS);
                return;

            } else {

            }

        } else {

        }

    }



}
