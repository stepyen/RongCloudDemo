package com.stepyen.rongclouddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.callkit.RongCallKit;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import okhttp3.Call;


/**
 *
 * 融云音视频通话测试
 *
 *
 *
 * 对融云进行修改：
 * 1、
 * 需要提醒开发者的是，如果您在开发过程中，没有集成融云 SDK 中的聊天会话界面 (ConversationFragment)，您还需
 * RongCallModule 中的变量 mViewLoaded 在该类的 onCreate 方法中设置为 True。
 *
 * 2、
 */
public class MainActivity extends AppCompatActivity {


    @BindView(R.id.et_my_token)
    EditText mEtMyToken;
    @BindView(R.id.btn_init)
    Button mBtnInit;
    @BindView(R.id.et_opposite_side_token)
    EditText mEtOppositeSideToken;
    @BindView(R.id.btn_call)
    Button mBtnCall;
    @BindView(R.id.tv_status)
    TextView mTvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_init, R.id.btn_call})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_init:
                getToken();
                break;
            case R.id.btn_call:
                call();
                break;
        }
    }


    private void getToken() {
        String headUrl = "http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655.jpg";
        String name = "小明";

        final String nonce = Integer.toString(new Random().nextInt(1000));
        final String timeStamp = Long.toString(System.currentTimeMillis());
        final String signature = SHA1Tool.SHA1(Constant.KEY_SECRET_RONG_CLOUD + nonce + timeStamp);

        OkHttpUtils.post()
                .url(Constant.BASE_URL+Constant.GET_TOKEN)
                .addHeader("RC-App-Key", Constant.KEY_RONG_CLOUD)
                .addHeader("RC-Nonce", nonce)
                .addHeader("RC-Timestamp", timeStamp)
                .addHeader("RC-Signature", signature)
                .addParams("userId",mEtMyToken.getText().toString().trim())
                .addParams("name", name)
                .addParams("portraitUri",headUrl)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(MainActivity.this, "获取token失败onError "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        RongTokenResponse rongTokenResponse = new Gson().fromJson(response, RongTokenResponse.class);
                        if (rongTokenResponse.isSuccess()) {
                            Toast.makeText(MainActivity.this, "获取token成功，为"+rongTokenResponse.token, Toast.LENGTH_SHORT).show();
                            connect(rongTokenResponse.token);
                        }else{
                            Toast.makeText(MainActivity.this, "获取token失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    /**
     * 连接融云服务器
     */
    private void connect(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
             */
            @Override
            public void onTokenIncorrect() {
                Log.d("haha", "连接融云服务器---onTokenIncorrect---Token 错误");
                Toast.makeText(MainActivity.this, "连接融云服务器---onTokenIncorrect---Token 错误", Toast.LENGTH_SHORT).show();

            }

            /**
             * 连接融云成功
             * @param userid 当前 token
             */
            @Override
            public void onSuccess(String userid) {
                Log.d("haha", "连接融云服务器---onSuccess---连接融云成功---用户ID:" + userid);
                Toast.makeText(MainActivity.this, "连接融云服务器---onSuccess---连接融云成功---用户ID:" + userid, Toast.LENGTH_SHORT).show();
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d("haha", "连接融云服务器---失败---"+errorCode);
                Toast.makeText(MainActivity.this, "连接融云服务器---失败---", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void call() {
        RongCallKit.startSingleCall(this, mEtOppositeSideToken.getText().toString().trim(),
                RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);
    }

}
