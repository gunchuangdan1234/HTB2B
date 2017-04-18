/**
 * ProjectName:AndroidShopNC2014Moblie
 * PackageName:net.shopnc.android.adapter
 * FileNmae:CommendGridViewAdapter.java
 */
package com.huatuo_b2b.htb2b.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.CartGood;
import com.huatuo_b2b.htb2b.bean.CartList;
import com.huatuo_b2b.htb2b.common.AnimateFirstDisplayListener;
import com.huatuo_b2b.htb2b.common.SystemHelper;
import com.huatuo_b2b.htb2b.ui.cart.CartFragment;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * 购物车ListView适配器
 *
 * @author KingKong·HE
 * @Time 2014-1-6 下午12:06:09
 * @E-mail hjgang@bizpoer.com
 */
public class CartListViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<CartList> cartLists;

    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options = SystemHelper.getDisplayImageOptions();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();


    public HashMap<String, Boolean> cartMinusPlusFlag = new HashMap<String, Boolean>();//标识购物车编辑数量的保存

    private CartFragment cartFragment;

    private int cartGoodsNum = 0; //记录购物车数量

    public CartListViewAdapter(Context context, CartFragment cartFragment) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.cartFragment = cartFragment;
    }

    @Override
    public int getCount() {
        return cartLists == null ? 0 : cartLists.size();
    }

    @Override
    public Object getItem(int position) {
        return cartLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<CartList> getCartLists() {
        return cartLists;
    }

    public void setCartLists(ArrayList<CartList> cartLists) {
        this.cartLists = cartLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.listivew_cart_item, null);
            holder = new ViewHolder();
            holder.minusPlusLayout = convertView.findViewById(R.id.minusPlusLayout);
            holder.numLayoutID = convertView.findViewById(R.id.numLayoutID);
            holder.cartEditID = (Button) convertView.findViewById(R.id.cartEditID);
            holder.cartNumSaveID = (Button) convertView.findViewById(R.id.cartNumSaveID);
            holder.storeNameID = (TextView) convertView.findViewById(R.id.storeNameID);
            holder.goodsNameID = (TextView) convertView.findViewById(R.id.goodsNameID);
            holder.goodsPriceID = (TextView) convertView.findViewById(R.id.goodsPriceID);
            holder.goodsNumID = (TextView) convertView.findViewById(R.id.goodsNumID);
            holder.goodsPicID = (ImageView) convertView.findViewById(R.id.goodsPicID);
            holder.cartCheakBoxID = (Button) convertView.findViewById(R.id.cartCheakBoxID);
            holder.cartNumID = (EditText) convertView.findViewById(R.id.cartNumID);
            holder.cartMinusID = (Button) convertView.findViewById(R.id.cartMinusID);
            holder.cartPlusID = (Button) convertView.findViewById(R.id.cartPlusID);
            holder.goodsDelID = (TextView) convertView.findViewById(R.id.goodsDelID);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final CartList bean = cartLists.get(position);

        holder.storeNameID.setText(bean.getStore_name() == null ? "" : bean.getStore_name());
        holder.goodsNameID.setText(bean.getGoods_name() == null ? "" : bean.getGoods_name());
        holder.goodsPriceID.setText(bean.getGoods_price() == null ? "￥0.00" : "￥" + bean.getGoods_price());
        holder.goodsNumID.setText(bean.getGoods_num() == null ? "x1" : "x" + bean.getGoods_num());
        imageLoader.displayImage(bean.getGoods_image_url(), holder.goodsPicID, options, animateFirstListener);

        if (cartFragment.selectNum < cartFragment.cartList.size()) {
            cartFragment.upflag = false;
            cartFragment.allCheackCartID.setChecked(false);
            cartFragment.upflag = true;
        } else if (cartFragment.selectNum == cartFragment.cartList.size()) {
            cartFragment.upflag = true;
            cartFragment.allCheackCartID.setChecked(true);
        }

        //判断选中的商品
        CartGood cartGoodsBean = cartFragment.cartFlag.get(bean.getCart_id());
        if (cartGoodsBean != null && cartGoodsBean.isFlag()) {
            holder.cartCheakBoxID.setBackgroundResource(R.mipmap.checkbox_flat_on);
        } else {
            holder.cartCheakBoxID.setBackgroundResource(R.mipmap.checkbox_flat_off);
        }

        String price = new DecimalFormat("#0.00").format(cartFragment.sumPrice);

        //显示合计价格
        cartFragment.allPriceID.setText("￥" + price);

        //判断显示编辑数据
        if (cartMinusPlusFlag.get(bean.getCart_id()) != null && cartMinusPlusFlag.get(bean.getCart_id())) {
            holder.minusPlusLayout.setVisibility(View.VISIBLE);
            holder.cartNumSaveID.setVisibility(View.VISIBLE);
            holder.cartEditID.setVisibility(View.GONE);
            holder.goodsPriceID.setVisibility(View.GONE);
            holder.goodsNumID.setVisibility(View.GONE);
            cartGoodsNum = Integer.parseInt(bean.getGoods_num() == null ? "1" : bean.getGoods_num());
            holder.cartNumID.setText(cartGoodsNum + "");
        } else {
            holder.minusPlusLayout.setVisibility(View.GONE);
            holder.cartNumSaveID.setVisibility(View.GONE);
            holder.goodsPriceID.setVisibility(View.VISIBLE);
            holder.goodsNumID.setVisibility(View.VISIBLE);
            holder.cartEditID.setVisibility(View.VISIBLE);
        }

        holder.cartEditID.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                cartMinusPlusFlag.clear();

                cartMinusPlusFlag.put(bean.getCart_id(), true);

                notifyDataSetChanged();
            }
        });

        holder.goodsDelID.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {//删除购物车中的商品
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("功能选择")
                        .setMessage("您确定在购物车中移除此商品？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        cartFragment.cartDetele(bean.getCart_id());//删除购物车中的商品

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartGoodsNum = 1;
                        holder.cartNumID.setText(cartGoodsNum + "");
                    }
                }).create().show();
            }
        });

        holder.cartNumSaveID.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                cartMinusPlusFlag.remove(bean.getCart_id());

                notifyDataSetChanged();

                double dgoods_price = Double.parseDouble(bean.getGoods_price() == null ? "0.00" : bean.getGoods_price());
                double dgoods_num = Double.parseDouble(bean.getGoods_num() == null ? "0" : bean.getGoods_num());
                double oldPrice = dgoods_price * dgoods_num;

                cartFragment.cartEditQuantity(bean.getCart_id(), cartGoodsNum + "", oldPrice);//修改购物车数量
            }
        });

        holder.cartCheakBoxID.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                CartGood cartGoodsBean = cartFragment.cartFlag.get(bean.getCart_id());

                double goodsPrice = Double.parseDouble(bean.getGoods_price() == null ? "0.00" : bean.getGoods_price());

                int goodsNum = Integer.parseInt(bean.getGoods_num() == null ? "0" : bean.getGoods_num());

                double goodsAllPrice = goodsPrice * goodsNum;

                String goodsID = bean.getCart_id();


                if (cartGoodsBean != null && cartGoodsBean.isFlag()) {

                    boolean flag = false;


                    cartFragment.selectNum--;

                    cartFragment.cartFlag.put(bean.getCart_id(), new CartGood(goodsPrice, goodsAllPrice, goodsNum, goodsID, flag));
                    cartFragment.sumPrice -= goodsAllPrice;
                } else {

                    boolean flag = true;

                    cartFragment.selectNum++;

                    cartFragment.cartFlag.put(bean.getCart_id(), new CartGood(goodsPrice, goodsAllPrice, goodsNum, goodsID, flag));
                    cartFragment.sumPrice += goodsAllPrice;
                }

                Iterator it = cartFragment.cartFlag.keySet().iterator();
                int count = 0;
                while (it.hasNext()) {
                    String cartID = it.next().toString();
                    CartGood bean = cartFragment.cartFlag.get(cartID);
                    if (bean.isFlag()) { //全选
                        count++;
                    }
                }
                if (cartFragment.selectNum < cartFragment.cartList.size()) {
                    cartFragment.upflag = false;
                    cartFragment.allCheackCartID.setChecked(false);
                    cartFragment.upflag = true;
                } else if (cartFragment.selectNum == cartFragment.cartList.size()) {
                    cartFragment.upflag = true;
                    cartFragment.allCheackCartID.setChecked(true);
                }
                notifyDataSetChanged();
            }
        });

        final String xskz_package = bean.getXskz_package();
        final int midPackage = Integer.parseInt(TextUtils.isEmpty(bean.getMid_package()) ? "0" : bean.getMid_package());
        final int bigPackage = Integer.parseInt(TextUtils.isEmpty(bean.getBig_package()) ? "0" : bean.getBig_package());
        final int goods_storage = Integer.parseInt(TextUtils.isEmpty(bean.getGoods_storage()) ? "0" : bean.getGoods_storage());
        holder.cartMinusID.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = 0;


                if ("1".equals(xskz_package)) {
                    temp = midPackage;
                } else if ("2".equals(xskz_package)) {
                    temp = bigPackage;
                } else {

                    temp = 1;
                }


                cartGoodsNum -= temp;


                if (cartGoodsNum <= temp) {
                    cartGoodsNum = temp;
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("功能选择")
                            .setMessage("您确定在购物车中移除此商品？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {

                            cartFragment.cartDetele(bean.getCart_id());//删除购物车中的商品

                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            if ("1".equals(xskz_package)) {
                                cartGoodsNum = midPackage;
                            } else if ("2".equals(xskz_package)) {
                                cartGoodsNum = bigPackage;
                            } else {

                                cartGoodsNum = 1;
                            }
//                            cartGoodsNum = 1;
                            holder.cartNumID.setText(cartGoodsNum + "");
                        }
                    }).create().show();
                }
                holder.cartNumID.setText(cartGoodsNum + "");
            }
        });

        holder.cartPlusID.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                int temp;
                if ("1".equals(xskz_package)) {
                    temp = midPackage;
                } else if ("2".equals(xskz_package)) {
                    temp = bigPackage;
                } else {

                    temp = 1;
                }
                cartGoodsNum += temp;


                if (bean.getXianshi_info() != null) {
                    if (!TextUtils.isEmpty(bean.getXianshi_info().getLower_limit())) {

                        int upper_limit = Integer.parseInt(bean.getXianshi_info().getLower_limit());
                        if (cartGoodsNum > upper_limit) {
                            cartGoodsNum = upper_limit;
                            holder.cartNumID.setText(cartGoodsNum + "");
                            Toast.makeText(context, "限购" + upper_limit + "个", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }

                if (cartGoodsNum > goods_storage) {
                    cartGoodsNum = goods_storage;
                    holder.cartNumID.setText(cartGoodsNum + "");
                    Toast.makeText(context, "您购买的数量超过库存！", Toast.LENGTH_SHORT).show();
                    return;
                }

                holder.cartNumID.setText(cartGoodsNum + "");
            }
        });

//		convertView.setOnLongClickListener(new OnLongClickListener() {
//			@Override
//			public boolean onLongClick(View v) {
//
//				AlertDialog.Builder builder = new AlertDialog.Builder(context);
//				builder.setTitle("功能选择")
//				.setMessage("您确定在购物车中移除此商品？");
//				builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
//						@Override
//	                    public void onClick(DialogInterface dialog, int whichButton) {
//
//							cartFragment.cartDetele(bean.getCart_id());//删除购物车中的商品
//
//	                    }
//	                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//
//						}
//					}).create().show();
//
//				return false;
//			}
//		});

        return convertView;
    }

    class ViewHolder {
        View minusPlusLayout, numLayoutID;

        Button cartEditID, cartNumSaveID;
        ImageView goodsPicID;
        TextView storeNameID, goodsNameID, goodsPriceID, goodsNumID;
        Button cartCheakBoxID, cartMinusID, cartPlusID;
        EditText cartNumID;
        TextView goodsDelID;//删除按钮
    }
}
