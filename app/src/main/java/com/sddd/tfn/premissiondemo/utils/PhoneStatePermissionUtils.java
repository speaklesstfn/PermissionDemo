package com.sddd.tfn.premissiondemo.utils;

import static android.content.Context.TELEPHONY_SERVICE;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by tfn on 17-6-14.
 * <p>
 * 手机状态权限判断
 * <p>
 * 通过尝试获取imei来判断是否有权限
 */

public class PhoneStatePermissionUtils {

    public static synchronized boolean isPhoneStateCanUse(Context context) {
        try {
            TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);

            String imei = TelephonyMgr.getDeviceId();

            Log.d("TFN", "imei：" + imei);
            if (TextUtils.isEmpty(imei)) {
                Log.d("TFN", "手机状态没有权限");
                Log.d("TFN", "imei为空");
                return false;
            }
            Log.d("TFN", "手机状态有权限");
            return true;
        } catch (Exception e) {
            Log.d("TFN", "异常，手机状态没有权限");
            return false;
        }

    }
}
