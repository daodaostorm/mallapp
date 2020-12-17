package com.ran.mall.ui.gooddetail;

import android.graphics.Paint;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ran.library.widget.NoScrollViewPager;
import com.ran.mall.R;
import com.ran.mall.base.BaseActivity_2;
import com.ran.mall.entity.bean.GoodInfo;
import com.ran.mall.entity.constant.Constant;
import com.ran.mall.ui.gooddetail.adapter.GoodDetailViewPagerAdapter;
import com.ran.mall.utils.LogUtils;
import com.ran.mall.widget.LoadingView;

import java.util.ArrayList;

/**
 *
 */
public class GoodDetailActivity extends BaseActivity_2 implements TabLayout.OnTabSelectedListener {

    private static final String TAG = GoodDetailActivity.class.getSimpleName();

    public static final int REQUEST_COUNT = 20;

    public ArrayList<String> mTabTitle = new ArrayList<String>();
    public TabLayout mTabView;
    LoadingView mLoadingView = null;

    public String mStrJson;
    public GoodInfo mGoodInfo;


    public NoScrollViewPager mViewPager;
    public ImageView mMainTopView;
    public TextView mMainTile;
    public TextView mPriceValue;
    public TextView mOriginPriceValue;

    public GoodDetailViewPagerAdapter mGoodDetailViewPagerAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_googdetail;
    }

    @Override
    public void initView() {

        setLeftViewIcon(R.drawable.icon_black_left_back);
        mStrJson = getIntent().getStringExtra(Constant.GOOD_DETAIL_TYPE);
        mGoodInfo = new Gson().fromJson(mStrJson, GoodInfo.class);

        mMainTile = (TextView)findViewByIds(R.id.main_title_id);
        mPriceValue = (TextView)findViewByIds(R.id.main_price_id);
        mOriginPriceValue = (TextView)findViewByIds(R.id.main_origin_price_id);
        mMainTopView = (ImageView)findViewByIds(R.id.main_pic_id);
        mOriginPriceValue.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        if (mGoodInfo != null){
            Glide.with(this.getApplicationContext()).load(mGoodInfo.getDetailpic1()).into(mMainTopView);
            mMainTile.setText(mGoodInfo.getName());
            mPriceValue.setText(String.valueOf(mGoodInfo.getPrice()) + "元");
            mOriginPriceValue.setText(String.valueOf(mGoodInfo.getOrginprice()) + "元");
        }
        initViewPager();
    }

    public void initViewPager(){
        mViewPager = (NoScrollViewPager)findViewByIds(R.id.viewpager);
        mTabView = (TabLayout) findViewById(R.id.tablayout);

        mGoodDetailViewPagerAdapter = new GoodDetailViewPagerAdapter(this);
        mViewPager.setAdapter(mGoodDetailViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(mGoodDetailViewPagerAdapter.getCount());
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabView.addOnTabSelectedListener(this);
        mTabView.setupWithViewPager(mViewPager);
    }

    public void showLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingView != null) {
                    if (!(mLoadingView.isShowing())) {
                        mLoadingView.show();
                    }
                } else {
                    mLoadingView = new LoadingView(GoodDetailActivity.this, "Loading...", LoadingView.SHOWLOADING);
                    mLoadingView.show();
                }
            }
        });
    }

    public void hideLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLoadingView != null) {
                    try {
                        if (mLoadingView.isShowing()) {
                            mLoadingView.dismiss();
                        }
                    } catch (Exception e) {

                    } finally {
                        mLoadingView = null;
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void responseToBackView() {
        super.responseToBackView();
        this.finish();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        LogUtils.i("onTabSelected " + tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
