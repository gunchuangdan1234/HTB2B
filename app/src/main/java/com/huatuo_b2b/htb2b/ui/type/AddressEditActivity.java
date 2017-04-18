package com.huatuo_b2b.htb2b.ui.type;

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

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.InvoiceAddSpinnerAdapter;
import com.huatuo_b2b.htb2b.bean.AddressDetails;
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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 编辑收货地址界面
 *
 * @author KingKong·HE
 * @Time 2014年2月12日 下午1:47:27
 * @E-mail hjgang@bizpoer.com
 */
public class AddressEditActivity extends Activity implements OnClickListener {

    private String addressID;

    private MyShopApplication myApplication;

    private EditText editAddressName;

    private TextView editAddressInfo;

    private EditText editAddressMobPhone;

    private EditText editAddressTelPhone;

    private EditText editJieDaoAddress;

    private String city_id = "-1";

    private String area_id = "-1";

    private String[] addressINFO = new String[3];

    private MyAddress myAddressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_edit_view);

        myApplication = (MyShopApplication) AddressEditActivity.this.getApplication();

        addressID = AddressEditActivity.this.getIntent().getStringExtra("address_id");

        initViewID();//初始化注册加载控件ID

    }


    /**
     * 初始化注册加载控件ID
     */
    public void initViewID() {

        editAddressName = (EditText) findViewById(R.id.editAddressName);

        editAddressInfo = (TextView) findViewById(R.id.editAddressInfo);

        editAddressMobPhone = (EditText) findViewById(R.id.editAddressMobPhone);

        editAddressTelPhone = (EditText) findViewById(R.id.editAddressTelPhone);

        editJieDaoAddress = (EditText) findViewById(R.id.editJieDaoAddress);

        Button buttonSend = (Button) findViewById(R.id.buttonSend);

        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);

        myAddressDialog = new MyAddress(AddressEditActivity.this);

        loadingAddressDetailsData(addressID);//加载详情数据

        buttonSend.setOnClickListener(this);

        imageBack.setOnClickListener(this);

        editAddressInfo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingGetCityData(myAddressDialog.spinner_shengID, "0");
            }
        });


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
                        InvoiceAddSpinnerAdapter addSpinnerAdapter = new InvoiceAddSpinnerAdapter(AddressEditActivity.this);
                        addSpinnerAdapter.setDatas(CList);
                        view.setAdapter(addSpinnerAdapter);
                        addSpinnerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(AddressEditActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    ;
                }
            }
        });
    }

    /**
     * 地址编辑
     *
     * @param :key        当前登录令牌
     * @param :address_id 地址编号
     * @param :true_name  姓名
     * @param :area_id    地区编号
     * @param :city_id    城市编号
     * @param :area_info  地区信息，例：天津 天津市 红桥区
     * @param :address    地址信息，例：水游城8层
     * @param :tel_phone  电话号码
     * @param :mob_phone  手机
     */
    public void SendCityData(String true_name, String area_info, String address, String tel_phone, String mob_phone) {
        String url = Constants.URL_ADDRESS_EDIT;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("address_id", addressID);
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
                    if (json.equals("1")) {
                        Toast.makeText(AddressEditActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        ;
                        Intent mIntent = new Intent(AddressEditActivity.this, AddressListActivity.class);
                        setResult(Constants.ADD_ADDRESS_SUCC, mIntent);
                        AddressEditActivity.this.finish();
                    }
                    try {
                        JSONObject obj2 = new JSONObject(json);
                        String error = obj2.getString("error");
                        if (error != null) {
                            Toast.makeText(AddressEditActivity.this, error, Toast.LENGTH_SHORT).show();
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

    /**
     * 加载收货地址详情页面
     *
     * @param address_id
     */
    public void loadingAddressDetailsData(String address_id) {

        String url = Constants.URL_ADDRESS_DETAILS;

        HashMap<String, String> params = new HashMap<String, String>();

        params.put("key", myApplication.getLoginKey());

        params.put("address_id", address_id);

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    try {
                        JSONObject obj = new JSONObject(json);
                        String objJson = obj.getString("address_info");
                        AddressDetails addressDetailsList = AddressDetails.newInstanceDetails(objJson);

                        if (addressDetailsList != null) {
                            city_id = addressDetailsList.getCity_id() == null ? "" : addressDetailsList.getCity_id();
                            area_id = addressDetailsList.getArea_id() == null ? "" : addressDetailsList.getArea_id();
                            editAddressName.setText(addressDetailsList.getTrue_name() == null ? "" : addressDetailsList.getTrue_name());
                            ;
                            editAddressInfo.setText(addressDetailsList.getArea_info() == null ? "" : addressDetailsList.getArea_info());
                            editAddressMobPhone.setText(addressDetailsList.getMob_phone() == null ? "" : addressDetailsList.getMob_phone());
                            editAddressTelPhone.setText(addressDetailsList.getTel_phone() == null ? "" : addressDetailsList.getTel_phone());
                            editJieDaoAddress.setText(addressDetailsList.getAddress() == null ? "" : addressDetailsList.getAddress());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject obj2 = new JSONObject(json);
                        String error = obj2.getString("error");
                        if (error != null) {
                            Toast.makeText(AddressEditActivity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(AddressEditActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    ;
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSend:

                String true_name = editAddressName.getText().toString();//姓名
                String area_info = editAddressInfo.getText().toString();//地区信息，例：天津 天津市 红桥区
                String address = editJieDaoAddress.getText().toString();//地址信息，例：水游城8层
                String tel_phone = editAddressTelPhone.getText().toString();//电话号码
                String mob_phone = editAddressMobPhone.getText().toString();//手机

                if (city_id.equals("-1") || city_id.equals("") || city_id.equals("null") || city_id == null) {
                    Toast.makeText(AddressEditActivity.this, "城市信息不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }

                if (area_id.equals("-1") || area_id.equals("") || area_id.equals("null") || area_id == null) {
                    Toast.makeText(AddressEditActivity.this, "地区信息不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }

                if (area_info.equals("") || area_info.equals("null") || area_info == null) {
                    Toast.makeText(AddressEditActivity.this, "地址信息不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }
                if (address.equals("") || address.equals("null") || address == null) {
                    Toast.makeText(AddressEditActivity.this, "街道地址不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }
                if (true_name.equals("") || true_name.equals("null") || true_name == null) {
                    Toast.makeText(AddressEditActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }

                if (mob_phone.equals("") || mob_phone.equals("null") || mob_phone == null) {
                    Toast.makeText(AddressEditActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }

                if (tel_phone.equals("") || tel_phone.equals("null") || tel_phone == null) {
                    Toast.makeText(AddressEditActivity.this, "固定电话不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }

                SendCityData(true_name, area_info, address, tel_phone, mob_phone);

                break;

            case R.id.imageBack:

                finish();

                break;

            default:
                break;
        }
    }
}
