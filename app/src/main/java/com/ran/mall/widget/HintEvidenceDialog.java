package com.ran.mall.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ran.mall.R;

/**
 * Created by DELL on 2017/10/19.
 */

public class HintEvidenceDialog extends Dialog {
    private LinearLayout mSubmitButton;
    private TextView mCancleButton;
    private Context mContext;
    private onCommDilogCallBack mCallback;

    public HintEvidenceDialog(@NonNull Context context, onCommDilogCallBack callback) {
        super(context, R.style.DialogStyle_other);
        mContext=context;
        mCallback=callback;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_hint);
        initView();
        Window dialogWindow=getWindow();
        dialogWindow.setType(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        WindowManager.LayoutParams lp=dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.BOTTOM);
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height= WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
    }

    private void initView() {
        mSubmitButton= (LinearLayout) findViewById(R.id.dialog_submit);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback!=null){
                    mCallback.onOkDialog();
                }
                HintEvidenceDialog.this.dismiss();
            }
        });
        mCancleButton= (TextView) findViewById(R.id.rightContent);
        mCancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback!=null){
                    mCallback.onCancleDialog();
                }

                HintEvidenceDialog.this.dismiss();
            }
        });
    }

    public interface onCommDilogCallBack{
        public void onOkDialog();
        public void onCancleDialog();
    }
}
