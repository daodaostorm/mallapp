package com.ran.mall.ui.main

import com.ran.mall.base.BasePresenterItf
import com.ran.mall.base.BaseViewT
import com.ran.mall.entity.bean.TaskDetailInfo
import com.ran.mall.entity.bean.TaskInfoBean

/**
 */

interface MainContract {


    interface View : BaseViewT<Presenter> {

        fun requestFail(errCode: Int, errMsg: String)
        fun requestSuccess(listInfo: ArrayList<TaskDetailInfo>)
    }

    interface Presenter : BasePresenterItf {
        
		fun exit() //退出

        fun getListTaskData(pageIndex: Int, strTimeInterval: String)
    }
}