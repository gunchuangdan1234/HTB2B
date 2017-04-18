package com.huatuo_b2b.htb2b.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jinguo on 2016/4/9.
 */
public class Discount {


//    "goods_name": "阿托伐他汀钙片(立普妥)-M23264",
//            "drug_spec": "10ml*7片",
//            "goods_promotion_price": "46.43",
//            "goods_image": "http:\/\/120.24.60.165\/data\/upload\/shop\/store\/goods\/14\/14_05060937120527896_240.jpg",
//            "product_company": "辉瑞制药有限公司",
//            "goods_id": "102164"


    public static class Attr {
        public static final String GOODS_NAME = "goods_name";
        public static final String DRUG_SPEC = "drug_spec";
        public static final String GOODS_PROMOTION_PRICE = "goods_promotion_price";
        public static final String GOODS_IMAGE = "goods_image";
        public static final String PRODUCT_COMPANY = "product_company";
        public static final String GOODS_ID = "goods_id";
        public static final String GOODS_PRICE = "goods_price";
    }

    private String goods_name;
    private String drug_spec;
    private String goods_promotion_price;
    private String goods_price;
    private String goods_image;
    private String product_company;
    private String goods_id;

    public Discount() {
    }

    public Discount(String goods_name, String drug_spec, String goods_promotion_price, String goods_image, String product_company, String goods_id, String goods_price) {
        super();
        this.goods_name = goods_name;
        this.drug_spec = drug_spec;
        this.goods_promotion_price = goods_promotion_price;
        this.goods_image = goods_image;
        this.product_company = product_company;
        this.goods_id = goods_id;
        this.goods_price = goods_price;
    }

    public static Discount newInstanceList(String json) {
        Discount bean = null;
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.length() > 0) {
                String goods_name = obj.optString(Attr.GOODS_NAME);
                String drug_spec = obj.optString(Attr.DRUG_SPEC);
                String goods_promotion_price = obj.optString(Attr.GOODS_PROMOTION_PRICE);
                String goods_image = obj.optString(Attr.GOODS_IMAGE);
                String product_company = obj.optString(Attr.PRODUCT_COMPANY);
                String goods_id = obj.optString(Attr.GOODS_ID);
                String goods_price = obj.optString(Attr.GOODS_PRICE);

                bean = new Discount(goods_name, drug_spec, goods_promotion_price, goods_image, product_company, goods_id, goods_price);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getDrug_spec() {
        return drug_spec;
    }

    public void setDrug_spec(String drug_spec) {
        this.drug_spec = drug_spec;
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

    public String getProduct_company() {
        return product_company;
    }

    public void setProduct_company(String product_company) {
        this.product_company = product_company;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "goods_name='" + goods_name + '\'' +
                ", drug_spec='" + drug_spec + '\'' +
                ", goods_promotion_price='" + goods_promotion_price + '\'' +
                ", goods_price='" + goods_price + '\'' +
                ", goods_image='" + goods_image + '\'' +
                ", product_company='" + product_company + '\'' +
                ", goods_id='" + goods_id + '\'' +
                '}';
    }
}
