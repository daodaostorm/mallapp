package com.ran.mall.ui.my

import android.content.Intent
import android.view.View
import com.ran.mall.R
import com.ran.mall.base.BaseActivity_2
import com.ran.mall.ui.goodlist.GoodListActivity
import com.ran.mall.ui.login.LoginActivity
import com.ran.mall.ui.mainscreen.MainScreenActivity
import com.ran.mall.utils.CommonUtils
import com.ran.mall.utils.LogUtils
import com.ran.mall.utils.PreferenceUtils
import com.ran.mall.utils.ToastUtils
import com.ran.mall.widget.CustomDialog
import kotlinx.android.synthetic.main.activity_my.*
import kotlinx.android.synthetic.main.main_foot_layout.*

class MyActivity : BaseActivity_2(), MyContract.View {

    override fun userLogoutSccuess() {

    }

    override fun setPresenter(presenter: MyContract.Presenter?) {
        mPresenter = checkNotNull(presenter)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showExitDialog() {

    }

    private var mPresenter: MyContract.Presenter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_my
    }

    override fun initView() {
        mPresenter = MyPresenter(this, this)

        main_type_my.background = resources.getDrawable(R.drawable.main_5_on)

        val userInfo = PreferenceUtils.getUser()
        if (userInfo != null){
            userinfo_name.text = userInfo.user
        }

    }

    fun startToConnectSetting(){

    }
    fun startToDeviceDetail(){

    }

    fun startToDevice(){

    }


    fun startToSurvey(){


    }
    override fun responseToBackView() {
        super.responseToBackView()
        startToSurvey()
        this@MyActivity.finish()
    }

    fun startToMainActivity(){
        val intent = Intent(this, MainScreenActivity::class.java)
        this.startActivity(intent)
    }
    fun startToMallActivity(){
        val intent = Intent(this, GoodListActivity::class.java)
        this.startActivity(intent)
    }

    fun startToLoginOrDetail(){
        val intent = Intent(this, LoginActivity::class.java)
        this.startActivity(intent)
    }

    fun bottomClick(view: View){
        when(view.id){
            R.id.main_type_first -> {
                startToMainActivity()
            }
            R.id.main_type_mall -> {
                startToMallActivity()
            }
            R.id.userinfo_name -> {
                startToLoginOrDetail()
            }
        }
    }
}
