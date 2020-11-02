package com.ran.mall.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.github.jdsjlzx.recyclerview.ProgressStyle
import com.google.gson.Gson
import com.ran.mall.R
import com.ran.mall.base.BaseActivity_2
import com.ran.mall.entity.bean.*
import com.ran.mall.ui.adapter.TaskInfoAdapter
import com.ran.mall.ui.login.LoginActivity
import com.ran.mall.utils.LogUtils
import com.ran.mall.utils.ToastUtils
import com.ran.mall.utils.permission.PermissionConstants
import com.ran.mall.utils.permission.PermissionUtils
import com.ran.mall.widget.CustomDialog
import com.ran.mall.widget.LoadingView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.main_foot_layout.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : BaseActivity_2(),MainContract.View {

    override fun requestBannerFail(errCode: Int, errMsg: String) {
            ToastUtils.shortShow(errMsg)
    }

    override fun requestBannerSuccess(listInfo: ArrayList<BannerInfo>) {
        LogUtils.i(Gson().toJson(listInfo))
        mListBannerDatas = listInfo


    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }

    var mListDatas = ArrayList<TaskDetailInfo>()

    var mListBannerDatas = ArrayList<BannerInfo>()

    private var mCurrentPage = 1
    private val REQUEST_COUNT = 10
    private var isRefresh = false
    private var mTaskInfoAdapter: TaskInfoAdapter? = null

    private var mPresenter: MainContract.Presenter? = null

    private var mLoadingView: LoadingView? = null
    private var mBluetoothLoadingView: LoadingView? = null

    override fun setPresenter(presenter: MainContract.Presenter?) {

        mPresenter = checkNotNull(presenter)
    }

    override fun requestFail(errCode: Int, errMsg: String) {

        hideLoading()

        runOnUiThread {
            ToastUtils.longShow(errMsg)
        }
    }

    override fun requestSuccess(list: ArrayList<TaskDetailInfo>) {


        hideLoading()

        runOnUiThread {
            mListDatas.addAll(list)

            if (list.isEmpty()) {
                if (mCurrentPage == 1) {
                    mTaskInfoAdapter?.clear()
                    recyclerView?.setEmptyView(empty_view)
                    LogUtils.i("EmptyView")
                } else {
                    recyclerView.setNoMore(true)
                    LogUtils.i("NoMore")
                }
            } else {
                mTaskInfoAdapter?.clear()
                mTaskInfoAdapter?.setDataList(mListDatas)
            }
            recyclerView.refreshComplete(REQUEST_COUNT)
            mCurrentPage++
            isRefresh = false
        }
    }




    override fun showLoading() {
        runOnUiThread {
            if (mLoadingView != null ) {
                if (!(mLoadingView!!.isShowing())){
                    mLoadingView!!.show()
                }
            }else {
                mLoadingView = LoadingView(this, "请稍后...", LoadingView.SHOWLOADING)
                mLoadingView?.show()
            }
        }
    }

    override fun hideLoading() {
        runOnUiThread {
            if (mLoadingView != null && mLoadingView!!.isShowing()) {
                try {
                    mLoadingView!!.dismiss()
                } catch (e : Exception) {

                } finally {
                    mLoadingView = null
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    override fun initView() {
        mPresenter = MainPresenter(this, this)
        //setLeftIconShow(true)

        setLeftViewIcon(R.drawable.icon_black_left_back)

        setTitleText("")

        initRecyclerView()

        main_type_first.background = resources.getDrawable(R.drawable.main_tap_first_select)
        refreshData()
    }


    private val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE)

    interface onPermissionListener {
        fun onGranted()
        fun onDenied()
    }

    @SuppressLint("WrongConstant")
    @PermissionConstants.Permission
    fun checkPermission(onPermission: onPermissionListener) {
        val per = permissions

        PermissionUtils
                .permission(*per)
                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: MutableList<String>?) {
                        if (per.size == permissionsGranted?.size) {
                            onPermission.onGranted()

                        }
                        permissionsGranted?.forEach {
                            LogUtils.i("permissionsGranted-----$it")
                        }
                    }


                    override fun onDenied(permissionsDeniedForever: MutableList<String>?, permissionsDenied: MutableList<String>?) {
                        val strBuilder = StringBuilder()
                        permissionsDeniedForever?.forEach {
                            when (it) {
                                Manifest.permission.READ_EXTERNAL_STORAGE -> {
                                    strBuilder.append("存储权限、")
                                }
                                Manifest.permission.READ_PHONE_STATE -> {

                                    strBuilder.append("电话权限、")
                                }
                                Manifest.permission.ACCESS_FINE_LOCATION -> {
                                    strBuilder.append("位置权限、")
                                }

                                else -> {
                                }
                            }
                            //被拒绝的权限，而且点击了不允许再次提醒
                            LogUtils.i("permissionsDeniedForever-----$it")
                        }
                        permissionsDenied?.forEach {
                            //被拒绝的权限
                            LogUtils.i("permissionsDenied-----$it")
                        }
                        if (permissionsDeniedForever?.size != 0)
                            if (!this@MainActivity.isFinishing) {
                                CustomDialog.bulider().showSelectDialog(this@MainActivity, "确认去授权？",
                                        strBuilder.toString() + "这些权限被拒绝", object : CustomDialog.DialogClickListener {
                                    override fun confirm() {
                                        PermissionUtils.openAppSettings()
                                    }

                                    override fun cancel() {

                                    }
                                })
                            }
                    }
                })
                .rationale {
                    it.again(true)
                }
                .request()
    }
    override fun onDestroy() {
        super.onDestroy()
    }
    fun initRecyclerView() {


        mTaskInfoAdapter = TaskInfoAdapter(this)

        recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = LRecyclerViewAdapter(mTaskInfoAdapter)
            setLoadMoreEnabled(true)
            setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader) //设置下拉刷新Progress的样式
            setArrowImageView(R.drawable.progressbar) //设置下拉刷新箭头
            setHeaderViewColor(R.color.gray_text, R.color.gray_text, R.color.app_bg)
            setFooterViewColor(R.color.gray_text, R.color.gray_text, R.color.app_bg)//        设置底部加载颜色
            setOnLoadMoreListener {
                LogUtils.i("上拉加载更多")
                //requestData()

            }
            setOnRefreshListener {
                LogUtils.i("下拉刷新加载更多")
                isRefresh = true
                refreshData()
            }
        }

        mTaskInfoAdapter!!.setOnReportClickListener(object : TaskInfoAdapter.ReportClickListener {
            override fun skipVideoActivity(strJson: String) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    fun refreshData() {
        mCurrentPage = 1
        mListDatas.clear()

        mPresenter?.getBannerListData()
        //mPresenter?.getListTaskData(mCurrentPage, "")

    }
    override fun responseToBackView() {
        super.responseToBackView()
    }

    override fun responseToRightView() {
        super.responseToRightView()
    }

    fun startToSettingActivity() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        startActivity(intent)
    }

    fun startToDeviceDetail(){

    }

    fun bottomClick(view: View){
        when(view.id){
            R.id.main_type_first -> {

            }
            R.id.main_type_my -> {
                startToSettingActivity()
            }

        }
    }

}
