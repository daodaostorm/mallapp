package com.ran.mall.ui.addresslist

import android.app.Activity
import com.ran.library.base.SystemManager
import com.ran.mall.entity.bean.AddressInfoBean
import com.ran.mall.system.SystemHttpRequest

/**
 * effect：
 */
class AddressListPresenter(mContext: AddressListActivity, mView: AddressListContract.View) : AddressListContract.Presenter {



    private var mContext: Activity = checkNotNull(mContext)
    private var mView: AddressListContract.View = checkNotNull(mView)

    private var mDevices:ArrayList<AddressInfoBean> ?= null

    init {
        mView.setPresenter(this)
        mDevices = ArrayList<AddressInfoBean>()
    }

    override fun exit() {
    }

    override fun start() {
    }


    override fun getAddressListData(userId: String) {

    }
}