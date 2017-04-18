package com.huatuo_b2b.htb2b.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.GoodsList;
import com.huatuo_b2b.htb2b.common.AnimateFirstDisplayListener;
import com.huatuo_b2b.htb2b.common.SystemHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * 商品列表适配器
 *
 * @author KingKong·HE
 * @Time 2014-1-6 下午12:06:09
 * @E-mail hjgang@bizpoer.com
 */
public class GoodsListViewAdapter extends BaseAdapter {
    private Context context;

    private LayoutInflater inflater;

    private ArrayList<GoodsList> goodsLists;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    private DisplayImageOptions options = SystemHelper.getDisplayImageOptions();

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public GoodsListViewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return goodsLists == null ? 0 : goodsLists.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public ArrayList<GoodsList> getGoodsLists() {
        return goodsLists;
    }

    public void setGoodsLists(ArrayList<GoodsList> goodsLists) {
        this.goodsLists = goodsLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        GoodsList bean = goodsLists.get(position);
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.listivew_goods_item, null);
            holder = new ViewHolder();
            holder.imageGoodsPic = (ImageView) convertView.findViewById(R.id.imageGoodsPic);
            holder.textGoodsName = (TextView) convertView.findViewById(R.id.textGoodsName);
            holder.textGoodsPrice = (TextView) convertView.findViewById(R.id.textGoodsPrice);
            holder.imageXingJi = (LinearLayout) convertView.findViewById(R.id.imageXingJi);
            holder.textEvaluationCount = (TextView) convertView.findViewById(R.id.textEvaluationCount);
            holder.textGoodsType = (TextView) convertView.findViewById(R.id.textGoodsType);
            holder.textZengPin = (TextView) convertView.findViewById(R.id.textZengPin);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageXingJi.removeAllViews();
        int count = Integer.parseInt(bean.getEvaluation_good_star());
        for (int i = 0; i < 5; i++) {
            ImageView imageView = new ImageView(context);
            if (i <= count - 1) {
                imageView.setBackgroundResource(R.mipmap.ic_star_metro_orang);
            } else {
                imageView.setBackgroundResource(R.mipmap.ic_fav);
            }
            holder.imageXingJi.addView(imageView);
        }

        imageLoader.displayImage(bean.getGoods_image_url(), holder.imageGoodsPic, options, animateFirstListener);

        holder.textGoodsName.setText(bean.getGoods_name());
        holder.textGoodsPrice.setText("￥" + bean.getGoods_price());
        holder.textEvaluationCount.setText("（" + bean.getEvaluation_count() + "人）");

        if (Boolean.valueOf(bean.getGroup_flag())) {
            holder.textGoodsType.setText(context.getString(R.string.text_groupbuy));
            holder.textGoodsType.setVisibility(View.VISIBLE);
            holder.textGoodsType.setBackgroundResource(R.color.text_tuangou);
            ;
        } else if (Boolean.valueOf(bean.getXianshi_flag())) {
            holder.textGoodsType.setText(context.getString(R.string.text_xianshi));
            holder.textGoodsType.setVisibility(View.VISIBLE);
            holder.textGoodsType.setBackgroundResource(R.color.text_xianshi);
        } else if (bean.getIs_appoint().equals("1")) {
            holder.textGoodsType.setText(context.getString(R.string.text_appoint));
            holder.textGoodsType.setVisibility(View.VISIBLE);
            holder.textGoodsType.setBackgroundResource(R.color.text_yuyue);
        } else if (bean.getIs_fcode().equals("1")) {
            holder.textGoodsType.setText(context.getString(R.string.text_fcode));
            holder.textGoodsType.setVisibility(View.VISIBLE);
            holder.textGoodsType.setBackgroundResource(R.color.text_Fcode);
        } else if (bean.getIs_presell().equals("1")) {
            holder.textGoodsType.setText(context.getString(R.string.text_presell));
            holder.textGoodsType.setVisibility(View.VISIBLE);
            holder.textGoodsType.setBackgroundResource(R.color.text_yushou);
        } else if (bean.getIs_virtual().equals("1")) {
            holder.textGoodsType.setText(context.getString(R.string.text_virtual));
            holder.textGoodsType.setVisibility(View.VISIBLE);
            holder.textGoodsType.setBackgroundResource(R.color.text_xuni);
        } else {
            holder.textGoodsType.setVisibility(View.GONE);
        }
        if (bean.getHave_gift().equals("1")) {
            holder.textZengPin.setVisibility(View.VISIBLE);
        } else {
            holder.textZengPin.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView imageGoodsPic;
        TextView textGoodsName;
        TextView textGoodsPrice;
        LinearLayout imageXingJi;
        TextView textEvaluationCount;
        TextView textGoodsType;
        TextView textZengPin;
    }
}
