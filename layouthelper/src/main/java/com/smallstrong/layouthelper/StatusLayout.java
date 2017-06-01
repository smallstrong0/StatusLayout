package com.smallstrong.layouthelper;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by smallstrong on 2017/6/1.
 */

public class StatusLayout extends FrameLayout {

    LayoutInflater mInflater;
    // 默认的布局Id
    int mContentId = NO_ID, mEmptyResId = R.layout.layout_empty, mLoadingResId = R.layout.layout_loading, mErrorResId = R.layout.layout_error;
    //默认的网络错误重新加载按钮Id
    int mRetryBtnId = R.id.retry_button;
    //用来存储layout的map
    private Map<Integer, View> mLayouts = new HashMap<>();
    //私有化构造
    private StatusLayout(@NonNull Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
    }
    //你传进来的view 我在view外加了层StatusLayout包裹
    public static StatusLayout wrap(View view) {
        if (view == null) {
            throw new RuntimeException("content view can not be null");
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent == null) {
            throw new RuntimeException("parent view can not be null");
        }
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        int index = parent.indexOfChild(view);
        parent.removeView(view);

        StatusLayout layout = new StatusLayout(view.getContext());
        parent.addView(layout, index, lp);
        layout.addView(view);
        layout.setContentView(view);
        return layout;
    }
    //放入 content
    private void setContentView(View view) {
        mContentId = view.getId();
        mLayouts.put(mContentId, view);
    }
    //传入自定义loading
    public StatusLayout setLoading(@LayoutRes int id) {
        if (mLoadingResId != id) {
            remove(mLoadingResId);
            mLoadingResId = id;
        }
        return this;
    }
    //传入自定义empty
    public StatusLayout setEmpty(@LayoutRes int id) {
        if (mEmptyResId != id) {
            remove(mEmptyResId);
            mEmptyResId = id;
        }
        return this;
    }
    //传入自定义error 一定配合下面的setRetryBtnId使用
    public StatusLayout setError(@LayoutRes int id) {
        if (mErrorResId != id) {
            remove(mErrorResId);
            mErrorResId = id;
        }
        return this;
    }

    public StatusLayout setRetryBtnId(int id) {
        if (mRetryBtnId != id) {
            mRetryBtnId = id;
        }
        return this;
    }
    //自定义layout替换默认的
    private void remove(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            View vg = mLayouts.remove(layoutId);
            removeView(vg);
        }
    }

    //展示loading页面
    public void showLoading() {
        show(mLoadingResId);
    }
    //展示empty页面
    public void showEmpty() {
        show(mEmptyResId);
    }
    //展示error页面
    public void showError() {
        show(mErrorResId);
    }
    //展示content页面
    public void showContent() {
        show(mContentId);
    }

    private void show(int layoutId) {
        for (View view : mLayouts.values()) {
            view.setVisibility(GONE);
        }
        layout(layoutId).setVisibility(VISIBLE);
    }
    //当map中没有就inflate创建并加入map 有就直接返回并显示
    private View layout(int layoutId) {
        if (mLayouts.containsKey(layoutId)) {
            return mLayouts.get(layoutId);
        }
        View layout = mInflater.inflate(layoutId, this, false);
        layout.setVisibility(GONE);
        addView(layout);
        mLayouts.put(layoutId, layout);
        //如果将要显示的是error界面，则做好btn的回调工作，再次强调一定要传入btn的Id啊
        if (layoutId == mErrorResId) {
            Button btn = (Button) layout.findViewById(mRetryBtnId);
            if (btn != null) {
                btn.setOnClickListener(mRetryButtonClickListener);
            }
        }

        return layout;
    }

    View.OnClickListener mRetryButtonClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mRetryListener != null) {
                mRetryListener.onClick(v);
            }
        }
    };

    View.OnClickListener mRetryListener;

    public StatusLayout setRetryListener(OnClickListener listener) {
        mRetryListener = listener;
        return this;
    }
}
