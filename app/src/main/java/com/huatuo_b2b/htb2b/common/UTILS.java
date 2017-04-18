package com.huatuo_b2b.htb2b.common;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UTILS {

    public static final int AD_WHIDE = 720;//广告条的宽
    public static final int AD_HEIGTH = 306;//广告条的高

    public static final int TITLE_IMAGE_WHIDE_rate = 718;//抬头图片的宽
    public static final int TITLE_IMAGE_HEIGTH_rate = 400;//抬头图片的高

    public static final int TITLE_IMAGE_WHIDE_oldman = 432;//抬头图片的宽
    public static final int TITLE_IMAGE_HEIGTH_oldman = 164;//抬头图片的高

    public static final int TITLE_IMAGE_WHIDE_man = 404;//抬头图片的宽
    public static final int TITLE_IMAGE_HEIGTH_man = 172;//抬头图片的高

    public static final int TITLE_IMAGE_WHIDE_woman = 397;//抬头图片的宽
    public static final int TITLE_IMAGE_HEIGTH_woman = 202;//抬头图片的高

    public static final int TITLE_IMAGE_WHIDE_hzpp = 468;//抬头图片的宽
    public static final int TITLE_IMAGE_HEIGTH_hzpp = 517;//抬头图片的高

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        return display.getWidth();
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        return display.getHeight();
    }

    //规格化获得系统时间
    public static String get_Date_by_format(final int m_format_type) {
        SimpleDateFormat format;

        switch (m_format_type) {//日期显示规格样式
            case 0:
                format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
                break;

            case 1:
                format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                break;

            case 2:
                format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                break;

            case 3:
                format = new SimpleDateFormat("HH:MM:ss", Locale.getDefault());
                break;

            case 4://只获取当天日期 是几号
                format = new SimpleDateFormat("dd", Locale.getDefault());
                break;

            default:
                format = new SimpleDateFormat("HH:MM:ss", Locale.getDefault());
                break;
        }
        TimeZone destTimeZone = TimeZone.getTimeZone("GMT+8:00");
        TimeZone.setDefault(destTimeZone);// 设置时区

        Date date = new Date();
        String key = format.format(date);

        return key;
    }

    //判断字符串是否为数字
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^(13[0-9]|15[^4]|17[678]|18[0-9]|14[57])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isZipNO(String zip) {
        Pattern p = Pattern.compile("^([0-9]{6})$");
        Matcher m = p.matcher(zip);
        return m.matches();
    }

}