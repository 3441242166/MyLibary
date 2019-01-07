package com.example.base.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

import com.example.base.IPresenter;

public abstract class BaseMvpActivity<T extends IPresenter> extends BaseActivity {

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
            getLifecycle().addObserver(presenter);
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
