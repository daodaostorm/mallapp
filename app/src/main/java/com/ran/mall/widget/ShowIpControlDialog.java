package com.ran.mall.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ran.mall.R;
import com.ran.mall.entity.constant.SPConstant;
import com.ran.mall.utils.SPUtils;


/**
 * Created by DELL on 2017/8/25.
 */
public class ShowIpControlDialog extends Dialog {
    private EditText mIpEditText;
    private EditText mPortEditText;
    private Button mOnOkClick;
    private onDialogListenerCallBack mListener;
    private Context mContext;


    private String mRequestIp;
    private String mRequestPort;

    public ShowIpControlDialog(Context context) {
        super(context, R.style.MyDialog);
        mContext = context;
        mRequestIp = (String) SPUtils.get(context, SPConstant.IP, "");
        mRequestPort = (String) SPUtils.get(context, SPConstant.PORT, "");

    }

    public void setOnDialohClickListener(onDialogListenerCallBack listener) {
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_control);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mIpEditText = (EditText) findViewById(R.id.ip);
        mIpEditText.setText(mRequestIp);
        mPortEditText = (EditText) findViewById(R.id.port);
        mPortEditText.setText(mRequestPort);
        mOnOkClick = (Button) findViewById(R.id.leftContent);
        mOnOkClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onOkCliclck(mIpEditText.getText().toString(), mPortEditText.getText().toString());
                    SPUtils.put(mContext, SPConstant.IP, mIpEditText.getText().toString());
                    SPUtils.put(mContext, SPConstant.PORT, mPortEditText.getText().toString());
                }
                ShowIpControlDialog.this.dismiss();
            }
        });
    }

    public interface onDialogListenerCallBack {
        public void onOkCliclck(String ip, String port);
    }
}
