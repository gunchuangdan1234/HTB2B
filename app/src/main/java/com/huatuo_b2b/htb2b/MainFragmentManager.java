package com.huatuo_b2b.htb2b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.common.SystemHelper;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;
import com.huatuo_b2b.htb2b.ui.cart.CartFragment;
import com.huatuo_b2b.htb2b.ui.home.HomeFragment;
import com.huatuo_b2b.htb2b.ui.mine.MineFragment;
import com.huatuo_b2b.htb2b.ui.mine.UpdateManager;
import com.huatuo_b2b.htb2b.ui.type.OneTypeFragment;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 底部菜单管理界面
 *
 * @author KingKong-HE
 * @Time 2014-12-30
 * @Email KingKong@QQ.COM
 */
public class MainFragmentManager extends FragmentActivity {

    public static final String TAG = "MainFragmentManager";
    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    /**
     * 定义首页、分类、购物车、我的Fragment
     */
    private HomeFragment homeFragment;
    private OneTypeFragment typeFragment;
    private MineFragment mineFragment;
    private CartFragment cartFragment;

    /**
     * 定义首页、分类、购物车、我的tab的图标
     */
    private LinearLayout btnHomeID;
    private LinearLayout btnClassID;
    private LinearLayout btnCartID;
    private LinearLayout btnMineID;


    private ImageView image_home_home;
    private TextView tx_home_home;

    private ImageView image_home_class;
    private TextView tx_home_class;

    private ImageView image_home_cart;
    private TextView tx_home_cart;

    private ImageView image_home_user;
    private TextView tx_home_user;

    private MyShopApplication myApplication;

    /**
     * 对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);

        fragmentManager = getSupportFragmentManager();

        myApplication = (MyShopApplication) getApplicationContext();

        initViews();

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBoradcastReceiver();
        myApplication.getmSocket().connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
        myApplication.getmSocket().disconnect();
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constants.SHOW_CART_URL)) {
                CartIn();//显示购物车

            } else if (action.equals(Constants.SHOW_HOME_URL)) {
                HomeIn();//显示首页
            }
        }
    };

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constants.SHOW_CART_URL);
        myIntentFilter.addAction(Constants.SHOW_HOME_URL);
        registerReceiver(mBroadcastReceiver, myIntentFilter); //注册广播
    }


    /**
     * 隐藏所有的fragment
     *
     * @param transaction 用于对fragment进行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (typeFragment != null) {
            transaction.hide(typeFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
        if (cartFragment != null) {
            transaction.hide(cartFragment);
        }
    }

    /**
     * 初始化界面，并设置3个tab的监听
     */
    private void initViews() {
        // //////////////////// find View ////////////////////////////
        btnHomeID = (LinearLayout) this.findViewById(R.id.ly_home_home);
        btnClassID = (LinearLayout) this.findViewById(R.id.ly_home_class);
        btnCartID = (LinearLayout) this.findViewById(R.id.ly_home_cart);
        btnMineID = (LinearLayout) this.findViewById(R.id.ly_home_user);


        image_home_home = (ImageView) this.findViewById(R.id.image_home_home);
        tx_home_home = (TextView) this.findViewById(R.id.tx_home_home);

        image_home_class = (ImageView) this.findViewById(R.id.image_home_class);
        tx_home_class = (TextView) this.findViewById(R.id.tx_home_class);

        image_home_cart = (ImageView) this.findViewById(R.id.image_home_cart);
        tx_home_cart = (TextView) this.findViewById(R.id.tx_home_cart);

        image_home_user = (ImageView) this.findViewById(R.id.image_home_user);
        tx_home_user = (TextView) this.findViewById(R.id.tx_home_user);

        MyRadioButtonClickListener listener = new MyRadioButtonClickListener();
        btnHomeID.setOnClickListener(listener);
        btnClassID.setOnClickListener(listener);
        btnCartID.setOnClickListener(listener);
        btnMineID.setOnClickListener(listener);

        HomeIn();// 首次进入显示首页界面

        versionUpdate();//判断是否有新版本
    }

    //首页选中状态
    private void HomeIn_change_view() {
        image_home_home.setImageDrawable(getResources().getDrawable(R.mipmap.main_index_my_home_p));
        tx_home_home.setTextColor(Color.parseColor("#07ae98"));//选中

        image_home_class.setImageDrawable(getResources().getDrawable(R.mipmap.main_index_my_class_n));
        tx_home_class.setTextColor(Color.parseColor("#666666"));//未选中

        image_home_cart.setImageDrawable(getResources().getDrawable(R.mipmap.main_index_my_cart_n));
        tx_home_cart.setTextColor(Color.parseColor("#666666"));//未选中

        image_home_user.setImageDrawable(getResources().getDrawable(R.mipmap.main_index_my_mine_n));
        tx_home_user.setTextColor(Color.parseColor("#666666"));//未选中
    }

    //分类页选中状态
    private void TupeIn_change_view() {
        image_home_home.setImageDrawable(getResources().getDrawable(R.mipmap.main_index_my_home_n));
        tx_home_home.setTextColor(Color.parseColor("#666666"));//选中

        image_home_class.setImageDrawable(getResources().getDrawable(R.mipmap.main_index_my_class_p));
        tx_home_class.setTextColor(Color.parseColor("#07ae98"));//未选中

        image_home_cart.setImageDrawable(getResources().getDrawable(R.mipmap.main_index_my_cart_n));
        tx_home_cart.setTextColor(Color.parseColor("#666666"));//未选中

        image_home_user.setImageDrawable(getResources().getDrawable(R.mipmap.main_index_my_mine_n));
        tx_home_user.setTextColor(Color.parseColor("#666666"));//未选中
    }

    //分类页选中状态
    private void CartIn_change_view() {
        image_home_home.setImageDrawable(getResources().getDrawable(R.mipmap.main_index_my_home_n));
        tx_home_home.setTextColor(Color.parseColor("#666666"));//选中

        image_home_class.setImageDrawable(getResources().getDrawable(R.mipmap.main_index_my_class_n));
        tx_home_class.setTextColor(Color.parseColor("#666666"));//未选中

        image_home_cart.setImageDrawable(getResources().getDrawable(R.mipmap.main_index_my_cart_p));
        tx_home_cart.setTextColor(Color.parseColor("#07ae98"));//未选中

        image_home_user.setImageDrawable(getResources().getDrawable(R.mipmap.main_index_my_mine_n));
        tx_home_user.setTextColor(Color.parseColor("#666666"));//未选中
    }

    //分类页选中状态
    private void MineIn_change_view() {
        image_home_home.setImageDrawable(getResources().getDrawable(R.mipmap.main_index_my_home_n));
        tx_home_home.setTextColor(Color.parseColor("#666666"));//选中

        image_home_class.setImageDrawable(getResources().getDrawable(R.mipmap.main_index_my_class_n));
        tx_home_class.setTextColor(Color.parseColor("#666666"));//未选中

        image_home_cart.setImageDrawable(getResources().getDrawable(R.mipmap.main_index_my_cart_n));
        tx_home_cart.setTextColor(Color.parseColor("#666666"));//未选中

        image_home_user.setImageDrawable(getResources().getDrawable(R.mipmap.main_index_my_mine_p));
        tx_home_user.setTextColor(Color.parseColor("#07ae98"));//未选中
    }

    /**
     * 设置开启的tab首页页面
     */
    public void HomeIn() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            transaction.add(R.id.content, homeFragment);
        } else {
            transaction.show(homeFragment);
        }

        HomeIn_change_view();
        transaction.commitAllowingStateLoss();
    }

    /**
     * 设置开启的tab分类页面
     */
    public void TupeIn() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (typeFragment == null) {
            typeFragment = new OneTypeFragment();
            transaction.add(R.id.content, typeFragment);
        } else {
            transaction.show(typeFragment);
        }

        TupeIn_change_view();
        transaction.commitAllowingStateLoss();
    }

    /**
     * 设置开启的tab我的页面
     */
    public void MineIn() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (mineFragment == null) {
            mineFragment = new MineFragment();
            transaction.add(R.id.content, mineFragment);
        } else {
            mineFragment.IsCheckLogin();
            transaction.show(mineFragment);
        }

        MineIn_change_view();

        transaction.commitAllowingStateLoss();
    }

    /**
     * 设置开启的tab购物车页面
     */
    public void CartIn() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        if (cartFragment == null) {
            cartFragment = new CartFragment();
            transaction.add(R.id.content, cartFragment);
        } else {
            cartFragment.IsCheckLogin();
            transaction.show(cartFragment);
        }
        CartIn_change_view();
        transaction.commitAllowingStateLoss();
    }

    /**
     * 获取是否有最新版本
     */
    public void versionUpdate() {
        RemoteDataHandler.asyncDataStringGet(Constants.URL_VERSION_UPDATE, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {

                if (data != null) {
//                    LogUtil.e("MainFragmentManager", data.getJson());
                    if (data.getCode() == HttpStatus.SC_OK) {
                        String json = data.getJson();
                        try {
                            JSONObject obj = new JSONObject(json);
                            String objJson = obj.getString("version");
                            String urlJSON = obj.getString("url");
                            int isForceUp = obj.getInt("isForceUp");
                            String VersionName = SystemHelper.getAppVersionName(MainFragmentManager.this);
                            if (VersionName.equals(objJson) || VersionName.equals("")) {
//								Toast.makeText(MainActivity.this, "已经是最新版本", Toast.LENGTH_SHORT).show();;
                            } else {
                                //这里来检测版本是否需要更新
                                UpdateManager mUpdateManager = new UpdateManager(MainFragmentManager.this, urlJSON);
                                mUpdateManager.checkUpdateInfo();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    class MyRadioButtonClickListener implements View.OnClickListener {
        public void onClick(View v) {
            LinearLayout btn = (LinearLayout) v;
            switch (btn.getId()) {
                case R.id.ly_home_home:
                    HomeIn();
                    break;
                case R.id.ly_home_class:
                    TupeIn();
                    break;
                case R.id.ly_home_cart:
                    CartIn();
                    break;
                case R.id.ly_home_user:
                    MineIn();
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
//            System.exit(0);
        }
    }

}
