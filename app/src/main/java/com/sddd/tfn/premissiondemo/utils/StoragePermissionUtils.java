package com.sddd.tfn.premissiondemo.utils;

import java.io.File;

import android.os.Environment;
import android.util.Log;

/**
 * Created by tfn on 17-6-14.
 * <p>
 * 存储权限判断
 * <p>
 */

public class StoragePermissionUtils {

    public static synchronized boolean isStorageCanUse() {
        if (!existsSdcard()) {
            Log.d("TFN", "没有sd卡");
            return false;
        }

        try {
            String filePath = Environment.getExternalStorageDirectory() + File.separator + "tryFile.txt";
            Log.d("TFN", "路径：" + filePath);
            File file = new File(filePath);
            if (file.exists()) {
                Log.d("TFN", "文件本来就存在，那就删除了再来");
                file.delete();
            }

            file.createNewFile();
            if (file.exists()) {
                Log.d("TFN", "文件真的存在了，那就把它删除了吧");
                file.delete();
                return true;
            }
            Log.d("TFN", "文件不存在");
            return false;
        } catch (Exception e) {
            Log.d("TFN", "文件异常");
            return false;
        }
    }

    /**
     * 判断外置sdcard是否可以正常使用
     */
    private static Boolean existsSdcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable();
    }
}
