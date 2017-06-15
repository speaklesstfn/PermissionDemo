package com.sddd.tfn.premissiondemo.utils;

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
