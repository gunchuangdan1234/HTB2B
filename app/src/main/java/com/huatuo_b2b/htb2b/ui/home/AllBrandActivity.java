package com.huatuo_b2b.htb2b.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.AllBrandGridViewAdapter;
import com.huatuo_b2b.htb2b.bean.AllBrandList;
import com.huatuo_b2b.htb2b.bean.CommendList;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.LogUtil;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.ResponseData;
import com.huatuo_b2b.htb2b.ui.type.GoodsListFragmentManager;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jinguo on 2016/4/8.
 */
public class AllBrandActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private MyShopApplication myApplication;

    private GridView gv_all_brand;

    private AllBrandGridViewAdapter allBrandGridViewAdapter;


    private Button btn_xiyao;
    private Button btn_zhongyao;
    private Button btn_yiliaoqixie;
    private Button btn_baojian;
    private Button btn_qita;

    private ArrayList<AllBrandList> allBrandLists;

    //西药  1000
    //中药 1024
    //器械 1031
    //保键  1043
    //其他  0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyShopApplication) getApplicationContext();
        setContentView(R.layout.act_all_brand);
        initViewID();
        getAllBrand("1000");
    }


    /**
     * 初始化注册控件ID
     */
    public void initViewID() {


        gv_all_brand = (GridView) findViewById(R.id.gv_all_brand);

        allBrandGridViewAdapter = new AllBrandGridViewAdapter(this);

        gv_all_brand.setAdapter(allBrandGridViewAdapter);
        gv_all_brand.setFocusable(false);

        gv_all_brand.setOnItemClickListener(this);

        //分类选择块

        btn_xiyao = (Button) findViewById(R.id.btn_xiyao);
        btn_zhongyao = (Button) findViewById(R.id.btn_zhongyao);
        btn_yiliaoqixie = (Button) findViewById(R.id.btn_yiliaoqixie);
        btn_baojian = (Button) findViewById(R.id.btn_baojian);
        btn_qita = (Button) findViewById(R.id.btn_qita);

        btn_xiyao.setOnClickListener(this);
        btn_zhongyao.setOnClickListener(this);
        btn_yiliaoqixie.setOnClickListener(this);
        btn_baojian.setOnClickListener(this);
        btn_qita.setOnClickListener(this);

        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    /**
     * 初始化加载数据
     */
    public void getAllBrand(String class_id) {
        String url = Constants.URL_GET_ALL_BRAND;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("class_id", class_id);

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) { //liubw
                    String json = data.getJson();
                    LogUtil.e("AllbrandActivity====json==", json);

                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        String JosnObj = jsonObject
                                .getString("brand_list");
                        //所有品牌
                        allBrandLists = AllBrandList.newInstanceList(JosnObj);
                        allBrandGridViewAdapter.setAllBrandLists(allBrandLists);
                        allBrandGridViewAdapter.notifyDataSetChanged();


                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imageBack:
                finish();
                break;

            case R.id.btn_xiyao:
                setClassSelectedBg(1);
                getAllBrand("1000");
                break;

            case R.id.btn_zhongyao:
                setClassSelectedBg(2);
                getAllBrand("1024");
                break;
            case R.id.btn_yiliaoqixie:
                setClassSelectedBg(3);
                getAllBrand("1031");
                break;

            case R.id.btn_baojian:
                setClassSelectedBg(4);
                getAllBrand("1043");
                break;
            case R.id.btn_qita:
                setClassSelectedBg(5);
                getAllBrand("0");
                break;


        }
    }


    private void setClassSelectedBg(int index) {

        switch (index) {


            case 1:

                btn_xiyao.setBackgroundColor(getResources().getColor(R.color.class_bg_all_brand_selected));
                btn_xiyao.setTextColor(getResources().getColor(R.color.white));

                btn_zhongyao.setBackgroundColor(getResources().getColor(R.color.white));
                btn_zhongyao.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));

                btn_yiliaoqixie.setBackgroundColor(getResources().getColor(R.color.white));
                btn_yiliaoqixie.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));

                btn_baojian.setBackgroundColor(getResources().getColor(R.color.white));
                btn_baojian.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));


                btn_qita.setBackgroundColor(getResources().getColor(R.color.white));
                btn_qita.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));


                break;
            case 2:

                btn_xiyao.setBackgroundColor(getResources().getColor(R.color.white));
                btn_xiyao.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));

                btn_zhongyao.setBackgroundColor(getResources().getColor(R.color.class_bg_all_brand_selected));
                btn_zhongyao.setTextColor(getResources().getColor(R.color.white));

                btn_yiliaoqixie.setBackgroundColor(getResources().getColor(R.color.white));
                btn_yiliaoqixie.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));

                btn_baojian.setBackgroundColor(getResources().getColor(R.color.white));
                btn_baojian.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));


                btn_qita.setBackgroundColor(getResources().getColor(R.color.white));
                btn_qita.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));

                break;

            case 3:
                btn_xiyao.setBackgroundColor(getResources().getColor(R.color.white));
                btn_xiyao.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));

                btn_zhongyao.setBackgroundColor(getResources().getColor(R.color.white));
                btn_zhongyao.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));

                btn_yiliaoqixie.setBackgroundColor(getResources().getColor(R.color.class_bg_all_brand_selected));
                btn_yiliaoqixie.setTextColor(getResources().getColor(R.color.white));

                btn_baojian.setBackgroundColor(getResources().getColor(R.color.white));
                btn_baojian.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));


                btn_qita.setBackgroundColor(getResources().getColor(R.color.white));
                btn_qita.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));
                break;

            case 4:
                btn_xiyao.setBackgroundColor(getResources().getColor(R.color.white));
                btn_xiyao.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));

                btn_zhongyao.setBackgroundColor(getResources().getColor(R.color.white));
                btn_zhongyao.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));

                btn_yiliaoqixie.setBackgroundColor(getResources().getColor(R.color.white));
                btn_yiliaoqixie.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));

                btn_baojian.setBackgroundColor(getResources().getColor(R.color.class_bg_all_brand_selected));
                btn_baojian.setTextColor(getResources().getColor(R.color.white));


                btn_qita.setBackgroundColor(getResources().getColor(R.color.white));
                btn_qita.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));
                break;

            case 5:

                btn_xiyao.setBackgroundColor(getResources().getColor(R.color.white));
                btn_xiyao.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));

                btn_zhongyao.setBackgroundColor(getResources().getColor(R.color.white));
                btn_zhongyao.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));

                btn_yiliaoqixie.setBackgroundColor(getResources().getColor(R.color.white));
                btn_yiliaoqixie.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));

                btn_baojian.setBackgroundColor(getResources().getColor(R.color.white));
                btn_baojian.setTextColor(getResources().getColor(R.color.class_text_color_all_brand));


                btn_qita.setBackgroundColor(getResources().getColor(R.color.class_bg_all_brand_selected));
                btn_qita.setTextColor(getResources().getColor(R.color.white));
                break;


        }
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AllBrandList allBrandList = allBrandLists.get(position);
        String data = allBrandList.getBrand_name();
        Intent intent = new Intent(this, GoodsListFragmentManager.class);
        intent.putExtra("keyword", data);
        intent.putExtra("gc_name", data);
        startActivity(intent);
    }
}


//    {
//        "brand_list": [
//        {
//            "brand_initial": "D",
//                "class_id": "1000",
//                "brand_apply": "1",
//                "brand_id": "174",
//                "store_id": "0",
//                "brand_sort": "0",
//                "brand_recommend": "0",
//                "brand_class": "西药",
//                "show_type": "0",
//                "brand_pic": "http:\/\/120.24.60.165\/data\/upload\/shop\/brand\/05067093822168908_sm.jpg",
//                "brand_name": "大连辉瑞"
//        },
//        {
//            "brand_initial": "L",
//                "class_id": "1000",
//                "brand_apply": "1",
//                "brand_id": "175",
//                "store_id": "0",
//                "brand_sort": "0",
//                "brand_recommend": "0",
//                "brand_class": "西药",
//                "show_type": "0",
//                "brand_pic": "http:\/\/120.24.60.165\/data\/upload\/shop\/brand\/05067094197936379_sm.jpg",
//                "brand_name": "江苏联环"
//        },
//        {
//            "brand_initial": "X",
//                "class_id": "1000",
//                "brand_apply": "1",
//                "brand_id": "176",
//                "store_id": "0",
//                "brand_sort": "0",
//                "brand_recommend": "0",
//                "brand_class": "西药",
//                "show_type": "0",
//                "brand_pic": "http:\/\/120.24.60.165\/data\/upload\/shop\/brand\/05067095651088972_sm.jpg",
//                "brand_name": "先声药业"
//        },
//        {
//            "brand_initial": "H",
//                "class_id": "1000",
//                "brand_apply": "1",
//                "brand_id": "177",
//                "store_id": "0",
//                "brand_sort": "0",
//                "brand_recommend": "0",
//                "brand_class": "西药",
//                "show_type": "0",
//                "brand_pic": "http:\/\/120.24.60.165\/data\/upload\/shop\/brand\/05067095137584045_sm.jpg",
//                "brand_name": "华兰生物"
//        },
//        {
//            "brand_initial": "A",
//                "class_id": "1000",
//                "brand_apply": "1",
//                "brand_id": "189",
//                "store_id": "0",
//                "brand_sort": "0",
//                "brand_recommend": "0",
//                "brand_class": "西药",
//                "show_type": "0",
//                "brand_pic": "http:\/\/120.24.60.165\/data\/upload\/shop\/brand\/05078992242111568_sm.jpg",
//                "brand_name": "阿斯利康"
//        },
//        {
//            "brand_initial": "H",
//                "class_id": "1000",
//                "brand_apply": "1",
//                "brand_id": "192",
//                "store_id": "0",
//                "brand_sort": "0",
//                "brand_recommend": "0",
//                "brand_class": "西药",
//                "show_type": "0",
//                "brand_pic": "http:\/\/120.24.60.165\/data\/upload\/shop\/common\/default_brand_image.gif",
//                "brand_name": "安徽环球"
//        },
//        {
//            "brand_initial": "A",
//                "class_id": "1000",
//                "brand_apply": "1",
//                "brand_id": "195",
//                "store_id": "0",
//                "brand_sort": "0",
//                "brand_recommend": "0",
//                "brand_class": "西药",
//                "show_type": "0",
//                "brand_pic": "http:\/\/120.24.60.165\/data\/upload\/shop\/brand\/05078992863185594_sm.jpg",
//                "brand_name": "安士制药(中山)"
//        },
//        {
//            "brand_initial": "J",
//                "class_id": "1000",
//                "brand_apply": "1",
//                "brand_id": "196",
//                "store_id": "0",
//                "brand_sort": "0",
//                "brand_recommend": "0",
//                "brand_class": "西药",
//                "show_type": "0",
//                "brand_pic": "http:\/\/120.24.60.165\/data\/upload\/shop\/common\/default_brand_image.gif",
//                "brand_name": "安阳九州"
//        },
//        {
//            "brand_initial": "A",
//                "class_id": "1000",
//                "brand_apply": "1",
//                "brand_id": "197",
//                "store_id": "0",
//                "brand_sort": "0",
//                "brand_recommend": "0",
//                "brand_class": "西药",
//                "show_type": "0",
//                "brand_pic": "http:\/\/120.24.60.165\/data\/upload\/shop\/common\/default_brand_image.gif",
//                "brand_name": "奥克特法玛"
//        },
//        {
//            "brand_initial": "B",
//                "class_id": "1000",
//                "brand_apply": "1",
//                "brand_id": "198",
//                "store_id": "0",
//                "brand_sort": "0",
//                "brand_recommend": "0",
//                "brand_class": "西药",
//                "show_type": "0",
//                "brand_pic": "http:\/\/120.24.60.165\/data\/upload\/shop\/brand\/05078993053733521_sm.jpg",
//                "brand_name": "白云山"
//        },
//        {
//            "brand_initial": "B",
//                "class_id": "1000",
//                "brand_apply": "1",
//                "brand_id": "199",
//                "store_id": "0",
//                "brand_sort": "0",
//                "brand_recommend": "0",
//                "brand_class": "西药",
//                "show_type": "0",
//                "brand_pic": "http:\/\/120.24.60.165\/data\/upload\/shop\/brand\/05079160636478315_sm.jpg",
//                "brand_name": "拜耳医药"
//        },
//        {
//            "brand_initial": "F",
//                "class_id": "1000",
//                "brand_apply": "1",
//                "brand_id": "203",
//                "store_id": "0",
//                "brand_sort": "0",
//                "brand_recommend": "0",
//                "brand_class": "西药",
//                "show_type": "0",
//                "brand_pic": "http:\/\/120.24.60.165\/data\/upload\/shop\/common\/default_brand_image.gif",
//                "brand_name": "费森尤斯卡比"
//        },
//        {
//            "brand_initial": "H",
//                "class_id": "1000",
//                "brand_apply": "1",
//                "brand_id": "204",
//                "store_id": "0",
//                "brand_sort": "0",
//                "brand_recommend": "0",
//                "brand_class": "西药",
//                "show_type": "0",
//                "brand_pic": "http:\/\/120.24.60.165\/data\/upload\/shop\/brand\/05079160453683572_sm.jpg",
//                "brand_name": "北京韩美"
//        },
//        {
//            "brand_initial": "H",
//                "class_id": "1000",
//                "brand_apply": "1",
//                "brand_id": "205",
//                "store_id": "0",
//                "brand_sort": "0",
//                "brand_recommend": "0",
//                "brand_class": "西药",
//                "show_type": "0",
//                "brand_pic": "http:\/\/120.24.60.165\/data\/upload\/shop\/common\/default_brand_image.gif",
//                "brand_name": "北京华素"
//        },
//        {
//            "brand_initia
//
//        }

