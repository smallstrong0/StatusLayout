package com.smallstrong.statuslayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.smallstrong.layouthelper.StatusLayout;

import butterknife.ButterKnife;

/**
 * Created by smallstrong on 2017/6/1.
 */

public abstract class BaseActivity extends AppCompatActivity {
    StatusLayout statusLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayoutId();
        ButterKnife.bind(this);//在找到控件之后才能传入layout
        setStatusLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(statusLayout != null){
            statusLayout.setRetryListener(clickListener);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            reLoad();
        }
    };


    public abstract void reLoad();
    public abstract void setLayoutId();
    public abstract void setStatusLayout();

    public void showLoading(){
        if(statusLayout != null){
            statusLayout.showLoading();
        }
    }

    public void showContent(){
        if(statusLayout != null){
            statusLayout.showContent();
        }
    }

    public void showEmpty(){
        if(statusLayout != null){
            statusLayout.showEmpty();
        }
    }

    public void showError(){
        if(statusLayout != null){
            statusLayout.showError();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


}
