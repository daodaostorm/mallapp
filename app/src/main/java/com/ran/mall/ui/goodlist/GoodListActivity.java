package com.ran.mall.ui.goodlist;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.google.gson.Gson;
import com.ran.mall.R;
import com.ran.mall.base.BaseActivity_2;
import com.ran.mall.entity.bean.GoodInfo;
import com.ran.mall.entity.constant.Constant;
import com.ran.mall.ui.adapter.GoodInfoAdapter;
import com.ran.mall.ui.adapter.TuijianGoodInfoAdapter;
import com.ran.mall.ui.essaydetail.EssayDetailActivity;
import com.ran.mall.ui.main.TestActivity;
import com.ran.mall.ui.mainscreen.MainScreenActivity;
import com.ran.mall.utils.LogUtils;
import com.ran.mall.utils.ToastUtils;
import com.ran.mall.widget.LoadingView;
import com.ran.mall.widget.SpaceItemDecoration;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by pc on 2017/10/18.
 */
public class GoodListActivity extends BaseActivity_2 implements GoodListContract.View {


    private GoodListPresenter mPresenter;
    private static final String TAG = GoodListActivity.class.getSimpleName();

    public ArrayList<GoodInfo> mListGoodDatas;
    public LRecyclerView mGoodlistView;
    public LuRecyclerView mTuijianlistView;

    public static final int REQUEST_COUNT = 20;

    public GoodInfoAdapter mGoodAdapter;
    public LRecyclerViewAdapter mLRecyclerGoodAdapter;

    public TuijianGoodInfoAdapter mTuijianAdapter;
    public LuRecyclerViewAdapter mLRecyclerTuijianAdapter;

    LoadingView mLoadingView = null;

    public Button mMallButton;


    public void refreshData() {

        mPresenter.getGoodListData(0);

    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_googlist;
    }

    @Override
    public void initView() {

        setLeftViewIcon(R.drawable.icon_black_left_back);
        mPresenter = new GoodListPresenter(this, this);
        mGoodlistView = (LRecyclerView)findViewById(R.id.recyclerView_Good);
        mTuijianlistView = (LuRecyclerView)findViewById(R.id.recyclerView_Tuijian);
        mMallButton = (Button)findViewById(R.id.main_type_mall);
        mMallButton.setBackground(this.getDrawable(R.drawable.main_tap_record_select));

        mListGoodDatas = new ArrayList<GoodInfo>();

        initGoodRecyclerView();
        initTuijianRecyclerView();
        refreshData();
    }

    @Override
    public void requestFail(int errCode, @NotNull String errMsg) {
        ToastUtils.longShow(errMsg);
    }

    @Override
    public void requestSuccess(@NotNull ArrayList<GoodInfo> listInfo) {
        hideLoading();

        runOnUiThread(new Runnable() {
            @Override public void run() {
                mListGoodDatas.clear();
                mListGoodDatas = listInfo;
                LogUtils.i("getGoodInfo " + new Gson().toJson(mListGoodDatas));
                mGoodAdapter.clear();
                mGoodAdapter.setDataList(mListGoodDatas);
                mGoodlistView.refreshComplete(REQUEST_COUNT);

                mTuijianAdapter.clear();
                mTuijianAdapter.setDataList(mListGoodDatas);
                //mTuijianlistView.refreshComplete(REQUEST_COUNT);
            }
        });

    }

    public void  initTuijianRecyclerView() {


        mTuijianAdapter = new TuijianGoodInfoAdapter(this);
        mLRecyclerTuijianAdapter = new LuRecyclerViewAdapter(mTuijianAdapter);
        mTuijianlistView.setLayoutManager( new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        mTuijianlistView.setAdapter(mLRecyclerTuijianAdapter);

        mTuijianAdapter.setOnItemClickListener(new TuijianGoodInfoAdapter.GoodClickListener() {
            @Override
            public void onItemClick(@NotNull String strJson) {
                startEssayDetail(strJson);
            }
        });

    }

    public void  initGoodRecyclerView() {


        mGoodAdapter = new GoodInfoAdapter(this);
        mLRecyclerGoodAdapter = new LRecyclerViewAdapter(mGoodAdapter);
        mGoodlistView.setLayoutManager( new GridLayoutManager(this, 2));

        mGoodlistView.setAdapter(mLRecyclerGoodAdapter);
        mGoodlistView.setLoadMoreEnabled(true);
        mGoodlistView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mGoodlistView.setArrowImageView(R.drawable.progressbar);
        mGoodlistView.setHeaderViewColor(R.color.gray_text, R.color.gray_text, R.color.app_bg);
        mGoodlistView.setFooterViewColor(R.color.gray_text, R.color.gray_text, R.color.app_bg);

        int space = getResources().getDimensionPixelSize(R.dimen.dimen_10dp);
        mGoodlistView.addItemDecoration(new SpaceItemDecoration(space));
        mGoodlistView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mGoodlistView.refreshComplete(REQUEST_COUNT);
            }
        });

        mGoodlistView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mGoodlistView.refreshComplete(REQUEST_COUNT);
            }
        });

        mGoodAdapter.setOnItemClickListener(new GoodInfoAdapter.GoodClickListener() {
            @Override
            public void onItemClick(@NotNull String strJson) {
                startEssayDetail(strJson);
            }
        });

    }

    public void startEssayDetail(String strJson){
        Intent intent = new Intent(this, EssayDetailActivity.class);
        intent.putExtra(Constant.ESSAY_DETAIL_TYPE, strJson);
        startActivity(intent);
    }

    @Override
    public void setPresenter(GoodListContract.Presenter presenter) {
        mPresenter = (GoodListPresenter)presenter;
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
                    mLoadingView = new LoadingView(GoodListActivity.this, "Loading...", LoadingView.SHOWLOADING);
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

    public void startEssay(){
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
    }

    public void bottomClick(View view){
        switch (view.getId()){
            case R.id.main_type_first:
                startEssay();
                break;
            case R.id.main_type_my:
                startSettings();
                break;
        }
    }

}
