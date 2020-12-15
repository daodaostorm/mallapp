package com.ran.mall.ui.gooddetail.adapter

import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.ran.library.base.BaseFragmentPagerAdapter
import com.ran.library.base.BaseLazyViewPagerFragment
import com.ran.mall.ui.gooddetail.GoodDetailViewPagerFragment
import com.ran.mall.ui.gooddetail.GoodHistoryViewPagerFragment

/**
 */
class GoodDetailViewPagerAdapter (activity: FragmentActivity) :
        BaseFragmentPagerAdapter<BaseLazyViewPagerFragment>(activity) {

    override fun init(fm: FragmentManager, list: MutableList<BaseLazyViewPagerFragment>, mTitlePages: MutableList<String>, count:Int) {
        mTitlePages.add("商品详情")
        mTitlePages.add("购买记录")

        list.add(GoodDetailViewPagerFragment.newInstance())
        list.add(GoodHistoryViewPagerFragment.newInstance())
    }


}