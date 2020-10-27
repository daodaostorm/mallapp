package com.ran.mall.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ran.mall.R;


public class LoadingView extends Dialog {

    private String mMessage;
    private BallFadeLoadingView mLoadView;
    private Context mContext;

    private LinearLayout mShowUploadView;
    private TextView mUploadNumber;
    private TextView mLoadTextView;

    public final static int SHOWLOADING = 0;
    public final static int SHOWUPLOAD = 1;

    public int mType = SHOWLOADING;

    public LoadingView(Context context, int theme) {
        super(context, theme);
    }

    public LoadingView(Context context, String message, int type) {
        this(context, R.style.DialogStyle);
        this.mMessage = message;
        this.mContext = context;
        this.mType = type;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_loading, null);
        setContentView(view);
        mLoadTextView = (TextView) view.findViewById(R.id.tvLoadingText);
        if (!TextUtils.isEmpty(mMessage)) {
            mLoadTextView.setText(mMessage);
        }
        mUploadNumber = (TextView) findViewById(R.id.uploadnumber);
        mShowUploadView = (LinearLayout) findViewById(R.id.showuploadnumber);
        mLoadView = (BallFadeLoadingView) view.findViewById(R.id.ballView);
        initShowType();
        setCanceledOnTouchOutside(false);
    }

    public void setmUploadNumber(int uploadnumber, int total) {
        String number = uploadnumber + "/" + total;
        mUploadNumber.setText(number);
    }

    public void setmLoadTextView(String message){
        if (!TextUtils.isEmpty(message)) {
            if (mLoadTextView != null) {
                mLoadTextView.setText(message);
            }
        }
    }

    public void setEmptyText() {
        mUploadNumber.setText("");
    }


    //设置显示的type
    public void initShowType() {
        if (mType == SHOWUPLOAD) {
            mShowUploadView.setVisibility(View.VISIBLE);
            mLoadTextView.setVisibility(View.GONE);
        } else if (mType == SHOWLOADING) {
            mShowUploadView.setVisibility(View.GONE);
            mLoadTextView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void show() {
        super.show();


        if (mLoadView != null)
            mLoadView.startAnimators();


    }

    @Override
    public void dismiss() {

        if (mLoadView != null) {
            mLoadView.stopAnimators();
            setEmptyText();
        }
        super.dismiss();
    }
}
