package com.ran.mall.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ran.mall.R;


/**
 * Created by DELL on 2017/8/25.
 */
public class ToastDialog extends Dialog {
    private String mMsgContent;
    private TextView mContenMsg;
    private Button mOkaButton;
    private Button mCancleButton;
    private onDialogListenerCallBack mListener;
    public ToastDialog(@NonNull Context context, String msg) {
        super(context, R.style.DialogStyle);
        mMsgContent=msg;
    }

    public void setOnDialohClickListener(onDialogListenerCallBack listener){
        mListener=listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_toast);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        mContenMsg= (TextView) findViewById(R.id.msg);
        mContenMsg.setText(mMsgContent);
        mOkaButton= (Button) findViewById(R.id.leftContent);
        mOkaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null)
                    mListener.onOkCliclck();
                ToastDialog.this.dismiss();

            }
        });
        mCancleButton= (Button) findViewById(R.id.cancle);
        mCancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null)
                    mListener.onCancleClick();
                ToastDialog.this.dismiss();
            }
        });
        this.setOnDismissListener(new OnDismissListener(){
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mListener!=null)
                    mListener.onCancleClick();
            }
        });
    }



    public interface onDialogListenerCallBack{
        public void onOkCliclck();
        public void onCancleClick();
    }
}
