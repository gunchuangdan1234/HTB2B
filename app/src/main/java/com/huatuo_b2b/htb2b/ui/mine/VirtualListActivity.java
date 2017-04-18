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
import com.huatuo_b2b.htb2b.adapter.VirtualOrderListViewAdapter;
import com.huatuo_b2b.htb2b.bean.VirtualList;
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
 * 虚拟订单列表界面
 *
 * @author KingKong·HE
 * @Time 2014年7月15日 上午10:25:29
 */
public class VirtualListActivity extends Activity implements OnClickListener, IXListViewListener {
    private XListView listViewID;
    private VirtualOrderListViewAdapter adapter;
    public int pageno = 1;
    private MyShopApplication myApplication;
    private ArrayList<VirtualList> lists;
    private Handler mXLHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.virtual_list_view);
        myApplication = (MyShopApplication) VirtualListActivity.this.getApplication();

        mXLHandler = new Handler();

        initViewID();//初始化注册控件ID
    }

    /**
     * 初始化注册控件ID
     */
    public void initViewID() {

        listViewID = (XListView) findViewById(R.id.listViewID);

        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);

        lists = new ArrayList<VirtualList>();

        adapter = new VirtualOrderListViewAdapter(this);

        listViewID.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        listViewID.setXListViewListener(this);

        loadingListData();

        imageBack.setOnClickListener(this);

//		listViewOrder.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
//					long arg3) {
//				VirtualList bean =(VirtualList) listViewOrder.getItemAtPosition(arg2);
//				Intent intent = new Intent(VirtualListActivity.this,GoodsDetailsActivity.class);
//				intent.putExtra("goods_id", bean.getGoods_id());
//				VirtualListActivity.this.startActivity(intent);
//			}
//		});
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
            if (action.equals(Constants.VPAYMENT_SUCCESS)) {
                pageno = 1;
                listViewID.setPullLoadEnable(true);
                loadingListData();//初始化加载我的信息
            }
        }
    };

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constants.VPAYMENT_SUCCESS);
        registerReceiver(mBroadcastReceiver, myIntentFilter);  //注册广播       
    }

    /**
     * 加载虚拟商品列表
     */
    public void loadingListData() {
        String url = Constants.URL_MEMBER_VR_ORDER + "&curpage=" + pageno + "&page=" + Constants.PAGESIZE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String updataTime = sdf.format(new Date(System.currentTimeMillis()));
        listViewID.setRefreshTime(updataTime);


        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {

                listViewID.stopLoadMore();
                listViewID.stopRefresh();

                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();

                    if (!data.isHasMore()) {
                        listViewID.setPullLoadEnable(false);
                    } else {
                        listViewID.setPullLoadEnable(true);
                    }
                    if (pageno == 1) {
                        lists.clear();
                    }

                    try {
                        JSONObject obj = new JSONObject(json);
                        String order_group_list = obj.getString("order_list");
                        ArrayList<VirtualList> virtualLists = VirtualList.newInstanceList(order_group_list);
                        lists.addAll(virtualLists);
                        adapter.setVirtualLists(lists);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject obj2 = new JSONObject(json);
                        String error = obj2.getString("error");
                        if (error != null) {
                            Toast.makeText(VirtualListActivity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(VirtualListActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    ;
                }
            }
        });
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
