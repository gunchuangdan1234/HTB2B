package com.huatuo_b2b.htb2b.ui.type;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.AddressSetAdapter;
import com.huatuo_b2b.htb2b.bean.Area;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.LogUtil;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.ResponseData;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jinguo on 2016/4/18.
 */
public class AddressSetActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "AddressSetActivity";

    private ListView lv_1, lv_2, lv_3;


    private AddressSetAdapter addressSetAdapter1, addressSetAdapter2, addressSetAdapter3;

    private ArrayList<Area> areas1, areas2, areas3;

    private String str1, str2, str3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_address_set);

        initViewID();
        requestAddress(1, "0");
    }

    private void initViewID() {

        findViewById(R.id.imageBack).setOnClickListener(this);

        lv_1 = (ListView) findViewById(R.id.lv_1);
        lv_2 = (ListView) findViewById(R.id.lv_2);
        lv_3 = (ListView) findViewById(R.id.lv_3);

        lv_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Area area = areas1.get(position);

                String areaId = area.getArea_id();

                str1 = area.getArea_name();
                requestAddress(2, areaId);
            }
        });
        lv_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Area area = areas2.get(position);

                String areaId = area.getArea_id();
                str2 = area.getArea_name();
                requestAddress(3, areaId);
            }
        });
        lv_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Area area = areas3.get(position);

                String areaId = area.getArea_id();
                str3 = area.getArea_name();

                Intent intent = new Intent();
                intent.putExtra("address_set", str1 + "^t" + str2 + "^t" + str3);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }


    /**
     * 获取地址
     *
     * @param area_id 用户名
     */
    public void requestAddress(final int type, String area_id) {
//        index.php?act=area&op=area_list&area_id=3

        String url = Constants.URL_ADDRESS_GET + area_id;

        RemoteDataHandler.asyncDataStringGet(url, new RemoteDataHandler.Callback() {


            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    LogUtil.e(TAG, json);
                    try {

                        JSONObject obj = new JSONObject(json);

                        String array = obj.getString("area_list");


                        if (!array.equals("[]")) {


                            if (type == 1) {

                                areas1 = Area.newInstanceList(array);
                                addressSetAdapter1 = new AddressSetAdapter(AddressSetActivity.this);
                                setData(areas1, addressSetAdapter1, lv_1);
                            } else if (type == 2) {

                                areas2 = Area.newInstanceList(array);
                                addressSetAdapter2 = new AddressSetAdapter(AddressSetActivity.this);
                                setData(areas2, addressSetAdapter2, lv_2);
                            } else if (type == 3) {

                                areas3 = Area.newInstanceList(array);
                                addressSetAdapter3 = new AddressSetAdapter(AddressSetActivity.this);
                                setData(areas3, addressSetAdapter3, lv_3);
                            }


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }


    private void setData(ArrayList<Area> areas, AddressSetAdapter addressSetAdapter, ListView lv) {

        if (areas != null && areas.size() > 0) {


            addressSetAdapter.setAreas(areas);

            lv.setAdapter(addressSetAdapter);

            addressSetAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())

        {
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
