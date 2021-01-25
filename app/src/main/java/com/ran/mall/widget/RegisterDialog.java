package com.ran.mall.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ran.mall.R;
import com.ran.mall.utils.CodeUtils;
import com.ran.mall.utils.LogUtils;


/**
 */
public class RegisterDialog extends Dialog {
    private EditText mUserIdEditText;
    private EditText mUserPassEditText;
    private EditText mUserRePassEditText;
    private EditText mUserPhoneEditText;
    private EditText mUserConfirmCodeEditText;
    private ImageView mUserConfirmCodeImage;
    private ImageView mUserConfirmCodeStatusImage;
    private TextView mNoticeMessage;
    private TextView mCancelBtn;
    private TextView mRegisterBtn;
    private onDialogListenerCallBack mListener;
    private Context mContext;

    private Bitmap mConfirmCodeBitmap;
    private String mStrImageCodeResult;

    public RegisterDialog(Context context) {
        super(context, R.style.MyDialog);
        mContext = context;

    }

    public void setOnDialohClickListener(onDialogListenerCallBack listener) {
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_registerid);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mUserIdEditText = (EditText) findViewById(R.id.regist_tv_text);
        mUserIdEditText.setText("");
        mUserPassEditText = (EditText) findViewById(R.id.regist_pass_tv_text);
        mUserPassEditText.setText("");
        mUserPassEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mUserRePassEditText = (EditText) findViewById(R.id.regist_conform_pass_tv_text);
        mUserRePassEditText.setText("");
        mUserRePassEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mUserPhoneEditText = (EditText) findViewById(R.id.phone_tv_text);
        mUserPhoneEditText.setText("");
        mUserConfirmCodeEditText = (EditText) findViewById(R.id.confirm_code_tv_text);
        mUserConfirmCodeEditText.setText("");
        mUserConfirmCodeImage = (ImageView) findViewById(R.id.confirm_code_pic_id);

        mNoticeMessage = (TextView) findViewById(R.id.notice_message_tv_text);

        mConfirmCodeBitmap = CodeUtils.getInstance().createBitmap();
        mStrImageCodeResult = CodeUtils.getInstance().getCode();
        mUserConfirmCodeImage.setImageBitmap(mConfirmCodeBitmap);
        LogUtils.i("CodeResult: " + mStrImageCodeResult);

        mUserConfirmCodeStatusImage = (ImageView) findViewById(R.id.confirm_code_checkstatus_id);
        mUserConfirmCodeStatusImage.setVisibility(View.INVISIBLE);
        mCancelBtn = (TextView) findViewById(R.id.leftContent);
        mRegisterBtn = (TextView) findViewById(R.id.confirm);

        mUserConfirmCodeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConfirmCodeBitmap = CodeUtils.getInstance().createBitmap();
                mStrImageCodeResult = CodeUtils.getInstance().getCode();
                mUserConfirmCodeImage.setImageBitmap(mConfirmCodeBitmap);
                LogUtils.i("CodeResult: " + mStrImageCodeResult);
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterDialog.this.dismiss();
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = mUserIdEditText.getText().toString();
                String userPass = mUserPassEditText.getText().toString();
                String userrePass = mUserRePassEditText.getText().toString();
                String userPhone = mUserPhoneEditText.getText().toString();
                String userConfirmCode = mUserConfirmCodeEditText.getText().toString().toLowerCase();

                LogUtils.i("ConfirmCode: " + userConfirmCode);

                if (userPass.length() < 8){
                    mNoticeMessage.setText("密码必须不少于8个字符!");
                    return;
                } else if (!userPass.equals(userrePass)){
                    mNoticeMessage.setText("两次输入密码不一致!");
                    return;
                } else if (!userConfirmCode.equals(mStrImageCodeResult)){
                    mNoticeMessage.setText("验证码错误!");
                    return;
                }

                if (mListener != null) {
                    mListener.onOkCliclck(userId, userPass, userPhone);
                }
            }
        });


    }

    public interface onDialogListenerCallBack {
        public void onOkCliclck(String userId, String userPass, String userPhone);
    }
}
