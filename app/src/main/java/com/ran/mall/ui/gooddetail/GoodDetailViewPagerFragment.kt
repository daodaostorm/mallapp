package com.ran.mall.ui.gooddetail

import com.ran.library.base.BaseLazyViewPagerFragment
import com.ran.mall.R
import kotlinx.android.synthetic.main.activity_taskdetail_web.*


class GoodDetailViewPagerFragment : BaseLazyViewPagerFragment() {
    override fun getLayoutId(): Int {
        return R.layout.activity_taskdetail_web
    }

    override fun getTitleBarId(): Int {
        return 0
    }

    var detailActivity: GoodDetailActivity? = null
    override fun initView() {
        detailActivity = supportActivity as GoodDetailActivity

        test_text.text = "GoodDetailViewPager"
    }

    override fun initData() {

    }

    override fun isLazyLoad(): Boolean {
        return false
    }


    companion object {

        fun newInstance(): GoodDetailViewPagerFragment {
            return GoodDetailViewPagerFragment()
        }
    }
}