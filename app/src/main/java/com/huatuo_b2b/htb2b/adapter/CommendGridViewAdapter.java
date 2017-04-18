/**
 * ProjectName:AndroidShopNC2014Moblie
 * PackageName:net.shopnc.android.adapter
 * FileNmae:CommendGridViewAdapter.java
 */
package com.huatuo_b2b.htb2b.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.CommendList;
import com.huatuo_b2b.htb2b.common.AnimateFirstDisplayListener;
import com.huatuo_b2b.htb2b.common.SystemHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * @author KingKong·HE
 * @Time 2014-1-6 下午12:06:09
 * @E-mail hjgang@bizpoer.com
 */
public class CommendGridViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<CommendList> commendLists;

    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options = SystemHelper.getDisplayImageOptions();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
    private TextView specGoodsPriceID;

    private String loginKey, memberRole;

    public String getLoginKey() {
        return loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

    public String getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(String memberRole) {
        this.memberRole = memberRole;
    }

    public CommendGridViewAdapter(Context context, String loginKey, String member_role) {
        this.context = context;
        this.loginKey = loginKey;
        this.memberRole = member_role;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return commendLists == null ? 0 : commendLists.size();
    }

    @Override
    public Object getItem(int position) {
        return commendLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<CommendList> getCommendLists() {
        return commendLists;
    }

    public void setCommendLists(ArrayList<CommendList> commendLists) {
        this.commendLists = commendLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.gridview_commend_item, null);
            holder = new ViewHolder();
            holder.imageviewPIC = (ImageView) convertView.findViewById(R.id.imageviewPIC);
            holder.textGoodsCommendName = (TextView) convertView.findViewById(R.id.textGoodsCommendName);
            holder.textGoodsCommendPrice = (TextView) convertView.findViewById(R.id.textGoodsCommendPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CommendList bean = commendLists.get(position);
        holder.textGoodsCommendName.setText(bean.getGoods_name() == null ? "" : bean.getGoods_name());

//		if(bean.getPromotion_price() != null && !bean.getPromotion_price().equals("") && !bean.getPromotion_price().equals("null")){
//
//			if(loginKey == null ||"".equals(loginKey)){//价格登录后可见
//				holder.textGoodsCommendPrice.setText("价格登录后可见");
//			}else {
//				if(memberRole.equals("0")){//显示正常价格   liubw
//					holder.textGoodsCommendPrice.setText("价格认证后可见");
//				}else{//价格认证后可见
//					holder.textGoodsCommendPrice.setText("￥"+(bean.getPromotion_price() == null ? "0.00" : bean.getPromotion_price()));
//				}
//			}
//
//
//
//		}else{
//
//			if(loginKey == null ||"".equals(loginKey)){//价格登录后可见
//				holder.textGoodsCommendPrice.setText("价格登录后可见");
//			}else {
//				if(memberRole.equals("0")){//显示正常价格   liubw
//					holder.textGoodsCommendPrice.setText("价格认证后可见");
//				}else{//价格认证后可见
//					holder.textGoodsCommendPrice.setText("￥"+(bean.getGoods_price() == null ? "0.00" : bean.getGoods_price()));
//				}
//			}
//
//		}


        if (TextUtils.isEmpty(this.loginKey)) {//价格登录后可见
            holder.textGoodsCommendPrice.setText("价格登录后可见");
        } else {
            if (this.memberRole.equals("0")) {//显示正常价格   liubw
                holder.textGoodsCommendPrice.setText("价格认证后可见");
            } else {//价格认证后可见
//                holder.TextViewPrice.setText("￥"+bean.getGoods_promotion_price());

                if (bean.getPromotion_price() != null && !bean.getPromotion_price().equals("") && !bean.getPromotion_price().equals("null")) {
                    holder.textGoodsCommendPrice.setText("￥" + (bean.getPromotion_price() == null ? "0.00" : bean.getPromotion_price()));
                } else {
                    holder.textGoodsCommendPrice.setText("￥" + (bean.getGoods_price() == null ? "0.00" : bean.getGoods_price()));
                }
            }
        }

        imageLoader.displayImage(bean.getGoods_image_url(), holder.imageviewPIC, options, animateFirstListener);

        return convertView;
    }

    class ViewHolder {
        ImageView imageviewPIC;
        TextView textGoodsCommendName;
        TextView textGoodsCommendPrice;
    }
}
