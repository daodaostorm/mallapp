package com.ran.mall.ui.addresslist

import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.google.gson.Gson
import com.ran.mall.R
import com.ran.mall.base.BaseActivity_2
import com.ran.mall.entity.bean.AddressInfoBean
import com.ran.mall.ui.adapter.AddressInfoAdapter
import java.util.*
import kotlin.collections.ArrayList


class AddressListActivity : BaseActivity_2(), AddressListContract.View {
    override fun requestFail(errCode: Int, errMsg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun requestSuccess(listInfo: ArrayList<AddressInfoBean>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private var mPresenter: AddressListContract.Presenter? = null

    var mDeviceAdapter: AddressInfoAdapter?= null

    var mFromActivity: Int = 0

    override fun setPresenter(presenter: AddressListContract.Presenter?) {

        mPresenter = checkNotNull(presenter)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_addresslists
    }

    override fun initView() {
        mPresenter =AddressListPresenter(this, this)
        setLeftIconShow(true)

        setRightViewIcon(R.drawable.button_blue_stroke_bg)

        setTitleText(resources.getString(R.string.mainlist_title))

        mDeviceAdapter = AddressInfoAdapter(this)

        //UpdateDeviesInfo()

    }

    fun UpdateDeviesInfo(){

    }
    override fun responseToBackView() {
        super.responseToBackView()
        this@AddressListActivity.finish()
    }

    fun startToDetail(){

    }

    fun buttonClick(view: View) {
        when (view.id) {
            R.id.skip_btn -> {

                startToDetail()
            }
        }

    }

}
