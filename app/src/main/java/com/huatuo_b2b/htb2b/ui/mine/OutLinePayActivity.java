package com.huatuo_b2b.htb2b.ui.mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.common.MyShopApplication;

/**
 * Created by jinguo on 2016/7/26.
 */
public class OutLinePayActivity extends Activity implements View.OnClickListener {
    private MyShopApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ontline_pay);

        myApplication = (MyShopApplication) getApplicationContext();

        intiViewID();
    }

    /**
     * 初始化注册控件ID
     */
    public void intiViewID() {
        findViewById(R.id.imageBack).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())

        {
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
