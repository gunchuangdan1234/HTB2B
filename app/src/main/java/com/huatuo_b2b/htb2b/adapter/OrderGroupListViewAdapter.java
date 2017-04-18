package com.huatuo_b2b.htb2b.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.alipay.PayDemoActivity;
import com.huatuo_b2b.htb2b.bean.OrderGoodsList;
import com.huatuo_b2b.htb2b.bean.OrderGroupList;
import com.huatuo_b2b.htb2b.bean.OrderList;
import com.huatuo_b2b.htb2b.common.AnimateFirstDisplayListener;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.common.SystemHelper;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;
import com.huatuo_b2b.htb2b.ui.mine.OrderDeliverDetailsActivity;
import com.huatuo_b2b.htb2b.ui.mine.OutLinePayActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 我的订单列表适配器
 *
 * @author KingKong·HE
 * @Time 2014-1-6 下午12:06:09
 * @E-mail hjgang@bizpoer.com
 */
public class OrderGroupListViewAdapter extends BaseAdapter {
    private Context context;

    private LayoutInflater inflater;

    private ArrayList<OrderGroupList> orderLists;

    private MyShopApplication myApplication;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    private DisplayImageOptions options = SystemHelper.getDisplayImageOptions();

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private AlertDialog menuDialog;// 支付方式选择菜单  liubw

    private GridView menuGrid;

    private View menuView;

    private OrderGroupList groupList2FU;

    public OrderGroupListViewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        myApplication = (MyShopApplication) context.getApplicationContext();
        // 创建AlertDialog
        menuView = View.inflate(context, R.layout.gridview_menu, null);
        menuDialog = new AlertDialog.Builder(context).create();
        menuDialog.setView(menuView);
        menuGrid = (GridView) menuView.findViewById(R.id.gridview);
    }

    @Override
    public int getCount() {
        return orderLists == null ? 0 : orderLists.size();
    }

    @Override
    public Object getItem(int position) {
        return orderLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<OrderGroupList> getOrderLists() {
        return orderLists;
    }

    public void setOrderLists(ArrayList<OrderGroupList> orderLists) {
        this.orderLists = orderLists;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.listivew_order_item, null);
            holder = new ViewHolder();
            holder.textOrderAddTime = (TextView) convertView.findViewById(R.id.textOrderAddTime);
            holder.linearLayoutFLag = (LinearLayout) convertView.findViewById(R.id.linearLayoutFLag);
            holder.buttonFuKuan = (Button) convertView.findViewById(R.id.buttonFuKuan);
            holder.addViewID = (LinearLayout) convertView.findViewById(R.id.addViewID);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final OrderGroupList bean = orderLists.get(position);

        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderTime = time
                .format(Long.parseLong(bean.getAdd_time()) * 1000);
        holder.textOrderAddTime.setText("下单时间：" + orderTime);
        if (!bean.getPay_amount().equals("")
                && !bean.getPay_amount().equals("null")
                && !bean.getPay_amount().equals("0")
                && bean.getPay_amount() != null) {
            holder.linearLayoutFLag.setVisibility(View.VISIBLE);
        } else {
            holder.linearLayoutFLag.setVisibility(View.GONE);
        }

        if (!bean.getPay_amount().equals("0") && !bean.getPay_amount().equals("null") && bean.getPay_amount() != null) {
            String price = new DecimalFormat("#0.00").format(Double.parseDouble((bean.getPay_amount() == null ? "0.00" : bean.getPay_amount()) == "" ? "0.00" : bean.getPay_amount()));
            holder.buttonFuKuan.setText("订单支付(￥ " + price + ")");
        }

        holder.buttonFuKuan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击进入支付方式选择页面

                loadingPaymentListData();
                groupList2FU = orderLists.get(position);
                menuDialog.show();

            }
        });

        ArrayList<OrderList> orderLists = OrderList.newInstanceList(bean.getOrder_list());

        holder.addViewID.removeAllViews();

        for (int i = 0; i < orderLists.size(); i++) {
            OrderList orderList = orderLists.get(i);
            View orderListView = inflater.inflate(R.layout.listivew_order2_item, null);

            initUIOrderList(orderListView, orderList);

            holder.addViewID.addView(orderListView);
        }

        menuGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                menuDialog.dismiss();
                HashMap<String, Object> map = (HashMap<String, Object>) arg0
                        .getItemAtPosition(arg2);
                switch (Integer.parseInt(map.get("itemImage").toString())) {
                    case R.mipmap.weixin_appicon:// "微信"
                        loadingWXPaymentData(groupList2FU.getPay_sn());    //进入微信支付流程
                        break;
                    case R.mipmap.zhifubao_appicon:// "支付宝"

                        payforPage(arg2);
                        break;

                    case R.mipmap.outline_pay:// "线下支付"

                        Intent intent = new Intent(context, OutLinePayActivity.class);

                        context.startActivity(intent);

                        break;
                }
            }
        });

        return convertView;
    }

    private void payforPage(final int position) {

        final OrderGroupList bean = orderLists.get(position);
        String price = new DecimalFormat("#0.00").format(Double.parseDouble((bean.getPay_amount() == null ? "0.00" : bean.getPay_amount()) == "" ? "0.00" : bean.getPay_amount()));

//      Intent intent = new Intent(context, PayMentWebActivity.class); //wap 支付方式
        Intent intent = new Intent(context, PayDemoActivity.class); //库支付方式
        intent.putExtra("pay_sn", groupList2FU.getPay_sn());  //进入支付宝支付流程
        intent.putExtra("order_price", price);  //进入支付宝支付流程


        // context.startActivityForResult(intent);
        ((Activity) context).startActivityForResult(intent, 1);
        //startActivityForResult()方法得到另一个Activity的返回值
        // ((Activity)context).finish();
//liubw

//      启动一个ForResult的意图：

//      Intent intent = new Intent(MainAcitvity.this,RequestActivity.class);
//      //发送意图标示为REQUSET=1
//                        startActivityForResult(intent, REQUSET);
//
//
//
//                        B Activity处理数据：

//                        Intent intent=new Intent();
//                        intent.putExtra(KEY_USER_ID, et01.getText().toString());
//                        intent.putExtra(KEY_USER_PASSWORD, et02.getText().toString());
//                        setResult(RESULT_OK, intent);
//                        finish();
    }


    /**
     * 生成界面
     */
    public void initUIOrderList(View view, final OrderList orderList) {

        TextView textOrderStoreName = (TextView) view.findViewById(R.id.textOrderStoreName);
        TextView textOrderSN = (TextView) view.findViewById(R.id.textOrderSN);
        TextView textOrderAllPrice = (TextView) view.findViewById(R.id.textOrderAllPrice);
        TextView textOrderShippingFee = (TextView) view.findViewById(R.id.textOrderShippingFee);
        final TextView textOrderOperation = (TextView) view.findViewById(R.id.textOrderOperation);
        TextView textOrderSuccess = (TextView) view.findViewById(R.id.textOrderSuccess);
        TextView textOrderOperation2 = (TextView) view.findViewById(R.id.textOrderOperation2);
        LinearLayout addViewID = (LinearLayout) view.findViewById(R.id.addViewID);

        textOrderStoreName.setText(orderList.getStore_name());
        textOrderSN.setText("订单编号：" + (orderList.getOrder_sn()));
        textOrderAllPrice.setText("￥" + orderList.getOrder_amount());
        textOrderShippingFee.setText("￥" + orderList.getShipping_fee());
        ArrayList<OrderGoodsList> goodsDatas = OrderGoodsList.newInstanceList(orderList.getExtend_order_goods());

        if (orderList.getIf_cancel().equals("true")) {
            textOrderOperation.setText(Html.fromHtml("<a href='#'>取消</a>"));
        } else if (orderList.getIf_receive().equals("true")) {
            textOrderOperation.setText(Html.fromHtml("<a href='#'>确认收货</a>"));
        } else if (orderList.getIf_lock().equals("true")) {
            textOrderOperation.setText(Html.fromHtml("<a href='#'>锁定</a>"));
        } else {
            textOrderOperation.setText(Html.fromHtml("<a href='#'>删除</a>"));
        }

        if (orderList.getIf_deliver().equals("true")) {
            textOrderOperation2.setText(Html.fromHtml("<a href='#'>查看物流</a>"));
            textOrderOperation2.setVisibility(View.VISIBLE);
        } else {
            textOrderOperation2.setVisibility(View.GONE);
        }

        if (orderList.getState_desc() != null
                && !orderList.getState_desc().equals("")) {
            textOrderSuccess.setVisibility(View.VISIBLE);
            textOrderSuccess.setText(orderList.getState_desc());
        } else {
            textOrderSuccess.setVisibility(View.GONE);
        }

        textOrderOperation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = textOrderOperation.getText().toString();
                if (key.equals("取消")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("操作提示")
                            .setMessage("是否确认操作")
                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                        }
                                    })
                            .setPositiveButton("确认",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog,
                                                int whichButton) {
                                            loadingSaveOrderData(Constants.URL_ORDER_CANCEL, orderList.getOrder_id());
                                        }
                                    }).create().show();
                } else if (key.equals("确认收货")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("操作提示")
                            .setMessage("是否确认操作")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            })
                            .setPositiveButton("确认",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            loadingSaveOrderData(Constants.URL_ORDER_RECEIVE, orderList.getOrder_id());
                                        }
                                    }).create().show();
                } else if (key.equals("删除")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("操作提示")
                            .setMessage("是否确认操作")
                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                        }
                                    })
                            .setPositiveButton("确认",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog,
                                                int whichButton) {
                                            loadingSaveOrderData(Constants.URL_ORDER_DELETE, orderList.getOrder_id());
                                        }
                                    }).create().show();

                }
            }
        });
        textOrderOperation2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDeliverDetailsActivity.class);
                intent.putExtra("order_id", orderList.getOrder_id());
                context.startActivity(intent);
            }
        });

        for (int j = 0; j < goodsDatas.size(); j++) {
            OrderGoodsList ordergoodsList = goodsDatas.get(j);
            View orderGoodsListView = inflater.inflate(
                    R.layout.listivew_order_goods_item, null);
            addViewID.addView(orderGoodsListView);

            ImageView imageGoodsPic = (ImageView) orderGoodsListView
                    .findViewById(R.id.imageGoodsPic);
            TextView textGoodsName = (TextView) orderGoodsListView
                    .findViewById(R.id.textGoodsName);
            TextView textGoodsPrice = (TextView) orderGoodsListView
                    .findViewById(R.id.textGoodsPrice);
            TextView textGoodsNUM = (TextView) orderGoodsListView
                    .findViewById(R.id.textGoodsNUM);

            textGoodsName.setText(ordergoodsList.getGoods_name());
            textGoodsPrice.setText("￥" + ordergoodsList.getGoods_price());
            textGoodsNUM.setText(ordergoodsList.getGoods_num());

            imageLoader.displayImage(ordergoodsList.getGoods_image_url(),
                    imageGoodsPic, options, animateFirstListener);
        }

//        goodsListView.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                OrderGoodsList bean = (OrderGoodsList)
//                        goodsListView.getItemAtPosition(arg2);
//                if (bean != null) {
//                    Intent intent = new Intent(context, GoodsDetailsActivity.class);
//                    intent.putExtra("goods_id", bean.getGoods_id());
//                    context.startActivity(intent);
//                }
//            }
//        });
    }

    /**
     * 获取可用支付方式
     */
    public void loadingPaymentListData() {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        RemoteDataHandler.asyncLoginPostDataString(
                Constants.URL_ORDER_PAYMENT_LIST, params, myApplication,
                new Callback() {
                    @Override
                    public void dataLoaded(ResponseData data) {
                        if (data.getCode() == HttpStatus.SC_OK) {
                            String json = data.getJson();
                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                String JosnObj = jsonObject
                                        .getString("payment_list");
                                JSONArray arr = new JSONArray(JosnObj);
                                int size = null == arr ? 0 : arr.length();
                                ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<HashMap<String, Object>>();
                                for (int i = 0; i < size; i++) {
                                    String Values = arr.getString(i);
                                    HashMap<String, Object> map = new HashMap<String, Object>();
//                                    if (Values.equals("wxpay")) {
                                    if (Values.equals("wxpay")) {
                                        map.put("itemImage",
                                                R.mipmap.weixin_appicon);
                                        map.put("itemText", "微信支付");
                                    } else if (Values.equals("alipay")) {
                                        map.put("itemImage",
                                                R.mipmap.zhifubao_appicon);
                                        map.put("itemText", "支付宝");
                                    }
                                    hashMaps.add(map);
                                }
                                HashMap<String, Object> mapXianXia = new HashMap<String, Object>();
                                mapXianXia.put("itemImage",
                                        R.mipmap.outline_pay);
                                mapXianXia.put("itemText", "线下支付");

                                hashMaps.add(mapXianXia);

                                SimpleAdapter simperAdapter = new SimpleAdapter(
                                        context,
                                        hashMaps,
                                        R.layout.item_menu,
                                        new String[]{"itemImage", "itemText"},
                                        new int[]{R.id.item_image,
                                                R.id.item_text});
                                menuGrid.setAdapter(simperAdapter);
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                            try {
                                JSONObject obj2 = new JSONObject(json);
                                String error = obj2.getString("error");
                                if (error != null) {
                                    Toast.makeText(context, error,
                                            Toast.LENGTH_SHORT).show();
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
     * 获取微信参数
     *
     * @param pay_sn 支付编号
     */
    public void loadingWXPaymentData(String pay_sn) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("pay_sn", pay_sn);

        RemoteDataHandler.asyncLoginPostDataString(
                Constants.URL_MEMBER_WX_PAYMENT, params, myApplication,
                new Callback() {
                    @Override
                    public void dataLoaded(ResponseData data) {
                        if (data.getCode() == HttpStatus.SC_OK) {
                            String json = data.getJson();

//                                String appid = "wxb4ba3c02aa476ea1";// 微信开放平台appid
//                                String noncestr = "4364b8ff49d2d81271bca62597fdb829";// 随机字符串
//                                String packageStr = "Sign=WXPay";// 支付内容
//                                String partnerid = "10000100";// 财付通id
//                                String prepayid ="wx20160224164224f04ec2f8d10564332907";// 微信预支付编号
//                                String sign = "0F52EFCFE6F9FBC5885C075A744F6715";// 签名
//                                String timestamp = "1456303344";// 时间

                            try {
                                JSONObject jsonObject = new JSONObject(json);
                                String appid = jsonObject.getString("appid");// 微信开放平台appid
                                String noncestr = jsonObject
                                        .getString("noncestr");// 随机字符串
                                String packageStr = jsonObject
                                        .getString("package");// 支付内容
                                String partnerid = jsonObject
                                        .getString("partnerid");// 财付通id
                                String prepayid = jsonObject
                                        .getString("prepayid");// 微信预支付编号
                                String sign = jsonObject.getString("sign");// 签名
                                String timestamp = jsonObject
                                        .getString("timestamp");// 时间


                                IWXAPI api = WXAPIFactory.createWXAPI(context, appid);

                                PayReq req = new PayReq();
                                req.appId = appid;
                                req.partnerId = partnerid;
                                req.prepayId = prepayid;
                                req.nonceStr = noncestr;
                                req.timeStamp = timestamp;
                                req.packageValue = packageStr;
                                req.sign = sign;
                                req.extData = "app data"; // optional
                                Toast.makeText(context, "正常调起支付",
                                        Toast.LENGTH_SHORT).show();
                                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                api.sendReq(req);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                JSONObject obj2 = new JSONObject(json);
                                String error = obj2.getString("error");
                                if (error != null) {
                                    Toast.makeText(context, error,
                                            Toast.LENGTH_SHORT).show();
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
     * 确认收货、取消订单
     *
     * @param :orderID 订单ID
     */
    public void loadingSaveOrderData(String url, String order_id) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("order_id", order_id);


        System.out.println("#$#$#$#$#$$#$#$#$#$#$#$#$#");

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication,
                new Callback() {
                    @Override
                    public void dataLoaded(ResponseData data) {

                        System.out.println("#$#$#$#$#$$#$#$#$#$#$#$#$#" + data.getCode());

                        if (data.getCode() == HttpStatus.SC_OK) {
                            String json = data.getJson();
                            if (json.equals("1")) {
                                // Toast.makeText(context, "",
                                // Toast.LENGTH_SHORT).show();;
                                // 刷新界面
                                Intent mIntent = new Intent(
                                        Constants.PAYMENT_SUCCESS);
                                context.sendBroadcast(mIntent);
                            }
                            try {
                                JSONObject obj2 = new JSONObject(json);
                                String error = obj2.getString("error");
                                if (error != null) {
                                    Toast.makeText(context, error,
                                            Toast.LENGTH_SHORT).show();
                                    ;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    class ViewHolder {
        TextView textOrderAddTime;
        LinearLayout linearLayoutFLag;
        Button buttonFuKuan;
        LinearLayout addViewID;
    }
}
