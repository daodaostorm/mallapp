package com.ran.mall.ui.setting

import android.content.Intent
import com.ran.mall.R
import com.ran.mall.base.BaseActivity_2
import com.ran.mall.utils.PreferenceUtils
import com.ran.mall.utils.ToastUtils
import com.ran.mall.widget.CustomDialog
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : BaseActivity_2(), SettingContract.View {

    override fun userLogoutSccuess() {

    }

    override fun setPresenter(presenter: SettingContract.Presenter?) {
        mPresenter = checkNotNull(presenter)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showExitDialog() {
        CustomDialog.bulider().showSelectDialog(this@SettingActivity, resources.getString(R.string.exit_title), resources.getString(R.string.exit_msg), object : CustomDialog.DialogClickListener {
            override fun confirm() {
                logout()
            }

            override fun cancel() {

            }
        }
        )
    }

    private var mPresenter: SettingContract.Presenter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initView() {
        mPresenter = SettingPresenter(this, this)
        setLeftIconShow(true)
        setTitleText("设置")
        feedback.setOnClickListener {
            mPresenter!!.feedback()
        }

        amendpwd.setOnClickListener {
            //startActivity(Intent(this@SettingActivity, AmendPwdActivity::class.java))
            ToastUtils.shortShow(resources.getString(R.string.wait_message))
        }

        exit.setOnClickListener {
            showExitDialog()
        }

        about_ll.setOnClickListener {
            ToastUtils.shortShow(resources.getString(R.string.wait_message))
        }

        video_devices_ll.setOnClickListener {
            startToDevice()
        }

        val userInfo = PreferenceUtils.getUser()
        if (userInfo != null){
            userinfo_name.text = userInfo.user
        }

        UpdateDeviceConnectStatus()

    }

    fun startToConnectSetting(){

    }
    fun startToDeviceDetail(){

    }

    fun startToDevice(){
        if (video_devices_status.text.trim().equals(resources.getString(R.string.device_connected_text))){
            startToDeviceDetail()
        } else {
            startToConnectSetting()
        }
    }

    fun UpdateDeviceConnectStatus(){
        val deviceInfo = PreferenceUtils.getPairedDevice()
        if (deviceInfo == null){
            video_devices_status.text = resources.getString(R.string.device_not_connected_text)
            video_devices_status.setTextColor(resources.getColor(R.color.black))
        } else {
            video_devices_status.text = resources.getString(R.string.device_connected_text)
            video_devices_status.setTextColor(resources.getColor(R.color.color_007aff))
        }
    }


    fun startToSurvey(){


    }
    override fun responseToBackView() {
        super.responseToBackView()
        startToSurvey()
        this@SettingActivity.finish()
    }

}
