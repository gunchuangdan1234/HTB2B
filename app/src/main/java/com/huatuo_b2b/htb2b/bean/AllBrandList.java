package com.huatuo_b2b.htb2b.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jinguo on 2016/4/9.
 */
public class AllBrandList {

//    {
//            "brand_initial": "D",
//                "class_id": "1000",
//                "brand_apply": "1",
//                "brand_id": "174",
//                "store_id": "0",

//                "brand_sort": "0",
//                "brand_recommend": "0",
//                "brand_class": "西药",
//                "show_type": "0",
//                "brand_pic": "http:\/\/120.24.60.165\/data\/upload\/shop\/brand\/05067093822168908_sm.jpg",
//                "brand_name": "大连辉瑞"
//        },


    public static class Attr {
        public static final String BRAND_INITIAL = "brand_initial";
        public static final String CLASS_ID = "class_id";
        public static final String BRAND_APPLY = "brand_apply";
        public static final String BRAND_ID = "brand_id";
        public static final String STORE_ID = "store_id";

        public static final String BRAND_SORT = "brand_sort";
        public static final String BRAND_RECOMMEND = "brand_recommend";
        public static final String BRAND_CLASS = "brand_class";
        public static final String SHOW_TYPE = "show_type";
        public static final String BRAND_PIC = "brand_pic";
        public static final String BRAND_NAME = "brand_name";
    }

    private String brand_initial;
    private String class_id;
    private String brand_apply;
    private String brand_id;
    private String store_id;

    private String brand_sort;
    private String brand_recommend;
    private String brand_class;
    private String show_type;
    private String brand_pic;
    private String brand_name;

    public AllBrandList() {
    }


    public AllBrandList(String brand_initial, String class_id, String brand_apply, String brand_id, String store_id,
                        String brand_sort, String brand_recommend, String brand_class,
                        String show_type, String brand_pic, String brand_name) {
        super();
        this.brand_initial = brand_initial;
        this.class_id = class_id;
        this.brand_apply = brand_apply;
        this.brand_id = brand_id;
        this.store_id = store_id;
        this.brand_sort = brand_sort;
        this.brand_recommend = brand_recommend;
        this.brand_class = brand_class;
        this.show_type = show_type;
        this.brand_pic = brand_pic;
        this.brand_name = brand_name;
    }


    public static ArrayList<AllBrandList> newInstanceList(String jsonDatas) {
        ArrayList<AllBrandList> allBrandLists = new ArrayList<AllBrandList>();

        try {
            JSONArray arr = new JSONArray(jsonDatas);
            int size = null == arr ? 0 : arr.length();
            for (int i = 0; i < size; i++) {
                JSONObject obj = arr.getJSONObject(i);
                String brand_initial = obj.optString(Attr.BRAND_INITIAL);
                String class_id = obj.optString(Attr.CLASS_ID);
                String brand_apply = obj.optString(Attr.BRAND_APPLY);
                String brand_id = obj.optString(Attr.BRAND_ID);
                String store_id = obj.optString(Attr.STORE_ID);

                String brand_sort = obj.optString(Attr.BRAND_SORT);
                String brand_recommend = obj.optString(Attr.BRAND_RECOMMEND);
                String brand_class = obj.optString(Attr.BRAND_CLASS);
                String show_type = obj.optString(Attr.SHOW_TYPE);
                String brand_pic = obj.optString(Attr.BRAND_PIC);
                String brand_name = obj.optString(Attr.BRAND_NAME);
                allBrandLists.add(new AllBrandList(brand_initial, class_id, brand_apply, brand_id, store_id, brand_sort, brand_recommend,
                        brand_class, show_type, brand_pic, brand_name));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allBrandLists;
    }

    public String getBrand_initial() {
        return brand_initial;
    }

    public void setBrand_initial(String brand_initial) {
        this.brand_initial = brand_initial;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getBrand_apply() {
        return brand_apply;
    }

    public void setBrand_apply(String brand_apply) {
        this.brand_apply = brand_apply;
    }

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getBrand_sort() {
        return brand_sort;
    }

    public void setBrand_sort(String brand_sort) {
        this.brand_sort = brand_sort;
    }

    public String getBrand_recommend() {
        return brand_recommend;
    }

    public void setBrand_recommend(String brand_recommend) {
        this.brand_recommend = brand_recommend;
    }

    public String getBrand_class() {
        return brand_class;
    }

    public void setBrand_class(String brand_class) {
        this.brand_class = brand_class;
    }

    public String getShow_type() {
        return show_type;
    }

    public void setShow_type(String show_type) {
        this.show_type = show_type;
    }

    public String getBrand_pic() {
        return brand_pic;
    }

    public void setBrand_pic(String brand_pic) {
        this.brand_pic = brand_pic;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    @Override
    public String toString() {
        return "AllBrandList{" +
                "brand_initial='" + brand_initial + '\'' +
                ", class_id='" + class_id + '\'' +
                ", brand_apply='" + brand_apply + '\'' +
                ", brand_id='" + brand_id + '\'' +
                ", store_id='" + store_id + '\'' +
                ", brand_sort='" + brand_sort + '\'' +
                ", brand_recommend='" + brand_recommend + '\'' +
                ", brand_class='" + brand_class + '\'' +
                ", show_type='" + show_type + '\'' +
                ", brand_pic='" + brand_pic + '\'' +
                ", brand_name='" + brand_name + '\'' +
                '}';
    }
}
