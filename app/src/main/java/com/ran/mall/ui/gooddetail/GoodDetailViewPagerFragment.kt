package com.ran.mall.ui.gooddetail

import android.support.v7.widget.LinearLayoutManager
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.ran.library.base.BaseLazyViewPagerFragment
import com.ran.mall.R
import com.ran.mall.entity.bean.GoodDetailAdapterBean
import com.ran.mall.ui.adapter.GoodDetailInfoAdapter
import kotlinx.android.synthetic.main.activity_gooddetail_detail_fragment.*


class GoodDetailViewPagerFragment : BaseLazyViewPagerFragment() {
    override fun getLayoutId(): Int {
        return R.layout.activity_gooddetail_detail_fragment
    }

    val REQUEST_COUNT=10

    override fun getTitleBarId(): Int {
        return 0
    }

    var detailActivity: GoodDetailActivity? = null

    var mGoodInfoAdapter: GoodDetailInfoAdapter? = null

    var mDataList: ArrayList<GoodDetailAdapterBean>? = null

    override fun initView() {
        detailActivity = supportActivity as GoodDetailActivity
        mDataList = ArrayList<GoodDetailAdapterBean>()
        initRecyclerView()
    }

    override fun initData() {

        mDataList!!.clear()
        val data1 = GoodDetailAdapterBean()
        data1.detailpic = detailActivity!!.mGoodInfo.detailpic1
        val data2 = GoodDetailAdapterBean()
        data2.detailpic = detailActivity!!.mGoodInfo.detailpic2
        val data3 = GoodDetailAdapterBean()
        data3.detailpic = detailActivity!!.mGoodInfo.detailpic3
        mDataList!!.add(data1)
        mDataList!!.add(data2)
        mDataList!!.add(data3)

        mGoodInfoAdapter!!.setDataList(mDataList!!)

        recyclerView_gooddetail.refreshComplete(REQUEST_COUNT)
    }

    override fun isLazyLoad(): Boolean {
        return false
    }

    fun initRecyclerView(){
        mGoodInfoAdapter = GoodDetailInfoAdapter(supportActivity)

        recyclerView_gooddetail.apply {
            layoutManager = LinearLayoutManager(supportActivity)
            adapter = LRecyclerViewAdapter(mGoodInfoAdapter)
            setArrowImageView(R.drawable.progressbar)
            setOnLoadMoreListener {
                refreshComplete(REQUEST_COUNT)
            }
            setOnRefreshListener {
                refreshComplete(REQUEST_COUNT)
            }
        }
    }



    companion object {

        fun newInstance(): GoodDetailViewPagerFragment {
            return GoodDetailViewPagerFragment()
        }
    }
}