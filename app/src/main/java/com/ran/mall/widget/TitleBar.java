package com.ran.mall.widget;


import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ran.mall.R;


/**
 * Created by huangds on 2016/5/6.
 */
public class TitleBar extends FrameLayout implements View.OnClickListener {

    private TextView mTvLeftView;

    private TextView mTvTitle;
    private ImageView mIvMdddleView;
    private ImageView mIvLeftView;
    private TextView mTvRightView;
    private ImageView mIvRightView;
    private TitleBarListener mListener;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflate(getContext(), R.layout.layout_base_header, this);
        mIvLeftView = (ImageView) findViewById(R.id.left_iv);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvRightView = (TextView) findViewById(R.id.right_tv);
        mIvRightView = (ImageView) findViewById(R.id.right_iv);
        mTvLeftView = (TextView) findViewById(R.id.left_txt);
        mIvMdddleView = (ImageView) findViewById(R.id.tv_title_middle);
        mTvLeftView.setVisibility(View.GONE);
        mTvRightView.setVisibility(View.GONE);
        mIvRightView.setVisibility(View.GONE);
        mIvLeftView.setVisibility(INVISIBLE);
        mTvTitle.setVisibility(View.GONE);
        mIvLeftView.setOnClickListener(this);
        mTvTitle.setOnClickListener(this);
        mTvRightView.setOnClickListener(this);
        mIvRightView.setOnClickListener(this);
        mTvLeftView.setOnClickListener(this);

    }

    public void setTitleBarListener(TitleBarListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_iv:
                mListener.responseToBackView();
                break;
            case R.id.tv_title:
                mListener.responseToTitle();
                break;
            case R.id.right_iv:
            case R.id.right_tv:
                mListener.responseToRightView();
                break;
            case R.id.left_txt:
                mListener.responseLeftTxt();
                break;

        }
    }

    public interface TitleBarListener {
        void responseToTitle();

        void responseToRightView();

        void responseToBackView();

        void setTitleText(String text);

        void responseLeftTxt();

        void setLeftViewText(String msg);

        void setLeftViewIcon(@DrawableRes int drawableId);

        void setRightViewText(String text);
        void setRightViewViabile(Boolean isViabile);

        void setRightViewIcon(@DrawableRes int drawableId);

    }

    public TextView getTitleView() {
        return mTvTitle;
    }

    public ImageView getLeftView() {
        return mIvLeftView;
    }

    public ImageView getRightImageView() {
        return mIvRightView;
    }

    public TextView getRightTextView() {
        return mTvRightView;
    }

    public TextView getLeftTextView() {
        return mTvLeftView;
    }


}
