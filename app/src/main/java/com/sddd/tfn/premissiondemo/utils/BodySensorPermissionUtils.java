package com.sddd.tfn.premissiondemo.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

/**
 * Created by tfn on 17-6-14.
 * <p>
 * 身体传感器权限判断
 * <p>
 * todo 目前身体传感器权限仅限于有心率检测硬件的设置，因此目前无所谓
 */

public class BodySensorPermissionUtils {

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    public static synchronized boolean isBodySensorCanUse(Context context) {
        //        try {
        //            SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        //
        //            sensorManager.registerListener(new SensorEventListener() {
        //                @Override
        //                public void onSensorChanged(SensorEvent event) {
        //                    Log.d("TFN", "onSensorChanged");
        //                }
        //
        //                @Override
        //                public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //                    Log.d("TFN", "onAccuracyChanged:" + accuracy);
        //                }
        //            }, sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE), SensorManager.SENSOR_DELAY_GAME);
        //        } catch (Exception e) {
        //            Log.d("TFN", "异常了");
        //        }
        return true;
    }
}
