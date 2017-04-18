package com.huatuo_b2b.htb2b.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 购物车列表Bean
 *
 * @author KingKong·HE
 * @Time 2015年1月13日
 * @E-mail hjgang@bizpoer.com
 */
public class CartList {
    public static class Attr {
        public static final String BL_ID = "bl_id";
        public static final String BUYER_ID = "buyer_id";
        public static final String GOODS_PRICE = "goods_price";
        public static final String CART_ID = "cart_id";
        public static final String GOODS_NUM = "goods_num";
        public static final String GOODS_ID = "goods_id";
        public static final String GOODS_IMAGE_URL = "goods_image_url";
        public static final String GOODS_NAME = "goods_name";
        public static final String STORE_ID = "store_id";
        public static final String GOODS_IMAGE = "goods_image";
        public static final String STORE_NAME = "store_name";
        public static final String PREMIUMS = "premiums";
        public static final String XSKZ_PACKAGE = "xskz_package";//销售控制
        public static final String MID_PACKAGE = "mid_package";//中包
        public static final String BIG_PACKAGE = "big_package";//大包
        public static final String GOODS_STORAGE = "goods_storage";//库存
        public static final String UPPER_LIMIT = "upper_limit"; // 最多购买数
        public static final String XIANSHI_INFO = "xianshi_info"; // 最多购买数
    }

    private String bl_id;
    private String buyer_id;
    private String goods_price;
    private String cart_id;
    private String goods_num;
    private String goods_id;
    private String goods_image_url;
    private String goods_name;
    private String store_id;
    private String goods_image;
    private String store_name;
    private String premiums;
    private String xskz_package;
    private String mid_package;
    private String big_package;
    private String goods_storage;
    private String upper_limit;

    private Xianshi xianshi_info;
    private boolean Selected = false;

    public CartList() {
    }


    public CartList(String bl_id, String buyer_id, String goods_price,
                    String cart_id, String goods_num, String goods_id,
                    String goods_image_url, String goods_name, String store_id,
                    String goods_image, String store_name, String premiums, String xskz_package
            , String mid_package, String big_package, String goods_storage, String upper_limit, Xianshi xianshi_info) {
        super();
        this.bl_id = bl_id;
        this.buyer_id = buyer_id;
        this.goods_price = goods_price;
        this.cart_id = cart_id;
        this.goods_num = goods_num;
        this.goods_id = goods_id;
        this.goods_image_url = goods_image_url;
        this.goods_name = goods_name;
        this.store_id = store_id;
        this.goods_image = goods_image;
        this.store_name = store_name;
        this.premiums = premiums;
        this.xskz_package = xskz_package;
        this.mid_package = mid_package;
        this.big_package = big_package;
        this.goods_storage = goods_storage;
        this.upper_limit = upper_limit;
        this.xianshi_info = xianshi_info;
    }


    public static ArrayList<CartList> newInstanceList(String jsonDatas) {
        ArrayList<CartList> AdvertDatas = new ArrayList<CartList>();

        try {
            JSONArray arr = new JSONArray(jsonDatas);
            int size = null == arr ? 0 : arr.length();
            for (int i = 0; i < size; i++) {
                JSONObject obj = arr.getJSONObject(i);
                String bl_id = obj.optString(Attr.BL_ID);
                String buyer_id = obj.optString(Attr.BUYER_ID);
                String goods_price = obj.optString(Attr.GOODS_PRICE);
                String cart_id = obj.optString(Attr.CART_ID);
                String goods_num = obj.optString(Attr.GOODS_NUM);
                String goods_id = obj.optString(Attr.GOODS_ID);
                String goods_image_url = obj.optString(Attr.GOODS_IMAGE_URL);
                String goods_name = obj.optString(Attr.GOODS_NAME);
                String store_id = obj.optString(Attr.STORE_ID);
                String goods_image = obj.optString(Attr.GOODS_IMAGE);
                String store_name = obj.optString(Attr.STORE_NAME);
                String premiums = obj.optString(Attr.PREMIUMS);
                String xskz_package = obj.optString(Attr.XSKZ_PACKAGE);
                String mid_package = obj.optString(Attr.MID_PACKAGE);
                String big_package = obj.optString(Attr.BIG_PACKAGE);
                String goods_storage = obj.optString(Attr.GOODS_STORAGE);
                String upper_limit = obj.optString(Attr.UPPER_LIMIT);

                Xianshi xianshi_info = Xianshi.newInstanceList(obj.optString(Attr.XIANSHI_INFO));
                AdvertDatas.add(new CartList(bl_id, buyer_id, goods_price, cart_id,
                        goods_num, goods_id, goods_image_url, goods_name, store_id, goods_image, store_name
                        , premiums, xskz_package, mid_package, big_package, goods_storage, upper_limit, xianshi_info));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return AdvertDatas;
    }


    public boolean isSelected() {
        return Selected;
    }


    public void setSelected(boolean selected) {
        Selected = selected;
    }


    public String getBl_id() {
        return bl_id;
    }

    public void setBl_id(String bl_id) {
        this.bl_id = bl_id;
    }


    public String getBuyer_id() {
        return buyer_id;
    }


    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }


    public String getCart_id() {
        return cart_id;
    }


    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }


    public String getGoods_num() {
        return goods_num;
    }


    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }


    public String getGoods_id() {
        return goods_id;
    }


    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }


    public String getGoods_image_url() {
        return goods_image_url;
    }


    public void setGoods_image_url(String goods_image_url) {
        this.goods_image_url = goods_image_url;
    }


    public String getGoods_name() {
        return goods_name;
    }


    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }


    public String getStore_id() {
        return store_id;
    }


    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }


    public String getGoods_image() {
        return goods_image;
    }


    public void setGoods_image(String goods_image) {
        this.goods_image = goods_image;
    }


    public String getStore_name() {
        return store_name;
    }


    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }


    public String getPremiums() {
        return premiums;
    }


    public void setPremiums(String premiums) {
        this.premiums = premiums;
    }

    public String getXskz_package() {
        return xskz_package;
    }

    public void setXskz_package(String xskz_package) {
        this.xskz_package = xskz_package;
    }

    public String getMid_package() {
        return mid_package;
    }

    public void setMid_package(String mid_package) {
        this.mid_package = mid_package;
    }

    public String getBig_package() {
        return big_package;
    }

    public void setBig_package(String big_package) {
        this.big_package = big_package;
    }

    public String getGoods_storage() {
        return goods_storage;
    }

    public void setGoods_storage(String goods_storage) {
        this.goods_storage = goods_storage;
    }

    public String getUpper_limit() {
        return upper_limit;
    }

    public void setUpper_limit(String upper_limit) {
        this.upper_limit = upper_limit;
    }

    public Xianshi getXianshi_info() {
        return xianshi_info;
    }

    public void setXianshi_info(Xianshi xianshi_info) {
        this.xianshi_info = xianshi_info;
    }

    @Override
    public String toString() {
        return "CartList{" +
                "bl_id='" + bl_id + '\'' +
                ", buyer_id='" + buyer_id + '\'' +
                ", goods_price='" + goods_price + '\'' +
                ", cart_id='" + cart_id + '\'' +
                ", goods_num='" + goods_num + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", goods_image_url='" + goods_image_url + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", store_id='" + store_id + '\'' +
                ", goods_image='" + goods_image + '\'' +
                ", store_name='" + store_name + '\'' +
                ", premiums='" + premiums + '\'' +
                ", xskz_package='" + xskz_package + '\'' +
                ", mid_package='" + mid_package + '\'' +
                ", big_package='" + big_package + '\'' +
                ", goods_storage='" + goods_storage + '\'' +
                ", upper_limit='" + upper_limit + '\'' +
                ", xianshi_info=" + xianshi_info +
                ", Selected=" + Selected +
                '}';
    }
}
