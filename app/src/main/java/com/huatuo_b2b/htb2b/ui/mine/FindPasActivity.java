package com.huatuo_b2b.htb2b.ui.mine;

import android.os.Bundle;
import android.view.View;

import com.huatuo_b2b.htb2b.common.PreferenceUtils;
import com.huatuo_b2b.htb2b.fragment.FindByEmailFragment;
import com.huatuo_b2b.htb2b.fragment.FindByMobileFragment;
import com.huatuo_b2b.htb2b.fragment.MoblieLoginFragment;
import com.huatuo_b2b.htb2b.fragment.UserNameLoginFragment;
import com.huatuo_b2b.htb2b.fragment.widget.IndicatorFragmentActivity;

import java.util.List;

/**
 * Created by jinguo on 2016/3/18.
 */
public class FindPasActivity extends IndicatorFragmentActivity implements
        View.OnClickListener {

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
        tabs.add(new TabInfo(0, "忘记密码",
                FindByEmailFragment.class));

        int sms_password = PreferenceUtils.getInt(this, "sms_password", 0);

        if (sms_password == 1) {
            tabs.add(new TabInfo(1, "手机找回密码",
                    FindByMobileFragment.class));
        }
        return 0;
    }
}

