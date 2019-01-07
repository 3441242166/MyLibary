package com.example.administrator.libarytest


import com.example.base.activity.BaseTopBarVpActivity

class MainActivity : BaseTopBarVpActivity<MainPresenter>() ,IMainView  {

    override fun getFrameView() = R.layout.activity_main

    override fun setPresenter(): MainPresenter {
        return MainPresenter(this,this)
    }

    override fun initContent() {
        setTopLeftButton()
        setTopRightButton("") {
            setTitle("aaa")
        }
        setTitle("xxx")
    }
}
