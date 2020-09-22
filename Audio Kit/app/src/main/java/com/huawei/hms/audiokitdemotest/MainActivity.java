package com.huawei.hms.audiokitdemotest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.huawei.hms.api.bean.HwAudioPlayItem;
import com.huawei.hms.audiokit.player.callback.HwAudioConfigCallBack;
import com.huawei.hms.audiokit.player.manager.HwAudioManager;
import com.huawei.hms.audiokit.player.manager.HwAudioManagerFactory;
import com.huawei.hms.audiokit.player.manager.HwAudioPlayerConfig;
import com.huawei.hms.audiokit.player.manager.HwAudioPlayerManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private HwAudioManager mHwAudioManager;

    private HwAudioPlayerManager mHwAudioPlayerManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(MainActivity.this);
        Button playBtn = findViewById(R.id.play);
        Button pauseBtn = findViewById(R.id.pause);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHwAudioPlayerManager != null) {
                    mHwAudioPlayerManager.play();
                }
            }
        });
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHwAudioPlayerManager != null) {
                    mHwAudioPlayerManager.pause();
                }
            }
        });
    }

    /**
     * init sdk
     * @param context context
     */
    @SuppressLint("StaticFieldLeak")
    public void init(final Context context) {
        Log.i(TAG, "init start");
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                HwAudioPlayerConfig hwAudioPlayerConfig = new HwAudioPlayerConfig(context);
                HwAudioManagerFactory.createHwAudioManager(hwAudioPlayerConfig, new HwAudioConfigCallBack() {
                    @Override
                    public void onSuccess(HwAudioManager hwAudioManager) {
                        try {
                            Log.i(TAG, "createHwAudioManager onSuccess");
                            mHwAudioManager = hwAudioManager;
                            mHwAudioPlayerManager = hwAudioManager.getPlayerManager();
                            mHwAudioPlayerManager.playList(getOnlinePlaylist(), 0, 0);
                        } catch (Exception e) {
                            Log.e(TAG, "player init fail", e);
                        }
                    }

                    @Override
                    public void onError(int errorCode) {
                        Log.e(TAG, "init err:" + errorCode);
                    }
                });
                return null;
            }
        }.execute();
    }

    /**
     * get online PlayList
     *
     * @return play list
     */
    public List<HwAudioPlayItem> getOnlinePlaylist() {
        List<HwAudioPlayItem> playItemList = new ArrayList<>();
        HwAudioPlayItem audioPlayItem1 = new HwAudioPlayItem();
        audioPlayItem1.setAudioId("1000");
        audioPlayItem1.setSinger("Taoge");
        audioPlayItem1.setOnlinePath("https://lfmusicservice.hwcloudtest.cn:18084/HMS/audio/Taoge-chengshilvren.mp3");
        audioPlayItem1.setOnline(1);
        audioPlayItem1.setAudioTitle("chengshilvren");
        playItemList.add(audioPlayItem1);

        HwAudioPlayItem audioPlayItem2 = new HwAudioPlayItem();
        audioPlayItem2.setAudioId("1001");
        audioPlayItem2.setSinger("Taoge");
        audioPlayItem2.setOnlinePath("https://lfmusicservice.hwcloudtest.cn:18084/HMS/audio/Taoge-dayu.mp3");
        audioPlayItem2.setOnline(1);
        audioPlayItem2.setAudioTitle("dayu");
        playItemList.add(audioPlayItem2);

        HwAudioPlayItem audioPlayItem3 = new HwAudioPlayItem();
        audioPlayItem3.setAudioId("1002");
        audioPlayItem3.setSinger("Taoge");
        audioPlayItem3.setOnlinePath("https://lfmusicservice.hwcloudtest.cn:18084/HMS/audio/Taoge-wangge.mp3");
        audioPlayItem3.setOnline(1);
        audioPlayItem3.setAudioTitle("wangge");
        playItemList.add(audioPlayItem3);
        return playItemList;
    }
}
