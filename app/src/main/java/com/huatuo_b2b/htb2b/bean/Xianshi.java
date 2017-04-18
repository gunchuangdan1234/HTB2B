package com.huatuo_b2b.htb2b.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jinguo on 2016/6/14.
 */
public class Xianshi {

    public static class Attr {


//        "xianshi_id": "243",
//                "lower_limit": "200",
//                "down_price": "0.01",
//                "state": "1",
//                "goods_name": "人血白蛋白-201510085",
//                "store_id": "14",
//                "xianshi_price": "235.99",
//                "goods_image": "14_05180911999475906.jpg",
//                "xianshi_discount": "10.0折",
//                "xianshi_name": "限时抢购  人血白蛋白",
//                "goods_url": "http://b2b.huatuoyf.com/item-102736.html",
//                "gc_id_1": "1000",
//                "xianshi_title": "",
//                "goods_price": "236.00",
//                "image_url": "http://b2b.huatuoyf.com/data/upload/shop/common/05061846136108822_60.jpg",
//                "end_time": "1467302340",
//                "goods_id": "102736",
//                "start_time": "1464710400",
//                "xianshi_goods_id": "208",
//                "xianshi_explain": "",
//                "xianshi_recommend": "0"


        public static final String LOWER_LIMIT = "lower_limit";
        public static final String XIANSHI_ID = "xianshi_id";
        public static final String XIANSHI_PRICE = "xianshi_price";
        public static final String GOODS_PRICE = "goods_price";


    }

    private String lower_limit;
    private String xianshi_id;
    private String xianshi_price;
    private String goods_price;

    public Xianshi() {
    }


    public Xianshi(String lower_limit, String xianshi_id, String xianshi_price, String goods_price) {
        super();
        this.lower_limit = lower_limit;
        this.xianshi_id = xianshi_id;
        this.xianshi_price = xianshi_price;
        this.goods_price = goods_price;
    }


    public static Xianshi newInstanceList(String json) {
        Xianshi bean = null;
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.length() > 0) {
                String lower_limit = obj.optString(Attr.LOWER_LIMIT);
                String xianshi_id = obj.optString(Attr.XIANSHI_ID);
                String xianshi_price = obj.optString(Attr.XIANSHI_PRICE);
                String goods_price = obj.getString(Attr.GOODS_PRICE);//member_role  0 普通   1、2 是买家或是卖家

                bean = new Xianshi(lower_limit, xianshi_id, xianshi_price, goods_price);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }


    public String getLower_limit() {
        return lower_limit;
    }

    public void setLower_limit(String lower_limit) {
        this.lower_limit = lower_limit;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getXianshi_price() {
        return xianshi_price;
    }

    public void setXianshi_price(String xianshi_price) {
        this.xianshi_price = xianshi_price;
    }

    public String getXianshi_id() {
        return xianshi_id;
    }

    public void setXianshi_id(String xianshi_id) {
        this.xianshi_id = xianshi_id;
    }


    @Override
    public String toString() {
        return "Xianshi{" +
                "lower_limit='" + lower_limit + '\'' +
                ", xianshi_id='" + xianshi_id + '\'' +
                ", xianshi_price='" + xianshi_price + '\'' +
                ", goods_price='" + goods_price + '\'' +
                '}';
    }
}
