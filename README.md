# Android6.0以下版本9种危险权限判断

## 一、危险权限简介

Android6.0（SDK版本23）及以上开始，谷歌将Android的权限分成了两种，一种是普通类型的，像获取网络状态等等，这些只要在AndroidManifest.xml里申明就可以自行获取到了，还有一种是危险类型，这种权限首先要在AndroidManifest.xml里申明，然后还要在代码中显式地调用申请，待用户同意了以后才能有这个权限，用户拒绝那就没了。

**当然了，app中的targetSdkVersion版本必须要23及以上，才会走新的权限逻辑，如果你的app的targetSdkVersion低于23，那么即时在Android6.0及以上手机中，权限逻辑也按照旧的逻辑走。**

危险权限被谷歌分成了9组，这些权限涉及到了用户的隐私，因此谷歌希望由用户来决定是否给予app权限，分别如下：

1. android.permission-group.CAMERA（拍照权限）：

    * android.permission.CAMERA

2. android.permission-group.LOCATION（定位权限）：

    * android.permission.ACCESS_FINE_LOCATION

    * android.permission.ACCESS_COARSE_LOCATION

3. android.permission-group.MICROPHONE（录音权限）：

    * android.permission.RECORD_AUDIO

4. android.permission-group.STORAGE（存储权限）：

    * android.permission.READ_EXTERNAL_STORAGE

    * android.permission.WRITE_EXTERNAL_STORAGE

5. android.permission-group.CALENDAR（日历权限）：

    * android.permission.READ_CALENDAR

    * android.permission.WRITE_CALENDAR

6. android.permission-group.CONTACTS（联系人权限）：

    * android.permission.READ_CONTACTS

    * android.permission.WRITE_CONTACTS

    * android.permission.GET_ACCOUNTS

7. android.permission-group.PHONE（手机状态权限）：

    * android.permission.READ_PHONE_STATE

    * android.permission.CALL_PHONE

    * android.permission.READ_CALL_LOG

    * android.permission.WRITE_CALL_LOG

    * com.android.voicemail.permission.ADD_VOICEMAIL

    * android.permission.USE_SIP

    * android.permission.PROCESS_OUTGOING_CALLS

8. android.permission-group.SMS（短信权限）：

    * android.permission.SEND_SMS

    * android.permission.RECEIVE_SMS

    * android.permission.READ_SMS

    * android.permission.RECEIVE_WAP_PUSH

    * android.permission.RECEIVE_MMS

    * android.permission.READ_CELL_BROADCASTS

9. android.permission-group.SENSORS（传感器权限）：

    * android.permission.BODY_SENSORS

**上述9组权限，每组中只要有一个权限被用户同意授权了，那么该组中的所有权限都会被同意**。

上面9组权限，相对比较重要，也比较常见的是**拍照权限**、**定位权限**、**录音权限**、**存储权限**、**联系人权限**、**手机状态权限**和**短信权限**，而日历权限只针对谷歌原生的日历，这个在目前国产手机中，没什么意义，传感器权限针对的是具有记录心率功能的设备，主要见于可穿戴设备，因此也可忽略。

## 二、Android6.0以下危险权限判断的必要性

Android6.0及以上版本的权限判断我就不说了，随便百度谷歌一大堆，这里主要说的就是针对6.0以下的手机，如何去判断上面的权限是否开启还是关闭。

那么为什么要判断呢？这里分两种情况，针对targetSdkVersion的版本：

1. targetSdkVersion>=23：此时Android6.0及以上手机，那么上面的权限就按照要求在代码中申请就是了；Android6.0以下手机，理论上，或者说谷歌原生系统的app，在安装应用的时候，会直接将开发人员在AndroidManifest.xml中申明的权限全部获取，而且无法手动关闭，**但是，国产手机以及部分国外手机，会修改操作系统，将这些权限开放给用户，比如小米，华为，三星**，这就有可能会让用户手动关闭，程序就会出问题。

2. targetSdkVersion<23：此时无论Android6.0以下还是以上手机，都是旧的权限逻辑，但是同样有上面所说的国产手机手动关闭问题。

正是基于上面的两点，我觉得有必要针对Android6.0以下手机的权限判断做一个梳理。

## 三、Android6.0以下危险权限判断

针对如何进行判断，其实没有什么好办法，只能说是主动尝试去做一下需要权限的操作，然后捕获异常，通过异常来确定当前该权限是否开启。

### 拍照权限

思路还是如上所说，尝试开启摄像头，看是否会异常，如果异常，说明权限关闭。

```java
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
```

### 定位权限

***定位权限比较蛋疼，目前没有什么办法可以很好的判断，高德好像也无法判断。***

### 录音权限

同样的思路，靠异常来判断。

```java
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * Created by tfn on 17-6-14.
 * <p>
 * 录音权限判断
 * <p>
 */

public class RecordPermissionUtils {

    /**
     * 是不是有录音权限
     *
     * @return 是不是有录音权限
     */
    public static synchronized boolean isRecordCanUse() {
        AudioRecord record = null;
        try {
            record = new AudioRecord(MediaRecorder.AudioSource.MIC, 22050, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT,
                    AudioRecord.getMinBufferSize(22050, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT));
            record.startRecording();
            int recordingState = record.getRecordingState();
            if (recordingState == AudioRecord.RECORDSTATE_STOPPED) {
                return false;
            }
            //第一次  为true时，先释放资源，在进行一次判定
            //************
            record.release();
            record = new AudioRecord(MediaRecorder.AudioSource.MIC, 22050, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT,
                    AudioRecord.getMinBufferSize(22050, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT));
            record.startRecording();
            int recordingState1 = record.getRecordingState();
            if (recordingState1 == AudioRecord.RECORDSTATE_STOPPED) {
            }
            //**************
            //如果两次都是true， 就返回true  原因未知
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (record != null) {
                record.release();
            }
        }
    }
}
```

### 存储权限

存储权限的话同样，可以通过尝试创建一个文件看是否失败来判断是否权限有开启。

```java
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
```

### 联系人权限

联系人权限判断就是直接去获取联系人来判断，不过这里有一个还未解决的问题就是，如果联系人本来就为空，那么还是会判断为没有权限，**待完善**。

```java
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

    /**
     * todo 目前如果联系人列表为空，则这里无论用户设置关闭联系人权限与否，都会返回false
     *
     * @param context 上下文对象
     * @return 是不是有联系人权限
     */
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
```

### 手机状态权限

手机状态权限主要用于获取一些手机的状态，判断权限是否开启的方式就是直接获取imei值，看是否为空。

```java
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
```

### 短信权限

短信权限，其实不推荐进行权限判断，因为没有什么必要，即时没有权限，也可以通过intent携带数据跳转到发短信页面去，让用户主动发送，这样对用户来说也更加友好。

```java
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
```

## 四、总结

总的来说，除了定位权限无法判断，其他的重要权限都可以通过尝试调用某些触发该权限的方法来进行判断。

如果有不是太清楚的，可以看下我的demo：[Android6.0以下危险权限判断Demo](https://github.com/speaklesstfn/PermissionDemo)。

最后说一句：**这些手机厂商真是瞎几把改！**
