package com.huatuo_b2b.htb2b.ui.type;

import java.util.ArrayList;
import java.util.HashMap;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.InvoiceAddSpinnerAdapter;
import com.huatuo_b2b.htb2b.bean.CityList;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.custom.MyAddress;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 新增收货地址
 *
 * @author KingKong-HE
 * @Time 2015-1-21
 * @Email KingKong@QQ.COM
 */
public class AddressADDActivity extends Activity implements OnClickListener {

    private MyShopApplication myApplication;

    private MyAddress myAddressDialog;

    private String[] addressINFO = new String[3];

    private String city_id = "-1";//city_id 城市编号(地址联动的第二级)

    private String area_id = "-1";//area_id 地区编号(地址联动的第三级)

    private TextView editAddressInfo;

    private EditText editAddressName, editJieDaoAddress, editAddressMobPhone, editAddressTelPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_add_view);

        myApplication = (MyShopApplication) getApplicationContext();

        initViewID();//初始化注册控件ID
    }

    /**
     * 初始化注册控件ID
     */
    public void initViewID() {

        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);

        editAddressInfo = (TextView) findViewById(R.id.editAddressInfo);

        editAddressName = (EditText) findViewById(R.id.editAddressName);

        editJieDaoAddress = (EditText) findViewById(R.id.editJieDaoAddress);

        editAddressMobPhone = (EditText) findViewById(R.id.editAddressMobPhone);

        editAddressTelPhone = (EditText) findViewById(R.id.editAddressTelPhone);

        Button buttonSend = (Button) findViewById(R.id.buttonSend);

        myAddressDialog = new MyAddress(AddressADDActivity.this);

        editAddressInfo.setOnClickListener(this);

        imageBack.setOnClickListener(this);

        buttonSend.setOnClickListener(this);

        myAddressDialog.spinner_shengID.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                CityList bean = (CityList) myAddressDialog.spinner_shengID.getItemAtPosition(arg2);
                loadingGetCityData(myAddressDialog.spinner_shiID, bean.getArea_id());
                addressINFO[0] = bean.getArea_name();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        myAddressDialog.spinner_shiID.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                CityList bean = (CityList) myAddressDialog.spinner_shiID.getItemAtPosition(arg2);
                loadingGetCityData(myAddressDialog.spinner_quID, bean.getArea_id());
                city_id = bean.getArea_id();
                addressINFO[1] = bean.getArea_name();
                addressINFO[2] = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        myAddressDialog.spinner_quID.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                CityList bean = (CityList) myAddressDialog.spinner_quID.getItemAtPosition(arg2);
                area_id = bean.getArea_id();
                addressINFO[2] = bean.getArea_name();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        myAddressDialog.btu_off.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myAddressDialog.dismiss();
            }
        });
        myAddressDialog.btu_on.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                myAddressDialog.dismiss();
                editAddressInfo.setText(addressINFO[0] + "\t" + addressINFO[1] + "\t" + addressINFO[2]);
            }
        });
    }


    /**
     * 初始化加载数据
     */
    public void loadingGetCityData(final Spinner view, String area_id) {
        String url = Constants.URL_GET_CITY;
        ;//index.php?act=member_buy&op=test
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("area_id", area_id);
        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                myAddressDialog.show();
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    try {
                        JSONObject objJSON = new JSONObject(json);
                        String area_list = objJSON.getString("area_list");
                        ArrayList<CityList> CList = new ArrayList<CityList>();
                        if (!area_list.equals("[]")) {
                            CList = CityList.newInstanceList(area_list);
                        }
                        InvoiceAddSpinnerAdapter addSpinnerAdapter = new InvoiceAddSpinnerAdapter(AddressADDActivity.this);
                        addSpinnerAdapter.setDatas(CList);
                        view.setAdapter(addSpinnerAdapter);
                        addSpinnerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//					Toast.makeText(AddressAddActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }

    /**
     * 地址添加
     *
     * @param key       当前登录令牌
     * @param true_name 姓名
     * @param city_id   城市编号(地址联动的第二级)
     * @param area_id   地区编号(地址联动的第三级)
     * @param area_info 地区信息，例：天津 天津市 红桥区
     * @param address   地址信息，例：水游城8层
     * @param tel_phone 电话号码
     * @param mob_phone 手机
     */
    public void SendCityData(String true_name, String area_info, String address, String tel_phone, String mob_phone) {
        String url = Constants.URL_ADDRESS_ADD;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("true_name", true_name);
        params.put("area_id", area_id);
        params.put("city_id", city_id);
        params.put("area_info", area_info);
        params.put("address", address);
        params.put("tel_phone", tel_phone);
        params.put("mob_phone", mob_phone);

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    if (json.contains("address_id")) {
                        Toast.makeText(AddressADDActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        ;

                        Intent mIntent = new Intent(AddressADDActivity.this, AddressListActivity.class);
                        setResult(Constants.ADD_ADDRESS_SUCC, mIntent);
                        AddressADDActivity.this.finish();
                    }
                    try {
                        JSONObject obj2 = new JSONObject(json);
                        String error = obj2.getString("error");
                        if (error != null) {
                            Toast.makeText(AddressADDActivity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(AddressADDActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();
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
            case R.id.editAddressInfo:

                loadingGetCityData(myAddressDialog.spinner_shengID, "0");

                break;
            case R.id.buttonSend:

                String true_name = editAddressName.getText().toString();//姓名
                String area_info = editAddressInfo.getText().toString();//地区信息，例：天津 天津市 红桥区
                String address = editJieDaoAddress.getText().toString();//地址信息，例：水游城8层
                String tel_phone = editAddressTelPhone.getText().toString();//电话号码
                String mob_phone = editAddressMobPhone.getText().toString();//手机

                if (city_id.equals("-1") || city_id.equals("") || city_id.equals("null") || city_id == null) {
                    Toast.makeText(AddressADDActivity.this, "城市信息不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }

                if (area_id.equals("-1") || area_id.equals("") || area_id.equals("null") || area_id == null) {
                    Toast.makeText(AddressADDActivity.this, "地区信息不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }

                if (area_info.equals("") || area_info.equals("null") || area_info == null) {
                    Toast.makeText(AddressADDActivity.this, "地址信息不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }
                if (address.equals("") || address.equals("null") || address == null) {
                    Toast.makeText(AddressADDActivity.this, "街道地址不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }
                if (true_name.equals("") || true_name.equals("null") || true_name == null) {
                    Toast.makeText(AddressADDActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }

                if ((mob_phone.equals("") || mob_phone.equals("null") || mob_phone == null)) {
                    Toast.makeText(AddressADDActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }

//			if(tel_phone.equals("") || tel_phone.equals("null") ||tel_phone == null){
//				Toast.makeText(AddressADDActivity.this, "固定电话不能为空", Toast.LENGTH_SHORT).show();;
//				return ;
//			}

                SendCityData(true_name, area_info, address, tel_phone, mob_phone);//新增收货地址

                break;
            default:
                break;
        }
    }

}
