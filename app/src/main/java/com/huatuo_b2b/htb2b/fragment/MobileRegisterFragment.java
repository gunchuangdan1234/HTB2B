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
import android.widget.TextView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.Login;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.LogUtil;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.fragment.widget.BaseFragmentActivity;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.ResponseData;
import com.huatuo_b2b.htb2b.ui.mine.FindPasActivity;
import com.huatuo_b2b.htb2b.ui.mine.MobileRegisterSubmitActivity;
import com.huatuo_b2b.htb2b.ui.mine.RegisteredActivity;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by jinguo on 2016/3/18.
 */
public class MobileRegisterFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "MobileRegisterFragment";

    private MyShopApplication myApplication;
    private BaseFragmentActivity activity;

    private EditText ed_mobile_num;
    private EditText ed_check_num;

    private Button btn_get_check;
    private TimeCount time;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mobile_register, container,
                false);
        activity = (BaseFragmentActivity) getActivity();
        initViewID(view);
        time = new TimeCount(60000, 1000);
        return view;
    }

    private void initViewID(View view) {

        view.findViewById(R.id.submitID).setOnClickListener(this);
        btn_get_check = (Button) view.findViewById(R.id.btn_get_check);

        btn_get_check.setOnClickListener(this);

        ed_mobile_num = (EditText) view.findViewById(R.id.ed_mobile_num);
        ed_check_num = (EditText) view.findViewById(R.id.ed_check_num);

        TextView registeredID = (TextView) view.findViewById(R.id.registeredID);
        TextView tv_rem_pw = (TextView) view.findViewById(R.id.tv_rem_pw);

        registeredID.setOnClickListener(this);
        tv_rem_pw.setOnClickListener(this);

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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registeredID://注册新帐号
                startActivity(new Intent(activity, RegisteredActivity.class));
                activity.finish();

                break;

            case R.id.tv_rem_pw://忘记密码按钮

//                ShowDialog("请拨打客服电话（4006964266）重置密码!");
                startActivity(new Intent(activity, FindPasActivity.class));
                activity.finish();
                break;


            case R.id.submitID: {

                String phone = ed_mobile_num.getText().toString();
                String captcha = ed_check_num.getText().toString();
                checkCaptcha(phone, "1", captcha);

            }
            break;

            case R.id.btn_get_check: {

                String phone = ed_mobile_num.getText().toString();

                if (TextUtils.isEmpty(phone)) {

                    Toast.makeText(activity, "请输入手机号！", Toast.LENGTH_SHORT).show();
                    return;
                }

                time.start();
                getCaptcha(phone, "1");
            }
            break;
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
     * 验证验证码
     */
    public void checkCaptcha(String phone, String type, String sms_captcha) {
        String url = Constants.URL_CHECK_CAPTCHA;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("phone", phone);
        params.put("type", type);
        params.put("sms_captcha", sms_captcha);


        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();

                    try {
                        JSONObject obj = new JSONObject(json);
                        boolean isOK = obj.getBoolean("isOK");

                        String msg = obj.getString("msg");

                        LogUtil.e(TAG, msg);

                        if (isOK) {
                            Intent intent = new Intent(activity, MobileRegisterSubmitActivity.class);
                            String phone = ed_mobile_num.getText().toString();
                            String captcha = ed_check_num.getText().toString();
                            intent.putExtra("member_mobile", phone);
                            intent.putExtra("sms_captcha", captcha);
                            activity.startActivity(intent);
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

}
