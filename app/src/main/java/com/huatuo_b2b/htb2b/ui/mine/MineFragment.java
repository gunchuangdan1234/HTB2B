package com.huatuo_b2b.htb2b.ui.mine;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.Mine;
import com.huatuo_b2b.htb2b.common.AnimateFirstDisplayListener;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.common.SystemHelper;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;
import com.huatuo_b2b.htb2b.ui.type.AddressListActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 个人中心界面
 *
 * @author KingKong-HE
 * @Time 2015-1-12
 * @Email KingKong@QQ.COM
 */
public class MineFragment extends Fragment implements OnClickListener {

    private MyShopApplication myApplication;


    private LinearLayout ll_user_choose_show; //登录后用户操作页面模块
    private LinearLayout ly_orderID;        //我的订单
    private LinearLayout ly_fav_goods_ID;       //商品收藏查看
    private LinearLayout ly_fav_store_ID;       //店铺收藏查看
    private LinearLayout ly_couponID;           //代金券查看
    private ImageView ly_settingID;             //个人设置
    private LinearLayout ly_addressID;          //查看收货地址

    private RelativeLayout rl_login_flag_ok;    //用户登录后私有面板显示的信息
    private ImageView userPicID;                //用户头像

    private LinearLayout ll_item_user_login_flag_NO;    //登录注册显示版面
    private ImageView btn_user_login_ID;                //登录按钮
    private ImageView btn_user_regin_ID;                //注册按钮

    private ImageView image_logo_not_login;             //用户未登录显示公司logo

    private TextView loginNameID, pointID, predepoitID, availableRcBalanceID;

    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options = SystemHelper.getRoundedBitmapDisplayImageOptions(100);
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewLayout = inflater.inflate(R.layout.main_mine_view, container, false);

        myApplication = (MyShopApplication) getActivity().getApplicationContext();

        initViewID(viewLayout);//初始化注册控件ID

        return viewLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        IsCheckLogin();
    }

    /**
     * 检测是否登录
     */
    public void IsCheckLogin() {
        String loginKey = myApplication.getLoginKey();

        if (loginKey != null && !loginKey.equals("")) {//用户正常登录

            rl_login_flag_ok.setVisibility(View.VISIBLE);  //用户登录后私有面板显示的信息
            ll_user_choose_show.setVisibility(View.VISIBLE); //登录后用户操作页面模块

            ll_item_user_login_flag_NO.setVisibility(View.GONE); //登录注册显示版面
            image_logo_not_login.setVisibility(View.GONE);  //用户未登录显示公司logo

            loadingMyStoreData();//初始化加载我的信息

        } else {
            rl_login_flag_ok.setVisibility(View.GONE);  //用户登录后私有面板显示的信息
            ll_user_choose_show.setVisibility(View.GONE); //登录后用户操作页面模块

            ll_item_user_login_flag_NO.setVisibility(View.VISIBLE); //登录注册显示版面
            image_logo_not_login.setVisibility(View.VISIBLE);  //用户未登录显示公司logo

            pointID.setText("0.00");
            availableRcBalanceID.setText("0.00");
            predepoitID.setText("0.00");
        }
    }

    /**
     * 初始化注册控件ID
     */
    public void initViewID(View view) {
        ll_user_choose_show = (LinearLayout) view.findViewById(R.id.ll_user_choose_show);

        rl_login_flag_ok = (RelativeLayout) view.findViewById(R.id.rl_login_flag_ok);    //用户登录后私有面板显示的信息
        userPicID = (ImageView) view.findViewById(R.id.userPicID);                //用户头像

        ll_item_user_login_flag_NO = (LinearLayout) view.findViewById(R.id.ll_item_user_login_flag_NO);    //登录注册显示版面
        btn_user_login_ID = (ImageView) view.findViewById(R.id.btn_user_login_ID);                //登录按钮
        btn_user_regin_ID = (ImageView) view.findViewById(R.id.btn_user_regin_ID);                //注册按钮
        image_logo_not_login = (ImageView) view.findViewById(R.id.image_logo_not_login);             //用户未登录显示公司logo

        ly_addressID = (LinearLayout) view.findViewById(R.id.addressID);
        loginNameID = (TextView) view.findViewById(R.id.loginNameID);

        pointID = (TextView) view.findViewById(R.id.pointID);

        predepoitID = (TextView) view.findViewById(R.id.predepoitID);

        ly_orderID = (LinearLayout) view.findViewById(R.id.orderID);            //我的订单
        ly_fav_goods_ID = (LinearLayout) view.findViewById(R.id.fav_goods_ID);    //商品收藏查看
        ly_fav_store_ID = (LinearLayout) view.findViewById(R.id.fav_store_ID);    //店铺收藏查看
        ly_couponID = (LinearLayout) view.findViewById(R.id.couponID);            //代金券查看
        ly_settingID = (ImageView) view.findViewById(R.id.settingID);        //个人设置

        availableRcBalanceID = (TextView) view.findViewById(R.id.availableRcBalanceID);

        ly_settingID.setOnClickListener(this);      //设置按钮
        ly_orderID.setOnClickListener(this);        //我的订单
        ly_fav_goods_ID.setOnClickListener(this);   //商品收藏查看
        ly_fav_store_ID.setOnClickListener(this);   //店铺收藏查看
        ly_couponID.setOnClickListener(this);       //代金券查看
        ly_addressID.setOnClickListener(this);      //查看收货地址

        btn_user_login_ID.setOnClickListener(this); //登录按钮
        btn_user_regin_ID.setOnClickListener(this); //注册按钮

        IsCheckLogin();
    }

    @Override
    public void onStart() {
        super.onStart();
        registerBoradcastReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constants.LOGIN_SUCCESS_URL)) {
                loadingMyStoreData();//初始化加载我的信息
            }
        }
    };

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constants.LOGIN_SUCCESS_URL);
        getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);  //注册广播       
    }

    /**
     * 初始化加载我的信息
     */
    public void loadingMyStoreData() {

        String url = Constants.URL_MYSTOIRE;

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {

                System.out.println("####$$$$$$$$$$$$%%%%%%%%%%%%  data.getCode():" + data.getCode());

                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    try {
                        JSONObject obj = new JSONObject(json);
                        String objJson = obj.getString("member_info");

                        // System.out.println("####$$$$$$$$$$$$%%%%%%%%%%%%  objJson:"+ objJson);

                        Mine bean = Mine.newInstanceList(objJson);

                        if (bean != null) {

                            loginNameID.setText(bean.getUsername() == null ? "" : bean.getUsername());
                            pointID.setText(bean.getPoint() == null ? "0" : bean.getPoint());
                            availableRcBalanceID.setText(bean.getAvailable_rc_balance());
                            predepoitID.setText(bean.getPredepoit());
                            imageLoader.displayImage(bean.getAvator(), userPicID, options, animateFirstListener);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        JSONObject objError = new JSONObject(json);
                        String error = objError.getString("error");
                        if (error != null) {
                            Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                    rl_login_flag_ok.setVisibility(View.GONE);  //用户登录后私有面板显示的信息
                    ll_user_choose_show.setVisibility(View.GONE); //登录后用户操作页面模块

                    ll_item_user_login_flag_NO.setVisibility(View.VISIBLE); //登录注册显示版面
                    image_logo_not_login.setVisibility(View.VISIBLE);  //用户未登录显示公司logo

                    pointID.setText("0.00");
                    availableRcBalanceID.setText("0.00");
                    predepoitID.setText("0.00");
//					Toast.makeText(MyStoreActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_user_login_ID://登录按钮
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;

            case R.id.btn_user_regin_ID://注册按钮
                startActivity(new Intent(getActivity(), RegisteredActivity.class));
                break;

          /*  case R.id.imID:   IM入口

                startActivity(new Intent(getActivity(), IMFriendsListActivity.class));
                break;*/

            case R.id.orderID://我的订单
                startActivity(new Intent(getActivity(), OrderListActivity.class)); //VirtualListActivity 虚拟订单查看入口

                break;
            case R.id.fav_goods_ID://商品收藏查看
                startActivity(new Intent(getActivity(), FavGoodsListActivity.class));

                break;
            case R.id.fav_store_ID://店铺收藏查看
                startActivity(new Intent(getActivity(), FavStoreListActivity.class));

                break;

            case R.id.couponID://代金券查看
                startActivity(new Intent(getActivity(), VoucherListActivity.class));

                break;

            case R.id.settingID://个人设置
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;

            case R.id.addressID://用户收获地址查看

                Intent intent = new Intent(getActivity(), AddressListActivity.class);
                intent.putExtra("addressFlag", "0");
                intent.putExtra("addressFlag", "0");
                startActivity(intent);

                break;
            default:
                break;
        }
    }
}
