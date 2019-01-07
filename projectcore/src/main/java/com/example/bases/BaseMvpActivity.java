package com.example.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.LayoutRes;

public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity {

    public T presenter;

    protected abstract T setPresenter();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract @LayoutRes int getContentView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int contentId = getContentView();

        if(contentId != -1) {
            setContentView(getContentView());
        }

        presenter = setPresenter();

        init(savedInstanceState);

        if(presenter != null){
            presenter.onStart();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(presenter!=null){
            presenter.onDestroy();
            presenter = null;
        }

    }
}
