package com.sddd.tfn.premissiondemo;

import com.sddd.tfn.premissiondemo.utils.CameraPermissionUtils;
import com.sddd.tfn.premissiondemo.utils.ContactPermissionUtils;
import com.sddd.tfn.premissiondemo.utils.PhoneStatePermissionUtils;
import com.sddd.tfn.premissiondemo.utils.RecordPermissionUtils;
import com.sddd.tfn.premissiondemo.utils.SMSPermissionUtils;
import com.sddd.tfn.premissiondemo.utils.StoragePermissionUtils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 6.0以下敏感权限测试Demo
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TFN";
    private Button mCameraBtn = null;
    private TextView mCameraTxt = null;
    private Button mLocationBtn = null;
    private TextView mLocationTxt = null;
    private Button mRecordBtn = null;
    private TextView mRecordTxt = null;
    private Button mStorageBtn = null;
    private TextView mStorageTxt = null;
    private Button mCalendarBtn = null;
    private TextView mCalendarTxt = null;
    private Button mContactBtn = null;
    private TextView mContactTxt = null;
    private Button mPhoneStateBtn = null;
    private TextView mPhoneStateTxt = null;
    private Button mSMSBtn = null;
    private TextView mSMSTxt = null;
    private Button mBodySensorBtn = null;
    private TextView mBodySensorTxt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.cameraBtn:
                cameraBtnOnClick();
                break;
            case R.id.locationBtn:
                locationBtnOnClick();
                break;
            case R.id.recordBtn:
                recordBtnOnClick();
                break;
            case R.id.storageBtn:
                storageBtnOnClick();
                break;
            case R.id.calendarBtn:
                calendarBtnOnClick();
                break;
            case R.id.contactBtn:
                contactBtnOnClick();
                break;
            case R.id.phoneStateBtn:
                phoneStateBtnOnClick();
                break;
            case R.id.smsBtn:
                smsBtnOnClick();
                break;
            case R.id.bodySensorBtn:
                bodySensorBtnOnClick();
                break;
        }
    }

    /**
     * 点击拍照权限按钮
     */
    private void cameraBtnOnClick() {
        boolean isCanUse = CameraPermissionUtils.isCameraCanUse();
        if (isCanUse) {
            mCameraTxt.setText("有权限");
        } else {
            mCameraTxt.setText("没有权限");
        }
    }

    /**
     * 点击定位权限按钮
     */
    private void locationBtnOnClick() {
        Log.d(TAG, "定位权限没有办法解决目前");
    }

    /**
     * 点击录音权限按钮
     */
    private void recordBtnOnClick() {
        boolean isCanUse = RecordPermissionUtils.isRecordCanUse();
        if (isCanUse) {
            mRecordTxt.setText("有权限");
        } else {
            mRecordTxt.setText("没有权限");
        }
    }

    /**
     * 点击存储权限按钮
     */
    private void storageBtnOnClick() {
        boolean isCanUse = StoragePermissionUtils.isStorageCanUse();
        if (isCanUse) {
            mStorageTxt.setText("有权限");
        } else {
            mStorageTxt.setText("没有权限");
        }
    }

    /**
     * 点击日历权限按钮
     */
    private void calendarBtnOnClick() {
        Log.d(TAG, "日历权限针对谷歌原生的日历控件，因此目前无所谓");
    }

    /**
     * 点击联系人权限按钮
     */
    private void contactBtnOnClick() {
        boolean isCanUse = ContactPermissionUtils.isContactCanUse(MainActivity.this);
        if (isCanUse) {
            mContactTxt.setText("有权限");
        } else {
            mContactTxt.setText("没有权限或联系人为空");
        }
    }

    /**
     * 点击手机状态权限按钮
     */
    private void phoneStateBtnOnClick() {
        boolean isCanUse = PhoneStatePermissionUtils.isPhoneStateCanUse(MainActivity.this);
        if (isCanUse) {
            mPhoneStateTxt.setText("有权限");
        } else {
            mPhoneStateTxt.setText("没有权限");
        }
    }

    /**
     * 点击短信权限按钮
     */
    private void smsBtnOnClick() {
        boolean isCanUse = SMSPermissionUtils.isSMSCanUse(MainActivity.this);
        if (isCanUse) {
            mSMSTxt.setText("有权限");
        } else {
            mSMSTxt.setText("没有权限或没有短信");
        }
    }

    /**
     * 点击身体传感器权限按钮
     */
    private void bodySensorBtnOnClick() {
        Log.d(TAG, "身体传感器权限仅针对有心率检测功能的设备，因此目前无所谓");
    }

    /**
     * 初始化布局
     */
    private void findViews() {
        mCameraBtn = (Button) findViewById(R.id.cameraBtn);
        mCameraBtn.setOnClickListener(this);
        mCameraTxt = (TextView) findViewById(R.id.cameraTxt);

        mLocationBtn = (Button) findViewById(R.id.locationBtn);
        mLocationBtn.setOnClickListener(this);
        mLocationTxt = (TextView) findViewById(R.id.locationTxt);

        mRecordBtn = (Button) findViewById(R.id.recordBtn);
        mRecordBtn.setOnClickListener(this);
        mRecordTxt = (TextView) findViewById(R.id.recordTxt);

        mStorageBtn = (Button) findViewById(R.id.storageBtn);
        mStorageBtn.setOnClickListener(this);
        mStorageTxt = (TextView) findViewById(R.id.storageTxt);

        mCalendarBtn = (Button) findViewById(R.id.calendarBtn);
        mCalendarBtn.setOnClickListener(this);
        mCalendarTxt = (TextView) findViewById(R.id.calendarTxt);

        mContactBtn = (Button) findViewById(R.id.contactBtn);
        mContactBtn.setOnClickListener(this);
        mContactTxt = (TextView) findViewById(R.id.contactTxt);

        mPhoneStateBtn = (Button) findViewById(R.id.phoneStateBtn);
        mPhoneStateBtn.setOnClickListener(this);
        mPhoneStateTxt = (TextView) findViewById(R.id.phoneStateTxt);

        mSMSBtn = (Button) findViewById(R.id.smsBtn);
        mSMSBtn.setOnClickListener(this);
        mSMSTxt = (TextView) findViewById(R.id.smsTxt);

        mBodySensorBtn = (Button) findViewById(R.id.bodySensorBtn);
        mBodySensorBtn.setOnClickListener(this);
        mBodySensorTxt = (TextView) findViewById(R.id.bodySensorTxt);
    }
}
