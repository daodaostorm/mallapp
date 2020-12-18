package com.ran.mall.ui.my

import com.ran.mall.base.BasePresenterItf
import com.ran.mall.base.BaseViewT

/**
 */

interface MyContract {


    interface View : BaseViewT<Presenter> {

        fun showExitDialog()

        fun userLogoutSccuess()

    }

    interface Presenter : BasePresenterItf {

        fun exit() //退出

        fun feedback() //意见反馈

        fun amendPwd() // 修改密码

        fun userLogout()

    }
}