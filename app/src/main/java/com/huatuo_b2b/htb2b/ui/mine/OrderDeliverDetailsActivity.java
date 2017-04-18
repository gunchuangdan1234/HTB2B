package com.huatuo_b2b.htb2b.ui.mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.OrderDeliverListViewAdapter;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.custom.XListView;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 查看物流界面
 *
 * @author KingKong-HE
 * @Time 2015-1-27
 * @Email KingKong@QQ.COM
 */
public class OrderDeliverDetailsActivity extends Activity implements OnClickListener {

    private MyShopApplication myApplication;

    private TextView deliverCodeID, deliverNameID;

    private OrderDeliverListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_deliver_view);

        myApplication = (MyShopApplication) getApplicationContext();

        initViewID();// 初始化注册控件ID
    }

    /**
     * 初始化注册控件ID
     */
    public void initViewID() {

        String order_id = getIntent().getStringExtra("order_id");

        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);

        XListView listViewID = (XListView) findViewById(R.id.listViewID);

        deliverCodeID = (TextView) findViewById(R.id.deliverCodeID);

        deliverNameID = (TextView) findViewById(R.id.deliverNameID);

        adapter = new OrderDeliverListViewAdapter(OrderDeliverDetailsActivity.this);

        listViewID.setAdapter(adapter);

        listViewID.setPullLoadEnable(false);

        listViewID.setPullRefreshEnable(false);

        imageBack.setOnClickListener(this);

        loadingDeliverData(order_id);

    }

    /**
     * 加载物流信息数据
     *
     * @param ：key     当前登录令牌
     * @param order_id 订单编号
     */
    public void loadingDeliverData(String order_id) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("order_id", order_id);
        RemoteDataHandler.asyncLoginPostDataString(Constants.URL_QUERY_DELIVER, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String express_name = jsonObject.getString("express_name");
                        String shipping_code = jsonObject.getString("shipping_code");
                        String deliver_info = jsonObject.getString("deliver_info");
                        deliverNameID.setText("物流公司：" + (express_name == null ? "" : express_name));
                        deliverCodeID.setText("物流编号：" + (shipping_code == null ? "" : shipping_code));
                        adapter.setjsonArray(new JSONArray(deliver_info));
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        JSONObject obj2 = new JSONObject(json);
                        String error = obj2.getString("error");
                        if (error != null) {
                            Toast.makeText(OrderDeliverDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(OrderDeliverDetailsActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    ;
                }
            }
        });

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
