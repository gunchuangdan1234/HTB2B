package com.huatuo_b2b.htb2b.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jinguo on 2016/4/18.
 */
public class Area {

    public static class Attr {
        public static final String AREA_ID = "area_id";
        public static final String AREA_NAME = "area_name";
    }

    private String area_id;
    private String area_name;


    public Area() {
    }

    public Area(String area_id, String area_name) {

        super();

        this.area_id = area_id;
        this.area_name = area_name;

    }


    public static ArrayList<Area> newInstanceList(String jsonDatas) {
        ArrayList<Area> typeList = new ArrayList<Area>();

        try {
            JSONArray arr = new JSONArray(jsonDatas);
            int size = null == arr ? 0 : arr.length();
            for (int i = 0; i < size; i++) {
                JSONObject obj = arr.getJSONObject(i);
                String area_id = obj.optString(Attr.AREA_ID);
                String area_name = obj.optString(Attr.AREA_NAME);
                ;
                Area t = new Area(area_id, area_name);
                typeList.add(t);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return typeList;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    @Override
    public String toString() {
        return "Area{" +
                "area_id='" + area_id + '\'' +
                ", area_name='" + area_name + '\'' +
                '}';
    }
}
