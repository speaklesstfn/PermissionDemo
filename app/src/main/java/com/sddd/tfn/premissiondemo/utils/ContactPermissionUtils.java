package com.sddd.tfn.premissiondemo.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by tfn on 17-6-14.
 * <p>
 * 联系人权限判断
 * <p>
 * todo 如果联系人列表为空，则这里无论用户设置关闭联系人权限与否，都会返回false
 */

public class ContactPermissionUtils {

    public static synchronized boolean isContactCanUse(Context context) {

        Cursor phoneCursor = null;

        try {
            ContentResolver resolver = context.getContentResolver();
            // 获取手机联系人
            phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            if (phoneCursor != null) {
                Log.d("TFN", "联系人长度：" + phoneCursor.getCount());
                while (phoneCursor.getCount() > 0) {
                    Log.d("TFN", "有权限");
                    return true;
                }
                Log.d("TFN", "没有权限1");
                return false;
            }

            Log.d("TFN", "没有权限2");
            return false;
        } catch (Exception e) {
            Log.d("TFN", "异常，没有权限");
            return false;
        } finally {
            if (phoneCursor != null) {
                phoneCursor.close();
            }
        }
    }
}
