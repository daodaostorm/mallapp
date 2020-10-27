package com.ran.mall.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.ran.library.base.SystemManager;
import com.ran.mall.R;
import com.ran.mall.system.SystemCommon;

/**
 * Created by DELL on 2017/10/19.
 */

public class EndEvidenceDialog extends Dialog {
    private LinearLayout mSubmitButton;
    private LinearLayout mCancleButton;
    private Context mContext;
    private onCommDilogCallBack mCallback;

    public EndEvidenceDialog(@NonNull Context context,onCommDilogCallBack callback) {
        super(context, R.style.DialogStyle_other);
        mContext=context;
        mCallback=callback;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_end);
        initView();
        Window dialogWindow=getWindow();
        dialogWindow.setType(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        WindowManager.LayoutParams lp=dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.BOTTOM);
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height= SystemManager.getInstance().getSystem(SystemCommon.class).getScreenHigh((Activity) mContext)/2;
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
                EndEvidenceDialog.this.dismiss();
            }
        });
        mCancleButton= (LinearLayout) findViewById(R.id.dialog_calcle);
        mCancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback!=null){
                    mCallback.onCancleDialog();
                }

                EndEvidenceDialog.this.dismiss();
            }
        });
    }

    public interface onCommDilogCallBack{
        public void onOkDialog();
        public void onCancleDialog();
    }
}
