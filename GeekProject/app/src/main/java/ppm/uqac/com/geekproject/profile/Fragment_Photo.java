package ppm.uqac.com.geekproject.profile;

import java.io.FileOutputStream;
import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.app.Fragment;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;

import ppm.uqac.com.geekproject.R;

/**
 * Created by Valentin on 25/11/2015.
 */

public class Fragment_Photo extends Fragment
{
    private static final String DEBUG_TAG = "StillImageActivity";
    final private static String STILL_IMAGE_FILE = "cameraImage.jpg";
    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootview = inflater.inflate(R.layout.fragment_photo,container,false);

        //creation de l'objet cameraView
        final CameraSurfaceView cameraView = new CameraSurfaceView(getActivity());

        FrameLayout frame = (FrameLayout) rootview.findViewById(R.id.frame);
        frame.addView(cameraView);
        Button btnShare = (Button) rootview.findViewById(R.id.btn_share);

        //sauvegarde dans le dossier partage
        btnShare.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                cameraView.capture(new Camera.PictureCallback()
                {
                    public void onPictureTaken(byte[] data, Camera camera)
                    {
                        Log.v("Still", "Image data received from camera");

                        try

                        {
                            Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
                            String fileUrl = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bm,
                                    "Camera Still Image",
                                    "Camera Pic Sample App Took");

                            //Ou se retrouve ce fichier?
                            Toast.makeText(getActivity(), "image partage est dans : " + fileUrl, Toast.LENGTH_LONG).show();

                            if (fileUrl == null)
                            {
                                Log.d("Still", "Image Insert failed");
                                return;
                            }

                        }

                        catch (Exception e)

                        {
                            Log.e("Still", "Error writing file", e);
                        }
                    }
                });
            }
        });


        //sauvegarde en local
        Button btn_local = (Button) rootview.findViewById(R.id.btn_local);

        btn_local.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Log.v(DEBUG_TAG, "Requesting btn_loc");

                cameraView.capture(new Camera.PictureCallback()
                {
                    public void onPictureTaken(byte[] data, Camera camera)
                    {
                        Log.v("Still", "Image data received from camera");

                        FileOutputStream fos;

                        try
                        {
                            // Fully qualified path name. In this case, we
                            // use the Files subdir
                            String pathForAppFiles = getActivity().getFilesDir().getAbsolutePath();
                            pathForAppFiles = pathForAppFiles + "/" + STILL_IMAGE_FILE;

                            Log.d("Still image filename:", pathForAppFiles);

                            fos = getActivity().openFileOutput(STILL_IMAGE_FILE, getActivity().MODE_WORLD_READABLE);
                            fos.write(data);
                            fos.close();
                            //Ou se retrouve ce fichier?
                            Toast.makeText(getActivity(), "saved dans " + pathForAppFiles, Toast.LENGTH_LONG).show();


                        }

                        catch (Exception e)
                        {
                            Log.e("Still", "Error writing file", e);
                        }
                    }
                });
            }
        });

        // Sauvegarde comme papier peint de bureau
        Button paper = (Button) rootview.findViewById(R.id.btn_wallpaper);
        paper.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cameraView.capture(new Camera.PictureCallback() {
                    public void onPictureTaken(byte[] data, Camera camera) {
                        Log.v("Still", "Image data received from camera");
                        Bitmap recordedImage = BitmapFactory.decodeByteArray(
                                data, 0, data.length);

                        try
                        {
                            WallpaperManager wpManager = WallpaperManager
                                    .getInstance(getActivity());
                            int height = wpManager.getDesiredMinimumHeight();
                            int width = wpManager.getDesiredMinimumWidth();
                            Toast.makeText(getActivity(),
                                    "Wallpaper size = " + width + "x" + height,
                                    Toast.LENGTH_LONG).show();
                            Log.v("Still", "Wallpaper size=" + width + "x"
                                    + height);
                            Bitmap scaledImage = Bitmap.createScaledBitmap(
                                    recordedImage, width, height, false);
                            wpManager.setBitmap(scaledImage);
                        } catch (Exception e) {
                            Log.e("Still", "Setting wallpaper failed.", e);
                        }
                    }
                });
            }
        });

        return rootview;
    }

    private class CameraSurfaceView extends SurfaceView implements
            SurfaceHolder.Callback
    {
        private Camera camera = null;
        private SurfaceHolder mHolder = null;
        private int cameraId = 0;

        public CameraSurfaceView(Context context)
        {
            super(context);

            // Install a SurfaceHolder.Callback so we get notified when the
            // underlying surface is created and destroyed

            mHolder = getHolder();
            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

            // optional -- use the front facing camera
            CameraInfo info = new CameraInfo();

            for (int camIndex = 0; camIndex < Camera.getNumberOfCameras(); camIndex++)
            {
                Camera.getCameraInfo(camIndex, info);
                if (info.facing == CameraInfo.CAMERA_FACING_FRONT)
                {
                    cameraId = camIndex;
                    break;
                }
            }
        }

        public void surfaceCreated(SurfaceHolder holder) {
            camera = Camera.open(cameraId);
            try {
                camera.setPreviewDisplay(mHolder);
            } catch (Exception e) {
                Log.e(DEBUG_TAG, "Failed to set camera preview display", e);
            }
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            try {
                Camera.Parameters params = camera.getParameters();
                // not all cameras supporting setting arbitrary sizes
                List<Camera.Size> sizes = params.getSupportedPreviewSizes();
                Camera.Size pickedSize = getBestFit(sizes, width, height);
                if (pickedSize != null) {
                    params.setPreviewSize(pickedSize.width, pickedSize.height);
                    Log.d(DEBUG_TAG, "Preview size: (" + pickedSize.width + ","
                            + pickedSize.height + ")");
                    // even after setting a supported size, the preview size may
                    // still end up just being the surface size (supported or
                    // not)
                    camera.setParameters(params);
                }
                // set the orientation to standard portrait.
                // Do this only if you know the specific orientation (0,90,180,
                // etc.)
                // Only works on API Level 8+
                if  (pickedSize != null) {
                    setCameraDisplayOrientation(pickedSize.width, pickedSize.height);
                } else {
                    Camera.Size previewSize = camera.getParameters().getPreviewSize();
                    setCameraDisplayOrientation(previewSize.width, previewSize.height);
                }
                camera.startPreview();
            } catch (Exception e) {
                Log.e(DEBUG_TAG, "Failed to set preview size", e);
            }
        }

        private Size getBestFit(List<Size> sizes, int width, int height) {
            Size bestFit = null;
            ListIterator<Size> items = sizes.listIterator();
            while (items.hasNext()) {
                Size item = items.next();
                if (item.width <= width && item.height <= height) {
                    if (bestFit != null) {
                        // if our current best fit has a smaller area, then we
                        // want the new one (bigger area == better fit)
                        if (bestFit.width * bestFit.height < item.width
                                * item.height) {
                            bestFit = item;
                        }
                    } else {
                        bestFit = item;
                    }
                }
            }
            return bestFit;
        }

        private void setCameraDisplayOrientation(int width, int height) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(cameraId, info);
            int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
            int degrees = rotation * 90;

            int result;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                result = (info.orientation + degrees) % 360;
                result = (360 - result) % 360;
            } else {
                result = (info.orientation - degrees + 360) % 360;
            }
            camera.setDisplayOrientation(result);

            if (result == 90 || result == 270) {
                mHolder.setFixedSize(height, width);
            } else {
                mHolder.setFixedSize(width, height);

            }
        }



        public void surfaceDestroyed(SurfaceHolder holder) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }

        // On capture l'image
        public boolean capture(Camera.PictureCallback jpegHandler) {
            if (camera != null) {
                camera.takePicture(null, null, jpegHandler);
                return true;
            } else {
                return false;
            }
        }
    }
}
