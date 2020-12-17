package com.ran.mall.ui.gooddetail

import android.support.v7.widget.LinearLayoutManager
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.ran.library.base.BaseLazyViewPagerFragment
import com.ran.mall.R
import com.ran.mall.entity.bean.GoodHistoryBean
import com.ran.mall.ui.adapter.GoodDetailHistoryAdapter
import kotlinx.android.synthetic.main.activity_gooddetail_history_fragment.*


class GoodHistoryViewPagerFragment : BaseLazyViewPagerFragment() {
    override fun getLayoutId(): Int {
        return R.layout.activity_gooddetail_history_fragment
    }

    override fun getTitleBarId(): Int {
        return 0
    }

    val REQUEST_COUNT=10

    var detailActivity: GoodDetailActivity? = null

    var mGoodHistoryAdapter: GoodDetailHistoryAdapter? = null

    var mDataList: ArrayList<GoodHistoryBean>? = null

    override fun initView() {
        detailActivity = supportActivity as GoodDetailActivity

        mDataList = ArrayList<GoodHistoryBean>()
        initRecyclerView()

    }


    override fun initData() {

        mDataList!!.clear()
        val data1 = GoodHistoryBean()
        data1.username = "tesst1"
        data1.usercomment = ""
        val data2 = GoodHistoryBean()
        data2.username = "tesst2"
        data2.usercomment = "很不错"
        val data3 = GoodHistoryBean()
        data3.username = "tesst3"
        data3.usercomment = "太差了。一点都不好用"
        val data4 = GoodHistoryBean()
        data4.username = "tesst4"
        data4.usercomment = "太差不好用"
        val data5 = GoodHistoryBean()
        data5.username = "tesst5"
        data5.usercomment = "太差  不好用"
        mDataList!!.add(data1)
        mDataList!!.add(data2)
        mDataList!!.add(data3)
        mDataList!!.add(data4)
        mDataList!!.add(data5)

        mGoodHistoryAdapter!!.setDataList(mDataList!!)

        recyclerView_goodhistory.refreshComplete(REQUEST_COUNT)
    }

    override fun isLazyLoad(): Boolean {
        return false
    }

    fun initRecyclerView(){
        mGoodHistoryAdapter = GoodDetailHistoryAdapter(supportActivity)

        recyclerView_goodhistory.apply {
            layoutManager = LinearLayoutManager(supportActivity)
            adapter = LRecyclerViewAdapter(mGoodHistoryAdapter)
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

        fun newInstance(): GoodHistoryViewPagerFragment {
            return GoodHistoryViewPagerFragment()
        }
    }
}