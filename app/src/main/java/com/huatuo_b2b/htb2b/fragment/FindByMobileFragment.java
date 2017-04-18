package com.huatuo_b2b.htb2b.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.fragment.widget.BaseFragmentActivity;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.ResponseData;
import com.huatuo_b2b.htb2b.ui.mine.MobileRegisterSubmitActivity;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by jinguo on 2016/3/18.
 */
public class FindByMobileFragment extends Fragment implements View.OnClickListener {
    private MyShopApplication myApplication;
    private BaseFragmentActivity activity;

    private EditText ed_mobile_num;
    private EditText ed_check_num;
    private EditText ed_new_pas;

    private Button btn_get_check;
    private TimeCount time;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_pas_mobile, container,
                false);
        activity = (BaseFragmentActivity) getActivity();
        myApplication = (MyShopApplication) activity.getApplicationContext();
        initViewID(view);
        time = new TimeCount(60000, 1000);
        return view;
    }

    private void initViewID(View view) {
        btn_get_check = (Button) view.findViewById(R.id.btn_get_check);

        btn_get_check.setOnClickListener(this);

        ed_mobile_num = (EditText) view.findViewById(R.id.ed_mobile_num);
        ed_check_num = (EditText) view.findViewById(R.id.ed_check_num);
        ed_new_pas = (EditText) view.findViewById(R.id.ed_new_pas);


        view.findViewById(R.id.btn_find).setOnClickListener(this);
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            btn_get_check.setText("发送动态密码");
            btn_get_check.setClickable(true);
            btn_get_check.setSelected(true);
//            bt_get_verification
//                    .setBackgroundResource(R.drawable.selector_color_button_bg_conner_lv);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            btn_get_check.setClickable(false);
            btn_get_check.setSelected(false);
//            bt_get_verification
//                    .setBackgroundResource(R.drawable.selector_color_button_bg_conner_hui);
            btn_get_check.setText(millisUntilFinished / 1000 + "秒后 重发");
        }
    }

    /**
     * 获取验证码
     */
    public void getCaptcha(String phone, String type) {
        String url = Constants.URL_GET_CAPTCHA;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);
        params.put("type", type);


        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    try {
                        JSONObject obj = new JSONObject(json);
                        boolean isOK = obj.getBoolean("isOK");

                        String msg = obj.getString("msg");

                        if (msg != null)
                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(activity, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    /**
     * 手机找回密码
     * •	member_mobile	手机号
     * •	sms_captcha		验证码
     * •	new_passwd	新密码
     */
    public void findPas(String phone, String captcha, String newPas) {
        String url = Constants.URL_FIND_PAS;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("member_mobile", phone);
        params.put("sms_captcha", captcha);
        params.put("new_passwd", newPas);

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    try {
                        JSONObject obj = new JSONObject(json);
                        boolean isOK = obj.getBoolean("isOK");

                        String msg = obj.getString("msg");

                        if (msg != null)
                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();


                        if (isOK) {

                            Intent mIntent = new Intent(Constants.LOGIN_SUCCESS_URL);
                            activity.sendBroadcast(mIntent);
                            activity.finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(activity, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_find: {

                String phone = ed_mobile_num.getText().toString();
                String checkNum = ed_check_num.getText().toString();
                String newPas = ed_new_pas.getText().toString();

                if (TextUtils.isEmpty(phone)) {

                    Toast.makeText(activity, "请输入手机号！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(checkNum)) {

                    Toast.makeText(activity, "请输入验证码！", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(newPas)) {

                    Toast.makeText(activity, "请输入新密码！", Toast.LENGTH_SHORT).show();
                    return;
                }

                findPas(phone, checkNum, newPas);
            }
            break;


            case R.id.btn_get_check: {
                String phone = ed_mobile_num.getText().toString();

                if (TextUtils.isEmpty(phone)) {

                    Toast.makeText(activity, "请输入手机号！", Toast.LENGTH_SHORT).show();
                    return;
                }

                time.start();
                getCaptcha(phone, "3");
            }
            break;
        }

    }
}


