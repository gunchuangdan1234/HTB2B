package com.huatuo_b2b.htb2b.common;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by admin on 15/12/30.
 */
public class AnimateFirstDisplayListener implements ImageLoadingListener {

    static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

    @Override
    public void onLoadingCancelled(String arg0, View arg1) {

    }

    @Override
    public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {

    }

    @Override
    public void onLoadingStarted(String arg0, View arg1) {

    }

    @Override
    public void onLoadingComplete(String imageUri, View arg1, Bitmap loadedImage) {
        if (loadedImage != null) {
            ImageView imageView = (ImageView) arg1;
            // 是否第一次显示
            boolean firstDisplay = !displayedImages.contains(imageUri);
            if (firstDisplay) {
                // 图片淡入效果
                FadeInBitmapDisplayer.animate(imageView, 500);
                displayedImages.add(imageUri);
            }
        }
    }
}
