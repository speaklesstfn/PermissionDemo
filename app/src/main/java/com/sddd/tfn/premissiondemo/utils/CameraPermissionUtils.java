package com.sddd.tfn.premissiondemo.utils;

import android.hardware.Camera;

/**
 * Created by tfn on 17-5-25.
 * <p>
 * 拍照权限判断
 */

public class CameraPermissionUtils {

    /**
     * 判断摄像头是否可用
     * <p>
     * 主要针对6.0 之前的版本，现在主要是依靠try...catch... 报错信息，感觉不太好，
     * <p>
     * 以后有更好的方法的话可适当替换
     */
    public static synchronized boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            // setParameters 是针对魅族MX5 做的。MX5 通过Camera.open() 拿到的Camera
            // 对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            canUse = false;
        }
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
        return canUse;
    }
}
