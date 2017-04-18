package com.huatuo_b2b.htb2b.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.HomeGoodsMyGridViewListAdapter;
import com.huatuo_b2b.htb2b.bean.AdvertList;
import com.huatuo_b2b.htb2b.bean.Deadline;
import com.huatuo_b2b.htb2b.bean.Home1Data;
import com.huatuo_b2b.htb2b.bean.Home2Data;
import com.huatuo_b2b.htb2b.bean.Home6Data;
import com.huatuo_b2b.htb2b.bean.Home7Data;
import com.huatuo_b2b.htb2b.bean.Home8Data;
import com.huatuo_b2b.htb2b.bean.HomeGoodsList;
import com.huatuo_b2b.htb2b.bean.OneType;
import com.huatuo_b2b.htb2b.common.AnimateFirstDisplayListener;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.common.SystemHelper;
import com.huatuo_b2b.htb2b.common.UTILS;
import com.huatuo_b2b.htb2b.custom.MyGridView;
import com.huatuo_b2b.htb2b.custom.ViewFlipperScrollView;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;
import com.huatuo_b2b.htb2b.pulltorefresh.library.PullToRefreshBase;
import com.huatuo_b2b.htb2b.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.huatuo_b2b.htb2b.pulltorefresh.library.PullToRefreshScrollView;
import com.huatuo_b2b.htb2b.ui.type.GoodsDetailsActivity;
import com.huatuo_b2b.htb2b.ui.type.GoodsListFragmentManager;
import com.huatuo_b2b.htb2b.ui.type.TwoTypeActivity;
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
import java.util.Date;


/**
 * Created by admin on 15/12/30.
 */
public class HomeFragment extends Fragment implements OnGestureListener, OnTouchListener, OnClickListener {
    private PullToRefreshScrollView mPullRefreshScrollView;

    private MyShopApplication myApplication;

    private ViewFlipper viewflipper;

    private LinearLayout dian;

    private boolean showNext = true;

    private int currentPage = 0;

    private final int SHOW_NEXT = 0011;

    private float downNub;//记录按下时的距离

    private LinearLayout HomeView;

    private static final int FLING_MIN_DISTANCE = 50;

    private static final int FLING_MIN_VELOCITY = 0;

    private GestureDetector mGestureDetector;

    private ViewFlipperScrollView myScrollView;

    private ArrayList<ImageView> viewList = new ArrayList<ImageView>();

    private Animation left_in, left_out, right_in, right_out;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    private DisplayImageOptions options = SystemHelper.getDisplayImageOptions();

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    //分类接口数据
    private ArrayList<OneType> typeList;

    //当前系统时间
    private long servertime;

    //限时抢购是否已经结束
    private boolean deadLineIsEnd;


    private int index;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewLayout = inflater.inflate(R.layout.main_home_view, container, false);

        myApplication = (MyShopApplication) getActivity().getApplicationContext();

        initViewID(viewLayout);//注册控件ID
        //加载分类信息
        loadingTypeData();
        return viewLayout;
    }


    /**
     * 初始化加载数据
     */
    public void loadingTypeData() {

        String url = Constants.URL_GOODSCLASS;

        RemoteDataHandler.asyncDataStringGet(url, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {

                mPullRefreshScrollView.onRefreshComplete();//加载完成下拉控件取消显示

                if (data.getCode() == HttpStatus.SC_OK) {


                    String json = data.getJson();


                        try {

                            JSONObject obj = new JSONObject(json);

                            String array = obj.getString("class_list");

                            typeList = OneType.newInstanceList(array);

                        } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//					Toast.makeText(TypeAcitivity.this, getString(R.string.datas_loading_fail_prompt), Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }

/*    @Override
    public void onStart(){
        super.onStart();

        System.out.println("onStart   89345923495923757239573475938475937459349759347593857983459783459734");
    }


    @Override
    public void onResume(){
        super.onResume();

        System.out.println("onResume   89345923495923757239573475938475937459349759347593857983459783459734");
    }*/

    /**
     * 初始化注册控件ID
     */
    public void initViewID(View view) {

        mPullRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);

        viewflipper = (ViewFlipper) view.findViewById(R.id.viewflipper);

        dian = (LinearLayout) view.findViewById(R.id.dian);

        myScrollView = (ViewFlipperScrollView) view.findViewById(R.id.viewFlipperScrollViewID);

        HomeView = (LinearLayout) view.findViewById(R.id.homeViewID);

        LinearLayout textHomeSearch = (LinearLayout) view.findViewById(R.id.textHomeSearch);

        Button barcodeID = (Button) view.findViewById(R.id.barcodeID);

        left_in = AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_in);
        left_out = AnimationUtils.loadAnimation(getActivity(), R.anim.push_left_out);
        right_in = AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_in);
        right_out = AnimationUtils.loadAnimation(getActivity(), R.anim.push_right_out);

        //加载分类控件
        view.findViewById(R.id.iv_category_1).setOnClickListener(this);
        view.findViewById(R.id.iv_category_2).setOnClickListener(this);
        view.findViewById(R.id.iv_category_3).setOnClickListener(this);
        view.findViewById(R.id.iv_category_4).setOnClickListener(this);
        view.findViewById(R.id.iv_category_5).setOnClickListener(this);
//        view.findViewById(R.id.iv_category_6).setOnClickListener(this);


        //下拉刷新监听
        mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {


                loadUIData();
            }
        });

        textHomeSearch.setOnClickListener(this);

        barcodeID.setOnClickListener(this);

        loadUIData();
    }

    /**
     * 初始化加载数据
     */
    public void loadUIData() {

        index = 0;
        System.out.println("@@@@@@@@@@@@@@@@@@@Constants.URL_HOME:" + Constants.URL_HOME);
        RemoteDataHandler.asyncDataStringGet(Constants.URL_HOME, new Callback() {
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
//                                //                                View home1View = getActivity().getLayoutInflater().inflate(R.layout.tab_home_item_home1, null);
//
//                                View home1View = getActivity().getLayoutInflater().inflate(R.layout.view_promotion, null);
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


                                View home1View = getActivity().getLayoutInflater().inflate(R.layout.tab_home_item_home1, null);

//                                View home5View = getActivity().getLayoutInflater().inflate(R.layout.view_hot_sell, null);

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

                                View home2View = getActivity().getLayoutInflater().inflate(R.layout.tab_home_item_home2_left, null);

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

                                View home4View = getActivity().getLayoutInflater().inflate(R.layout.tab_home_item_home2_rehit, null);

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
////                                View home3View = getActivity().getLayoutInflater().inflate(R.layout.tab_home_item_home3, null);
//
//                                View home3View = getActivity().getLayoutInflater().inflate(R.layout.view_brand_area, null);
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

                                View home6View = getActivity().getLayoutInflater().inflate(R.layout.tab_home_item_home6, null);


                                Home6Data bean = Home6Data.newInstanceList(home6Json);

                                ImageView iv_left = (ImageView) home6View.findViewById(R.id.iv_left);
                                ImageView iv_right = (ImageView) home6View.findViewById(R.id.iv_right);

                                imageLoader.displayImage(bean.getRectangle1_image(), iv_left, options, animateFirstListener);
                                imageLoader.displayImage(bean.getRectangle2_image(), iv_right, options, animateFirstListener);

                                OnImageViewClick(iv_left, "goods", bean.getRectangle1_data());
                                OnImageViewClick(iv_right, "goods", bean.getRectangle2_data());
                                HomeView.addView(home6View);

                            } else if (!JsonObj.isNull("home7")) {

                                String home7Json = JsonObj.getString("home7");


                                JSONObject home7obj = new JSONObject(home7Json);
                                String item = home7obj.getString("item");

                                String title = home7obj.getString("title");


                                View home7View = getActivity().getLayoutInflater().inflate(R.layout.tab_home_item_home7, null);

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

                                    if (home7Datas != null && home7Datas.size() > 0) {


                                        for (int j = 0; j < home7Datas.size(); j++) {

                                            Home7Data bean = home7Datas.get(j);

                                            if (j == 0) {
                                                imageLoader.displayImage(bean.getGoods_image(), iv_1, options, animateFirstListener);

                                                OnImageViewClick(iv_1, "goods", bean.getGoods_id());


                                                tv_1.setText(bean.getGoods_name());

                                                if (myApplication.getLoginKey() == null || myApplication.getLoginKey().equals("")) {//价格登录后可见
                                                    tv_price_1.setText("价格登录后可见");
                                                } else {
                                                    if (myApplication.getMember_role().equals("0")) {//显示正常价格   liubw
                                                        tv_price_1.setText("价格认证后可见");
                                                    } else {//价格认证后可见
                                                        tv_price_1.setText("￥" + bean.getGoods_promotion_price());
                                                    }
                                                }


                                            } else if (j == 1) {
                                                imageLoader.displayImage(bean.getGoods_image(), iv_2, options, animateFirstListener);

                                                OnImageViewClick(iv_2, "goods", bean.getGoods_id());

                                                tv_2.setText(bean.getGoods_name());


                                                if (myApplication.getLoginKey() == null || myApplication.getLoginKey().equals("")) {//价格登录后可见
                                                    tv_price_2.setText("价格登录后可见");
                                                } else {
                                                    if (myApplication.getMember_role().equals("0")) {//显示正常价格   liubw
                                                        tv_price_2.setText("价格认证后可见");
                                                    } else {//价格认证后可见
                                                        tv_price_2.setText("￥" + bean.getGoods_promotion_price());
                                                    }
                                                }
                                            } else if (j == 2) {
                                                imageLoader.displayImage(bean.getGoods_image(), iv_3, options, animateFirstListener);

                                                OnImageViewClick(iv_3, "goods", bean.getGoods_id());

                                                tv_3.setText(bean.getGoods_name());
                                                if (myApplication.getLoginKey() == null || myApplication.getLoginKey().equals("")) {//价格登录后可见
                                                    tv_price_3.setText("价格登录后可见");
                                                } else {
                                                    if (myApplication.getMember_role().equals("0")) {//显示正常价格   liubw
                                                        tv_price_3.setText("价格认证后可见");
                                                    } else {//价格认证后可见
                                                        tv_price_3.setText("￥" + bean.getGoods_promotion_price());
                                                    }
                                                }
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
                                        initHeadImage(advertList);
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

                                    View goodsView = getActivity().getLayoutInflater().inflate(R.layout.tab_home_item_goods, null);

                                    TextView textView = (TextView) goodsView.findViewById(R.id.TextViewTitle);

                                    MyGridView gridview = (MyGridView) goodsView.findViewById(R.id.gridview);

                                    gridview.setFocusable(false);

//                                    System.out.println("########################  this.member_role:" + myApplication.getMember_role());
//                                    System.out.println("########################  this.getLoginKey:" + myApplication.getLoginKey());


                                    HomeGoodsMyGridViewListAdapter adapter =
                                            new HomeGoodsMyGridViewListAdapter(getActivity(),
                                                    myApplication.getMember_role(),
                                                    myApplication.getLoginKey());

                                    adapter.setHomeGoodsList(goodsList);

                                    gridview.setAdapter(adapter);

                                    adapter.notifyDataSetChanged();

                                    if (!title.equals("") && !title.equals("null") && title != null) {
                                        textView.setVisibility(View.VISIBLE);
                                        textView.setText(title);
                                    } else {
                                        textView.setVisibility(View.GONE);
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

                                View tab_home_item_deadline = getActivity().getLayoutInflater().inflate(R.layout.tab_home_item_deadline, null);

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

                                long currentTime = System.currentTimeMillis();

                                long distanceTime = millionSeconds - currentTime;
                                if (distanceTime <= 0) {
                                    deadLineIsEnd = true;
                                }
                                //图片点击事件
//                                if(deadLineIsEnd){
//                                    Toast.makeText(getActivity(),"当前活动已结束！",Toast.LENGTH_SHORT).show();
//                                }else {
                                OnImageViewClick(iv_bg, bean.getType(), bean.getData(), deadLineIsEnd);
//                                }
//计数器
                                CustomDigitalClock remainTime = (CustomDigitalClock) tab_home_item_deadline.findViewById(R.id.remainTime);


//                                remainTime.setServerTime(servertime);
                                remainTime.setEndTime(millionSeconds);

                                remainTime.setClockListener(new CustomDigitalClock.ClockListener() { // register the clock's listener

                                    @Override
                                    public void timeEnd() {
                                        deadLineIsEnd = true;
                                    }

                                    @Override
                                    public void remainFiveMinutes() {
                                        // The clock time is remain five minutes.
                                    }
                                });

                                HomeView.addView(tab_home_item_deadline);

                            } else if (!JsonObj.isNull("home8")) {
                                index++;
                                String home8Json = JsonObj.getString("home8");

                                View home8View = getActivity().getLayoutInflater().inflate(R.layout.tab_home_item_home8, null);


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

                                HomeView.addView(home8View);

                                if (index == 2) {
                                    View tab_home_item_home8_bottom = getActivity().getLayoutInflater().inflate(R.layout.tab_home_item_home8_bottom, null);
                                    OnImageViewClick(tab_home_item_home8_bottom, "special", "9");
//                                    "data": "9",
//                                            "type": "special",
                                    HomeView.addView(tab_home_item_home8_bottom);
                                    index = 3;
                                }
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

    public void OnImageViewClick(View view, String type, String data) {

        this.OnImageViewClick(view, type, data, false);
    }

    public void OnImageViewClick(View view, final String type, final String data, final boolean deadLineIsEnd) {
        view.setOnTouchListener(new OnTouchListener() {
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
                            Intent intent = new Intent(getActivity(), GoodsListFragmentManager.class);
                            intent.putExtra("keyword", data);
                            intent.putExtra("gc_name", data);
                            startActivity(intent);
                        } else if (type.equals("special")) {//专题编号

                            if (deadLineIsEnd) {

                                Toast.makeText(getActivity(), "当前活动已结束！", Toast.LENGTH_SHORT).show();

                            } else {
//                            Intent intent = new Intent(getActivity(), SubjectWebActivity.class);
                                Intent intent = new Intent(getActivity(), SubjectActivity.class);
//                            intent.putExtra("data", Constants.URL_SPECIAL + "&special_id=" + data + "&type=html");
                                intent.putExtra("data", Constants.URL_SPECIAL + "&special_id=" + data);
                                startActivity(intent);
                            }
                        } else if (type.equals("goods")) {//商品编号
                            Intent intent = new Intent(getActivity(), GoodsDetailsActivity.class);
                            intent.putExtra("goods_id", data);
                            startActivity(intent);
                        } else if (type.equals("url")) {//地址
                            Intent intent = new Intent(getActivity(), SubjectWebActivity.class);
                            intent.putExtra("data", data);
                            startActivity(intent);
                        }
                    }
                }
                return true;
            }
        });
    }

    public void initHeadImage(ArrayList<AdvertList> list) {
        mHandler.removeMessages(SHOW_NEXT);

        //清除已有视图防止重复
        viewflipper.removeAllViews();
        dian.removeAllViews();
        viewList.clear();

        int screenWidth = UTILS.getScreenWidth(getActivity());//屏幕宽度
        int m_height = screenWidth * Constants.AD_HEIGTH / Constants.AD_WHIDE;//广告图片的高度

        for (int i = 0; i < list.size(); i++) {

            //广告图显示设置
            AdvertList bean = list.get(i);
            ImageView imageView = new ImageView(HomeFragment.this.getActivity());
            imageView.setScaleType(ScaleType.CENTER_INSIDE);
            imageView.setBackgroundResource(R.mipmap.dic_av_item_pic_bg);

            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    m_height));
            imageLoader.displayImage(bean.getImage(), imageView, options, animateFirstListener);
            viewflipper.addView(imageView);
            OnImageViewClick(imageView, bean.getType(), bean.getData());

            //广告图显示区域下方 红色表示区域
            ImageView dianimageView = new ImageView(HomeFragment.this.getActivity());
            LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            dianimageView.setLayoutParams(params);
            imageView.setScaleType(ScaleType.FIT_XY);
            dianimageView.setBackgroundResource(R.drawable.dian_select);
            dian.addView(dianimageView);
            viewList.add(dianimageView);
        }

        mGestureDetector = new GestureDetector(this);
        viewflipper.setOnTouchListener(this);
        myScrollView.setGestureDetector(mGestureDetector);

        if (viewList.size() > 1) {
            dian_select(currentPage);
            mHandler.sendEmptyMessageDelayed(SHOW_NEXT, 3800);
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_NEXT:
                    if (showNext) {
                        // 从右向左滑动
                        showNextView();
                    } else {
                        // 从左向右滑动
                        showPreviousView();
                    }
                    mHandler.sendEmptyMessageDelayed(SHOW_NEXT, 3800);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };

    /**
     * 向左滑动
     */
    private void showNextView() {
        viewflipper.setInAnimation(left_in);
        viewflipper.setOutAnimation(left_out);
        viewflipper.showNext();
        currentPage++;
        if (currentPage == viewflipper.getChildCount()) {
            dian_unselect(currentPage - 1);
            currentPage = 0;
            dian_select(currentPage);
        } else {
            dian_select(currentPage);//第currentPage页
            dian_unselect(currentPage - 1);
        }
    }

    /**
     * 向右滑动
     */
    private void showPreviousView() {
        dian_select(currentPage);
        viewflipper.setInAnimation(right_in);
        viewflipper.setOutAnimation(right_out);
        viewflipper.showPrevious();
        currentPage--;
        if (currentPage == -1) {
            dian_unselect(currentPage + 1);
            currentPage = viewflipper.getChildCount() - 1;
            dian_select(currentPage);
        } else {
            dian_select(currentPage);
            dian_unselect(currentPage + 1);
        }
    }

    /**
     * 对应被选中的点的图片
     *
     * @param id
     */
    private void dian_select(int id) {
        ImageView img = viewList.get(id);
        img.setSelected(true);
    }

    /**
     * 对应未被选中的点的图片
     *
     * @param id
     */
    private void dian_unselect(int id) {
        ImageView img = viewList.get(id);
        img.setSelected(false);
    }

    @Override
    public boolean onDown(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {//开始向左滑动了
            if (viewList.size() > 1) {
                showNextView();
                showNext = true;
            }

        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {//开始向右滑动了
            if (viewList.size() > 1) {
                showPreviousView();
                showNext = true;
            }
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent arg0) {
    }

    @Override
    public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
                            float arg3) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent arg0) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @Override
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId()) {
            case R.id.textHomeSearch://文字搜索

                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;

            case R.id.barcodeID://二维码搜索
                //测试用入口  liubw
                //liubw intent = new Intent(getActivity(), PayDemoActivity.class);
                intent = new Intent(getActivity(), CaptureActivity.class);
                startActivity(intent);
                break;

            case R.id.iv_category_1:

                interGoodsclass(0);
                break;
            case R.id.iv_category_2:

                interGoodsclass(1);
                break;
            case R.id.iv_category_3:
                interGoodsclass(2);
                break;
            case R.id.iv_category_4:
                interGoodsclass(3);
                break;
            case R.id.iv_category_5:
                interGoodsclass(4);
                break;
//            case R.id.iv_category_6:
//                interGoodsclass(5);
//                break;


        }


    }

    private void interGoodsclass(int index) {

        if (typeList != null && typeList.size() > 0) {
            OneType bean = (OneType) typeList.get(index);

            if (bean != null) {
                ifNOData(bean.getGc_id(), bean.getGc_name());//断是否有下级
            }
        }
    }

    /**
     * 判断是否有下级
     */
    public void ifNOData(final String gc_id, final String gc_name) {

        String url = Constants.URL_GOODSCLASS + "&gc_id=" + gc_id;

        RemoteDataHandler.asyncDataStringGet(url, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    try {
                        JSONObject obj = new JSONObject(json);
                        String array = obj.getString("class_list");
                        if (array.toString().equals("0") || array.toString().equals("null")) {

                            Intent intent = new Intent(getActivity(), GoodsListFragmentManager.class);
                            intent.putExtra("gc_id", gc_id);
                            intent.putExtra("gc_name", gc_name);
                            startActivity(intent);

                        } else {

                            Intent intent = new Intent(getActivity(), TwoTypeActivity.class);
                            intent.putExtra("gc_id", gc_id);
                            intent.putExtra("gc_name", gc_name);
                            startActivity(intent);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//					Toast.makeText(TypeNextActivity.this, getString(R.string.datas_loading_fail_prompt), Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }


    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constants.LOGIN_SUCCESS_URL);
        getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);  //注册广播
    }


    @Override
    public void onStart() {
        super.onStart();
        registerBoradcastReceiver();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mBroadcastReceiver);
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constants.LOGIN_SUCCESS_URL)) {

//                getLoginKey = myApplication.getLoginKey();
//                member_role = myApplication.getMember_role();

                loadUIData();
            }
        }
    };
}
