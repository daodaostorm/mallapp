package com.ran.mall.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ran.mall.R;


/**
 * Created by owner on 2016/8/30.
 */
public class UpdateDialog extends Dialog {

    private Activity mContext;

    private Button mBtnCancel;
    private Button mBtnOk;
    private View mDialogView;
    private TextView mTextTitle;
    private TextView mTextContent;
    private View.OnClickListener listener;


    public UpdateDialog(Activity context, View.OnClickListener listener) {
        super(context, R.style.DialogStyle);
        mContext=context;
        this.listener=listener;
        mDialogView= LayoutInflater.from(context).inflate(R.layout.dialog_update,null);
        mBtnCancel= (Button) mDialogView.findViewById(R.id.btn_cancel);
        mBtnOk= (Button) mDialogView.findViewById(R.id.btn_ok);
        mTextTitle= (TextView) mDialogView.findViewById(R.id.title);
        mTextContent= (TextView) mDialogView.findViewById(R.id.content);

        mBtnOk.setOnClickListener(listener);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mDialogView);
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             dismiss();
            }
        });

        setCanceledOnTouchOutside(false);
        setCancelable(false);
        WindowManager windowManager = mContext.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int)((display.getWidth())*0.8); //设置宽度
        getWindow().setAttributes(lp);
    }

    public void setTitle(String title){
        mTextTitle.setText(title);
    }

    public void setContent(String content){
        mTextContent.setText(content);
    }


    public void show(boolean canCancel){
        show();
        mBtnCancel.setVisibility(canCancel?View.INVISIBLE:View.VISIBLE);
    }

}
