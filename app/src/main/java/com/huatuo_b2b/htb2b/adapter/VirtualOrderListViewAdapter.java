package com.huatuo_b2b.htb2b.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.VRCodeLlist;
import com.huatuo_b2b.htb2b.bean.VirtualList;
import com.huatuo_b2b.htb2b.common.AnimateFirstDisplayListener;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.common.SystemHelper;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;
import com.huatuo_b2b.htb2b.ui.mine.PayMentWebActivity;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 虚拟订单列表适配器
 *
 * @author KingKong·HE
 * @Time 2014-1-6 下午12:06:09
 * @E-mail hjgang@bizpoer.com
 */
public class VirtualOrderListViewAdapter extends BaseAdapter {
    private Context context;

    private LayoutInflater inflater;

    private ArrayList<VirtualList> virtualLists;

    private MyShopApplication myApplication;

    private Map<Integer, Boolean> isCheckMap = new HashMap<Integer, Boolean>();

    private AlertDialog menuDialog;// menu菜单Dialog

    private GridView menuGrid;

    private VirtualList Qbean;

    private View menuView;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    private DisplayImageOptions options = SystemHelper.getDisplayImageOptions();

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public VirtualOrderListViewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        myApplication = (MyShopApplication) context.getApplicationContext();

        menuView = View.inflate(context, R.layout.gridview_menu, null);
        // 创建AlertDialog
        menuDialog = new AlertDialog.Builder(context).create();
        menuDialog.setView(menuView);
        menuGrid = (GridView) menuView.findViewById(R.id.gridview);
    }

    @Override
    public int getCount() {
        return virtualLists == null ? 0 : virtualLists.size();
    }

    @Override
    public Object getItem(int position) {
        return virtualLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<VirtualList> getVirtualLists() {
        return virtualLists;
    }

    public void setVirtualLists(ArrayList<VirtualList> virtualLists) {
        this.virtualLists = virtualLists;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final VirtualList bean = virtualLists.get(position);
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.listivew_virtual_order_item, null);
            holder = new ViewHolder();
            holder.textOrderStoreName = (TextView) convertView.findViewById(R.id.textOrderStoreName);
            holder.textOrderSN = (TextView) convertView.findViewById(R.id.textOrderSN);
            holder.textGoodsPrice = (TextView) convertView.findViewById(R.id.textGoodsPrice);
            holder.textGoodsNUM = (TextView) convertView.findViewById(R.id.textGoodsNUM);
            holder.textOrderAllPrice = (TextView) convertView.findViewById(R.id.textOrderAllPrice);
            holder.buttonFuKuan = (Button) convertView.findViewById(R.id.buttonFuKuan);
            holder.textOrderSuccess = (TextView) convertView.findViewById(R.id.textOrderSuccess);
            holder.textOrderOperation = (TextView) convertView.findViewById(R.id.textOrderOperation);
            holder.imageGoodsPic = (ImageView) convertView.findViewById(R.id.imageGoodsPic);
            holder.textGoodsName = (TextView) convertView.findViewById(R.id.textGoodsName);
            holder.detailsVisibility = (CheckBox) convertView.findViewById(R.id.detailsVisibility);
            holder.linearLayoutDelverList = (LinearLayout) convertView.findViewById(R.id.linearLayoutDelverList);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//		SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//		String orderTime =time.format(Long.parseLong(bean.getAdd_time())*1000);
        holder.textOrderStoreName.setText("店铺名称：" + (bean.getStore_name() == null ? "" : bean.getStore_name()));
        holder.textOrderSN.setText("订单编号：" + (bean.getOrder_sn() == null ? "" : bean.getOrder_sn()));
        holder.textGoodsPrice.setText("￥" + (bean.getGoods_price() == null ? "0.00" : bean.getGoods_price()));
        holder.textGoodsNUM.setText(bean.getGoods_num() == null ? "0" : bean.getGoods_num());
        holder.textOrderAllPrice.setText("￥" + (bean.getOrder_amount() == null ? "0.00" : bean.getOrder_amount()));
        holder.textGoodsName.setText(bean.getGoods_name());

        if (bean.getOrder_state().equals("10")) {
            holder.buttonFuKuan.setVisibility(View.VISIBLE);
            ;
            holder.textOrderSuccess.setVisibility(View.GONE);
            ;
            holder.textOrderOperation.setVisibility(View.VISIBLE);
            ;
            holder.textOrderOperation.setText(Html.fromHtml("<a href='#'>取消</a>"));
        } else if (bean.getOrder_state().equals("20")) {
            holder.textOrderSuccess.setText("已支付");
            holder.textOrderSuccess.setVisibility(View.VISIBLE);
            ;
            holder.buttonFuKuan.setVisibility(View.GONE);
            ;
            holder.textOrderOperation.setVisibility(View.GONE);
        } else if (bean.getOrder_state().equals("40")) {
            holder.textOrderSuccess.setText("交易成功");
            holder.textOrderSuccess.setVisibility(View.VISIBLE);
            ;
            holder.buttonFuKuan.setVisibility(View.GONE);
            ;
            holder.textOrderOperation.setVisibility(View.GONE);
        } else if (bean.getOrder_state().equals("0")) {
            holder.textOrderSuccess.setText("已取消");
            holder.textOrderSuccess.setVisibility(View.VISIBLE);
            ;
            holder.buttonFuKuan.setVisibility(View.GONE);
            ;
            holder.textOrderOperation.setVisibility(View.GONE);
        } else {
            holder.buttonFuKuan.setVisibility(View.GONE);
            ;
            holder.textOrderSuccess.setVisibility(View.GONE);
            ;
            holder.textOrderOperation.setVisibility(View.GONE);
        }
        imageLoader.displayImage(bean.getGoods_image_url(), holder.imageGoodsPic, options, animateFirstListener);

        holder.textOrderOperation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("操作提示")
                        .setMessage("是否确认操作")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                loadingSaveOrderData(Constants.URL_MEMBER_VR_ORDER_CANCEL, bean.getOrder_id());
                            }
                        }).create().show();
            }
        });
        holder.buttonFuKuan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                menuDialog.show();
                Qbean = bean;
                loadingPaymentListData();
            }
        });
        if (isCheckMap.containsKey(position) && isCheckMap.get(position) == true) {
            holder.linearLayoutDelverList.setVisibility(View.VISIBLE);
            holder.detailsVisibility.setBackgroundResource(R.mipmap.qb_ad_expand);
            loading_member_vr_order(holder.linearLayoutDelverList, bean.getOrder_id());
        } else {
            holder.detailsVisibility.setBackgroundResource(R.mipmap.qb_ad_coll);
            holder.linearLayoutDelverList.setVisibility(View.GONE);
        }
        holder.detailsVisibility.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //将选中的放入hashmap中
                    isCheckMap.put(position, isChecked);
                } else {
                    //取消选中的则剔除
                    isCheckMap.remove(position);
                }
                notifyDataSetChanged();
            }
        });

        menuGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                menuDialog.dismiss();
                HashMap<String, Object> map = (HashMap<String, Object>) arg0.getItemAtPosition(arg2);
                switch (Integer.parseInt(map.get("itemImage").toString())) {
                    case R.mipmap.sns_weixin_icon:// "微信"

                        loadingWXPaymentData(Qbean.getOrder_sn());

                        break;
                    case R.mipmap.zhifubao_appicon:// "支付宝"
                        Intent intent = new Intent(context, PayMentWebActivity.class);
                        intent.putExtra("order_sn", Qbean.getOrder_sn());
                        context.startActivity(intent);
                        break;
                }
            }
        });
        return convertView;
    }


    public void loading_member_vr_order(final LinearLayout layout, String order_id) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("order_id", order_id);
        RemoteDataHandler.asyncLoginPostDataString(Constants.URL_MEMBER_VR_ODER, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                layout.removeAllViews();
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    try {
                        JSONObject object = new JSONObject(json);
                        String objJson = object.getString("code_list");
                        ArrayList<VRCodeLlist> code_list = VRCodeLlist.newInstanceList(objJson);
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
                        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                        for (int i = 0; i < code_list.size(); i++) {
                            TextView textView = new TextView(context);
                            textView.setTextSize(15);
                            textView.setPadding(5, 5, 5, 5);
                            Drawable drawable = context.getResources().getDrawable(R.mipmap.kakalib_url_white);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
                            textView.setCompoundDrawables(drawable, null, null, null);
                            textView.setCompoundDrawablePadding(5);
                            String scanningTime = formatter.format(Long.parseLong(code_list.get(i).getVr_indate()) * 1000);
                            textView.setText(Html.fromHtml(scanningTime + "&nbsp;&nbsp;" + code_list.get(i).getVr_code()));
                            textView.setTextColor(ColorStateList.valueOf(R.color.black));
                            ;
                            layout.addView(textView);
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        JSONObject obj2 = new JSONObject(json);
                        String error = obj2.getString("error");
                        if (error != null) {
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
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
                Constants.URL_MEMBER_WX_VPAYMENT, params, myApplication,
                new Callback() {
                    @Override
                    public void dataLoaded(ResponseData data) {
                        if (data.getCode() == HttpStatus.SC_OK) {
                            String json = data.getJson();
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
                                        .getString("timestamp");// 时间戳

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

                            } catch (JSONException e) {
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
                                    if (Values.equals("wxpay")) {
                                        map.put("itemImage",
                                                R.mipmap.sns_weixin_icon);
                                        map.put("itemText", "微信支付");
                                    } else if (Values.equals("alipay")) {
                                        map.put("itemImage",
                                                R.mipmap.zhifubao_appicon);
                                        map.put("itemText", "支付宝");
                                    }
                                    hashMaps.add(map);
                                }
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
     * 确认收货、取消订单
     *
     * @param :orderID 订单ID
     */
    public void loadingSaveOrderData(String url, String order_id) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("order_id", order_id);

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication,
                new Callback() {
                    @Override
                    public void dataLoaded(ResponseData data) {
                        if (data.getCode() == HttpStatus.SC_OK) {
                            String json = data.getJson();
                            if (json.equals("1")) {
                                // Toast.makeText(context, "",
                                // Toast.LENGTH_SHORT).show();;
                                // 刷新界面
                                Intent mIntent = new Intent(
                                        Constants.VPAYMENT_SUCCESS);
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
        TextView textOrderStoreName;
        TextView textOrderSN;
        TextView textGoodsPrice;
        TextView textGoodsNUM;
        TextView textOrderAllPrice;
        TextView textOrderSuccess;
        TextView textOrderOperation;
        Button buttonFuKuan;
        ImageView imageGoodsPic;
        TextView textGoodsName;
        CheckBox detailsVisibility;
        LinearLayout linearLayoutDelverList;
    }
}
