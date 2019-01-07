package com.example.base;


import android.arch.lifecycle.LifecycleObserver;

public interface IModel extends LifecycleObserver {

    void onDestroy();
}
