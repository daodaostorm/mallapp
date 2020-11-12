package com.ran.mall.ui.mainscreen;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ran.mall.R;
import com.ran.mall.base.BaseActivity_2;
import com.ran.mall.entity.bean.BannerInfo;
import com.ran.mall.entity.bean.EssayInfo;
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
    public BannerView mBannerView;

    LoadingView mLoadingView = null;


    public void refreshData() {

        mPresenter.getBannerListData();


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
        mListBannerDatas = new ArrayList<BannerInfo>();

        mBannerView.setViewFactory(new BannerViewFactory());
        refreshData();
    }

    @Override
    public void requestFail(int errCode, @NotNull String errMsg) {

    }

    @Override
    public void requestSuccess(@NotNull ArrayList<EssayInfo> listInfo) {


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
