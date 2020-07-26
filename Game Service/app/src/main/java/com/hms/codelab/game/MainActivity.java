package com.hms.codelab.game;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.jos.JosApps;
import com.huawei.hms.jos.JosAppsClient;
import com.huawei.hms.jos.games.Games;
import com.huawei.hms.jos.games.PlayersClient;
import com.huawei.hms.jos.games.buoy.BuoyClient;
import com.huawei.hms.jos.games.player.Player;
import com.huawei.hms.jos.games.player.PlayerExtraInfo;
import com.huawei.hms.support.hwid.HuaweiIdAuthManager;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper;
import com.huawei.hms.support.hwid.result.AuthHuaweiId;
import com.huawei.hms.support.hwid.result.HuaweiIdAuthResult;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 浮标实例 buoyClien
     */
    BuoyClient buoyClient;
    private String TAG = "Game_codeLab";
    private PlayersClient playersClient;
    private String playerID;

    /**
     * sessionId 事件ID，用于上报每个周期的游戏开始与暂停。每个事件上报开始和结束ID要一致。
     */
    private String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buoyClient = Games.getBuoyClient(this);

        initView();


    }

    private void initView() {

        findViewById(R.id.codelab_gameLogin).setOnClickListener(this);
        findViewById(R.id.codelab_getPlayerExtraInfo).setOnClickListener(this);
        findViewById(R.id.codelab_reportStart).setOnClickListener(this);
        findViewById(R.id.codelab_reportEnd).setOnClickListener(this);
        findViewById(R.id.codelab_init).setOnClickListener(this);
        findViewById(R.id.codelab_signIn).setOnClickListener(this);
    }


    /**
     * @return 组装游戏登录scope配置对象
     */
    public HuaweiIdAuthParams getHuaweiIdParams() {

        return new HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM_GAME).setIdToken().createParams();

    }

    /**
     * 游戏登录需要先调用华为账号认证登录完成之后，再调用getcurrentPlayer接口获取游戏玩家信息
     * 1.先调用华为账号登录接口的静默登录接口，此接口对于已经授权登录过的应用不会再次拉起登录页面。
     * 2.静默登录失败一般是由于需要首次登录需要授权，此时在回调中调用显示登录接口拉起登录授权页面进行登录认证。
     * 登录接口会在onActivity生命周期中获取，可在此时调用游戏获取玩家信息接口。
     */
    public void signIn() {

        Task<AuthHuaweiId> authHuaweiIdTask = HuaweiIdAuthManager.getService(this, getHuaweiIdParams()).silentSignIn();
        authHuaweiIdTask.addOnSuccessListener(new OnSuccessListener<AuthHuaweiId>() {
            @Override
            public void onSuccess(AuthHuaweiId authHuaweiId) {
                Log.i(TAG, "silentsignIn success");
                Log.i(TAG, "display:" + authHuaweiId.getDisplayName());
                login();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    ApiException apiException = (ApiException) e;
                    Log.i(TAG, "signIn failed:" + apiException.getStatusCode());
                    Log.i(TAG, "start getSignInIntent");
//                    显示登录
                    HuaweiIdAuthService service = HuaweiIdAuthManager.getService(MainActivity.this, getHuaweiIdParams());
                    startActivityForResult(service.getSignInIntent(), 6013);
                }
            }
        });
    }

    /**
     * 获取游戏玩家用户信息
     */
    public void login() {
        playersClient = Games.getPlayersClient(this);
        Task<Player> playerTask = playersClient.getCurrentPlayer();
        playerTask.addOnSuccessListener(new OnSuccessListener<Player>() {
            @Override
            public void onSuccess(Player player) {
                playerID = player.getPlayerId();
                Log.i(TAG, "getPlayerInfo Success, player info: " + player.getPlayerId());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                // 获取玩家信息失败
                if (e instanceof ApiException) {
                    Log.e(TAG, "getPlayerInfo failed, status: " + ((ApiException) e).getStatusCode());
                }
            }
        });
    }

    /**
     * 当游戏界面在前台时，展示浮标。
     * 开启游戏防沉迷事件开始上报.
     */
    @Override
    protected void onResume() {
        super.onResume();

        buoyClient.showFloatWindow();
    }

    /**
     * 当游戏在后台时，隐藏浮标。
     * 开启游戏防沉迷事件结束上报
     */
    @Override
    protected void onPause() {
        super.onPause();
        buoyClient.hideFloatWindow();
    }

    /**
     * SDK初始化
     */
    private void init() {
        JosAppsClient appsClient = JosApps.getJosAppsClient(this);
        appsClient.init();
        showLog("init success");
    }


    /**
     * 防沉迷事件开始时间上报。
     */
    private void timeReportStart() {
        if (playersClient == null) {

            Log.i(TAG, "playersClient is null, please init  playersClient first");
            login();
            return;
        }
        if (playerID == null) {

            Log.i(TAG, "playerID is null, please getcurrentPlayer login first");
            login();
            return;
        }
        String uid = UUID.randomUUID().toString();
        Task<String> task = playersClient.submitPlayerEvent(playerID, uid, "GAMEBEGIN");
        task.addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String jsonRequest) {
                try {
                    JSONObject data = new JSONObject(jsonRequest);
                    sessionId = data.getString("transactionId");
                } catch (JSONException e) {
                    showLog("parse jsonArray meet json exception");
                    return;
                }
                showLog("submitPlayerEvent traceId: " + jsonRequest);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    showLog(result);
                }
            }
        });

    }


    /**
     * 防沉迷事件结束时间上报
     * sessionId： 每次Begin上报"开始"事件成功后会在回调中返回此次上报事件的标识，用于“结束”事件的上报闭环统计此段游戏时长。
     */
    private void timeReportEnd() {

        if (playersClient == null) {

            Log.i(TAG, "playersClient is null, please init  playersClient first");
            login();
            return;
        }
        if (playerID == null) {

            Log.i(TAG, "playerID is null, please getcurrentPlayer login first");
            login();
            return;
        }
        if (sessionId == null) {

            Log.i(TAG, "sessionId is null, please submitPlayerEvent Begin  first");
            login();
            return;
        }


        Task<String> task = playersClient.submitPlayerEvent(playerID, sessionId, "GAMEEND");
        task.addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                showLog("submitPlayerEvent traceId: " + s);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    showLog(result);
                }
            }
        });
    }

    /**
     * 日志输出
     */
    public void showLog(String logLine) {
        StringBuffer sbLog = new StringBuffer();
        DateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:SS", Locale.ENGLISH);
        String time = format.format(new Date());

        sbLog.append(time).append(":").append(logLine);

        Log.i(TAG, sbLog.toString());
    }


    /**
     * 按钮点击监听事件
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.codelab_gameLogin) {

            login();
        }
        if (v.getId() == R.id.codelab_getPlayerExtraInfo) {

            getPlayerExfra();
        }
        if (v.getId() == R.id.codelab_reportStart) {

            timeReportStart();
        }
        if (v.getId() == R.id.codelab_reportEnd) {

            timeReportEnd();
        }
        if (v.getId() == R.id.codelab_init) {

            init();
        }
        if (v.getId() == R.id.codelab_signIn) {

            signIn();
        }
    }

    /**
     * 查询防沉迷事件上报详情
     * 如果玩家未成年，则需使用PlayerExtraInfo.getPlayerDuration查看其当前的累计游戏时长，该游戏时长为玩家当天最新的累计游戏时长。
     * 游戏需要根据累计游戏时长进行未成年玩家的防沉迷监控，具体规则由开发者自行定义。
     */
    private void getPlayerExfra() {
        if (playersClient == null) {

            Log.i(TAG, "playersClient is null, please init  playersClient first");
            login();
            return;
        }
        if (sessionId == null) {

            Log.i(TAG, "sessionId is null, please submitPlayerEvent Begin  first");
            login();
            return;
        }
        Task<PlayerExtraInfo> task = playersClient.getPlayerExtraInfo(sessionId);
        task.addOnSuccessListener(new OnSuccessListener<PlayerExtraInfo>() {
            @Override
            public void onSuccess(PlayerExtraInfo extra) {
                if (extra != null) {
                    showLog("IsRealName: " + extra.getIsRealName() + ", IsAdult: " + extra.getIsAdult()
                            + ", PlayerId: " + extra.getPlayerId() + ", PlayerDuration: " + extra.getPlayerDuration());
                } else {
                    showLog("Player extra info is empty.");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                if (e instanceof ApiException) {
                    String result = "rtnCode:" + ((ApiException) e).getStatusCode();
                    showLog(result);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 6013) {

            if (null == data) {
                showLog("signIn inetnt is null");
                return;
            }
            String jsonSignInResult = data.getStringExtra("HUAWEIID_SIGNIN_RESULT");
            if (TextUtils.isEmpty(jsonSignInResult)) {
                showLog("signIn result is empty");
                return;
            }
            try {
                HuaweiIdAuthResult signInResult = new HuaweiIdAuthResult().fromJson(jsonSignInResult);
                if (0 == signInResult.getStatus().getStatusCode()) {
                    showLog("signIn success.");
                    showLog("signIn result: " + signInResult.toJson());
                } else {
                    showLog("signIn failed: " + signInResult.getStatus().getStatusCode());
                }
            } catch (JSONException var7) {
                showLog("Failed to convert json from signInResult.");
            }
        }
    }
}
