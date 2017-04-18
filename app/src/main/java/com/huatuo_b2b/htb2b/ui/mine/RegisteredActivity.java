package com.huatuo_b2b.htb2b.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.Login;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.common.PreferenceUtils;
import com.huatuo_b2b.htb2b.fragment.MobileRegisterFragment;
import com.huatuo_b2b.htb2b.fragment.MoblieLoginFragment;
import com.huatuo_b2b.htb2b.fragment.UserNameLoginFragment;
import com.huatuo_b2b.htb2b.fragment.UserNameRegisterFragment;
import com.huatuo_b2b.htb2b.fragment.widget.IndicatorFragmentActivity;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * 用户注册界面
 *
 * @author KingKong·HE
 * @Time 2014年3月10日 下午2:02:39
 * @E-mail hjgang@bizpoer.com
 */
public class RegisteredActivity extends IndicatorFragmentActivity implements
        OnClickListener {

    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }

    @Override
    protected int supplyTabs(List<TabInfo> tabs) {
        tabs.add(new TabInfo(0, "用户注册",
                UserNameRegisterFragment.class));

        int sms_register = PreferenceUtils.getInt(this, "sms_register", 0);
        if (sms_register == 1) {
            tabs.add(new TabInfo(1, "手机注册",
                    MobileRegisterFragment.class));
        }
        return 0;
    }

}
