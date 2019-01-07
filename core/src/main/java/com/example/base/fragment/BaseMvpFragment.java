package com.example.base.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.base.IPresenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMvpFragment<P extends IPresenter> extends Fragment {

    public P mPresenter;
    private Unbinder mUnbinder;
    private Bundle savedInstanceState;

    private boolean canInit = false;
    private boolean isInit = false;

    protected abstract P setPresenter();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract @LayoutRes int getContentView();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        mPresenter = setPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(),container,false);

        mUnbinder = ButterKnife.bind(this, view);
        canInit = true;

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    private void isCanLoadData(){
        // 可见 && 可以初始化 && 未初始化过
        if(getUserVisibleHint() && canInit && !isInit){
            isInit = true;
            init(savedInstanceState);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        if(mPresenter!=null){
            mPresenter = null;
        }
    }
}
