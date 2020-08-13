package com.huawei.codelabs.hihealth.happysport.viewmodels.HiHealthBaseAdapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.huawei.hms.hihealth.HiHealthOptions;
import com.huawei.hms.hihealth.HuaweiHiHealth;
import com.huawei.hms.hihealth.SensorsController;
import com.huawei.hms.hihealth.data.DataCollector;
import com.huawei.hms.hihealth.data.DataType;
import com.huawei.hms.hihealth.data.DeviceInfo;
import com.huawei.hms.hihealth.data.Field;
import com.huawei.hms.hihealth.data.SamplePoint;
import com.huawei.hms.hihealth.options.OnSamplePointListener;
import com.huawei.hms.hihealth.options.SensorOptions;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;

import java.util.concurrent.TimeUnit;

public class HiHealthBaseAdapter {
    private static final String TAG = "HiHealthBaseAdapter";

    public static final long SAMPLE_INTERVAL = 10 * 1000L;

    private static final long FIRST_DELAY = 1000L;

    private ISportListener mSportListener;

    private Context mContext;

    private SensorsController mSensorsController;

    private boolean mIsStopped;

    private long mBaseTimeStamp;

    private long mCurrentStartTime;

    private int mSteps;

    private Handler mHandler;

    private Runnable mSampleHandler;

    // TODO: listener
    private OnSamplePointListener mListener = null;

    public HiHealthBaseAdapter(Context context, ISportListener listener) {
        mContext = context;
        mSportListener = listener;
        mSteps = 0;
        mBaseTimeStamp = 0L;
        mCurrentStartTime = 0L;
        mIsStopped = false;

        // TODO: client

        setupSample();
    }

    private void processSamplePoint(OnSamplePointListener listener, SamplePoint samplePoint) {
        synchronized (listener.getClass()) {
            if (mIsStopped) {
                return;
            }
        }

        mBaseTimeStamp = samplePoint.getEndTime(TimeUnit.MILLISECONDS);
        mSteps += samplePoint.getFieldValue(Field.FIELD_STEPS_DELTA).asIntValue();
    }

    private void setupSample() {
        mHandler = new Handler();
        mSampleHandler = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "sample real time data");
                if (mBaseTimeStamp != 0L && mSteps != 0) {
                    WalkingSportData walkingSportData = new WalkingSportData(mBaseTimeStamp - mCurrentStartTime, mSteps);
                    mSportListener.onRecvData(walkingSportData);
                    mSteps = 0;
                    Log.d(TAG, String.format("feed data: %s", walkingSportData.toString()));
                }

                mHandler.postDelayed(this, SAMPLE_INTERVAL);
            }
        };
    }


    public boolean start(ISportListener listener) {
        Log.i(TAG, "begin to access sensor");

        synchronized (this.getClass()) {
            mIsStopped = false;
        }

        // TODO: start

        mHandler.postDelayed(mSampleHandler, FIRST_DELAY);
        mCurrentStartTime = System.currentTimeMillis();

        return true;
    }


    public boolean stop() {
        Log.i(TAG, "stop to access sensor");

        synchronized (this.getClass()) {
            mIsStopped = true;
        }

        // TODO: stop


        mHandler.removeCallbacks(mSampleHandler);
        mBaseTimeStamp = 0L;
        mCurrentStartTime = 0L;
        mSteps = 0;

        return true;
    }
}
