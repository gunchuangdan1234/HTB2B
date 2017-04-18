package com.huatuo_b2b.htb2b.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jinguo on 2016/4/7.
 */
public class Home8Data {


//            "title": "",

//            "rectangle1_type": "",
//            "rectangle2_type": "",
//            "rectangle3_type": "",
//            "rectangle4_type": "",

//            "rectangle1_image": "http:\/\/120.24.60.165\/data\/upload\/mobile\/special\/s0\/s0_05132485076956221.jpg",
//            "rectangle2_image": "http:\/\/120.24.60.165\/data\/upload\/mobile\/special\/s0\/s0_05132485182721280.jpg",
//            "rectangle3_image": "http:\/\/120.24.60.165\/data\/upload\/mobile\/special\/s0\/s0_05132485258724474.jpg",
//            "rectangle4_image": "http:\/\/120.24.60.165\/data\/upload\/mobile\/special\/s0\/s0_05132485376669398.jpg",

//            "rectangle1_data": ""
//            "rectangle2_data": "",
//            "rectangle3_data": "",
//            "rectangle4_data": "",

    public static class Attr {
        public static final String TITLE = "title";

        public static final String RECTANGLE1_TYPE = "rectangle1_type";
        public static final String RECTANGLE2_TYPE = "rectangle2_type";
        public static final String RECTANGLE3_TYPE = "rectangle3_type";
        public static final String RECTANGLE4_TYPE = "rectangle4_type";

        public static final String RECTANGLE1_IMAGE = "rectangle1_image";
        public static final String RECTANGLE2_IMAGE = "rectangle2_image";
        public static final String RECTANGLE3_IMAGE = "rectangle3_image";
        public static final String RECTANGLE4_IMAGE = "rectangle4_image";

        public static final String RECTANGLE1_DATA = "rectangle1_data";
        public static final String RECTANGLE2_DATA = "rectangle2_data";
        public static final String RECTANGLE3_DATA = "rectangle3_data";
        public static final String RECTANGLE4_DATA = "rectangle4_data";
    }

    private String title;

    private String rectangle1_type;
    private String rectangle2_type;
    private String rectangle3_type;
    private String rectangle4_type;

    private String rectangle1_image;
    private String rectangle2_image;
    private String rectangle3_image;
    private String rectangle4_image;

    private String rectangle1_data;
    private String rectangle2_data;
    private String rectangle3_data;
    private String rectangle4_data;

    public Home8Data() {
    }

    public Home8Data(String title, String rectangle1_type, String rectangle2_type, String rectangle3_type, String rectangle4_type, String rectangle1_image, String rectangle2_image, String rectangle3_image, String rectangle4_image, String rectangle1_data, String rectangle2_data, String rectangle3_data, String rectangle4_data) {
        super();
        this.title = title;

        this.rectangle1_type = rectangle1_type;
        this.rectangle2_type = rectangle2_type;
        this.rectangle3_type = rectangle3_type;
        this.rectangle4_type = rectangle4_type;


        this.rectangle1_image = rectangle1_image;
        this.rectangle2_image = rectangle2_image;
        this.rectangle3_image = rectangle3_image;
        this.rectangle4_image = rectangle4_image;

        this.rectangle1_data = rectangle1_data;
        this.rectangle2_data = rectangle2_data;
        this.rectangle3_data = rectangle3_data;
        this.rectangle4_data = rectangle4_data;
    }

    public static Home8Data newInstanceList(String json) {
        Home8Data bean = null;
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.length() > 0) {

                String title = obj.optString(Attr.TITLE);

                String rectangle1_type = obj.optString(Attr.RECTANGLE1_TYPE);
                String rectangle2_type = obj.optString(Attr.RECTANGLE2_TYPE);
                String rectangle3_type = obj.optString(Attr.RECTANGLE3_TYPE);
                String rectangle4_type = obj.optString(Attr.RECTANGLE4_TYPE);


                String rectangle1_image = obj.optString(Attr.RECTANGLE1_IMAGE);
                String rectangle2_image = obj.optString(Attr.RECTANGLE2_IMAGE);
                String rectangle3_image = obj.optString(Attr.RECTANGLE3_IMAGE);
                String rectangle4_image = obj.optString(Attr.RECTANGLE4_IMAGE);

                String rectangle1_data = obj.optString(Attr.RECTANGLE1_DATA);
                String rectangle2_data = obj.optString(Attr.RECTANGLE2_DATA);
                String rectangle3_data = obj.optString(Attr.RECTANGLE3_DATA);
                String rectangle4_data = obj.optString(Attr.RECTANGLE4_DATA);

                bean = new Home8Data(title, rectangle1_type, rectangle2_type, rectangle3_type, rectangle4_type, rectangle1_image, rectangle2_image, rectangle3_image, rectangle4_image, rectangle1_data, rectangle2_data, rectangle3_data, rectangle4_data);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRectangle1_type() {
        return rectangle1_type;
    }

    public void setRectangle1_type(String rectangle1_type) {
        this.rectangle1_type = rectangle1_type;
    }

    public String getRectangle2_type() {
        return rectangle2_type;
    }

    public void setRectangle2_type(String rectangle2_type) {
        this.rectangle2_type = rectangle2_type;
    }

    public String getRectangle3_type() {
        return rectangle3_type;
    }

    public void setRectangle3_type(String rectangle3_type) {
        this.rectangle3_type = rectangle3_type;
    }

    public String getRectangle4_type() {
        return rectangle4_type;
    }

    public void setRectangle4_type(String rectangle4_type) {
        this.rectangle4_type = rectangle4_type;
    }

    public String getRectangle1_image() {
        return rectangle1_image;
    }

    public void setRectangle1_image(String rectangle1_image) {
        this.rectangle1_image = rectangle1_image;
    }

    public String getRectangle2_image() {
        return rectangle2_image;
    }

    public void setRectangle2_image(String rectangle2_image) {
        this.rectangle2_image = rectangle2_image;
    }

    public String getRectangle3_image() {
        return rectangle3_image;
    }

    public void setRectangle3_image(String rectangle3_image) {
        this.rectangle3_image = rectangle3_image;
    }

    public String getRectangle4_image() {
        return rectangle4_image;
    }

    public void setRectangle4_image(String rectangle4_image) {
        this.rectangle4_image = rectangle4_image;
    }

    public String getRectangle1_data() {
        return rectangle1_data;
    }

    public void setRectangle1_data(String rectangle1_data) {
        this.rectangle1_data = rectangle1_data;
    }

    public String getRectangle2_data() {
        return rectangle2_data;
    }

    public void setRectangle2_data(String rectangle2_data) {
        this.rectangle2_data = rectangle2_data;
    }

    public String getRectangle3_data() {
        return rectangle3_data;
    }

    public void setRectangle3_data(String rectangle3_data) {
        this.rectangle3_data = rectangle3_data;
    }

    public String getRectangle4_data() {
        return rectangle4_data;
    }

    public void setRectangle4_data(String rectangle4_data) {
        this.rectangle4_data = rectangle4_data;
    }


    @Override
    public String toString() {
        return "Home8Data{" +
                "title='" + title + '\'' +
                ", rectangle1_type='" + rectangle1_type + '\'' +
                ", rectangle2_type='" + rectangle2_type + '\'' +
                ", rectangle3_type='" + rectangle3_type + '\'' +
                ", rectangle4_type='" + rectangle4_type + '\'' +
                ", rectangle1_image='" + rectangle1_image + '\'' +
                ", rectangle2_image='" + rectangle2_image + '\'' +
                ", rectangle3_image='" + rectangle3_image + '\'' +
                ", rectangle4_image='" + rectangle4_image + '\'' +
                ", rectangle1_data='" + rectangle1_data + '\'' +
                ", rectangle2_data='" + rectangle2_data + '\'' +
                ", rectangle3_data='" + rectangle3_data + '\'' +
                ", rectangle4_data='" + rectangle4_data + '\'' +
                '}';
    }
}
