package com.example.akanksha.fcs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity implements SurfaceHolder.Callback {

    private static final String TAG = "";
    private Camera camera;
    //private ImageButton cameraClick;
    //private SurfaceHolder mHolder;
    String image_name = "akanksha";
    String no_pics = "1";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        takeSnapShots();


    }

    // Handles when shutter open
    ShutterCallback shutterCallback = new ShutterCallback() {
        public void onShutter() {

        }
    };

    /**
     * Handles data for raw picture
     */
    PictureCallback rawCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    };

    /**
     * Handles data for jpeg picture
     * PictureCallback jpegCallback = new PictureCallback()
     * {
     * public void onPictureTaken(byte[] data, Camera camera)
     * {
     * // we do something using return byte[] of taken image.
     * }
     * };
     */

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        // Set camera preview size,orientation,rotation using parameters
        Log.d("Helllo","Chceck ASgain");
        Camera.Parameters parameters = camera.getParameters();
        parameters.set("orientation", "portrait");
        camera.setParameters(parameters);
        camera.startPreview();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        releaseCameraAndPreview();
        try {
            camera = Camera.open();
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;

    }

    private void releaseCameraAndPreview() {

        if (camera != null) {
            Log.d("HII again", "Chevcking again");
            camera.release();
            camera = null;
        }
    }


    private void takeSnapShots() {
        Toast.makeText(getApplicationContext(), "Image snapshot   Started", Toast.LENGTH_SHORT).show();
        // here below "this" is activity context.
        SurfaceView surface = new SurfaceView(this);
        releaseCameraAndPreview();
        try {
            Log.d("HII", "Checking");
            camera = Camera.open();
            camera.setPreviewDisplay(surface.getHolder());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        camera.startPreview();
        camera.takePicture(null, null, jpegCallback);
    }


    PictureCallback jpegCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            FileOutputStream outStream = null;
            try {
                String dir_path = "";// set your directory path here
                outStream = new FileOutputStream(dir_path + File.separator + image_name + no_pics + ".jpg");
                outStream.write(data);
                outStream.close();
                Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                camera.stopPreview();
                camera.release();
                camera = null;
                Toast.makeText(getApplicationContext(), "Image snapshot Done", Toast.LENGTH_LONG).show();


            }
            Log.d(TAG, "onPictureTaken - jpeg");
        }
    };

}
