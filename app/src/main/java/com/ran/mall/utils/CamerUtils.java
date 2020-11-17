package com.ran.mall.utils;

import android.hardware.Camera;
import android.util.Log;

/**
 * Created by pc on 2018/4/21/021.
 * email：pc
 * effect：
 */

public class CamerUtils {
    private Camera camera;


    /**
     * 闪光灯开关
     */
    public  void flashlightUtils() {

        if (isFlashlightOn(false)) {
            Closeshoudian();
            camera = null;
        } else {
            Openshoudian();
        }
    }

    /*    *
     * 是否开启了闪光灯
     * @return
     */
    private  boolean isFlashlightOn(boolean flag) {
        try {
            Camera.Parameters parameters = camera.getParameters();
            String flashMode = parameters.getFlashMode();
            if (flashMode.equals(android.hardware.Camera.Parameters.FLASH_MODE_TORCH)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


    public void Openshoudian() {
        //异常处理一定要加，否则Camera打开失败的话程序会崩溃
        try {
            Log.d("smile", "camera打开");
             camera = Camera.open();
        } catch (Exception e) {
            Log.d("smile", "Camera打开有问题");


        }

        if (camera != null) {
            //打开闪光灯
            camera.startPreview();
            Camera.Parameters parameter = camera.getParameters();
            parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameter);
            Log.d("smile", "闪光灯打开");


        }
    }



    public void Closeshoudian() {
        if (camera != null) {
            //关闭闪光灯
            Log.d("smile", "closeCamera()");
            camera.getParameters().setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(camera.getParameters());
            camera.stopPreview();
            camera.release();
            camera = null;


        }
    }
}
