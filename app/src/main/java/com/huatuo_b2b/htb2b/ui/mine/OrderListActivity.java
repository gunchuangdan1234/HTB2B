package com.huatuo_b2b.htb2b.ui.mine;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.OrderGroupListViewAdapter;
import com.huatuo_b2b.htb2b.bean.OrderGroupList;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.custom.XListView;
import com.huatuo_b2b.htb2b.custom.XListView.IXListViewListener;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * 订单列表界面
 *
 * @author KingKong-HE
 * @Time 2015-1-27
 * @Email KingKong@QQ.COM
 */
public class OrderListActivity extends Activity implements OnClickListener, IXListViewListener {

    private MyShopApplication myApplication;

    private XListView listViewID;

    private Handler mXLHandler;

    public int pageno = 1;

    private ArrayList<OrderGroupList> orderLists;

    private OrderGroupListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_list_view);

        myApplication = (MyShopApplication) getApplicationContext();

        initViewID();//初始化注册控件ID
    }

    /**
     * 初始化注册控件ID
     */
    public void initViewID() {

        listViewID = (XListView) findViewById(R.id.listViewID);

        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);

        orderLists = new ArrayList<OrderGroupList>();

        adapter = new OrderGroupListViewAdapter(OrderListActivity.this);

        mXLHandler = new Handler();

        listViewID.setAdapter(adapter);

        listViewID.setXListViewListener(this);

        imageBack.setOnClickListener(this);

        loadingListData();//初始化加载数据
    }

    /**
     * 初始化加载数据
     */
    public void loadingListData() {

        //System.out.println("loadingListData @@@@@@@@@@@@@@@@@@@@@@\n");

        String url = Constants.URL_ORDER_LIST + "&curpage=" + pageno + "&page=" + Constants.PAGESIZE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String updataTime = sdf.format(new Date(System.currentTimeMillis()));
        listViewID.setRefreshTime(updataTime);

        //System.out.println("url 2 @@@@@@@@@@@@@@@@@@@@@@\n" + url);

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {


                listViewID.stopLoadMore();
                listViewID.stopRefresh();

                if (data.getCode() == HttpStatus.SC_OK) { //liubw
                    String json = data.getJson();

                    if (!data.isHasMore()) {
                        listViewID.setPullLoadEnable(false);
                    } else {
                        listViewID.setPullLoadEnable(true);
                    }
                    if (pageno == 1) {
                        orderLists.clear();
                    }
                    try {
                        JSONObject obj = new JSONObject(json);
                        String order_group_list = obj.getString("order_group_list");
                        ArrayList<OrderGroupList> groupList = OrderGroupList.newInstanceList(order_group_list);
                        orderLists.addAll(groupList);
                        adapter.setOrderLists(orderLists);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject obj2 = new JSONObject(json);
                        String error = obj2.getString("error");
                        if (error != null) {
                            Toast.makeText(OrderListActivity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(OrderListActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        registerBoradcastReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constants.PAYMENT_SUCCESS)) {
                pageno = 1;
                listViewID.setPullLoadEnable(true);
                loadingListData();//初始化加载我的信息
            }
        }
    };

    public void registerBoradcastReceiver() {

        System.out.println("registerBoradcastReceiver #################################");
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constants.PAYMENT_SUCCESS);
        registerReceiver(mBroadcastReceiver, myIntentFilter);  //注册广播       
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

    @Override
    public void onRefresh() {
        //下拉刷新
        mXLHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageno = 1;
                listViewID.setPullLoadEnable(true);
                loadingListData();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        //上拉加载
        mXLHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageno = pageno + 1;
                loadingListData();
            }
        }, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //requestCode标示请求的标示   resultCode表示有数据
//        if (requestCode == MainAcitvity.REQUSET && resultCode == RESULT_OK) {
//
//        }

        System.out.println("###################### onActivityResult:" + resultCode);

        finish();

    }
}
