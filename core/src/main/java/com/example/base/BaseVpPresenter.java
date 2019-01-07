package com.example.base;

import android.content.Context;

public abstract class BaseVpPresenter<V extends IView> extends BasePresenter<V,IModel> {

    public BaseVpPresenter(V view, Context context) {
        super(view, context);
    }

    @Override
    protected IModel setModel() {
        return null;
    }
}
