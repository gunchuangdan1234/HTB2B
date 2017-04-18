package com.huatuo_b2b.htb2b.ui.type;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.huatuo_b2b.htb2b.MainFragmentManager;
import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.CommendGridViewAdapter;
import com.huatuo_b2b.htb2b.bean.CommendList;
import com.huatuo_b2b.htb2b.bean.DrugLibrary;
import com.huatuo_b2b.htb2b.bean.GiftArrayList;
import com.huatuo_b2b.htb2b.bean.GoodsDetails;
import com.huatuo_b2b.htb2b.bean.ManSongInFo;
import com.huatuo_b2b.htb2b.bean.ManSongRules;
import com.huatuo_b2b.htb2b.bean.Spec;
import com.huatuo_b2b.htb2b.bean.StoreInFo;
import com.huatuo_b2b.htb2b.common.AnimateFirstDisplayListener;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.LogUtil;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.common.SystemHelper;
import com.huatuo_b2b.htb2b.custom.CustomScrollView;
import com.huatuo_b2b.htb2b.custom.MyGridView;
import com.huatuo_b2b.htb2b.custom.ViewFlipperScrollView;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;
import com.huatuo_b2b.htb2b.ui.mine.DrugLibraryActivity;
import com.huatuo_b2b.htb2b.ui.mine.IMSendMsgActivity;
import com.huatuo_b2b.htb2b.ui.mine.LoginActivity;
import com.huatuo_b2b.htb2b.ui.store.StoreInFoActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 商品细节展示
 *
 * @author KingKong-HE
 * @Time 2015-1-5
 * @Email KingKong@QQ.COM
 */
public class GoodsDetailsActivity extends Activity implements OnGestureListener, OnTouchListener, OnClickListener {

    private TextView goodsNameID, goodsJingleID, goodsStorageID, goodsSalenumID, goodsCollectID,
            textEvaluationCountID, cartNumID;

    private TextView tv_specNameID;//规格展示

    private TextView settingID;//比价按钮

    //    private RelativeLayout tuWenID;
//    private RelativeLayout goodsParamID;
    private RelativeLayout storeInFoID;
    private LinearLayout specNameID;//规格

    private LinearLayout imageXingJiID, manSongViewID, giftArrayViewID, specLinearLayoutID, specViewID;

    private MyGridView gridViewCommend;

    private CommendGridViewAdapter commendAdapter;

    private CustomScrollView customScrollViewID;

    private Button goods_details_spec_OK;//规格确认按钮
    private Button goods_details_spec_del;//规格取消按钮

    private Button addCartID, buyStepID, numMinusID, numPlusID;

    private ImageView specGoodsPicID, isFavYesID, isFavNoID;
    private LinearLayout imID;//客服入口

    private TextView specGoodsNameID;
    private TextView specGoodsPriceID;  //商品价格显示 liubw
    private TextView goodsPriceID;      //商品价格
    private TextView goods_mmarket_PriceID;//商品市场价
    private TextView goodsNumID;

    private LinearLayout showCartLayoutID;//显示购物车商品数

    private MyShopApplication myApplication;

    private String goods_id;//商品ID

    private String mobile_body;//商品手机版详情

    private String goods_attr;//产品规格参数

    private int goodsNum = 0; //商品数量

    private int upper_limit = 0;//限购

    private int goodsStorage = 0;//库存

    private String is_fcode;//是否为F码商品 1是 0否

    private String is_virtual;//是否为虚拟商品 1-是 0-否

    private String ifcart = "0";//购物车购买标志 1购物车 0不是

    private String store_id = "";//记录店铺ID

    private HashMap<String, Integer> hashMap = new HashMap<String, Integer>();//存储规格参数
    private int[] specListSort;
    private HashMap<String, View> viewsSpec = new HashMap<String, View>();//存储规格View

    private ViewFlipper viewflipper;

    private boolean showNext = true;

    private int currentPage = 0;

    private final int SHOW_NEXT = 0011;

    private static final int FLING_MIN_DISTANCE = 50;

    private static final int FLING_MIN_VELOCITY = 0;

    private GestureDetector mGestureDetector;

    private ViewFlipperScrollView myScrollView;

    private ArrayList<ImageView> viewList = new ArrayList<ImageView>();

    private Animation left_in, left_out, right_in, right_out;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    private DisplayImageOptions options = SystemHelper.getDisplayImageOptions();

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private String t_id, t_name; //记录商家ID 名称

    private AlertDialog menuDialog;// menu菜单Dialog
    private GridView menuGrid;
    private View menuView;
    /**
     * 菜单文字 *
     */
    private String[] menu_name_array = {"微信", "朋友圈", "Copy", "短信"};
    private final int ITEM_WEIXIN = 0;// 微信
    private final int ITEM_FRIEND = 1;// 朋友圈
    private final int ITEM_COPY = 2;// Copy
    private final int ITEM_DUANXIN = 3;// 短信
    private String pic_url;//记录图片地址
    private String textContent;//记录商品名

//    private TextView tv_sccj_src;//生产厂家
//    private TextView tv_tongyongming;//通用名
//    private TextView tv_pingpai;//品牌
//    private TextView tv_zhuzhi;//主治

    private String getLoginKey;
    private String member_role;

    //地址选择
    private TextView tv_address_set;


    private String xskz_package = "";
    private int midPackage;
    private int bigPackage;

    /**
     * 菜单图片 *
     */
    private int[] menu_image_array = {
            R.mipmap.sns_weixin_icon,
            R.mipmap.sns_weixin_timeline_icon,
            R.mipmap.sns_ydnote_icon,
            R.mipmap.sns_message_icon};
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;


    //商品规格
    private TextView tv_drug_spec;
    private TextView tv_drug_dosage;
    private TextView tv_approval_number;
    private TextView tv_product_company;
    private TextView tv_sale_unit;
    private TextView tv_xskz_package;
    private TextView tv_mid_package;
    private TextView tv_big_package;

    //产品说明 书

    private DrugLibrary drugLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_details_view);

        myApplication = (MyShopApplication) getApplicationContext();

        goods_id = GoodsDetailsActivity.this.getIntent().getStringExtra("goods_id");


        getLoginKey = myApplication.getLoginKey();
        member_role = myApplication.getMember_role();

        initViewID();//初始化注册控件ID

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);

        loadingGoodsDetailsData();//初始化加载数据
    }

    /**
     * 初始化注册控件ID
     */
    public void initViewID() {

        goodsNameID = (TextView) findViewById(R.id.goodsNameID);
        goodsJingleID = (TextView) findViewById(R.id.goodsJingleID);
        goodsStorageID = (TextView) findViewById(R.id.goodsStorageID);
        goodsSalenumID = (TextView) findViewById(R.id.goodsSalenumID);
        goodsCollectID = (TextView) findViewById(R.id.goodsCollectID);
        tv_specNameID = (TextView) findViewById(R.id.tv_specNameID);
        goodsPriceID = (TextView) findViewById(R.id.goodsPriceID);
        goods_mmarket_PriceID = (TextView) findViewById(R.id.goods_mmarket_PriceID);
//        tuWenID = (RelativeLayout) findViewById(R.id.tuWenID);
//        goodsParamID = (RelativeLayout) findViewById(R.id.goodsParamID);
        findViewById(R.id.ll_address_set).setOnClickListener(this);
        findViewById(R.id.rl_drug_library).setOnClickListener(this);

        tv_address_set = (TextView) findViewById(R.id.tv_address_set);

        storeInFoID = (RelativeLayout) findViewById(R.id.storeInFoID);
        textEvaluationCountID = (TextView) findViewById(R.id.textEvaluationCountID);
        imageXingJiID = (LinearLayout) findViewById(R.id.imageXingJiID);
        manSongViewID = (LinearLayout) findViewById(R.id.manSongViewID);
        specViewID = (LinearLayout) findViewById(R.id.specViewID);
        giftArrayViewID = (LinearLayout) findViewById(R.id.giftArrayViewID);
        specLinearLayoutID = (LinearLayout) findViewById(R.id.specLinearLayoutID);
        specNameID = (LinearLayout) findViewById(R.id.specNameID);
        gridViewCommend = (MyGridView) findViewById(R.id.gridViewCommend);
        customScrollViewID = (CustomScrollView) findViewById(R.id.customScrollViewID);
        viewflipper = (ViewFlipper) findViewById(R.id.viewflipper);
        myScrollView = (ViewFlipperScrollView) findViewById(R.id.viewFlipperScrollViewID);
        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);
        addCartID = (Button) findViewById(R.id.addCartID);
        buyStepID = (Button) findViewById(R.id.buyStepID);
        numMinusID = (Button) findViewById(R.id.numMinusID);
        numPlusID = (Button) findViewById(R.id.numPlusID);
        specGoodsPicID = (ImageView) findViewById(R.id.specGoodsPicID);
        imID = (LinearLayout) findViewById(R.id.imID);
        specGoodsNameID = (TextView) findViewById(R.id.specGoodsNameID);
        specGoodsPriceID = (TextView) findViewById(R.id.specGoodsPriceID);
        cartNumID = (TextView) findViewById(R.id.cartNumID);
        goodsNumID = (TextView) findViewById(R.id.goodsNumID);
        isFavYesID = (ImageView) findViewById(R.id.isFavYesID);
        isFavNoID = (ImageView) findViewById(R.id.isFavNoID);
        showCartLayoutID = (LinearLayout) findViewById(R.id.showCartLayoutID);
        LinearLayout sharekID = (LinearLayout) findViewById(R.id.sharekID);

        goods_details_spec_OK = (Button) findViewById(R.id.goods_details_spec_OK);//规格确认按钮
        goods_details_spec_del = (Button) findViewById(R.id.goods_details_spec_del);//规格取消按钮

    /*    tv_sccj_src = (TextView) findViewById(R.id.tv_sccj);
        tv_tongyongming = (TextView) findViewById(R.id.tv_tongyongming);//通用名
        tv_pingpai = (TextView) findViewById(R.id.tv_pingpai);//品牌
        tv_zhuzhi = (TextView) findViewById(R.id.tv_zhuzhi);//主治*/

        settingID = (TextView) findViewById(R.id.settingID);//比价按钮

        //2.0新增模块
        tv_drug_spec = (TextView) findViewById(R.id.tv_drug_spec);
        tv_drug_dosage = (TextView) findViewById(R.id.tv_drug_dosage);
        tv_approval_number = (TextView) findViewById(R.id.tv_approval_number);
        tv_product_company = (TextView) findViewById(R.id.tv_product_company);
        tv_sale_unit = (TextView) findViewById(R.id.tv_sale_unit);
        tv_xskz_package = (TextView) findViewById(R.id.tv_xskz_package);
        tv_mid_package = (TextView) findViewById(R.id.tv_mid_package);
        tv_big_package = (TextView) findViewById(R.id.tv_big_package);

        //初始化滑动控件动画
        left_in = AnimationUtils.loadAnimation(GoodsDetailsActivity.this, R.anim.push_left_in);
        left_out = AnimationUtils.loadAnimation(GoodsDetailsActivity.this, R.anim.push_left_out);
        right_in = AnimationUtils.loadAnimation(GoodsDetailsActivity.this, R.anim.push_right_in);
        right_out = AnimationUtils.loadAnimation(GoodsDetailsActivity.this, R.anim.push_right_out);

        commendAdapter = new CommendGridViewAdapter(this, getLoginKey, member_role);
        gridViewCommend.setAdapter(commendAdapter);
        gridViewCommend.setFocusable(false);

        //推荐商品列表点击监听
        gridViewCommend.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                CommendList bean = (CommendList) gridViewCommend.getItemAtPosition(arg2);

                if (bean != null) {
                    goods_id = bean.getGoods_id();
                    loadingGoodsDetailsData();
                    customScrollViewID.smoothScrollTo(0, 0);
                }

            }
        });


        if (TextUtils.isEmpty(myApplication.getLoginKey())) {
            imID.setVisibility(View.GONE);
        } else {
            imID.setVisibility(View.VISIBLE);
        }

        specLinearLayoutID.getBackground().setAlpha(100);//0~255透明度值

        imageBack.setOnClickListener(this);
        numMinusID.setOnClickListener(this);
        sharekID.setOnClickListener(this);
        numPlusID.setOnClickListener(this);
//        tuWenID.setOnClickListener(this);
//        goodsParamID.setOnClickListener(this);
        storeInFoID.setOnClickListener(this);
        specNameID.setOnClickListener(this);
        addCartID.setOnClickListener(this);
        buyStepID.setOnClickListener(this);
        specLinearLayoutID.setOnClickListener(this);
        isFavYesID.setOnClickListener(this);
        isFavNoID.setOnClickListener(this);
        showCartLayoutID.setOnClickListener(this);
        imID.setOnClickListener(this);
        settingID.setOnClickListener(this);

        goods_details_spec_OK.setOnClickListener(this);//规格确认按钮
        goods_details_spec_del.setOnClickListener(this);//规格取消按钮

        menuView = View.inflate(GoodsDetailsActivity.this, R.layout.gridview_menu, null);
        // 创建AlertDialog
        menuDialog = new AlertDialog.Builder(GoodsDetailsActivity.this).create();
        menuDialog.setView(menuView);
        menuGrid = (GridView) menuView.findViewById(R.id.gridview);
        menuGrid.setAdapter(getMenuAdapter(menu_name_array, menu_image_array));
        /** 监听menu选项 **/
        menuGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                menuDialog.dismiss();
                switch (arg2) {
                    case ITEM_WEIXIN://"微信"
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap bmp = BitmapFactory.decodeStream(new URL(pic_url).openStream());
                                    Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
                                    bmp.recycle();
                                    Message message = new Message();
                                    message.obj = thumbBmp;
                                    message.arg1 = 1;
                                    handler.sendMessage(message);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                        break;
                    case ITEM_FRIEND://"朋友圈"
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap bmp = BitmapFactory.decodeStream(new URL(pic_url).openStream());
                                    Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 150, 150, true);
                                    bmp.recycle();
                                    Message message = new Message();
                                    message.obj = thumbBmp;
                                    message.arg1 = 2;
                                    handler.sendMessage(message);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                        break;
                    case ITEM_COPY://"Copy"
                        // 得到剪贴板管理器
                        ClipboardManager cmb = (ClipboardManager) GoodsDetailsActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                        cmb.setText(textContent + pic_url + "(" + getString(R.string.app_name) + ")");
                        Toast.makeText(GoodsDetailsActivity.this, "复制到剪贴板", Toast.LENGTH_SHORT).show();
                        break;
                    case ITEM_DUANXIN://"短信"
                        Uri uri = Uri.parse("smsto:");
                        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                        it.putExtra("sms_body", textContent + pic_url + "(" + getString(R.string.app_name) + ")");
                        GoodsDetailsActivity.this.startActivity(it);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    private SimpleAdapter getMenuAdapter(String[] menuNameArray,
                                         int[] imageResourceArray) {
        ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < menuNameArray.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("itemImage", imageResourceArray[i]);
            map.put("itemText", menuNameArray[i]);
            data.add(map);
        }
        SimpleAdapter simperAdapter = new SimpleAdapter(this, data,
                R.layout.item_menu, new String[]{"itemImage", "itemText"},
                new int[]{R.id.item_image, R.id.item_text});
        return simperAdapter;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.arg1) {
                case 1:
                    WXWebpageObject webpage = new WXWebpageObject();
                    webpage.webpageUrl = pic_url;
                    WXMediaMessage msg = new WXMediaMessage(webpage);
                    msg.title = textContent;
                    msg.description = textContent + Constants.URL_GOODSDETAILS + "&goods_id=" + goods_id + "(" + getString(R.string.app_name) + ")";
                    msg.thumbData = bmpToByteArray((Bitmap) message.obj, true);

                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = buildTransaction("webpage");
                    req.message = msg;
                    req.scene = false ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                    api.sendReq(req);
                    if (!api.sendReq(req)) {
                        Toast.makeText(GoodsDetailsActivity.this, "您的版本不支持或者没有安装", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    int wxSdkVersion = api.getWXAppSupportAPI();
                    if (wxSdkVersion >= TIMELINE_SUPPORTED_VERSION) {//支持
                        WXWebpageObject webpagePengYou = new WXWebpageObject();
                        webpagePengYou.webpageUrl = pic_url;
                        WXMediaMessage msgPengYou = new WXMediaMessage(webpagePengYou);
                        msgPengYou.title = textContent;
                        msgPengYou.description = textContent + Constants.URL_GOODSDETAILS + "&goods_id=" + goods_id + "(" + getString(R.string.app_name) + ")";
                        msgPengYou.thumbData = bmpToByteArray((Bitmap) message.obj, true);
                        SendMessageToWX.Req reqPengYou = new SendMessageToWX.Req();
                        reqPengYou.transaction = buildTransaction("webpage");
                        reqPengYou.message = msgPengYou;
                        reqPengYou.scene = true ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
                        api.sendReq(reqPengYou);
                    } else {
                        Toast.makeText(GoodsDetailsActivity.this, "您的版本不支持或者没有安装", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    /**
     * 初始化加载数据
     */
    public void loadingGoodsDetailsData() {

        String url = Constants.URL_GOODSDETAILS + "&goods_id=" + goods_id + "&key=" + myApplication.getLoginKey();

        RemoteDataHandler.asyncDataStringGet(url, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {

                imageXingJiID.removeAllViews();
                manSongViewID.removeAllViews();
                giftArrayViewID.removeAllViews();
                specViewID.removeAllViews();
                viewflipper.removeAllViews();
                viewList.clear();

                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    LogUtil.e("GoodsDetailsActivity==", json);
                    try {
                        JSONObject obj = new JSONObject(json);
                        String goods_info = obj.getString("goods_info");// 商品信息
                        String goods_image = obj.getString("goods_image"); //商品图片
                        String mansong_info = obj.getString("mansong_info");// 满即送信息
                        String goods_commend_list = obj.getString("goods_commend_list"); //推荐商品列表
                        final String spec_list = obj.getString("spec_list"); // 规格列表
                        String store_info = obj.getString("store_info");// 店铺信息
                        String gift_array = obj.getString("gift_array");// 赠品数组
                        String drug_library = obj.getString("drug_library");//产品说明书

                        try {

                            String is_favorate = obj.getString("is_favorate");// 是否已经收藏
                            String cart_count = obj.getString("cart_count");// 购物车数量

                            if (is_favorate.equals("true")) {
                                isFavNoID.setVisibility(View.GONE);
                                isFavYesID.setVisibility(View.VISIBLE);
                            } else {
                                isFavNoID.setVisibility(View.VISIBLE);
                                isFavYesID.setVisibility(View.GONE);
                            }

                            cartNumID.setText(cart_count == null ? "0" : cart_count);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        GoodsDetails goodsBean = GoodsDetails.newInstanceList(goods_info);

                        StoreInFo storeBean = StoreInFo.newInstanceList(store_info);

                        drugLibrary = DrugLibrary.newInstanceList(drug_library);

                        if (storeBean != null) {
                            store_id = storeBean.getStore_id();
                            t_id = storeBean.getMember_id();
                            t_name = storeBean.getMember_name();
                        }

                        if (goodsBean != null) {

                            mobile_body = goodsBean.getMobile_body();//

                            goods_attr = goodsBean.getGoods_attr();//产品规格

                            //判断是否限购
                            if (goodsBean.getUpper_limit() != null && !goodsBean.getUpper_limit().equals("")) {
                                upper_limit = Integer.parseInt((goodsBean.getUpper_limit() == null ? "0" : goodsBean.getUpper_limit()) == "" ? "0" : goodsBean.getUpper_limit());
                            }

                            //显示商品名称
                            goodsNameID.setText(goodsBean.getGoods_name() == null ? "" : goodsBean.getGoods_name());
                            specGoodsNameID.setText(goodsBean.getGoods_name() == null ? "" : goodsBean.getGoods_name());
                            textContent = goodsBean.getGoods_name();

                            //判断是否显示商品说明
                            if (goodsBean.getGoods_jingle() != null && !goodsBean.getGoods_jingle().equals("") && !goodsBean.getGoods_jingle().equals("null")) {
                                goodsJingleID.setVisibility(View.VISIBLE);
                                goodsJingleID.setText(Html.fromHtml(goodsBean.getGoods_jingle() == null ? "" : goodsBean.getGoods_jingle()));
                            } else {
                                goodsJingleID.setVisibility(View.GONE);
                            }

                            if (goodsBean.getPromotion_price() != null && !goodsBean.getPromotion_price().equals("") && !goodsBean.getPromotion_price().equals("null")) {
                                //显示商品价格

                                if (getLoginKey == null || getLoginKey.equals("")) {//价格登录后可见
                                    goodsPriceID.setText("价格登录后可见");
                                    specGoodsPriceID.setText("价格登录后可见");
                                } else {
                                    if (member_role.equals("0")) {//显示正常价格   liubw
                                        goodsPriceID.setText("价格认证后可见");
                                        specGoodsPriceID.setText("价格认证后可见");
                                    } else {//价格认证后可见
                                        goodsPriceID.setText("￥" + (goodsBean.getPromotion_price() == null ? "0" : goodsBean.getPromotion_price()));
                                        specGoodsPriceID.setText("￥" + (goodsBean.getPromotion_price() == null ? "0" : goodsBean.getPromotion_price()));

                                        goods_mmarket_PriceID.setText("￥" + (goodsBean.getGoods_marketprice() == null ? "0" : goodsBean.getGoods_marketprice()));
                                        goods_mmarket_PriceID.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                    }
                                }
                            } else {

                                if (getLoginKey == null || getLoginKey.equals("")) {//价格登录后可见
                                    goodsPriceID.setText("价格登录后可见");
                                    specGoodsPriceID.setText("价格登录后可见");
                                } else {
                                    if (member_role.equals("0")) {//显示正常价格   liubw
                                        goodsPriceID.setText("价格认证后可见");
                                        specGoodsPriceID.setText("价格认证后可见");
                                    } else {//价格认证后可见
                                        goodsPriceID.setText("￥" + (goodsBean.getGoods_price() == null ? "0" : goodsBean.getGoods_price()));
                                        specGoodsPriceID.setText("￥" + (goodsBean.getGoods_price() == null ? "0" : goodsBean.getGoods_price()));
                                        goods_mmarket_PriceID.setText("￥" + (goodsBean.getGoods_marketprice() == null ? "0" : goodsBean.getGoods_marketprice()));
                                        goods_mmarket_PriceID.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                    }
                                }
                            }

//                            tv_sccj_src.setText(goodsBean.getGoods_storage() == null ? "0" : goodsBean.getGoods_storage());//显示生产厂家
//                            tv_tongyongming.setText(goodsBean.getGoods_storage() == null ? "0" : goodsBean.getGoods_storage());//通用名
//                            tv_pingpai.setText(goodsBean.getGoods_storage() == null ? "0" : goodsBean.getGoods_storage());//品牌
//                            tv_zhuzhi.setText(goodsBean.getGoods_storage() == null ? "0" : goodsBean.getGoods_storage());//主治


                            goodsStorage = Integer.parseInt((goodsBean.getGoods_storage() == null ? "0" : goodsBean.getGoods_storage()));

                            //显示商品库存数量
                            goodsStorageID.setText("(当前库存" + (goodsBean.getGoods_storage() == null ? "0" : goodsBean.getGoods_storage()) + "盒)");

                            //显示商品销量数量
                            goodsSalenumID.setText(goodsBean.getGoods_salenum() == null ? "0" : goodsBean.getGoods_salenum());

                            //显示商品收藏数量
                            goodsCollectID.setText(goodsBean.getGoods_collect() == null ? "0" : goodsBean.getGoods_collect());

                            //显示商品评价数量
                            textEvaluationCountID.setText("（" + (goodsBean.getEvaluation_count() == null ? "0" : goodsBean.getEvaluation_count()) + "人）");

                            //是否是F吗商品
                            is_fcode = goodsBean.getIs_fcode() == null ? "0" : goodsBean.getIs_fcode();

                            //是否是虚拟商品
                            is_virtual = goodsBean.getIs_virtual() == null ? "0" : goodsBean.getIs_virtual();

                            //判断各个商品购买关系，按钮隐藏显示
                            if (goodsBean.getIs_fcode().equals("1") || is_virtual.equals("1")) {
                                addCartID.setVisibility(View.GONE);
                            }

                            //显示商品评价等级
                            int count = Integer.parseInt(goodsBean.getEvaluation_good_star());
                            for (int i = 0; i < 5; i++) {
                                ImageView imageView = new ImageView(GoodsDetailsActivity.this);
                                if (i <= count - 1) {
                                    imageView.setBackgroundResource(R.mipmap.ic_star_metro_orang);
                                } else {
                                    imageView.setBackgroundResource(R.mipmap.ic_fav);
                                }
                                imageXingJiID.addView(imageView);
                            }

                            //显示规格值
                            String specValue = "";
                            if (goodsBean.getGoods_spec() != null && !goodsBean.getGoods_spec().equals("") && !goodsBean.getGoods_spec().equals("null")) {
                                JSONObject jsonGoods_spec = new JSONObject(goodsBean.getGoods_spec());
                                Iterator<?> itName = jsonGoods_spec.keys();
                                while (itName.hasNext()) {
                                    String specID = itName.next().toString();
                                    String specV = jsonGoods_spec.getString(specID);
                                    specValue += "," + specV;
                                }
                                tv_specNameID.setText("批号  " + specValue.replaceFirst(",", ""));
                            } else {
                                specNameID.setVisibility(View.GONE);
                            }

                            //显示推荐商品列表
                            ArrayList<CommendList> commendLists = CommendList.newInstanceList(goods_commend_list);
                            commendAdapter.setCommendLists(commendLists);
                            commendAdapter.setLoginKey(myApplication.getLoginKey());
                            commendAdapter.setMemberRole(myApplication.getMember_role());
                            commendAdapter.notifyDataSetChanged();

                            //显示商品图片
                            String[] image_url = goods_image.split(",");
                            if (image_url.length > 0) {
                                initHeadImage(image_url);

                                pic_url = image_url[0];

                                imageLoader.displayImage(image_url[0], specGoodsPicID, options, animateFirstListener);
                            }

                            //显示赠品
                            if (gift_array != null && !gift_array.equals("") && !gift_array.equals("[]") && !gift_array.equals("null")) {
                                ArrayList<GiftArrayList> giftArrayLists = GiftArrayList.newInstanceList(gift_array);
                                for (int i = 0; i < giftArrayLists.size(); i++) {

                                    GiftArrayList bean = giftArrayLists.get(i);

                                    TextView manSongID = (TextView) getLayoutInflater().inflate(R.layout.gift_array_view, null);

                                    manSongID.setText(bean.getGift_amount() + "个" + bean.getGift_goodsname());

                                    giftArrayViewID.addView(manSongID);
                                }
                            } else {
                                TextView manSongID = (TextView) getLayoutInflater().inflate(R.layout.gift_array_view, null);
                                giftArrayViewID.addView(manSongID);
                                giftArrayViewID.setVisibility(View.GONE);
                            }

                            //显示满即送
                            if (mansong_info != null && !mansong_info.equals("") && !mansong_info.equals("[]") && !mansong_info.equals("null")) {
                                ManSongInFo manSongInFo = ManSongInFo.newInstanceList(mansong_info);
                                final ArrayList<ManSongRules> mRules = ManSongRules.newInstanceList(manSongInFo.getRules());

                                for (int i = 0; i < mRules.size(); i++) {

                                    ManSongRules bean = mRules.get(i);

                                    RelativeLayout man_song_view = (RelativeLayout) getLayoutInflater().inflate(R.layout.man_song_view, null);
                                    man_song_view.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent msIntent = new Intent(GoodsDetailsActivity.this, ManSongActivity.class);
                                            msIntent.putParcelableArrayListExtra("mRules", mRules);
                                            startActivity(msIntent);
                                        }
                                    });
                                    TextView manSongID = (TextView) man_song_view.findViewById(R.id.manSongID);

                                    if (bean.getGoods_id().equals("0")) {
                                        manSongID.setText(Html.fromHtml("单笔订单满<font color='#FF3300'>" + bean.getPrice() + "元</font>，立减现金<font color='#339900'>" + bean.getDiscount() + "元</font>"));
                                    } else {
                                        manSongID.setText(Html.fromHtml("单笔订单满<font color='#FF3300'>" + bean.getPrice() + "元</font> ， 立减现金<font color='#339900'>" + bean.getDiscount() + "</font>， 送礼品"));
                                    }

                                    manSongViewID.addView(man_song_view);
                                }
                            } else {
                                RelativeLayout man_song_view = (RelativeLayout) getLayoutInflater().inflate(R.layout.man_song_view, null);
                                manSongViewID.addView(man_song_view);
                                manSongViewID.setVisibility(View.GONE);
                            }

                            //显示规格属性
                            JSONObject jsonObjName = new JSONObject(goodsBean.getSpec_name());
                            JSONObject jsonObjValue = new JSONObject(goodsBean.getSpec_value());
                            Iterator<?> itName = jsonObjName.keys();
                            while (itName.hasNext()) {
                                ArrayList<Spec> list = new ArrayList<Spec>();
                                final String specID = itName.next().toString();
                                String specName = jsonObjName.getString(specID);
                                String specValueName = jsonObjValue.getString(specID);
                                JSONObject jsonObj = new JSONObject(specValueName);
                                Iterator<?> iterator = jsonObj.keys();


                                LinearLayout goodSpecListView = (LinearLayout) getLayoutInflater().inflate(R.layout.goods_spec_list_view, null);
                                TextView specNameID = (TextView) goodSpecListView.findViewById(R.id.specNameID);
                                LinearLayout specListID = (LinearLayout) goodSpecListView.findViewById(R.id.specListID);
                                specNameID.setText(specName);

                                while (iterator.hasNext()) {
                                    final String SpecID = iterator.next().toString();
                                    String SpecName = jsonObj.getString(SpecID);
                                    Spec s = new Spec();
                                    s.setSpecID(SpecID);
                                    s.setSpecName(SpecName);
                                    list.add(s);

                                    LinearLayout specListItemView = (LinearLayout) getLayoutInflater().inflate(R.layout.spec_list_item_view, null);
                                    CheckBox specValuesID = (CheckBox) specListItemView.findViewById(R.id.specValuesID);
                                    specValuesID.setPadding(10, 10, 10, 10);

                                    specValuesID.setText(SpecName);
                                    specListID.addView(specListItemView);
                                    viewsSpec.put(SpecID, specValuesID);//存储全部规格view

                                    if (goodsBean.getGoods_spec() != null && !goodsBean.getGoods_spec().equals("") && !goodsBean.getGoods_spec().equals("null")) {
                                        JSONObject jsonGoodsSpec = new JSONObject(goodsBean.getGoods_spec());
                                        Iterator<?> itGoodsSpec = jsonGoodsSpec.keys();
                                        while (itGoodsSpec.hasNext()) {
                                            String goodsSpecID = itGoodsSpec.next().toString();
                                            if (goodsSpecID.equals(SpecID)) {

                                                specValuesID.setBackgroundResource(R.mipmap.product_detail_color_size_press);
                                                specValuesID.setPadding(10, 10, 10, 10);

                                            }
                                        }
                                    }

                                    specValuesID.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            hashMap.put(specID, Integer.parseInt(SpecID));

                                            Iterator iterator = viewsSpec.keySet().iterator();

                                            while (iterator.hasNext()) {

                                                String viewSpecID = iterator.next().toString();
                                                CheckBox sview = (CheckBox) viewsSpec.get(viewSpecID);
                                                sview.setBackgroundResource(R.mipmap.product_detail_color_size_normal);
                                                sview.setPadding(10, 10, 10, 10);

                                                Iterator it = hashMap.keySet().iterator();

                                                specListSort = new int[hashMap.size()];

                                                int count = 0;

                                                while (it.hasNext()) {

                                                    String sID = it.next().toString();
                                                    String ssID = String.valueOf(hashMap.get(sID));

                                                    specListSort[count] = hashMap.get(sID);

                                                    count++;

                                                    if (viewSpecID.equals(ssID)) {
                                                        sview.setBackgroundResource(R.mipmap.product_detail_color_size_press);
                                                        sview.setPadding(10, 10, 10, 10);
                                                    }

                                                }

                                            }

                                            Arrays.sort(specListSort);

                                            String spec_list_str = "";
                                            for (int i = 0; i < specListSort.length; i++) {
                                                spec_list_str += "|" + specListSort[i];
                                            }

                                            spec_list_str = spec_list_str.replaceFirst("\\|", "");

                                            try {
                                                JSONObject specListObj = new JSONObject(spec_list);
                                                goods_id = specListObj.getString(spec_list_str);

                                                loadingGoodsDetailsData();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    });
                                }

                                specViewID.addView(goodSpecListView);

                            }


                            //商品规格
                            tv_drug_spec.setText(goodsBean.getDrug_spec() == null ? "" : goodsBean.getDrug_spec());
                            tv_drug_dosage.setText(goodsBean.getDrug_dosage() == null ? "" : goodsBean.getDrug_dosage());
                            tv_approval_number.setText(goodsBean.getApproval_number() == null ? "" : goodsBean.getApproval_number());
                            tv_product_company.setText(goodsBean.getProduct_company() == null ? "" : goodsBean.getProduct_company());
                            tv_sale_unit.setText(goodsBean.getSale_unit() == null ? "" : goodsBean.getSale_unit());

                            tv_mid_package.setText(goodsBean.getMid_package() == null ? "" : goodsBean.getMid_package());
                            tv_big_package.setText(goodsBean.getBig_package() == null ? "" : goodsBean.getBig_package());

                            xskz_package = goodsBean.getXskz_package();

                            if (goodsBean.getXskz_package() != null) {
                                if ("1".equals(xskz_package)) {
                                    tv_xskz_package.setText("中包");
                                    goodsNumID.setText(goodsBean.getMid_package() == null ? "" : goodsBean.getMid_package());
                                    goodsNum = Integer.parseInt(goodsBean.getMid_package() == null ? "0" : goodsBean.getMid_package());
                                } else {
                                    tv_xskz_package.setText("大包");
                                    goodsNumID.setText(goodsBean.getBig_package() == null ? "" : goodsBean.getBig_package());
                                    goodsNum = Integer.parseInt(goodsBean.getBig_package() == null ? "0" : goodsBean.getBig_package());
                                }
                            }

                            if (!TextUtils.isEmpty(goodsBean.getMid_package())) {
                                midPackage = Integer.parseInt(goodsBean.getMid_package());
                            }

                            if (!TextUtils.isEmpty(goodsBean.getBig_package())) {
                                bigPackage = Integer.parseInt(goodsBean.getBig_package());
                            }


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject obj = new JSONObject(json);
                        String error = obj.getString("error");
                        if (error != null) {
                            Toast.makeText(GoodsDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                            GoodsDetailsActivity.this.finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//					customScrollViewID.smoothScrollTo(0, 0);
                } else {
//					Toast.makeText(GoodsDetailsActivity.this, getString(R.string.datas_loading_fail_prompt), Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }

    /**
     * 添加购物车
     *
     * @param :goods_id 商品ID
     * @param :key      登录标识
     * @param :quantity 数量
     */
    public void shopAddCart() {
        String url = Constants.URL_ADD_CART;

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("goods_id", goods_id);
        params.put("key", myApplication.getLoginKey());
        params.put("quantity", goodsNumID.getText().toString());

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    if (json.equals("1")) {
                        Toast.makeText(GoodsDetailsActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
                        loadingGoodsDetailsData();//初始化加载数据
                    }
                    try {
                        JSONObject obj = new JSONObject(json);
                        String error = obj.getString("error");
                        if (error != null) {
                            Toast.makeText(GoodsDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//					Toast.makeText(GoodsDetailsActivity.this, getString(R.string.datas_loading_fail_prompt), Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }

    /**
     * 收藏添加
     *
     * @param :goods_id 商品ID
     * @param :key      登录标识
     */
    public void addFav() {
        String url = Constants.URL_ADD_FAV;

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("goods_id", goods_id);
        params.put("key", myApplication.getLoginKey());

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    if (json.equals("1")) {
                        Toast.makeText(GoodsDetailsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();

                        loadingGoodsDetailsData();//初始化加载数据
                    }
                    try {
                        JSONObject obj = new JSONObject(json);
                        String error = obj.getString("error");
                        if (error != null) {
                            Toast.makeText(GoodsDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//					Toast.makeText(GoodsDetailsActivity.this, getString(R.string.datas_loading_fail_prompt), Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }

    /**
     * 收藏删除
     *
     * @param :fav_id 商品ID
     * @param :key    登录标识
     */
    public void deleteFav() {
        String url = Constants.URL_DELETE_FAV;

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("fav_id", goods_id);
        params.put("key", myApplication.getLoginKey());

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    if (json.equals("1")) {
//						Toast.makeText(GoodsDetailsActivity.this, "成功", Toast.LENGTH_SHORT).show();;
                        loadingGoodsDetailsData();//初始化加载数据
                    }
                    try {
                        JSONObject obj = new JSONObject(json);
                        String error = obj.getString("error");
                        if (error != null) {
                            Toast.makeText(GoodsDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//					Toast.makeText(GoodsDetailsActivity.this, getString(R.string.datas_loading_fail_prompt), Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }


    public void initHeadImage(String[] image_url) {
        int width = SystemHelper.getScreenInfo(GoodsDetailsActivity.this).widthPixels;
        for (int i = 0; i < image_url.length; i++) {
            String url = image_url[i];
            ImageView imageView = new ImageView(GoodsDetailsActivity.this);
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            imageLoader.displayImage(url, imageView, options, animateFirstListener);
            viewflipper.addView(imageView);
            viewList.add(imageView);
        }

        mGestureDetector = new GestureDetector(this);
        viewflipper.setOnTouchListener(this);
        myScrollView.setGestureDetector(mGestureDetector);


        if (viewList.size() > 0) {
            dian_select(currentPage);
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
            case R.id.imageBack:
                finish();
                break;
//            case R.id.tuWenID://图文信息
//
//                intent = new Intent(GoodsDetailsActivity.this, TuWenActivity.class);
//                intent.putExtra("goods_id", goods_id);
//                intent.putExtra("mobile_body", mobile_body);
//                break;
//            case R.id.goodsParamID:
//
//                intent = new Intent(GoodsDetailsActivity.this, GoodsParamActivity.class);
//                intent.putExtra("goods_attr", goods_attr);

            case R.id.rl_drug_library:
                intent = new Intent(GoodsDetailsActivity.this, DrugLibraryActivity.class);
                intent.putExtra("drug_library", drugLibrary);

                break;

            case R.id.ll_address_set:
                Intent addIntent = new Intent(GoodsDetailsActivity.this, AddressSetActivity.class);


                startActivityForResult(addIntent, 1001);
                break;

            case R.id.storeInFoID://进入店铺

                intent = new Intent(GoodsDetailsActivity.this, StoreInFoActivity.class);
                intent.putExtra("store_id", store_id);

                break;
            case R.id.specNameID:

                specLinearLayoutID.setVisibility(View.VISIBLE);

                break;

            case R.id.addCartID:

//			specLinearLayoutID.setVisibility(View.VISIBLE);

                if (goodsNum > goodsStorage) {
                    Toast.makeText(GoodsDetailsActivity.this, "您购买的数量超过库存！", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(getLoginKey)) {

                    Toast.makeText(this, "当前用户未登录！", Toast.LENGTH_LONG).show();

                    Intent intentToLogin = new Intent(this, LoginActivity.class);
                    startActivity(intentToLogin);

                } else {
                    if (member_role.equals("0")) {
                        Toast.makeText(this, "当前用户未认证！", Toast.LENGTH_LONG).show();
                    } else {
                        shopAddCart();//添加购物车
                    }
                }
//                shopAddCart();//添加购物车

                break;

            case R.id.buyStepID:

                if (goodsNum > goodsStorage) {
                    Toast.makeText(GoodsDetailsActivity.this, "您购买的数量超过库存！", Toast.LENGTH_SHORT).show();
                    return;
                }


                if (TextUtils.isEmpty(getLoginKey)) {//价格登录后可见

                    Toast.makeText(this, "当前用户未登录！", Toast.LENGTH_LONG).show();

                    Intent intentToLogin = new Intent(this, LoginActivity.class);
                    startActivity(intentToLogin);

                } else {
                    if (member_role.equals("0")) {
                        Toast.makeText(this, "当前用户未认证！", Toast.LENGTH_LONG).show();
                    } else {
                        if (is_virtual.equals("1")) {
                            intent = new Intent(GoodsDetailsActivity.this, VBuyStep1Activity.class);
                            intent.putExtra("is_fcode", is_fcode);
                            intent.putExtra("ifcart", ifcart);
                            intent.putExtra("goodscount", Integer.parseInt(goodsNumID.getText().toString()));
                            intent.putExtra("cart_id", goods_id);
                        } else {
                            intent = new Intent(GoodsDetailsActivity.this, BuyStep1Activity.class);
                            intent.putExtra("is_fcode", is_fcode);
                            intent.putExtra("ifcart", ifcart);
                            intent.putExtra("cart_id", goods_id + "|" + Integer.parseInt(goodsNumID.getText().toString()));
                        }
                    }
                }

//                if (is_virtual.equals("1")) {
//                    intent = new Intent(GoodsDetailsActivity.this, VBuyStep1Activity.class);
//                    intent.putExtra("is_fcode", is_fcode);
//                    intent.putExtra("ifcart", ifcart);
//                    intent.putExtra("goodscount", Integer.parseInt(goodsNumID.getText().toString()));
//                    intent.putExtra("cart_id", goods_id);
//                } else {
//                    intent = new Intent(GoodsDetailsActivity.this, BuyStep1Activity.class);
//                    intent.putExtra("is_fcode", is_fcode);
//                    intent.putExtra("ifcart", ifcart);
//                    intent.putExtra("cart_id", goods_id + "|" + Integer.parseInt(goodsNumID.getText().toString()));
//                }


                break;
            case R.id.specLinearLayoutID:

                specLinearLayoutID.setVisibility(View.GONE);

                break;
            case R.id.sharekID:

                menuDialog.show();

                break;

            case R.id.numMinusID: {

                int temp;

                if ("1".equals(xskz_package)) {
                    temp = midPackage;
                } else if ("2".equals(xskz_package)) {
                    temp = bigPackage;
                } else {

                    temp = 1;
                }

                goodsNum -= temp;

                if (goodsNum <= temp) {

                    goodsNum = temp;

                }

                goodsNumID.setText("" + goodsNum);
            }
            break;

            case R.id.numPlusID: {

                int temp;

                if ("1".equals(xskz_package)) {
                    temp = midPackage;
                } else if ("2".equals(xskz_package)) {
                    temp = bigPackage;
                } else {

                    temp = 1;
                }

                goodsNum += temp;

                if (goodsNum >= upper_limit && upper_limit != 0) {
                    goodsNum = upper_limit;
                    goodsNumID.setText("" + goodsNum);
                    Toast.makeText(GoodsDetailsActivity.this, "限购" + upper_limit + "个", Toast.LENGTH_SHORT).show();
                }


                if (goodsNum > goodsStorage) {
                    goodsNum = goodsStorage;
                    goodsNumID.setText("" + goodsNum);
                    Toast.makeText(GoodsDetailsActivity.this, "您购买的数量超过库存！", Toast.LENGTH_SHORT).show();
                }
                goodsNumID.setText("" + goodsNum);
            }
            break;

            case R.id.isFavYesID:

                deleteFav();

                break;

            case R.id.isFavNoID:

                addFav();//添加收藏
                break;

            case R.id.goods_details_spec_OK://规格确认按钮

                specLinearLayoutID.setVisibility(View.GONE);
                break;

            case R.id.goods_details_spec_del://规格取消按钮

                specLinearLayoutID.setVisibility(View.GONE);
                break;

            case R.id.imID:

                if (myApplication.getMemberID().equals(t_id)) {
                    Toast.makeText(GoodsDetailsActivity.this, "对不起，您不可以和自己聊天", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (myApplication.isIMConnect()) {
                    intent = new Intent(GoodsDetailsActivity.this, IMSendMsgActivity.class);
                    intent.putExtra("t_id", t_id);
                    intent.putExtra("t_name", t_name);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(GoodsDetailsActivity.this);
                    builder.setTitle("IM离线通知").setMessage("您的IM账号已经离线，点击确定重新登录");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            myApplication.getmSocket().connect();
                        }
                    }).create().show();
                }

                break;

            case R.id.showCartLayoutID:
                //跳转购物车
                intent = new Intent(GoodsDetailsActivity.this, MainFragmentManager.class);
                //广播通知显示购物车
                sendBroadcast(new Intent(Constants.SHOW_CART_URL));
                break;

            case R.id.settingID:
                //比价功能
                Toast.makeText(GoodsDetailsActivity.this, "本功能暂不开启，请期待后续版本!", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1001:

                if (resultCode == RESULT_OK) {
                    String address_set = data.getStringExtra("address_set");

                    tv_address_set.setText(address_set);
                }
                break;

        }
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Constants.LOGIN_SUCCESS_URL)) {

                getLoginKey = myApplication.getLoginKey();
                member_role = myApplication.getMember_role();

                loadingGoodsDetailsData();
            }
        }
    };

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(Constants.LOGIN_SUCCESS_URL);
        registerReceiver(mBroadcastReceiver, myIntentFilter);  //注册广播
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
}
