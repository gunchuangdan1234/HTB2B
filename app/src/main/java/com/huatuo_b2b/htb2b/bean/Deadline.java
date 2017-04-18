package com.huatuo_b2b.htb2b.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jinguo on 2016/4/6.
 */
public class Deadline {

//    "data": "",
//            "image": "s0_05132463346514124.jpg",
//            "title": "限时折扣",
//            "type": "",
//            "deadline": "2016-04-30 00:00",
//            "item": []


    public static class Attr {
        public static final String DATA = "data";
        public static final String IMAGE = "image";
        public static final String TITLE = "title";
        public static final String TYPE = "type";
        public static final String DEADLINE = "deadline";
    }

    private String data;
    private String image;
    private String title;
    private String type;
    private String deadline;


    public Deadline() {
    }

    public Deadline(String data, String image, String title, String type, String deadline) {
        super();
        this.data = data;
        this.image = image;
        this.title = title;
        this.type = type;
        this.deadline = deadline;
    }

    public static Deadline newInstanceList(String json) {
        Deadline bean = null;
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.length() > 0) {
                String data = obj.optString(Attr.DATA);
                String image = obj.optString(Attr.IMAGE);
                String title = obj.optString(Attr.TITLE);
                String type = obj.optString(Attr.TYPE);
                String deadline = obj.optString(Attr.DEADLINE);


                bean = new Deadline(data, image, title, type, deadline);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Deadline{" +
                "data='" + data + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", deadline='" + deadline + '\'' +
                '}';
    }
}
