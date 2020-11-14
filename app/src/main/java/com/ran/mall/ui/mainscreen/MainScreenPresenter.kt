package com.ran.mall.ui.main

import android.app.Activity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ran.library.base.SystemManager
import com.ran.mall.entity.bean.BannerInfo
import com.ran.mall.entity.bean.EssayInfo
import com.ran.mall.https.HttpRequestClient
import com.ran.mall.system.SystemHttpRequest
import com.ran.mall.ui.mainscreen.MainScreenActivity
import com.ran.mall.ui.mainscreen.MainScreenContract
import com.ran.mall.utils.PreferenceUtils
import org.json.JSONObject

/**
 */
class MainScreenPresenter(mContext: MainScreenActivity, mView: MainScreenContract.View) : MainScreenContract.Presenter {

    override fun getBannerListData() {
        mView?.showLoading()

        SystemManager.getInstance().getSystem(SystemHttpRequest::class.java).getBannerList(object : HttpRequestClient.RequestHttpCallBack {

            override fun onSuccess(json: String?) {

                mContext?.runOnUiThread {

                    mView?.hideLoading()

                    val jsonObject = JSONObject(json)
                    val jsonlist = jsonObject.getString("content")
                    mBannerListData.clear()
                    mBannerListData.addAll(Gson().fromJson<ArrayList<BannerInfo>>(jsonlist, object : TypeToken<List<BannerInfo>>() {}.type))

                    mView?.requestBannerSuccess(mBannerListData)

                }

            }
            override fun onFail(err: String?, code: Int) {
                mContext?.runOnUiThread {
                    mView?.hideLoading()
                    mView?.requestBannerFail(code, err!!)
                }
            }
        })
    }


    override fun getListEssayData() {

        mView?.showLoading()


        SystemManager.getInstance().getSystem(SystemHttpRequest::class.java).getEssayList(object : HttpRequestClient.RequestHttpCallBack {

            override fun onSuccess(json: String?) {

                val jsonObject = JSONObject(json)
                val jsonlist = jsonObject.getString("content")
                mListEssayDatas.clear()
                mListEssayDatas.addAll(Gson().fromJson<ArrayList<EssayInfo>>(jsonlist, object : TypeToken<List<EssayInfo>>() {}.type))

                mView?.requestSuccess(mListEssayDatas)

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
    private var mView: MainScreenContract.View = checkNotNull(mView)
    private var mListEssayDatas = ArrayList<EssayInfo>()
    private var mBannerListData = ArrayList<BannerInfo>()

    init {
        mView.setPresenter(this)
    }

    override fun exit() {
    }


}