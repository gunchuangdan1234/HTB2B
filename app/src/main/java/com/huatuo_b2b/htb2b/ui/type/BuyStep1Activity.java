package com.huatuo_b2b.htb2b.ui.type;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.StoreVoucherListViewAdapter;
import com.huatuo_b2b.htb2b.bean.AddressDetails;
import com.huatuo_b2b.htb2b.bean.BuyStep1;
import com.huatuo_b2b.htb2b.bean.CartList;
import com.huatuo_b2b.htb2b.bean.InvoiceInFO;
import com.huatuo_b2b.htb2b.bean.ManSongRulesInFo;
import com.huatuo_b2b.htb2b.bean.PlayGoodsList;
import com.huatuo_b2b.htb2b.bean.StoreVoucherList;
import com.huatuo_b2b.htb2b.bean.UpdateAddress;
import com.huatuo_b2b.htb2b.common.AnimateFirstDisplayListener;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.common.SystemHelper;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;
import com.huatuo_b2b.htb2b.ui.mine.OrderListActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 购买一步界面
 *
 * @author KingKong-HE
 * @Time 2015-1-15
 * @Email KingKong@QQ.COM
 */
public class BuyStep1Activity extends Activity implements OnClickListener {

    private String is_fcode;//是否为F码商品 1是 0否

    private String ifcart;//购物车购买标志 1购物车 0不是

    private String cart_id;//购买参数

    private boolean showAvailableRCBalance = false;//标识是否显示充值卡

    private boolean showAvailablePredeposit = false;//标识是否显示预存款

    private double goods_total = 0.00;//总价

    private double goods_freight = 0.00;//运费

    private double goods_voucher = 0.00;//折扣价格

    private String freight_hash; //记录运费hash

    private String offpay_hash; //货到付款hash

    private String offpay_hash_batch; //店铺是否支持货到付款hash

    private String inv_id;//记录发票ID

    private String address_id;//记录收货地址ID

    private String vat_hash;//记录发票信息hash

    private String if_pd_pay = "0";//记录是否充值卡支付  1-使用 0-不使用

    private String if_rcb_pay = "0";//记录是否预存款支付 1-使用 0-不使用

    private String pay_name = "online";//记录付款方式，可选值 online(线上付款) offline(货到付款)

    private MyShopApplication myApplication;

    private TextView areaInfoID, addressID, trueNameID, mobPhoneID, invInfoID, noAreaInfoID, textViewGoodsFreight, textViewGoodsTotal, textVoucher, textviewAllPrice;

    private RadioButton ifshowOnpayID, ifshowOffpayID;

    private LinearLayout predepositLayoutID, storeCartListID, addressInFoLayoutID;

    private CheckBox availablePredepositID, availableRCBalanceID;

    private Button commitID;

    private EditText editPasswordID, editFCodeID;

    private PopupWindow popupWindow; // 声明PopupWindow对象的引用

    private HashMap<String, StoreVoucherList> storeVoucherLists = new HashMap<String, StoreVoucherList>();
    ;//记录选中代金券

    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options = SystemHelper.getDisplayImageOptions();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_step1_view);

        myApplication = (MyShopApplication) getApplicationContext();

        is_fcode = getIntent().getStringExtra("is_fcode");
        ifcart = getIntent().getStringExtra("ifcart");
        cart_id = getIntent().getStringExtra("cart_id");

        initViewID();

    }

    /**
     * 初始化注册控件ID
     */
    public void initViewID() {

        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);

        editFCodeID = (EditText) findViewById(R.id.editFCodeID);

        areaInfoID = (TextView) findViewById(R.id.areaInfoID);

        addressID = (TextView) findViewById(R.id.addressID);

        trueNameID = (TextView) findViewById(R.id.trueNameID);

        mobPhoneID = (TextView) findViewById(R.id.mobPhoneID);

        invInfoID = (TextView) findViewById(R.id.invInfoID);

        noAreaInfoID = (TextView) findViewById(R.id.noAreaInfoID);

        textVoucher = (TextView) findViewById(R.id.textVoucher);

        editPasswordID = (EditText) findViewById(R.id.editPasswordID);

        textviewAllPrice = (TextView) findViewById(R.id.textviewAllPrice);

        textViewGoodsFreight = (TextView) findViewById(R.id.textViewGoodsFreight);

        textViewGoodsTotal = (TextView) findViewById(R.id.textViewGoodsTotal);

        ifshowOffpayID = (RadioButton) findViewById(R.id.ifshowOffpayID);

        ifshowOnpayID = (RadioButton) findViewById(R.id.ifshowOnpayID);

        predepositLayoutID = (LinearLayout) findViewById(R.id.predepositLayoutID);

        storeCartListID = (LinearLayout) findViewById(R.id.storeCartListID);

        addressInFoLayoutID = (LinearLayout) findViewById(R.id.addressInFoLayoutID);

        availablePredepositID = (CheckBox) findViewById(R.id.availablePredepositID);

        availableRCBalanceID = (CheckBox) findViewById(R.id.availableRCBalanceID);

        LinearLayout fCodeLayoutID = (LinearLayout) findViewById(R.id.fCodeLayoutID);

        commitID = (Button) findViewById(R.id.commitID);

        if (is_fcode != null && is_fcode.equals("1")) {
            fCodeLayoutID.setVisibility(View.VISIBLE);
            editFCodeID.setVisibility(View.VISIBLE);
        } else {
            fCodeLayoutID.setVisibility(View.GONE);
            editFCodeID.setVisibility(View.GONE);
        }

        MyifshowOnpayRadioButtonClickListener onpayRadioButtonClickListener = new MyifshowOnpayRadioButtonClickListener();
        ifshowOffpayID.setOnClickListener(onpayRadioButtonClickListener);
        ifshowOnpayID.setOnClickListener(onpayRadioButtonClickListener);

        imageBack.setOnClickListener(this);

        commitID.setOnClickListener(this);

        invInfoID.setOnClickListener(this);

        noAreaInfoID.setOnClickListener(this);

        addressInFoLayoutID.setOnClickListener(this);

        loadingBuyStep1Data();//加载购买一数据

        availablePredepositID.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if_pd_pay = "1";
                } else {
                    if_pd_pay = "0";
                }
            }
        });

        availableRCBalanceID.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if_rcb_pay = "1";
                } else {
                    if_rcb_pay = "0";
                }
            }
        });
    }

    /**
     * 加载购买一数据
     */
    public void loadingBuyStep1Data() {
        String url = Constants.URL_BUY_STEP1;
        ;//index.php?act=member_buy&op=test
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("cart_id", cart_id);
        params.put("ifcart", ifcart);

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();

                    BuyStep1 buyStep1 = BuyStep1.newInstanceList(json);

                    if (buyStep1 != null) {

                        AddressDetails addressDetails = AddressDetails.newInstanceDetails(buyStep1.getAddress_info());

                        //记录运费hash
                        freight_hash = buyStep1.getFreight_hash();

                        //记录发票hash
                        vat_hash = buyStep1.getVat_hash();

                        //判断显示隐藏收货地址
                        if (addressDetails != null) {
                            noAreaInfoID.setVisibility(View.GONE);
                            addressInFoLayoutID.setVisibility(View.VISIBLE);

                            //记录地址ID
                            address_id = addressDetails.getAddress_id();

                            //显示收货信息
                            areaInfoID.setText(addressDetails.getArea_info() == null ? "" : addressDetails.getArea_info());
                            addressID.setText(addressDetails.getAddress() == null ? "" : addressDetails.getAddress());
                            trueNameID.setText(addressDetails.getTrue_name() == null ? "" : addressDetails.getTrue_name());
                            mobPhoneID.setText(addressDetails.getMob_phone() == null ? "" : addressDetails.getMob_phone());

                            //更新收货地址
                            updataAddress(addressDetails.getCity_id(), addressDetails.getArea_id());

                        } else {

                            noAreaInfoID.setVisibility(View.VISIBLE);
                            addressInFoLayoutID.setVisibility(View.GONE);

                        }

                        InvoiceInFO inv_info = InvoiceInFO.newInstanceList(buyStep1.getInv_info());

                        if (inv_info != null) {

                            //记录发票ID
                            inv_id = inv_info.getInv_id() == null ? "0" : inv_info.getInv_id();

                            //显示发票信息
                            invInfoID.setText(inv_info.getContent() == null ? "" : inv_info.getContent());
                        }

                        //判断是否显示货到付款
                        if (buyStep1.getIfshow_offpay().equals("true")) {
                            ifshowOffpayID.setVisibility(View.VISIBLE);
                        } else {
                            ifshowOffpayID.setVisibility(View.GONE);
                        }

                        //判断是否显示预存款
                        if (buyStep1.getAvailable_predeposit() != null && !buyStep1.getAvailable_predeposit().equals("null") && !buyStep1.getAvailable_predeposit().equals("")
                                && !buyStep1.getAvailable_predeposit().equals("0") && !buyStep1.getAvailable_predeposit().equals("0.00")) {
                            showAvailablePredeposit = true;
                            availablePredepositID.setVisibility(View.VISIBLE);
                        } else {
                            showAvailablePredeposit = false;
                            availablePredepositID.setVisibility(View.GONE);
                        }

                        //判断是否显示充值卡
                        if (buyStep1.getAvailable_rc_balance() != null && !buyStep1.getAvailable_rc_balance().equals("null") && !buyStep1.getAvailable_rc_balance().equals("")
                                && !buyStep1.getAvailable_rc_balance().equals("0") && !buyStep1.getAvailable_rc_balance().equals("0.00")) {
                            showAvailableRCBalance = true;
                            availableRCBalanceID.setVisibility(View.VISIBLE);
                        } else {
                            showAvailableRCBalance = false;
                            availableRCBalanceID.setVisibility(View.GONE);
                        }

                        if (showAvailablePredeposit || showAvailableRCBalance) {
                            predepositLayoutID.setVisibility(View.VISIBLE);
                        } else {
                            predepositLayoutID.setVisibility(View.GONE);
                        }


                        //显示购买商品列表
                        try {

                            storeCartListID.removeAllViews();
                            goods_freight = 0.00;
                            goods_total = 0.00;

                            JSONObject jsonObj = new JSONObject(buyStep1.getStore_cart_list());
                            Iterator<?> iterator = jsonObj.keys();
                            ArrayList<PlayGoodsList> storeCartLists = new ArrayList<PlayGoodsList>();
                            while (iterator.hasNext()) {
                                String storeID = iterator.next().toString();
                                String Value = jsonObj.getString(storeID);
                                PlayGoodsList storecart = PlayGoodsList.newInstanceList(Value);
                                ArrayList<CartList> goodList = CartList.newInstanceList(storecart.getGoods_list());
                                storecart.setStore_id(storeID);

                                //添加显示店铺信息
                                LinearLayout playListView = (LinearLayout) getLayoutInflater().inflate(R.layout.buy_step1_playgoods_view, null);
                                LinearLayout goodsListLayoutID = (LinearLayout) playListView.findViewById(R.id.goodsListLayoutID);
                                final Button selectVoucheID = (Button) playListView.findViewById(R.id.selectVoucheID);
                                final TextView voucherPriceID = (TextView) playListView.findViewById(R.id.voucherPriceID);
                                final TextView manJianID = (TextView) playListView.findViewById(R.id.manJianID);

                                TextView storeNameID = (TextView) playListView.findViewById(R.id.storeNameID);
                                storeNameID.setText(storecart.getStore_name() == null ? "" : storecart.getStore_name());

                                //判断显示优惠券
                                String storeVoucher = storecart.getStore_voucher_list();
                                if (!storeVoucher.contains("[]")) {
                                    selectVoucheID.setVisibility(View.VISIBLE);
                                } else {
                                    selectVoucheID.setVisibility(View.GONE);
                                }

                                if (storeVoucher != null && !storeVoucher.equals("") && !storeVoucher.contains("[]")) {
                                    JSONObject jsonVoucher = new JSONObject(storeVoucher);
                                    Iterator<?> iteratorVoucher = jsonVoucher.keys();
                                    final ArrayList<StoreVoucherList> Voucherlist = new ArrayList<StoreVoucherList>();
                                    Voucherlist.add(new StoreVoucherList("0", storecart.getStore_id(), "0", "暂时不使用"));
                                    while (iteratorVoucher.hasNext()) {
                                        String voucherID = iteratorVoucher.next().toString();
                                        String voucherValue = jsonVoucher.getString(voucherID);
                                        StoreVoucherList Voucherbean = StoreVoucherList.newInstanceList(voucherValue);
                                        Voucherlist.add(Voucherbean);
                                    }

                                    voucherPriceID.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getPopupWindow(v, Voucherlist, selectVoucheID, voucherPriceID);//获取PopupWindow实例
                                        }
                                    });

                                    selectVoucheID.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            getPopupWindow(v, Voucherlist, selectVoucheID, voucherPriceID);//获取PopupWindow实例
                                        }
                                    });

                                }

                                //添加显示购买商品
                                for (int i = 0; i < goodList.size(); i++) {

                                    CartList bean = goodList.get(i);

                                    LinearLayout playListItem = (LinearLayout) getLayoutInflater().inflate(R.layout.buy_step1_playgoods_view_item, null);
                                    TextView goodsNameID = (TextView) playListItem.findViewById(R.id.goodsNameID);
                                    TextView goodsPriceID = (TextView) playListItem.findViewById(R.id.goodsPriceID);
                                    TextView goodsNumID = (TextView) playListItem.findViewById(R.id.goodsNumID);
                                    ImageView goodsPicID = (ImageView) playListItem.findViewById(R.id.goodsPicID);
                                    ImageView zengpinID = (ImageView) playListItem.findViewById(R.id.zengpinID);

                                    goodsNameID.setText(bean.getGoods_name() == null ? "" : bean.getGoods_name());
                                    goodsPriceID.setText("价格：￥" + (bean.getGoods_price() == null ? "" : bean.getGoods_price()));
                                    goodsNumID.setText("数量：" + (bean.getGoods_num() == null ? "" : bean.getGoods_num()));
                                    imageLoader.displayImage(bean.getGoods_image_url(), goodsPicID, options, animateFirstListener);

                                    if (bean.getPremiums().equals("true")) {
                                        zengpinID.setVisibility(View.VISIBLE);
                                    } else {
                                        zengpinID.setVisibility(View.GONE);
                                    }

                                    goodsListLayoutID.addView(playListItem);

                                }

                                storeCartListID.addView(playListView);

                                String manZheStr = storecart.getStore_manzhe_rule_list();

                                String manSong = storecart.getStore_mansong_rule_list();

                                if (!TextUtils.isEmpty(manSong)) {


                                    ManSongRulesInFo bean = ManSongRulesInFo.newInstanceList(manSong);
                                    double allprice = Double.parseDouble(storecart.getStore_goods_total());
                                    double price = Double.parseDouble(bean.getPrice() == null ? "0" : bean.getPrice());
                                    double discount = Double.parseDouble(bean.getDiscount() == null ? "0" : bean.getDiscount());

                                    if (bean != null && price > 0 && discount > 0) {
                                        manJianID.setText(Html.fromHtml("订单满<font color='#FF3300'>" + price + "元</font>，立减现金<font color='#339900'>" + discount + "元</font>"));
                                        manJianID.setVisibility(View.VISIBLE);
                                    } else {
                                        manJianID.setVisibility(View.GONE);
                                    }

                                    if (allprice >= price) {
                                        allprice = allprice - discount;
                                    }

                                    goods_total += allprice;


                                } else {
                                    ManSongRulesInFo bean = ManSongRulesInFo.newInstanceList(manZheStr);
                                    double allprice = Double.parseDouble(storecart.getStore_goods_total());
                                    double price = Double.parseDouble(bean.getPrice() == null ? "0" : bean.getPrice());
                                    double discount = Double.parseDouble(bean.getDiscount() == null ? "0" : bean.getDiscount());

                                    if (bean != null && price > 0 && discount > 0) {
                                        manJianID.setText(Html.fromHtml("订单满<font color='#FF3300'>" + price + "元</font>，立减现金<font color='#339900'>" + discount + "元</font>"));
                                        manJianID.setVisibility(View.VISIBLE);
                                    } else {
                                        manJianID.setVisibility(View.GONE);
                                    }

                                    if (allprice >= price) {
                                        allprice = allprice - discount;
                                    }

                                    goods_total += allprice;

                                }

                                storeCartLists.add(storecart);
                            }
//							aStoreCartListViewAdapter.setStoreCartLists(storeCartLists);
//							aStoreCartListViewAdapter.notifyDataSetChanged();
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }

                    upPriceUIData();//更新价格UI

                    try {
                        JSONObject obj = new JSONObject(json);
                        String error = obj.getString("error");
                        if (error != null) {
                            Toast.makeText(BuyStep1Activity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 更新收货地址
     */
    public void updataAddress(String city_id, String area_id) {
        String url = Constants.URL_UPDATE_ADDRESS;
        ;//
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("city_id", city_id);
        params.put("area_id", area_id);
        params.put("freight_hash", freight_hash);

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();

                    UpdateAddress updateAddress = UpdateAddress.newInstanceList(json);

                    if (updateAddress != null) {
                        //判断是否显示货到付款
                        if (updateAddress.getAllow_offpay().equals("1")) {
                            ifshowOffpayID.setVisibility(View.VISIBLE);
                        } else {
                            ifshowOffpayID.setVisibility(View.GONE);
                        }

                        //记录货到付款hash
                        offpay_hash = updateAddress.getOffpay_hash();

                        //店铺是否支持货到付款hash
                        offpay_hash_batch = updateAddress.getOffpay_hash_batch();

                        try {
                            goods_freight = 0.00;

                            JSONObject jsonObj = new JSONObject(updateAddress.getContent());

                            Iterator<?> iterator = jsonObj.keys();

                            while (iterator.hasNext()) {
                                String storeID = iterator.next().toString();
                                String Value = jsonObj.getString(storeID);
                                goods_freight += Double.parseDouble(Value);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        upPriceUIData();//更新价格UI

//						updateVoucher();
                    }
                } else {
//					Toast.makeText(BuyStep1Activity.this, getString(R.string.datas_loading_fail_prompt), Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow(View view, ArrayList<StoreVoucherList> Voucherlist, final Button selectVoucheID, final TextView voucherPriceID) {
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        View popupWindow_view = getLayoutInflater().inflate(R.layout.popupwindow_vouche_view, null, false);
        final ListView listViewID = (ListView) popupWindow_view.findViewById(R.id.listViewID);

        StoreVoucherListViewAdapter adapter = new StoreVoucherListViewAdapter(this);

        adapter.setDatas(Voucherlist);

        listViewID.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        // 设置动画效果
//	    popupWindow.setAnimationStyle(R.style.PopupVoucherAnimation);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });

        listViewID.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }

                StoreVoucherList bean = (StoreVoucherList) listViewID.getItemAtPosition(arg2);

                if (bean != null) {
                    if (bean.getVoucher_t_id().equals("0")) {
                        storeVoucherLists.remove(bean.getStore_id());
                        selectVoucheID.setVisibility(View.VISIBLE);
                        voucherPriceID.setVisibility(View.GONE);
                    } else {
                        selectVoucheID.setVisibility(View.GONE);
                        voucherPriceID.setVisibility(View.VISIBLE);
                        storeVoucherLists.put(bean.getStore_id(), bean);
                        ;
                        voucherPriceID.setText("￥ " + (bean.getVoucher_price() == null ? "0" : bean.getVoucher_price()));
                    }
                }

                //记录折扣价格
                goods_voucher = 0.00;
                Iterator iterator = storeVoucherLists.keySet().iterator();
                while (iterator.hasNext()) {

                    String store_id = (String) iterator.next();
                    StoreVoucherList vbean = storeVoucherLists.get(store_id);

                    if (vbean != null) {
                        goods_voucher += Double.parseDouble(vbean.getVoucher_price() == null ? "0.00" : vbean.getVoucher_price());
                    }
                }

                upPriceUIData();//更新价格UI
            }
        });
    }

    /**
     * 购买第二布 提交订单
     *
     * @param :key               当前登录令牌
     * @param :ifcart            购物车购买标志 1
     * @param :cart_id           购买参数
     * @param :address_id        收货地址编号
     * @param :vat_hash          发票信息hash，第一步接口提供
     * @param :offpay_hash       是否支持货到付款hash，通过更换收货地址接口获得
     * @param :offpay_hash_batch 店铺是否支持货到付款hash
     * @param :pay_name          付款方式，可选值 online(线上付款) offline(货到付款)
     * @param :invoice_id        发票信息编号
     * @param :voucher           代金券，内容以竖线分割 voucher_t_id|store_id|voucher_price，多个店铺用逗号分割，例：10|2|10,1|3|10
     * @param :pd_pay            是否使用预存款支付 1-使用 0-不使用
     * @param :rc_pay            是否使用预存款支付 1-使用 0-不使用
     * @param :password          用户支付密码，启动预存款支付时需要提交
     * @param :fcode             F码购买时需提交
     */
    public void sendBuyStep2Data(String password) {
        String url = Constants.URL_BUY_STEP2;
        ;

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("cart_id", cart_id);
        params.put("ifcart", ifcart);
        params.put("address_id", address_id);
        params.put("vat_hash", vat_hash);
        params.put("offpay_hash", offpay_hash);
        params.put("offpay_hash_batch", offpay_hash_batch);
        params.put("pay_name", pay_name);
        params.put("invoice_id", inv_id);
        params.put("pd_pay", if_pd_pay);
        params.put("rcb_pay", if_rcb_pay);
        params.put("password", password);

        if (is_fcode != null && is_fcode.equals("1")) {
            String fcode = editFCodeID.getText().toString();
            params.put("fcode", fcode);
        }

        if (storeVoucherLists.size() > 0) {
            String voucher = "";
            Iterator<?> iteratorVoucher = storeVoucherLists.keySet().iterator();
            ;
            while (iteratorVoucher.hasNext()) {
                String voucherID = iteratorVoucher.next().toString();
                StoreVoucherList voucherbean = storeVoucherLists.get(voucherID);
                voucher += "," + voucherbean.getVoucher_t_id() + "|" + voucherbean.getStore_id() + "|" + voucherbean.getVoucher_price();
            }
            voucher = voucher.replaceFirst("", voucher);
            params.put("voucher", voucher);
        }

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();

                    try {
                        JSONObject obj = new JSONObject(json);
                        String error = obj.getString("error");
                        if (error != null) {
                            Toast.makeText(BuyStep1Activity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    startActivity(new Intent(BuyStep1Activity.this, OrderListActivity.class));
                    finish();

//						Intent intent = new Intent(BuyStep1Activity.this,OrderListActivity.class);
//						BuyStep1Activity.this.startActivity(intent);
//						BuyStep1Activity.this.finish();
//						Intent mIntent = new Intent(Constants.APP_BORADCASTRECEIVER); 
//						sendBroadcast(mIntent); 
                } else {
//						Toast.makeText(BuyStep1Activity.this, getString(R.string.datas_loading_fail_prompt), Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }

    /**
     * 验证支付密码
     *
     * @param :key  登录返回标识
     * @param :支付密码
     */
    public void CheackPassword(final String password) {
        String url = Constants.URL_CHECK_PASSWORD;
        ;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("password", password);
        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    if (json.equals("1")) {
                        sendBuyStep2Data(password);
                    }
                    try {
                        JSONObject obj = new JSONObject(json);
                        String error = obj.getString("error");
                        if (error != null) {
                            Toast.makeText(BuyStep1Activity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//						Toast.makeText(BuyStep1Activity.this, getString(R.string.datas_loading_fail_prompt), Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }

    /**
     * 获取PopupWindow实例
     */
    private void getPopupWindow(View view, ArrayList<StoreVoucherList> voucherlist, Button selectVoucheID, TextView voucherPriceID) {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow(view, voucherlist, selectVoucheID, voucherPriceID);
        }
    }

    /**
     * 更新价格UI
     */
    public void upPriceUIData() {

        //显示折扣价格
        textVoucher.setText("-￥" + goods_voucher);

        //显示运费
        textViewGoodsFreight.setText(" +￥" + goods_freight);

        //显示商品总价
        textViewGoodsTotal.setText(" ￥" + goods_total);

        //显示总价
        textviewAllPrice.setText("￥" + (goods_total + goods_freight + goods_voucher));
    }

    class MyifshowOnpayRadioButtonClickListener implements OnClickListener {
        public void onClick(View v) {
            RadioButton btn = (RadioButton) v;
            switch (btn.getId()) {
                case R.id.ifshowOnpayID:

                    if (showAvailablePredeposit || showAvailableRCBalance) {
                        predepositLayoutID.setVisibility(View.VISIBLE);
                    }

                    pay_name = "online";//online(线上付款) offline(货到付款)
                    break;
                case R.id.ifshowOffpayID:

                    predepositLayoutID.setVisibility(View.GONE);

                    if_pd_pay = "0";
                    if_rcb_pay = "0";

                    pay_name = "offline";//online(线上付款) offline(货到付款)

                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SELECT_INVOICE) {
            inv_id = data.getStringExtra("inv_id");
            String inv_context = data.getStringExtra("inv_context");
            invInfoID.setText(inv_context == null ? "" : inv_context);
        }

        if (resultCode == Constants.SELECT_ADDRESS) {
            address_id = data.getStringExtra("address_id");
            String city_id = data.getStringExtra("city_id");
            String area_id = data.getStringExtra("area_id");
            String tureName = data.getStringExtra("tureName");
            String addressInFo = data.getStringExtra("addressInFo");
            String address = data.getStringExtra("address");
            String mobPhone = data.getStringExtra("mobPhone");

            //显示收货信息
            areaInfoID.setText(addressInFo == null ? "" : addressInFo);
            addressID.setText(address == null ? "" : address);
            trueNameID.setText(tureName == null ? "" : tureName);
            mobPhoneID.setText(mobPhone == null ? "" : mobPhone);

            addressInFoLayoutID.setVisibility(View.VISIBLE);
            noAreaInfoID.setVisibility(View.GONE);

            //更新收货地址
            updataAddress(city_id, area_id);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:

                finish();

                break;

            case R.id.invInfoID:
                Intent intent = new Intent(BuyStep1Activity.this, InvoiceListActivity.class);
                startActivityForResult(intent, 2);

                break;

            case R.id.commitID:

                //判断是否使用预存款或者充值卡如果使用验证密码
                if (if_pd_pay.equals("1") || if_rcb_pay.equals("1")) {

                    String password = editPasswordID.getText().toString();

                    if (password != null && !password.equals("") && !password.equals("null")) {
                        CheackPassword(password);
                    } else {
                        Toast.makeText(BuyStep1Activity.this, "支付密码不能为空", Toast.LENGTH_SHORT).show();
                        ;
                    }
                } else {
                    sendBuyStep2Data("");
                }

                break;

            case R.id.noAreaInfoID:

                Intent noAddressIntent = new Intent(BuyStep1Activity.this, AddressListActivity.class);
                noAddressIntent.putExtra("addressFlag", "1");//1是提交订单跳转过去的 0或者没有是 个人中心
                startActivityForResult(noAddressIntent, 5);

                break;

            case R.id.addressInFoLayoutID:

                Intent addressIntent = new Intent(BuyStep1Activity.this, AddressListActivity.class);
                addressIntent.putExtra("addressFlag", "1");//1是提交订单跳转过去的 0或者没有是 个人中心
                startActivityForResult(addressIntent, 5);

                break;

            default:
                break;
        }
    }
}
