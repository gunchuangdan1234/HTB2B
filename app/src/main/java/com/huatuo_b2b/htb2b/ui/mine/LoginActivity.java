package com.huatuo_b2b.htb2b.ui.mine;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.common.PreferenceUtils;
import com.huatuo_b2b.htb2b.fragment.MoblieLoginFragment;
import com.huatuo_b2b.htb2b.fragment.UserNameLoginFragment;
import com.huatuo_b2b.htb2b.fragment.widget.IndicatorFragmentActivity;

import java.util.List;


/**
 * 登录界面
 *
 * @author KingKong-HE
 * @Time 2015-1-12
 * @Email KingKong@QQ.COM
 */
public class LoginActivity extends IndicatorFragmentActivity implements
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
        tabs.add(new TabInfo(0, "帐号登录",
                UserNameLoginFragment.class));

        int sms_login = PreferenceUtils.getInt(this, "sms_login", 0);
        if (sms_login == 1) {
            tabs.add(new TabInfo(1, "验证码登录",
                    MoblieLoginFragment.class));
        }
        return 0;
    }


}
