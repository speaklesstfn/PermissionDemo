package com.sddd.tfn.premissiondemo.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Created by tfn on 17-6-14.
 * <p>
 * 短信权限判断
 * <p>
 * todo 如果短信列表为空，则这里无论用户设置关闭短信权限与否，都会返回false
 */

public class SMSPermissionUtils {
    private static final String SMS_URI_ALL = "content://sms/"; // 所有短信

    public static synchronized boolean isSMSCanUse(Context context) {
        Cursor cursor = null;

        try {
            Uri uri = Uri.parse(SMS_URI_ALL);
            String[] projection = new String[] {"_id", "address", "person", "body", "date", "type",};
            cursor = context.getContentResolver().query(uri, projection, null, null, "date desc"); // 获取手机内部短信

            if (null != cursor) {
                Log.d("TFN", "获取短信长度：" + cursor.getCount());
                if (cursor.getCount() > 0) {
                    Log.d("TFN", "获取短信有权限");
                    return true;
                }

                Log.d("TFN", "获取短信没有权限1");
                return false;
            }

            Log.d("TFN", "获取短信没有权限2");
            return false;
        } catch (Exception e) {
            Log.d("TFN", "获取短信异常");
            return false;
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
    }
}
