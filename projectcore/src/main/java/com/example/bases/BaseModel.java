package com.example.base;

import com.example.base.IModel;

public class BaseModel implements IModel {
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    public void onDestroy() {

    }
}
