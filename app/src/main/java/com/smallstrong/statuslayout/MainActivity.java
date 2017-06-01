package com.smallstrong.statuslayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.smallstrong.layouthelper.StatusLayout;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.ll_content)
    LinearLayout llContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayoutId() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void setStatusLayout() {
//        statusLayout = StatusLayout.wrap(llContent);//如果你觉得我lib里面的就够用了 就只需这行代码，当然你可以自己去改lib里面的res

        statusLayout = StatusLayout.wrap(llContent)//设置需要被替换的布局
                .setLoading(R.layout.my_layout_loading)// 可设置自定义loading布局

                .setEmpty(R.layout.my_layout_empty)// 可设置自定义empty布局

                .setError(R.layout.my_layout_error)
                .setRetryBtnId(R.id.my_retry_button);// 可设置自定义error布局,要传入重新加载的button的Id，记住一定是button啊

    }

    @Override
    public void reLoad() {
        //网络异常之后点击再次加载
        Toast.makeText(getApplicationContext(), "try reload", Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_empty:
                showEmpty();
                return true;
            case R.id.action_loading:
                showLoading();
                return true;
            case R.id.action_content:
                showContent();
                return true;
            case R.id.action_error:
                showError();
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.loading, menu);
        return true;
    }

}
