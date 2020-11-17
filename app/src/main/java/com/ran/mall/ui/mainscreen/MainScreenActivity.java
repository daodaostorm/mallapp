package com.ran.mall.ui.mainscreen;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.ran.mall.R;
import com.ran.mall.base.BaseActivity_2;
import com.ran.mall.entity.bean.BannerInfo;
import com.ran.mall.entity.bean.EssayInfo;
import com.ran.mall.ui.adapter.EssayInfoAdapter;
import com.ran.mall.ui.main.MainScreenPresenter;
import com.ran.mall.ui.main.TestActivity;
import com.ran.mall.utils.LogUtils;
import com.ran.mall.utils.ToastUtils;
import com.ran.mall.widget.BannerView;
import com.ran.mall.widget.LoadingView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by pc on 2017/10/18.
 */
public class MainScreenActivity extends BaseActivity_2 implements MainScreenContract.View {


    private MainScreenPresenter mPresenter;
    private static final String TAG = MainScreenActivity.class.getSimpleName();

    public ArrayList<BannerInfo> mListBannerDatas;
    public ArrayList<EssayInfo> mListEssayDatas;
    public BannerView mBannerView;
    public LRecyclerView mEssayView;

    public static final int REQUEST_COUNT = 20;

    public EssayInfoAdapter mEssayAdapter;
    public LRecyclerViewAdapter mLRecyclerEssayAdapter;

    LoadingView mLoadingView = null;


    public void refreshData() {

        mPresenter.getBannerListData();
        mPresenter.getListEssayData();

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        setLeftViewIcon(R.drawable.icon_black_left_back);
        setTitleText("中间");
        mPresenter = new MainScreenPresenter(this, this);
        mBannerView = (BannerView)findViewById(R.id.banner_top);
        mEssayView = (LRecyclerView)findViewById(R.id.recyclerView_Essay);

        mListBannerDatas = new ArrayList<BannerInfo>();
        mListEssayDatas = new ArrayList<EssayInfo>();

        mBannerView.setViewFactory(new BannerViewFactory());

        initEssayRecyclerView();

        refreshData();
    }

    @Override
    public void requestFail(int errCode, @NotNull String errMsg) {
        ToastUtils.longShow(errMsg);
    }

    @Override
    public void requestSuccess(@NotNull ArrayList<EssayInfo> listInfo) {
        hideLoading();

        runOnUiThread(new Runnable() {
            @Override public void run() {
                mListEssayDatas.clear();
                mListEssayDatas = listInfo;
                LogUtils.i("getEssayInfo " + new Gson().toJson(mListEssayDatas));
                mEssayAdapter.clear();
                mEssayAdapter.setDataList(mListEssayDatas);
                mEssayView.refreshComplete(REQUEST_COUNT);
            }
        });

    }

    @Override
    public void requestBannerFail(int errCode, @NotNull String errMsg) {
        ToastUtils.longShow(errMsg);
    }

    @Override
    public void requestBannerSuccess(@NotNull ArrayList<BannerInfo> listInfo) {

        hideLoading();

        runOnUiThread(new Runnable() {
            @Override public void run() {
                mListBannerDatas.clear();
                mListBannerDatas = listInfo;
                mBannerView.setDataList(mListBannerDatas);
                mBannerView.start();
            }
        });




    }

    public void  initEssayRecyclerView() {


        mEssayAdapter = new EssayInfoAdapter(this);
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

        mEssayAdapter.setOnItemClickListener(new EssayInfoAdapter.EssayClickListener() {
            @Override
            public void onItemClick(@NotNull String strJson) {

            }
        });

    }

    @Override
    public void setPresenter(MainScreenContract.Presenter presenter) {
        mPresenter = (MainScreenPresenter)presenter;
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
                    mLoadingView = new LoadingView(MainScreenActivity.this, "Loading...", LoadingView.SHOWLOADING);
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public void startSettings(){
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    public void bottomClick(View view){
        switch (view.getId()){
            case R.id.main_type_first:
                break;
            case R.id.main_type_my:
                startSettings();
                break;
        }
    }


    public static class BannerViewFactory implements BannerView.ViewFactory<BannerInfo> {
        @Override
        public View create(BannerInfo item, int position, ViewGroup container) {
            ImageView iv = new ImageView(container.getContext());
            //RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.DATA);
            Glide.with(container.getContext().getApplicationContext()).load(item.getPicUrl()).into(iv);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.i("onClick", String.valueOf(position));
                }
            });
            return iv;
        }
    }
}
