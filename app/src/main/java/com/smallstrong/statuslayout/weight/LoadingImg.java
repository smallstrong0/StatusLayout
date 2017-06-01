package com.smallstrong.statuslayout.weight;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;

/**
 * Created by smallstrong on 2017/6/1.
 */

public class LoadingImg extends android.support.v7.widget.AppCompatImageView {

    public LoadingImg(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingImg(Context context) {
        super(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getDrawable() != null)
            ((AnimationDrawable) getDrawable()).start();
    }
}