package com.huatuo_b2b.htb2b.fragment.widget;


import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;


public class SegmentView extends LinearLayout {
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private onSegmentViewClickListener listener;

    public SegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SegmentView(Context context) {
        super(context);
        init();
    }

    private void init() {
//		this.setLayoutParams(new LinearLayout.LayoutParams(dp2Px(getContext(), 60), LinearLayout.LayoutParams.WRAP_CONTENT));
        textView1 = new TextView(getContext());
        textView2 = new TextView(getContext());
        textView3 = new TextView(getContext());
        textView1.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        textView2.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        textView3.setLayoutParams(new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
        textView1.setText("未使用");
        textView2.setText("已使用");
        textView3.setText("已过期");
//        XmlPullParser xrp = getResources().getXml(R.drawable.seg_text_color_selector);
        try {
//            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            textView1.setTextColor((getResources().getColor(R.color.text_color_segment_selected)));
            textView2.setTextColor((getResources().getColor(R.color.text_color_segment_selected)));
            textView3.setTextColor((getResources().getColor(R.color.text_color_segment_selected)));
        } catch (Exception e) {
        }
        textView1.setGravity(Gravity.CENTER);
        textView2.setGravity(Gravity.CENTER);
        textView3.setGravity(Gravity.CENTER);
        textView1.setPadding(6, 14, 6, 14);
        textView2.setPadding(6, 14, 6, 14);
        textView3.setPadding(6, 14, 6, 14);
        setSegmentTextSize(14);
        textView1.setBackgroundResource(R.drawable.seg_left);
        textView2.setBackgroundResource(R.drawable.seg_mid);
        textView3.setBackgroundResource(R.drawable.seg_right);
        textView1.setSelected(true);
        textView1.setTextColor(getResources().getColor(R.color.white));
        this.removeAllViews();
        this.addView(textView1);
        this.addView(textView2);
        this.addView(textView3);
        this.invalidate();

        textView1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textView1.isSelected()) {
                    return;
                }
                textView1.setSelected(true);
                textView2.setSelected(false);
                textView3.setSelected(false);

                textView1.setTextColor(getResources().getColor(R.color.white));
                textView2.setTextColor((getResources().getColor(R.color.text_color_segment_selected)));
                textView3.setTextColor((getResources().getColor(R.color.text_color_segment_selected)));
                if (listener != null) {
                    listener.onSegmentViewClick(textView1, 0);
                }
            }
        });
        textView2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textView2.isSelected()) {
                    return;
                }
                textView2.setSelected(true);
                textView1.setSelected(false);
                textView3.setSelected(false);

                textView1.setTextColor(getResources().getColor(R.color.text_color_segment_selected));
                textView2.setTextColor((getResources().getColor(R.color.white)));
                textView3.setTextColor((getResources().getColor(R.color.text_color_segment_selected)));
                if (listener != null) {
                    listener.onSegmentViewClick(textView2, 1);
                }
            }
        });

        textView3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (textView3.isSelected()) {
                    return;
                }
                textView3.setSelected(true);
                textView2.setSelected(false);
                textView1.setSelected(false);
                textView1.setTextColor(getResources().getColor(R.color.text_color_segment_selected));
                textView2.setTextColor((getResources().getColor(R.color.text_color_segment_selected)));
                textView3.setTextColor((getResources().getColor(R.color.white)));
                if (listener != null) {
                    listener.onSegmentViewClick(textView3, 2);
                }
            }
        });
    }

    /**
     * 设置字体大小 单位dip
     * <p>2014年7月18日</p>
     *
     * @param dp
     * @author RANDY.ZHANG
     */
    public void setSegmentTextSize(int dp) {
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        textView3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
    }

    private static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void setOnSegmentViewClickListener(onSegmentViewClickListener listener) {
        this.listener = listener;
    }


    /**
     * 设置文字
     * <p>2014年7月18日</p>
     *
     * @param text
     * @param position
     * @author RANDY.ZHANG
     */
    public void setSegmentText(CharSequence text, int position) {
        if (position == 0) {
            textView1.setText(text);
        }
        if (position == 1) {
            textView2.setText(text);
        }

        if (position == 2) {
            textView3.setText(text);
        }
    }

    public static interface onSegmentViewClickListener {
        /**
         * <p>2014年7月18日</p>
         *
         * @param v
         * @param position 0-左边 1-右边
         * @author RANDY.ZHANG
         */
        public void onSegmentViewClick(View v, int position);
    }
}

