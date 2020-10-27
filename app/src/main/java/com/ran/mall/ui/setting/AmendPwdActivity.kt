package com.ran.mall.ui.setting

import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import com.ran.mall.R
import com.ran.mall.base.BaseActivity_2
import com.ran.mall.entity.constant.SPConstant
import com.ran.mall.system.SystemHttpRequest
import com.ran.mall.utils.CommonTextWatcher
import com.ran.mall.utils.LogUtils
import com.ran.mall.utils.SPUtils
import com.ran.mall.utils.ToastUtils
import com.ran.mall.widget.CustomDialog
import kotlinx.android.synthetic.main.activity_amend_pwd.*
import org.json.JSONObject


class AmendPwdActivity : BaseActivity_2() {
    override fun getLayoutId(): Int {
        return R.layout.activity_amend_pwd
    }

    override fun initView() {
        setLeftIconShow(true)
        setTitleText(resources.getString(R.string.amendpwd))
        sumbit.setOnClickListener {
            val oldPwdtxt = et_oldPwd.text.toString().trim() //旧密码
            val newPwdtxt = et_newPwd.text.toString().trim() //新密码
            val affirmnewPwdtxt = et_affirmnewPwd.text.toString().trim() //确认密码
            LogUtils.i("oldPwdtxt:$oldPwdtxt\nnewPwdtxt:$newPwdtxt\narrirmnewpwdtxt:$affirmnewPwdtxt")
            when {
                TextUtils.isEmpty(oldPwdtxt) -> {
                    ToastUtils.shortShow(getString(R.string.pwd_error))
                    return@setOnClickListener
                }
                TextUtils.isEmpty(newPwdtxt) -> {
                    ToastUtils.shortShow(getString(R.string.pwd_error))
                    return@setOnClickListener
                }
                newPwdtxt.length < 6 -> {
                    ToastUtils.shortShow(getString(R.string.pwd_error))
                    return@setOnClickListener
                }
                TextUtils.isEmpty(affirmnewPwdtxt) -> {
                    ToastUtils.shortShow(getString(R.string.pwd_error))
                    return@setOnClickListener
                }
                newPwdtxt.length < 6 -> {
                    ToastUtils.shortShow(getString(R.string.pwd_error))
                    return@setOnClickListener
                }
                else -> {
                    //请求接口

                    val loginName = SPUtils.get(this@AmendPwdActivity, SPConstant.LOGIN_NAME, "") as String
                    val json = JSONObject()
                    json.put("loginName", loginName)
                    json.put("password", oldPwdtxt)
                    json.put("newPw", newPwdtxt)
                    json.put("confirmPw", affirmnewPwdtxt)
                    /*getSystem(SystemHttpRequest::class.java).resetPwd(json, object : HttpRequestClient.RequestHttpCallBack {
                        override fun onSuccess(json: String?) {

                            LogUtils.i("resetPwd:onSuccess ", json.toString())

                            runOnUiThread {
                                showErrorTip(json.toString(), true)

                            }
                        }

                        override fun onFail(err: String?, code: Int) {
                            runOnUiThread {
                                showErrorTip(err!!, false)

                            }
                            LogUtils.i("resetPwd:onFail ", err!! + "----" + code)

                        }

                    })*/


                }
            }
        }
        et_oldPwd.isFocusable = true
        et_oldPwd.isFocusableInTouchMode = true
        et_oldPwd.requestFocus()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        et_oldPwd.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (!TextUtils.isEmpty(et_oldPwd.text.toString().trim())) {
                    iv_oldPwd.visibility = View.VISIBLE
                } else {
                    iv_oldPwd.visibility = View.INVISIBLE
                }

            } else {
                iv_oldPwd.visibility = View.INVISIBLE
            }

        }
        et_oldPwd.addTextChangedListener(object : CommonTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                if (!TextUtils.isEmpty(s.toString())) {
                    iv_oldPwd.visibility = View.VISIBLE
                } else {
                    iv_oldPwd.visibility = View.INVISIBLE
                }

            }


        })

        iv_oldPwd.setOnClickListener {
            et_oldPwd.text.clear()
        }
        iv_newPwd.setOnClickListener {
            et_newPwd.text.clear()
        }
        iv_affirmnewPwd.setOnClickListener {
            et_affirmnewPwd.text.clear()
        }

        et_newPwd.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (!TextUtils.isEmpty(et_newPwd.text.toString().trim())) {
                    iv_newPwd.visibility = View.VISIBLE
                } else {
                    iv_newPwd.visibility = View.INVISIBLE
                }

            } else {
                iv_newPwd.visibility = View.INVISIBLE
            }

        }
        et_newPwd.addTextChangedListener(object : CommonTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                if (!TextUtils.isEmpty(s.toString())) {
                    iv_newPwd.visibility = View.VISIBLE
                } else {
                    iv_newPwd.visibility = View.INVISIBLE
                }

            }


        })
        et_affirmnewPwd.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (!TextUtils.isEmpty(et_affirmnewPwd.text.toString().trim())) {
                    iv_affirmnewPwd.visibility = View.VISIBLE
                } else {
                    iv_affirmnewPwd.visibility = View.INVISIBLE
                }

            } else {
                iv_affirmnewPwd.visibility = View.INVISIBLE
            }

        }
        et_affirmnewPwd.addTextChangedListener(object : CommonTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                if (!TextUtils.isEmpty(s.toString())) {
                    iv_affirmnewPwd.visibility = View.VISIBLE
                } else {
                    iv_affirmnewPwd.visibility = View.INVISIBLE
                }

            }

        })


    }

    fun showErrorTip(tip: String, isJump: Boolean) {
        CustomDialog.bulider().showRadioDialog(this, tip, object : CustomDialog.DialogClickListener {
            override fun confirm() {
                if (isJump) {
                    SPUtils.remove(this@AmendPwdActivity, SPConstant.LOGIN_PWD)
                    logout()
                }

            }

            override fun cancel() {

            }
        })
    }

    override fun responseToBackView() {
        super.responseToBackView()
        this@AmendPwdActivity.finish()
    }

}
