package com.huatuo_b2b.htb2b.ui.type;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.AddressListViewAdapter;
import com.huatuo_b2b.htb2b.bean.AddressList;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 收货地址管理界面
 *
 * @author KingKong-HE
 * @Time 2015-1-21
 * @Email KingKong@QQ.COM
 */
public class AddressListActivity extends Activity implements OnClickListener {

    private MyShopApplication myApplication;

    private AddressListViewAdapter adapter;

    private ListView listViewID;

    private String addressFlag;//1是提交订单跳转过去的 0或者没有是 个人中心

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_list_view);

        myApplication = (MyShopApplication) getApplicationContext();

        addressFlag = getIntent().getStringExtra("addressFlag");

        initViewID();//初始化注册加载控件ID
    }

    /**
     * 初始化注册加载控件ID
     */
    public void initViewID() {
        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);

        TextView textAddID = (TextView) findViewById(R.id.textAddID);

        listViewID = (ListView) findViewById(R.id.listViewID);

        adapter = new AddressListViewAdapter(AddressListActivity.this);

        listViewID.setAdapter(adapter);

        imageBack.setOnClickListener(this);

        textAddID.setOnClickListener(this);

        loadingAddressListData();//加载收货地址列表

        listViewID.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                AddressList bean = (AddressList) listViewID.getItemAtPosition(arg2);

                if (bean != null) {

                    if (addressFlag != null && !addressFlag.equals("") && !addressFlag.equals("null") && !addressFlag.equals("0")) {
                        Intent mIntent = new Intent(AddressListActivity.this, BuyStep1Activity.class);
                        mIntent.putExtra("address_id", bean.getAddress_id());
                        mIntent.putExtra("city_id", bean.getCity_id() == null ? "" : bean.getCity_id());
                        mIntent.putExtra("area_id", bean.getArea_id() == null ? "" : bean.getArea_id());
                        mIntent.putExtra("addressInFo", bean.getArea_info() == null ? "" : bean.getArea_info());
                        mIntent.putExtra("address", bean.getAddress() == null ? "" : bean.getAddress());
                        mIntent.putExtra("tureName", bean.getTrue_name() == null ? "" : bean.getTrue_name());
                        mIntent.putExtra("mobPhone", bean.getMob_phone() == null ? "" : bean.getMob_phone());

                        setResult(Constants.SELECT_ADDRESS, mIntent);
                        AddressListActivity.this.finish();
                    }

                }
            }
        });

        listViewID.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {

                final AddressList bean = (AddressList) listViewID.getItemAtPosition(arg2);

                if (bean != null) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddressListActivity.this);
                    builder.setTitle("功能选择")
                            .setMessage("您确定操作的功能？");
                    builder.setPositiveButton("编辑", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intent = new Intent(AddressListActivity.this, AddressEditActivity.class);
                            intent.putExtra("address_id", bean.getAddress_id());
                            startActivityForResult(intent, 3);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setNeutralButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deteleAddressData(bean.getAddress_id());//删除
                        }
                    }).create().show();
                }

                return false;
            }
        });
    }

    /**
     * 加载收货地址列表
     */
    public void loadingAddressListData() {
        String url = Constants.URL_ADDRESS_LIST;//index.php?act=member_buy&op=test
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    try {
                        JSONObject obj = new JSONObject(json);
                        String objJson = obj.getString("address_list");
                        ArrayList<AddressList> addressList = AddressList.newInstanceList(objJson);
                        adapter.setAddressLists(addressList);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject obj2 = new JSONObject(json);
                        String error = obj2.getString("error");
                        if (error != null) {
                            Toast.makeText(AddressListActivity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//					Toast.makeText(BuyAddressListActivity.this, getString(R.string.datas_loading_fail_prompt), Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }

    /**
     * 删除收货地址
     *
     * @param addressID
     */
    public void deteleAddressData(String addressID) {
        String url = Constants.URL_ADDRESS_DETELE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("address_id", addressID);
        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    if (json.equals("1")) {
                        Toast.makeText(AddressListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        ;
                        loadingAddressListData();//刷新数据
                    }
                    try {
                        JSONObject obj2 = new JSONObject(json);
                        String error = obj2.getString("error");
                        if (error != null) {
                            Toast.makeText(AddressListActivity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//					Toast.makeText(AddressEditActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Constants.ADD_ADDRESS_SUCC) {
            loadingAddressListData();//刷新列表
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:

                finish();

                break;

            case R.id.textAddID:

                Intent addIntent = new Intent(AddressListActivity.this, AddressADDActivity.class);
                startActivityForResult(addIntent, 3);

                break;

            default:
                break;
        }
    }
}
