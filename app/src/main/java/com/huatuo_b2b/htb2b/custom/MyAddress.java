package com.huatuo_b2b.htb2b.custom;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.Spinner;

import com.huatuo_b2b.htb2b.R;

/**
 * 选择地址弹出窗口
 *
 * @author HJGANG
 */
public class MyAddress extends Dialog {
    public Button btu_on;
    public Button btu_off;
    public Spinner spinner_shengID;
    public Spinner spinner_shiID;
    public Spinner spinner_quID;

    public MyAddress(Context context) {
        super(context, R.style.MyProgressDialog);
        this.setContentView(R.layout.my_address_dialog);

        btu_on = (Button) findViewById(R.id.btu_on);
        btu_off = (Button) findViewById(R.id.btu_off);
        spinner_shengID = (Spinner) findViewById(R.id.spinner_shengID);
        spinner_shiID = (Spinner) findViewById(R.id.spinner_shiID);
        spinner_quID = (Spinner) findViewById(R.id.spinner_quID);

    }
}
