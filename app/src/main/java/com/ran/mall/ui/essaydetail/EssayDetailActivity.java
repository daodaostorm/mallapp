package com.ran.mall.ui.essaydetail;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.ran.mall.R;
import com.ran.mall.base.BaseActivity_2;
import com.ran.mall.entity.bean.EssayAdapterBean;
import com.ran.mall.entity.bean.EssayInfo;
import com.ran.mall.entity.constant.Constant;
import com.ran.mall.ui.adapter.EssayDetailInfoAdapter;
import com.ran.mall.utils.LogUtils;
import com.ran.mall.utils.ToastUtils;
import com.ran.mall.widget.LoadingView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by pc on 2017/10/18.
 */
public class EssayDetailActivity extends BaseActivity_2 implements EssayDetailContract.View {


    private EssayDetailPresenter mPresenter;
    private static final String TAG = EssayDetailActivity.class.getSimpleName();

    public EssayInfo mEssayDatas;
    public LRecyclerView mEssayView;

    public String mStrJson;
    public static final int REQUEST_COUNT = 20;

    public TextView mTitle;
    public TextView mSubTitle;
    public TextView mAutor;

    public EssayDetailInfoAdapter mEssayAdapter;
    public ArrayList<EssayAdapterBean> mDataList;
    public LRecyclerViewAdapter mLRecyclerEssayAdapter;

    LoadingView mLoadingView = null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_essaydetail;
    }

    @Override
    public void initView() {

        setLeftViewIcon(R.drawable.icon_black_left_back);
        mPresenter = new EssayDetailPresenter(this, this);
        mEssayView = (LRecyclerView)findViewById(R.id.recyclerView_Essay);
        mTitle = (TextView) findViewById(R.id.main_title_id);
        mSubTitle = (TextView) findViewById(R.id.main_subtitle_id);

        mStrJson = getIntent().getStringExtra(Constant.ESSAY_DETAIL_TYPE);
        mDataList = new ArrayList<EssayAdapterBean>();
        initEssayRecyclerView();

        refreshData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void responseToBackView() {
        super.responseToBackView();
        this.finish();
    }

    public void refreshData() {

        mPresenter.getEssayDetail(mStrJson);

    }

    @Override
    public void requestFail(int errCode, @NotNull String errMsg) {
        ToastUtils.longShow(errMsg);
    }

    @Override
    public void requestSuccess(@NotNull EssayInfo essayinfo) {

        LogUtils.i("EssayInfo " + new Gson().toJson(essayinfo));

        mTitle.setText(essayinfo.getName());
        mSubTitle.setText(essayinfo.getSubtitle());
        mDataList.clear();
        EssayAdapterBean data1 = new EssayAdapterBean();
        data1.setDetailpic(essayinfo.getDetailpic1());
        data1.setDetailtext(essayinfo.getDetailtext1());
        EssayAdapterBean data2 = new EssayAdapterBean();
        data2.setDetailpic(essayinfo.getDetailpic2());
        data2.setDetailtext(essayinfo.getDetailtext2());
        EssayAdapterBean data3 = new EssayAdapterBean();
        data3.setDetailpic(essayinfo.getDetailpic3());
        data3.setDetailtext(essayinfo.getDetailtext3());
        mDataList.add(data1);
        mDataList.add(data2);
        mDataList.add(data3);

        mEssayAdapter.setDataList(mDataList);

    }

    public void  initEssayRecyclerView() {


        mEssayAdapter = new EssayDetailInfoAdapter(this);
        mLRecyclerEssayAdapter = new LRecyclerViewAdapter(mEssayAdapter);
        mEssayView.setLayoutManager( new LinearLayoutManager(this));
        mEssayView.setAdapter(mLRecyclerEssayAdapter);
        mEssayView.setLoadMoreEnabled(true);
        mEssayView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mEssayView.setArrowImageView(R.drawable.progressbar);
        mEssayView.setHeaderViewColor(R.color.gray_text, R.color.gray_text, R.color.app_bg);
        mEssayView.setFooterViewColor(R.color.gray_text, R.color.gray_text, R.color.app_bg);

        mEssayView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mEssayView.refreshComplete(REQUEST_COUNT);
            }
        });

        mEssayView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mEssayView.refreshComplete(REQUEST_COUNT);
            }
        });

        mEssayAdapter.setOnItemClickListener(new EssayDetailInfoAdapter.EssayClickListener() {
            @Override
            public void onItemClick(@NotNull String strJson) {

            }
        });

    }

    @Override
    public void setPresenter(EssayDetailContract.Presenter presenter) {
        mPresenter = (EssayDetailPresenter) presenter;
    }

    @Override
    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override public void run() {
                if (mLoadingView != null ) {
                    if (!(mLoadingView.isShowing())){
                        mLoadingView.show();
                    }
                }else {
                    mLoadingView = new LoadingView(EssayDetailActivity.this, "Loading...", LoadingView.SHOWLOADING);
                    mLoadingView.show();
                }
            }
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(new Runnable() {
            @Override public void run() {
                if (mLoadingView != null ) {
                    try {
                        if (mLoadingView.isShowing()) {
                            mLoadingView.dismiss();
                        }
                    } catch (Exception e){

                    } finally {
                        mLoadingView = null;
                    }
                }
            }
        });
    }

    public void bottomClick(View view){
        switch (view.getId()){
            case R.id.main_type_first:
                break;
        }
    }

}
