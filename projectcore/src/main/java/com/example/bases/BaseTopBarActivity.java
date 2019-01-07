package com.example.base;

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

import com.example.projectcore.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseTopBarActivity<P extends BasePresenter> extends BaseMvpActivity<P> {

    private Unbinder mUnbinder;
    private Toolbar toolbar;
    private FrameLayout viewContent;

    private OnClickListener onClickListenerTopLeft;
    private OnClickListener onClickListenerTopRight;
    private boolean showMenu;
    private String menuStr;

    protected abstract void initContent();
    protected abstract void initEvent();

    public interface OnClickListener{
        void onClick();
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        toolbar = findViewById(R.id.toolbar);
        viewContent = findViewById(R.id.fl_content);

        //初始化设置 Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater.from(this).inflate(getContentView(), viewContent);
        mUnbinder = ButterKnife.bind(this, viewContent);

        initContent();
    }

    @Override
    protected int getContentView() {
        return R.layout.topbar_activity;
    }

    public void setTitle(int strId){
        toolbar.setTitle(getString(strId));
    }

    public void setTitle(String title){
        toolbar.setTitle(title);
    }

    public void setTopLeftButton(){
        setTopLeftButton(R.drawable.ic_x,this::finish);
    }

    public void setTopLeftButton(int id){
        setTopLeftButton(id, this::finish);
    }

    public void setTopLeftButton(OnClickListener onClickListener){
        setTopLeftButton(R.drawable.ic_x, onClickListener);
    }

    public void setTopLeftButton(int iconResId, OnClickListener onClickListener){
        toolbar.setNavigationIcon(iconResId);
        this.onClickListenerTopLeft = onClickListener;
    }

    public void setTopRightButton(String menuStr, OnClickListener onClickListener){
        this.onClickListenerTopRight = onClickListener;
        this.menuStr = menuStr;
    }

    public void setTopRightButton(String menuStr, boolean showMenu, OnClickListener onClickListener){
        this.showMenu = showMenu;
        this.menuStr = menuStr;
        this.onClickListenerTopRight = onClickListener;
    }

    protected abstract int getFrameView();

    //只调用一次
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!TextUtils.isEmpty(menuStr)){
            getMenuInflater().inflate(R.menu.menu_activity_base_top_bar, menu);
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!TextUtils.isEmpty(menuStr)){
            menu.findItem(R.id.menu_1).setTitle(menuStr);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onClickListenerTopLeft.onClick();
        }
        else if (item.getItemId() == R.id.menu_1){
            onClickListenerTopRight.onClick();
        }
        // true 告诉系统我们自己处理了点击事件
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


}
