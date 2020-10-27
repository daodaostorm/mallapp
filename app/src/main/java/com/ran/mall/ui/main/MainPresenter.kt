package com.ran.mall.ui.main

import android.app.Activity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ran.library.base.SystemManager
import com.ran.mall.R
import com.ran.mall.entity.bean.TaskDetailInfo
import com.ran.mall.entity.bean.TaskInfoBean
import com.ran.mall.https.HttpRequestClient
import com.ran.mall.system.SystemHttpRequest
import com.ran.mall.utils.PreferenceUtils
import org.json.JSONObject

/**
 */
class MainPresenter(mContext: MainActivity, mView: MainContract.View) : MainContract.Presenter {


    override fun getListTaskData(pageIndex: Int, strTimeInterval: String) {

        mView?.showLoading()

        val userbean = PreferenceUtils.getUser() ?: return

        SystemManager.getInstance().getSystem(SystemHttpRequest::class.java).getTaskList(userbean!!.agent!!.loginName, 10, pageIndex,strTimeInterval,object : HttpRequestClient.RequestHttpCallBack {

            override fun onSuccess(json: String?) {
                //mView?.onCreateTaskSuccess()
                val jsonObject = JSONObject(json)
                val jsonlist = jsonObject.getString("list")
                mTaskListData.clear()
                mTaskListData.addAll(Gson().fromJson<ArrayList<TaskDetailInfo>>(jsonlist, object : TypeToken<List<TaskDetailInfo>>() {}.type))

                mView?.requestSuccess(mTaskListData)

            }
            override fun onFail(err: String?, code: Int) {
                mView?.requestFail(code, err!!)
            }
        })
    }

    override fun start() {

    }


    private var mContext: Activity = checkNotNull(mContext)
    private var mView: MainContract.View = checkNotNull(mView)
    private var mTaskListData = ArrayList<TaskDetailInfo>()

    init {
        mView.setPresenter(this)
    }

    override fun exit() {
    }


}