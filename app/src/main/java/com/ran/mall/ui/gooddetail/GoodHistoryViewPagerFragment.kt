package com.ran.mall.ui.gooddetail

import android.support.v7.widget.LinearLayoutManager
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter
import com.ran.library.base.BaseLazyViewPagerFragment
import com.ran.mall.R
import com.ran.mall.entity.bean.GoodHistoryBean
import com.ran.mall.ui.adapter.GoodDetailHistoryAdapter
import kotlinx.android.synthetic.main.activity_gooddetail_history_fragment.*
import kotlinx.android.synthetic.main.activity_taskdetail_web.*


class GoodHistoryViewPagerFragment : BaseLazyViewPagerFragment() {
    override fun getLayoutId(): Int {
        return R.layout.activity_gooddetail_history_fragment
    }

    override fun getTitleBarId(): Int {
        return 0
    }

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
        mDataList!!.add(data1)
        mDataList!!.add(data2)
        mDataList!!.add(data3)

        mGoodHistoryAdapter!!.setDataList(mDataList!!)
    }

    override fun isLazyLoad(): Boolean {
        return false
    }

    fun initRecyclerView(){
        mGoodHistoryAdapter = GoodDetailHistoryAdapter(supportActivity)

        recyclerView_goodhistory.apply {
            layoutManager = LinearLayoutManager(supportActivity)
            adapter = LuRecyclerViewAdapter(mGoodHistoryAdapter)
        }
    }

    companion object {

        fun newInstance(): GoodHistoryViewPagerFragment {
            return GoodHistoryViewPagerFragment()
        }
    }
}