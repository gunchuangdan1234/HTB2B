package com.huatuo_b2b.htb2b.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.HomeGoodsMyGridViewListAdapter;
import com.huatuo_b2b.htb2b.bean.AdvertList;
import com.huatuo_b2b.htb2b.bean.Deadline;
import com.huatuo_b2b.htb2b.bean.Discount;
import com.huatuo_b2b.htb2b.bean.Home1Data;
import com.huatuo_b2b.htb2b.bean.Home2Data;
import com.huatuo_b2b.htb2b.bean.Home6Data;
import com.huatuo_b2b.htb2b.bean.Home7Data;
import com.huatuo_b2b.htb2b.bean.Home8Data;
import com.huatuo_b2b.htb2b.bean.HomeGoodsList;
import com.huatuo_b2b.htb2b.common.AnimateFirstDisplayListener;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.common.SystemHelper;
import com.huatuo_b2b.htb2b.custom.MyGridView;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.ResponseData;
import com.huatuo_b2b.htb2b.pulltorefresh.library.PullToRefreshBase;
import com.huatuo_b2b.htb2b.pulltorefresh.library.PullToRefreshScrollView;
import com.huatuo_b2b.htb2b.ui.type.GoodsDetailsActivity;
import com.huatuo_b2b.htb2b.ui.type.GoodsListFragmentManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by jinguo on 2016/4/7.
 */
public class SubjectActivity extends Activity implements View.OnClickListener {

    private PullToRefreshScrollView mPullRefreshScrollView;
    private LinearLayout HomeView;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    private DisplayImageOptions options = SystemHelper.getDisplayImageOptions();

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private MyShopApplication myApplication;


    //当前系统时间
    private long servertime;

    private String data;

    private TextView tv_inter_all_brand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_subject);
        myApplication = (MyShopApplication) getApplicationContext();

        data = getIntent().getStringExtra("data");
        initViewID(data);
    }

    /**
     * 初始化注册控件ID
     */
    public void initViewID(final String data) {

        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(this);

        mPullRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pull_refresh_scrollview);
        HomeView = (LinearLayout) findViewById(R.id.homeViewID);
        tv_inter_all_brand = (TextView) findViewById(R.id.tv_inter_all_brand);
        //下拉刷新监听
        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                loadUIData(data);
            }
        });


        loadUIData(data);

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

    /**
     * 初始化加载数据
     */
    public void loadUIData(String path) {

        System.out.println("@@@@@@@@@@@@@@@@@@@path:" + path);
        RemoteDataHandler.asyncDataStringGet(path, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {

                mPullRefreshScrollView.onRefreshComplete();//加载完成下拉控件取消显示

                if (data.getCode() == HttpStatus.SC_OK) {

                    HomeView.removeAllViews(); //删除homeview所有View

                    try {
                        String json = data.getJson();
                        Log.e("json", json);

                        JSONArray arr = new JSONArray(json);


                        int size = null == arr ? 0 : arr.length();

                        for (int i = 0; i < size; i++) {

                            JSONObject obj = arr.getJSONObject(i);

                            JSONObject JsonObj = new JSONObject(obj.toString());


//                            if (!JsonObj.isNull("home5")) {
//                                String home5Json = JsonObj.getString("home5");
//
//                                Home5Data bean = Home5Data.newInstanceList(home5Json);
//
//                                //                                View home1View =  getLayoutInflater().inflate(R.layout.tab_home_item_home1, null);
//
//                                View home1View =  getLayoutInflater().inflate(R.layout.view_promotion, null);
//
////                                TextView textView = (TextView) home1View.findViewById(R.id.TextViewHome1Title01);
//
//                                ImageView imageView = (ImageView) home1View.findViewById(R.id.ImageViewHome1Imagepic01);
//
////                                if (!bean.getTitle().equals("") && !bean.getTitle().equals("null") && bean.getTitle() != null) {
////                                    textView.setVisibility(View.VISIBLE);
////                                    textView.setText(bean.getTitle());
////                                } else {
////                                    textView.setVisibility(View.GONE);
////                                }
//
//                                imageLoader.displayImage(bean.getImage(), imageView, options, animateFirstListener);
//
//                                OnImageViewClick(imageView, bean.getType(), bean.getData());
//
//                                HomeView.addView(home1View);


//                            } else
                            if (!JsonObj.isNull("servertime")) {

                                servertime = JsonObj.optLong("servertime");

                            } else if (!JsonObj.isNull("home1")) {//模型板块布局A

                                String home1Json = JsonObj.getString("home1");

                                Home1Data bean = Home1Data.newInstanceList(home1Json);


                                View home1View = getLayoutInflater().inflate(R.layout.tab_home_item_home1, null);

//                                View home5View =  getLayoutInflater().inflate(R.layout.view_hot_sell, null);

                                //title
                                View ll_home_item_1_title = home1View.findViewById(R.id.ll_home_item_1_title);
                                ImageView iv_Home1Title01 = (ImageView) home1View.findViewById(R.id.iv_Home1Title01);
                                TextView textView = (TextView) home1View.findViewById(R.id.TextViewHome1Title01);

                                //content
                                ImageView imageView = (ImageView) home1View.findViewById(R.id.ImageViewHome1Imagepic01);

                                if (!bean.getTitle().equals("") && !bean.getTitle().equals("null") && bean.getTitle() != null) {
                                    ll_home_item_1_title.setVisibility(View.VISIBLE);
                                    textView.setText(bean.getTitle());

                                    if ("新品推荐".equals(bean.getTitle())) {
                                        iv_Home1Title01.setImageResource(R.mipmap.ic_home_new);

                                    } else if ("限时促销".equals(bean.getTitle())) {

                                        iv_Home1Title01.setImageResource(R.mipmap.ic_home_time);
                                    } else if ("推荐品牌".equals(bean.getTitle())) {

                                        iv_Home1Title01.setImageResource(R.mipmap.ic_home_brand);
                                    }
                                } else {
                                    ll_home_item_1_title.setVisibility(View.GONE);
                                }

                                imageLoader.displayImage(bean.getImage(), imageView, options, animateFirstListener);

                                OnImageViewClick(imageView, bean.getType(), bean.getData());
                                HomeView.addView(home1View);

                            } else if (!JsonObj.isNull("home2")) {//模型板块布局

                                String home2Json = JsonObj.getString("home2");

                                Home2Data bean = Home2Data.newInstanceDetelis(home2Json);

                                View home2View = getLayoutInflater().inflate(R.layout.tab_home_item_home2_left, null);

                                TextView textView = (TextView) home2View.findViewById(R.id.TextViewTitle);

                                ImageView imageViewSquare = (ImageView) home2View.findViewById(R.id.ImageViewSquare);
                                ImageView imageViewRectangle1 = (ImageView) home2View.findViewById(R.id.ImageViewRectangle1);
                                ImageView imageViewRectangle2 = (ImageView) home2View.findViewById(R.id.ImageViewRectangle2);

                                imageLoader.displayImage(bean.getSquare_image(), imageViewSquare, options, animateFirstListener);
                                imageLoader.displayImage(bean.getRectangle1_image(), imageViewRectangle1, options, animateFirstListener);
                                imageLoader.displayImage(bean.getRectangle2_image(), imageViewRectangle2, options, animateFirstListener);

                                OnImageViewClick(imageViewSquare, bean.getSquare_type(), bean.getSquare_data());
                                OnImageViewClick(imageViewRectangle1, bean.getRectangle1_type(), bean.getRectangle1_data());
                                OnImageViewClick(imageViewRectangle2, bean.getRectangle2_type(), bean.getRectangle2_data());

                                if (!bean.getTitle().equals("") && !bean.getTitle().equals("null") && bean.getTitle() != null) {
                                    textView.setVisibility(View.VISIBLE);
                                    textView.setText(bean.getTitle());
                                } else {
                                    textView.setVisibility(View.GONE);
                                }

                                HomeView.addView(home2View);
                            } else if (!JsonObj.isNull("home4")) {//模型板块布局D

                                String home2Json = JsonObj.getString("home4");

                                Home2Data bean = Home2Data.newInstanceDetelis(home2Json);

                                View home4View = getLayoutInflater().inflate(R.layout.tab_home_item_home2_rehit, null);

                                TextView textView = (TextView) home4View.findViewById(R.id.TextViewTitle);

                                ImageView imageViewSquare = (ImageView) home4View.findViewById(R.id.ImageViewSquare);
                                ImageView imageViewRectangle1 = (ImageView) home4View.findViewById(R.id.ImageViewRectangle1);
                                ImageView imageViewRectangle2 = (ImageView) home4View.findViewById(R.id.ImageViewRectangle2);

                                imageLoader.displayImage(bean.getSquare_image(), imageViewSquare, options, animateFirstListener);
                                imageLoader.displayImage(bean.getRectangle1_image(), imageViewRectangle1, options, animateFirstListener);
                                imageLoader.displayImage(bean.getRectangle2_image(), imageViewRectangle2, options, animateFirstListener);

                                OnImageViewClick(imageViewSquare, bean.getSquare_type(), bean.getSquare_data());
                                OnImageViewClick(imageViewRectangle1, bean.getRectangle1_type(), bean.getRectangle1_data());
                                OnImageViewClick(imageViewRectangle2, bean.getRectangle2_type(), bean.getRectangle2_data());

                                if (!bean.getTitle().equals("") && !bean.getTitle().equals("null") && bean.getTitle() != null) {
                                    textView.setVisibility(View.VISIBLE);
                                    textView.setText(bean.getTitle());
                                } else {
                                    textView.setVisibility(View.GONE);
                                }

                                HomeView.addView(home4View);
                            }

//                            else if (!JsonObj.isNull("home3")) {//模型板块布局C
//
//                                String home3Json = JsonObj.getString("home3");
//
//                                Home3Data bean = Home3Data.newInstanceDetelis(home3Json);
//
//                                ArrayList<Home3Data> home3Datas = Home3Data.newInstanceList(bean.getItem());
//
////                                View home3View =  getLayoutInflater().inflate(R.layout.tab_home_item_home3, null);
//
//                                View home3View =  getLayoutInflater().inflate(R.layout.view_brand_area, null);
//
////                                TextView textView = (TextView) home3View.findViewById(R.id.TextViewTitle);
//
//                                MyGridView gridview = (MyGridView) home3View.findViewById(R.id.gridview);
//
//                                gridview.setFocusable(false);
//
//                                HomeActivityMyGridViewListAdapter adapter = new HomeActivityMyGridViewListAdapter(getActivity());
//
//                                adapter.setHome3Datas(home3Datas);
//
//                                gridview.setAdapter(adapter);
//
//                                adapter.notifyDataSetChanged();
//
////                                if (!bean.getTitle().equals("") && !bean.getTitle().equals("null") && bean.getTitle() != null) {
////                                    textView.setVisibility(View.VISIBLE);
////                                    textView.setText(bean.getTitle());
////                                } else {
////                                    textView.setVisibility(View.GONE);
////                                }
//
//                                HomeView.addView(home3View);
//
//                            }

                            else if (!JsonObj.isNull("home6")) {

                                String home6Json = JsonObj.getString("home6");

                                View home6View = getLayoutInflater().inflate(R.layout.tab_home_item_home6, null);


                                Home6Data bean = Home6Data.newInstanceList(home6Json);

                                ImageView iv_left = (ImageView) home6View.findViewById(R.id.iv_left);
                                ImageView iv_right = (ImageView) home6View.findViewById(R.id.iv_right);

                                imageLoader.displayImage(bean.getRectangle1_image(), iv_left, options, animateFirstListener);
                                imageLoader.displayImage(bean.getRectangle2_image(), iv_right, options, animateFirstListener);

                                HomeView.addView(home6View);

                            } else if (!JsonObj.isNull("home7")) {

                                String home7Json = JsonObj.getString("home7");


                                JSONObject home7obj = new JSONObject(home7Json);
                                String item = home7obj.getString("item");

                                String title = home7obj.getString("title");


                                View home7View = getLayoutInflater().inflate(R.layout.tab_home_item_home7, null);

                                ImageView iv_1 = (ImageView) home7View.findViewById(R.id.iv_1);
                                ImageView iv_2 = (ImageView) home7View.findViewById(R.id.iv_2);
                                ImageView iv_3 = (ImageView) home7View.findViewById(R.id.iv_3);


                                TextView tv_1 = (TextView) home7View.findViewById(R.id.tv_1);
                                TextView tv_2 = (TextView) home7View.findViewById(R.id.tv_2);
                                TextView tv_3 = (TextView) home7View.findViewById(R.id.tv_3);

                                TextView tv_price_1 = (TextView) home7View.findViewById(R.id.tv_price_1);
                                TextView tv_price_2 = (TextView) home7View.findViewById(R.id.tv_price_2);
                                TextView tv_price_3 = (TextView) home7View.findViewById(R.id.tv_price_3);


                                if (!item.equals("[]")) {

                                    ArrayList<Home7Data> home7Datas = Home7Data.newInstanceList(item);

                                    if (home7Datas.size() > 0 && home7Datas != null) {


                                        for (int j = 0; j < home7Datas.size(); j++) {

                                            Home7Data bean = home7Datas.get(j);

                                            if (j == 0) {
                                                imageLoader.displayImage(bean.getGoods_image(), iv_1, options, animateFirstListener);
                                                tv_1.setText(bean.getGoods_name());
                                                tv_price_1.setText(bean.getGoods_promotion_price());
                                            } else if (j == 1) {
                                                imageLoader.displayImage(bean.getGoods_image(), iv_2, options, animateFirstListener);
                                                tv_2.setText(bean.getGoods_name());
                                                tv_price_2.setText(bean.getGoods_promotion_price());
                                            } else if (j == 2) {
                                                imageLoader.displayImage(bean.getGoods_image(), iv_3, options, animateFirstListener);
                                                tv_3.setText(bean.getGoods_name());
                                                tv_price_3.setText(bean.getGoods_promotion_price());

                                            }


                                        }
                                    }


                                }


                                HomeView.addView(home7View);


//


                            } else if (!JsonObj.isNull("adv_list")) {//广告板块解析

                                String advertJson = JsonObj.getString("adv_list");

                                JSONObject itemObj = new JSONObject(advertJson);

                                String item = itemObj.getString("item");

                                if (!item.equals("[]")) {

                                    ArrayList<AdvertList> advertList = AdvertList.newInstanceList(item);

                                    if (advertList.size() > 0 && advertList != null) {
//                                        initHeadImage(advertList);
                                    }

                                }
                            } else if (!JsonObj.isNull("goods")) {//商品瀑布流板块解析

                                String goodsJson = JsonObj.getString("goods");

                                // System.out.println("!!!!!!!!!!!!!!!!@@@@@@@@@@@@@@@@@  goodsJson:" + goodsJson);

//title":"热卖单品","item":[{"
//goods_image":"http:\/\/101.201.197.50\/data\/upload\/shop\/store\/goods\/1\/1_05059127226029300_240.png","
//goods_name":"注射用青霉素钠 20151111 盒","
// goods_promotion_price":"25.00","goods_id":"100047"},{"goods_image":"http:\/\/101.201.197.50\/data\/upload\/shop\/store\/goods\/1\/1_05059127226029300_240.png","goods_name":"注射用青霉素钠 20151111 件（30盒）","goods_promotion_price":"25.00","goods_id":"100046"},{"goods_image":"http:\/\/101.201.197.50\/data\/upload\/shop\/store\/goods\/1\/1_05059127226029300_240.png","goods_name":"注射用青霉素钠 20150101 盒","goods_promotion_price":"25.00","goods_id":"100045"},{"goods_image":"http:\/\/101.201.197.50\/data\/upload\/shop\/store\/goods\/1\/1_05059127226029300_240.png","goods_name":"注射用青霉素钠 20150101 件（30盒）","goods_promotion_price":"25.00","goods_id":"100044"}]}
//                                01-12 04:25:59.006      860-942/com.huatuo_b2b.htb2b D/ImageLoader﹕
//                                "title":"热卖单品",
//                                "item":[{
//                                "goods_image":"http:_05054964552914219_240.jpg",
//                                "goods_name":"盐酸曲美他嗪片",
//                                "goods_promotion_price":"14.08",
//                                "goods_id":"100034"}


                                JSONObject itemObj = new JSONObject(goodsJson);

                                String item = itemObj.getString("item");

                                String title = itemObj.getString("title");

                                if (!item.equals("[]")) {

                                    //获取首页商品瀑布流数据 liubw
                                    ArrayList<HomeGoodsList> goodsList = HomeGoodsList.newInstanceList(item);

                                    View goodsView = getLayoutInflater().inflate(R.layout.tab_home_item_goods, null);


                                    View rl_title = goodsView.findViewById(R.id.rl_title);
                                    TextView textView = (TextView) goodsView.findViewById(R.id.TextViewTitle);


                                    MyGridView gridview = (MyGridView) goodsView.findViewById(R.id.gridview);

                                    gridview.setFocusable(false);

//                                    System.out.println("########################  this.member_role:" + myApplication.getMember_role());
//                                    System.out.println("########################  this.getLoginKey:" + myApplication.getLoginKey());


                                    HomeGoodsMyGridViewListAdapter adapter =
                                            new HomeGoodsMyGridViewListAdapter(SubjectActivity.this,
                                                    myApplication.getMember_role(),
                                                    myApplication.getLoginKey());
                                    adapter.setType(1);
                                    adapter.setHomeGoodsList(goodsList);

                                    gridview.setAdapter(adapter);

                                    adapter.notifyDataSetChanged();

                                    if (!title.equals("") && !title.equals("null") && title != null) {
                                        rl_title.setVisibility(View.VISIBLE);
                                        textView.setText(title);
                                    } else {
                                        rl_title.setVisibility(View.GONE);
                                    }

                                    HomeView.addView(goodsView);

                                }
                            } else if (!JsonObj.isNull("deadline")) {//限时促销


                                String deadlineJson = JsonObj.getString("deadline");


                                Deadline bean = Deadline.newInstanceList(deadlineJson);

                                String str = bean.getDeadline();

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

                                long millionSeconds = 0;
                                try {
                                    millionSeconds = sdf.parse(str).getTime();//毫秒
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                View tab_home_item_deadline = getLayoutInflater().inflate(R.layout.tab_home_item_deadline, null);

                                //title
                                View ll_home_deadline_title = tab_home_item_deadline.findViewById(R.id.ll_home_deadline_title);
                                ImageView iv_deadlineTitle = (ImageView) ll_home_deadline_title.findViewById(R.id.iv_deadlineTitle);
                                TextView tv_deadlineTitle = (TextView) ll_home_deadline_title.findViewById(R.id.tv_deadlineTitle);

                                if (!bean.getTitle().equals("") && !bean.getTitle().equals("null") && bean.getTitle() != null) {
                                    ll_home_deadline_title.setVisibility(View.VISIBLE);
                                    tv_deadlineTitle.setText(bean.getTitle());

                                    if ("新品推荐".equals(bean.getTitle())) {
                                        iv_deadlineTitle.setImageResource(R.mipmap.ic_home_new);

                                    } else if ("限时折扣".equals(bean.getTitle())) {

                                        iv_deadlineTitle.setImageResource(R.mipmap.ic_home_time);
                                    } else if ("推荐品牌".equals(bean.getTitle())) {

                                        iv_deadlineTitle.setImageResource(R.mipmap.ic_home_brand);
                                    }
                                } else {
                                    tv_deadlineTitle.setVisibility(View.GONE);
                                }

//图片背景
                                ImageView iv_bg = (ImageView) tab_home_item_deadline.findViewById(R.id.iv_bg);
                                imageLoader.displayImage(bean.getImage(), iv_bg, options, animateFirstListener);

//计数器
                                CustomDigitalClock remainTime = (CustomDigitalClock) tab_home_item_deadline.findViewById(R.id.remainTime);


//                                remainTime.setServerTime(servertime);
//                                remainTime.setEndTime(millionSeconds - servertime);
                                remainTime.setEndTime(millionSeconds);

                                remainTime.setClockListener(new CustomDigitalClock.ClockListener() { // register the clock's listener

                                    @Override
                                    public void timeEnd() {
                                        // The clock time is ended.
                                    }

                                    @Override
                                    public void remainFiveMinutes() {
                                        // The clock time is remain five minutes.
                                    }
                                });

                                HomeView.addView(tab_home_item_deadline);

                            } else if (!JsonObj.isNull("discount")) {

                                String discountJson = JsonObj.getString("discount");


                                JSONObject discountObj = new JSONObject(discountJson);

                                String discount = discountObj.optString("discount");


                                int discountInt = Integer.parseInt(discount);


                                String disArrStr = discountObj.getString("item");

                                if (!disArrStr.equals("[]")) {

                                    JSONArray discountArr = new JSONArray(disArrStr);


                                    int discountSize = null == discountArr ? 0 : discountArr.length();

                                    for (int d = 0; d < discountSize; d++) {

                                        JSONObject disObj = discountArr.getJSONObject(d);

                                        String temp = disObj.toString();

                                        Discount bean = Discount.newInstanceList(temp);

                                        View discountView = getLayoutInflater().inflate(R.layout.tab_home_item_discount, null);


                                        TextView tv_factory = (TextView) discountView.findViewById(R.id.tv_factory);

                                        tv_factory.setText(bean.getProduct_company() == null ? "" : bean.getProduct_company());

                                        ImageView iv_discount = (ImageView) discountView.findViewById(R.id.iv_discount);

                                        TextView tv_title = (TextView) discountView.findViewById(R.id.tv_title);

                                        tv_title.setText(bean.getGoods_name() == null ? "" : bean.getGoods_name());

                                        TextView tv_drug_spec = (TextView) discountView.findViewById(R.id.tv_drug_spec);
                                        tv_drug_spec.setText(bean.getDrug_spec() == null ? "" : bean.getDrug_spec());


                                        //现价
                                        TextView tv_goods_price = (TextView) discountView.findViewById(R.id.tv_goods_price);

//                                        float goodsPrice = Float.parseFloat(bean.getGoods_promotion_price()) * discountInt / 100;

                                        tv_goods_price.setText("￥" + bean.getGoods_price());
                                        tv_goods_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                                        //折扣
                                        ProgressBar pb_price = (ProgressBar) discountView.findViewById(R.id.pb_price);

                                        pb_price.setProgress(discountInt);


                                        TextView tv_goods_promotion_price = (TextView) discountView.findViewById(R.id.tv_goods_promotion_price);
                                        tv_goods_promotion_price.setText("￥" + bean.getGoods_promotion_price());


                                        Button btn_buy = (Button) discountView.findViewById(R.id.btn_buy);

                                        OnImageViewClick(btn_buy, "goods", bean.getGoods_id());
                                        imageLoader.displayImage(bean.getGoods_image(), iv_discount, options, animateFirstListener);

                                        HomeView.addView(discountView);

                                    }
                                }


                            } else if (!JsonObj.isNull("home8")) {

                                String home8Json = JsonObj.getString("home8");

                                View home8View = getLayoutInflater().inflate(R.layout.tab_home_item_home8, null);


                                Home8Data bean = Home8Data.newInstanceList(home8Json);

                                ImageView iv_home8_1 = (ImageView) home8View.findViewById(R.id.iv_home8_1);
                                ImageView iv_home8_2 = (ImageView) home8View.findViewById(R.id.iv_home8_2);
                                ImageView iv_home8_3 = (ImageView) home8View.findViewById(R.id.iv_home8_3);
                                ImageView iv_home8_4 = (ImageView) home8View.findViewById(R.id.iv_home8_4);

                                imageLoader.displayImage(bean.getRectangle1_image(), iv_home8_1, options, animateFirstListener);
                                imageLoader.displayImage(bean.getRectangle2_image(), iv_home8_2, options, animateFirstListener);
                                imageLoader.displayImage(bean.getRectangle3_image(), iv_home8_3, options, animateFirstListener);
                                imageLoader.displayImage(bean.getRectangle4_image(), iv_home8_4, options, animateFirstListener);


                                //图片点击事件
                                OnImageViewClick(iv_home8_1, bean.getRectangle1_type(), bean.getRectangle1_data());
                                OnImageViewClick(iv_home8_2, bean.getRectangle2_type(), bean.getRectangle2_data());
                                OnImageViewClick(iv_home8_3, bean.getRectangle3_type(), bean.getRectangle3_data());
                                OnImageViewClick(iv_home8_4, bean.getRectangle4_type(), bean.getRectangle4_data());


                                tv_inter_all_brand.setVisibility(View.VISIBLE);
                                tv_inter_all_brand.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(SubjectActivity.this, AllBrandActivity.class);
                                        startActivity(intent);
                                    }
                                });


                                HomeView.addView(home8View);


                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
//							Toast.makeText(HomeActivity.this, getString(R.string.datas_loading_fail_prompt),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private float downNub;//记录按下时的距离

    public void OnImageViewClick(View view, final String type, final String data) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //  触摸时按下
                    downNub = event.getX();
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    // 触摸时移动
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //  触摸时抬起
                    if (downNub == event.getX()) {
                        if (type.equals("keyword")) {//搜索关键字
                            Intent intent = new Intent(SubjectActivity.this, GoodsListFragmentManager.class);
                            intent.putExtra("keyword", data);
                            intent.putExtra("gc_name", data);
                            startActivity(intent);
                        } else if (type.equals("special")) {//专题编号
                            Intent intent = new Intent(SubjectActivity.this, SubjectWebActivity.class);
//                            intent.putExtra("data", Constants.URL_SPECIAL + "&special_id=" + data + "&type=html");
                            intent.putExtra("data", Constants.URL_SPECIAL + "&special_id=" + data);
                            startActivity(intent);
                        } else if (type.equals("goods")) {//商品编号
                            Intent intent = new Intent(SubjectActivity.this, GoodsDetailsActivity.class);
                            intent.putExtra("goods_id", data);
                            startActivity(intent);
                        } else if (type.equals("url")) {//地址
                            Intent intent = new Intent(SubjectActivity.this, SubjectWebActivity.class);
                            intent.putExtra("data", data);
                            startActivity(intent);
                        }
                    }
                }
                return true;
            }
        });
    }

}
