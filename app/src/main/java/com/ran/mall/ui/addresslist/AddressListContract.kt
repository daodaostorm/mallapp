package com.ran.mall.ui.addresslist

import com.ran.mall.base.BasePresenterItf
import com.ran.mall.base.BaseViewT
import com.ran.mall.entity.bean.AddressInfoBean

/**
 */

interface AddressListContract {


    interface View : BaseViewT<Presenter> {

        fun requestFail(errCode: Int, errMsg: String)
        fun requestSuccess(listInfo: ArrayList<AddressInfoBean>)
    }

    interface Presenter : BasePresenterItf {
        fun exit() //退出

        fun getAddressListData(userId: String)
    }
}