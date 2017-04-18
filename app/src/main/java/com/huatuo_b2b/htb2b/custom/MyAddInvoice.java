package com.huatuo_b2b.htb2b.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.ui.type.AddressSetActivity;

/**
 * 自定义发票弹出窗口
 *
 * @author HJGANG
 */
public class MyAddInvoice extends Dialog {
    public Button btu_on;
    public Button btu_off;

    public RadioButton rb_invoice_normal;
    public RadioButton rb_invoice_proprietary;


    public EditText add_invoice_edit_danwei_name;
    public EditText add_invoice_edit_inv_num;
    public EditText add_invoice_edit_company_address;
    public EditText add_invoice_edit_phone_num;
    public EditText add_invoice_edit_bank_name;
    public EditText add_invoice_edit_bank_num;


    public EditText et_invoice_name;
    public EditText et_invoice_phone_num;
    public TextView tv_invoice_province;
    public EditText et_invoice_address;

//	public Spinner add_invoice_spinner_context;

    public String inv_title_select = "1";//1或2，是普通发票还是专用发票的分类
//	public String inv_content;//发票内容

    private Activity context;

    public MyAddInvoice(Activity context) {
        super(context, R.style.MyProgressDialog);
        this.context = context;

        this.setContentView(R.layout.my_add_invoic);

        btu_on = (Button) findViewById(R.id.btu_on);
        btu_off = (Button) findViewById(R.id.btu_off);

        //发票抬头
        rb_invoice_normal = (RadioButton) findViewById(R.id.rb_invoice_normal);
        rb_invoice_proprietary = (RadioButton) findViewById(R.id.rb_invoice_proprietary);

//
        add_invoice_edit_danwei_name = (EditText) findViewById(R.id.add_invoice_edit_danwei_name);
        add_invoice_edit_inv_num = (EditText) findViewById(R.id.add_invoice_edit_inv_num);
        add_invoice_edit_company_address = (EditText) findViewById(R.id.add_invoice_edit_company_address);
        add_invoice_edit_phone_num = (EditText) findViewById(R.id.add_invoice_edit_phone_num);
        add_invoice_edit_bank_name = (EditText) findViewById(R.id.add_invoice_edit_bank_name);
        add_invoice_edit_bank_num = (EditText) findViewById(R.id.add_invoice_edit_bank_num);


        et_invoice_name = (EditText) findViewById(R.id.et_invoice_name);
        et_invoice_phone_num = (EditText) findViewById(R.id.et_invoice_phone_num);
        tv_invoice_province = (TextView) findViewById(R.id.tv_invoice_province);
        et_invoice_address = (EditText) findViewById(R.id.et_invoice_address);


//		add_invoice_spinner_context = (Spinner) findViewById(R.id.add_invoice_spinner_context);

        MyRadioButtonClickListener listener = new MyRadioButtonClickListener();
        rb_invoice_normal.setOnClickListener(listener);
        rb_invoice_proprietary.setOnClickListener(listener);


//		add_invoice_spinner_context.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//
//				CityList bean = (CityList) add_invoice_spinner_context.getItemAtPosition(arg2);
//
//				if(bean !=null ){
//					inv_content = bean.getArea_name();
//				}
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0) {
//
//			}
//		});

    }

    class MyRadioButtonClickListener implements View.OnClickListener {
        public void onClick(View v) {
            RadioButton btn = (RadioButton) v;
            switch (btn.getId()) {
                case R.id.rb_invoice_normal:
                    inv_title_select = "1";
//				add_invoice_edit_danwei_name.setVisibility(View.GONE);
                    break;
                case R.id.rb_invoice_proprietary:
                    inv_title_select = "2";
//				add_invoice_edit_danwei_name.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }


}
