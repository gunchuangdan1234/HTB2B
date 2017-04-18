package com.huatuo_b2b.htb2b;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.bean.Login;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.PreferenceUtils;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.ResponseData;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

//欢迎页面
public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      //去掉标题
        setContentView(R.layout.welcome_view);

        //加入定时器 睡眠 2000毫秒 自动跳转页面
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                boolean is_enter_guide = PreferenceUtils.getBoolean(WelcomeActivity.this, "is_enter_guide", false);

                if (is_enter_guide) {
                    Intent it = new Intent();
                    it.setClass(WelcomeActivity.this, MainFragmentManager.class);
                    WelcomeActivity.this.startActivity(it);
                    WelcomeActivity.this.finish();
                } else {
                    Intent it = new Intent();
                    it.setClass(WelcomeActivity.this, GuideActivity.class);
                    startActivity(it);
                    WelcomeActivity.this.finish();
                }
            }
        }, 1000);
        getLoginMode();
    }


    /**
     * 判断当前app是否支持手机登录注册找回密码
     */
    public void getLoginMode() {
        String url = Constants.URL_MB_LONGIN_MODE;
        RemoteDataHandler.asyncPostDataString(url, null, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
//                    {"sms_password":"1","sms_login":"1","sms_register":"1"}
                    System.out.println("infoLoginCheck  ############# json： " + json);


                    try {
                        JSONObject objDate = new JSONObject(json);
                        int sms_password = objDate.optInt("sms_password");
                        int sms_login = objDate.optInt("sms_login");
                        int sms_register = objDate.optInt("sms_register");

                        PreferenceUtils.setInt(WelcomeActivity.this, "sms_password", sms_password);
                        PreferenceUtils.setInt(WelcomeActivity.this, "sms_login", sms_login);
                        PreferenceUtils.setInt(WelcomeActivity.this, "sms_register", sms_register);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }
        });
    }

}
