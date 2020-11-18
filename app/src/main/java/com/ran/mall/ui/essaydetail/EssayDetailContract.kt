package com.ran.mall.ui.essaydetail

import com.ran.mall.base.BasePresenterItf
import com.ran.mall.base.BaseViewT
import com.ran.mall.entity.bean.EssayInfo

/**
 */

interface EssayDetailContract {


    interface View : BaseViewT<Presenter> {

        fun requestFail(errCode: Int, errMsg: String)
        fun requestSuccess(essayinfo: EssayInfo)

    }

    interface Presenter : BasePresenterItf {
        
		fun exit() //退出

        fun getEssayDetail(essayId: String)

    }
}