package com.huatuo_b2b.htb2b.ui.mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.huatuo_b2b.htb2b.R;

/**
 * 关于我们页面
 *
 * @author KingKong-HE
 * @Time 2015-2-1
 * @Email KingKong@QQ.COM
 */
public class AboutActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_about);

        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;

            default:
                break;
        }
    }

}
