package com.ran.mall.ui.goodlist

import android.app.Activity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ran.library.base.SystemManager
import com.ran.mall.entity.bean.GoodInfo
import com.ran.mall.https.HttpRequestClient
import com.ran.mall.system.SystemHttpRequest
import org.json.JSONObject

/**
 */
class GoodListPresenter(mContext: GoodListActivity, mView: GoodListContract.View) : GoodListContract.Presenter {


    override fun getGoodListData(goodType: Int) {

        mView?.showLoading()


        SystemManager.getInstance().getSystem(SystemHttpRequest::class.java).getGoodList(goodType.toString(), object : HttpRequestClient.RequestHttpCallBack {

            override fun onSuccess(json: String?) {

                val jsonObject = JSONObject(json)
                val jsonlist = jsonObject.getString("content")
                mListGoodDatas.clear()
                mListGoodDatas.addAll(Gson().fromJson<ArrayList<GoodInfo>>(jsonlist, object : TypeToken<List<GoodInfo>>() {}.type))

                mView?.requestSuccess(mListGoodDatas)

            }
            override fun onFail(err: String?, code: Int) {
                mContext?.runOnUiThread {
                    mView?.hideLoading()
                    mView?.requestFail(code, err!!)
                }
            }
        })
    }

    override fun start() {

    }


    private var mContext: Activity = checkNotNull(mContext)
    private var mView: GoodListContract.View = checkNotNull(mView)
    private var mListGoodDatas = ArrayList<GoodInfo>()

    init {
        mView.setPresenter(this)
    }

    override fun exit() {
    }


}