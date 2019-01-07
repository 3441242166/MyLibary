package com.example.base.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.base.BasePresenter;
import com.example.core.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseTopBarActivity<P extends BasePresenter> extends BaseMvpActivity<P> {
    private static final String TAG = "BaseTopBarActivity";

    private Unbinder mUnbinder;
    private Toolbar toolbar;
    private TextView tvTitle;
    private FrameLayout viewContent;

    protected abstract void initContent();
    protected abstract int getFrameView();

    @Override
    protected void init(Bundle savedInstanceState) {
        Log.i(TAG, "init");

        toolbar = findViewById(R.id.toolbar);
        viewContent = findViewById(R.id.fl_content);
        tvTitle = findViewById(R.id.toolbar_title);

        LayoutInflater.from(this).inflate(getFrameView(), viewContent);
        mUnbinder = ButterKnife.bind(this, viewContent);

        initContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.topbar_activity;
    }

    public void setTitle(int strId){
        tvTitle.setText(strId);
    }

    public void setTitle(String title){
        tvTitle.setText(title);
    }

    public void setTopLeftButton(){
        setTopLeftButton(R.drawable.ic_return_white, view -> finish());
    }

    public void setTopLeftButton(int iconId){
        setTopLeftButton(iconId, view -> finish());
    }

    public void setTopLeftButton(View.OnClickListener onClickListener){
        setTopLeftButton(R.drawable.ic_return_white, onClickListener);
    }

    public void setTopLeftButton(int iconId, View.OnClickListener onClickListener){
        toolbar.setNavigationIcon(iconId);
        toolbar.setNavigationOnClickListener(onClickListener);
    }


    public void setTopRightButton(int menuId,Toolbar.OnMenuItemClickListener listener){
        toolbar.setOnMenuItemClickListener(listener);
        toolbar.inflateMenu(menuId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


}
