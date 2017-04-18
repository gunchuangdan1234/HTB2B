package com.huatuo_b2b.htb2b.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 15/12/30.   商品数据模型   liubw
 */
public class HomeGoodsList {

//    "image": "http://120.24.60.165/data/upload/shop/common/05061846136108822_240.jpg",
//            "goods_name": "硫酸氢氯吡格雷片-5A451",
//            "drug_spec": "75mg/片",
//            "goods_promotion_price": "113.60",
//            "goods_image": "14_05060187008961817.jpg",
//            "product_company": "杭州赛诺菲圣德堡民生制药有限公司",
//            "goods_id": "102279"

    public static class Attr {
        public static final String GOODS_ID = "goods_id";
        public static final String GOODS_NAME = "goods_name";
        public static final String GOODS_PROMOTION_PRICE = "goods_promotion_price";//促销价格
        public static final String GOODS_IMAGE = "goods_image";
        public static final String GOODS_GUIGE = "drug_spec";         //商品规格
        public static final String GOODS_FACTORY = "product_company";     //生产厂家

//      drug_spec //规格
//      product_company//生产厂家
    }

/*
    jsonDatas:[{"goods_image"
                "goods_name":
                "goods_promotion_price":"2.00",
                "goods_id":"100025"
*/

    private String goods_id;
    private String goods_name;
    private String goods_promotion_price;
    private String goods_image;
    private String goods_guige;
    private String goods_factory;

    public HomeGoodsList() {
    }

    public HomeGoodsList(String goods_id, String goods_name,
                         String goods_promotion_price,
                         String goods_image,
                         String goods_guige,
                         String goods_factory) {
        super();
        this.goods_id = goods_id;
        this.goods_name = goods_name;
        this.goods_promotion_price = goods_promotion_price;
        this.goods_image = goods_image;
        this.goods_guige = goods_guige;
        this.goods_factory = goods_factory;
    }

    public static ArrayList<HomeGoodsList> newInstanceList(String jsonDatas) {
        ArrayList<HomeGoodsList> AdvertDatas = new ArrayList<HomeGoodsList>();

        try {
            // System.out.println("@@@#############jsonDatas:" + jsonDatas);

            JSONArray arr = new JSONArray(jsonDatas);

            int size = null == arr ? 0 : arr.length();
            for (int i = 0; i < size; i++) {
                JSONObject obj = arr.getJSONObject(i);
                String goods_id = obj.optString(Attr.GOODS_ID);
                String goods_name = obj.optString(Attr.GOODS_NAME);
                String goods_promotion_price = obj.optString(Attr.GOODS_PROMOTION_PRICE);
                String goods_image = obj.optString(Attr.GOODS_IMAGE);
                String goods_guige = obj.optString(Attr.GOODS_GUIGE);//规格
                String goods_factory = obj.optString(Attr.GOODS_FACTORY);//生产厂家
                AdvertDatas.add(new HomeGoodsList(goods_id, goods_name, goods_promotion_price, goods_image, goods_guige, goods_factory));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return AdvertDatas;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_promotion_price() {
        return goods_promotion_price;
    }

    public void setGoods_promotion_price(String goods_promotion_price) {
        this.goods_promotion_price = goods_promotion_price;
    }

    public String getGoods_image() {
        return goods_image;
    }

    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }

    public String getGoods_guige() {
        return goods_guige;
    }

    public void setGoods_guige(String goods_guige) {
        this.goods_guige = goods_guige;
    }

    public String getGoods_factory() {
        return goods_factory;
    }

    public void setGoods_factory(String goods_factory) {
        this.goods_factory = goods_factory;
    }

    @Override
    public String toString() {
        return "HomeGoodsList [goods_id=" + goods_id +
                ", goods_name=" + goods_name +
                ", goods_factory=" + goods_factory +
                ", goods_guige=" + goods_guige +
                ", goods_promotion_price=" + goods_promotion_price +
                ", goods_image=" + goods_image + "]";
    }
}
