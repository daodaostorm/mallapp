package com.ran.mall.ui.gooddetail;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Button;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LuRecyclerView;
import com.ran.mall.R;
import com.ran.mall.base.BaseActivity_2;
import com.ran.mall.entity.bean.GoodInfo;
import com.ran.mall.widget.LoadingView;

import java.util.ArrayList;

/**
 *
 */
public class GoodDetailActivity extends BaseActivity_2 {

    private static final String TAG = GoodDetailActivity.class.getSimpleName();

    public static final int REQUEST_COUNT = 20;

    public ArrayList<String> mTabTitle = new ArrayList<String>();
    public TabLayout mTabView;
    LoadingView mLoadingView = null;

    public Button mMallButton;

    @Override
    public int getLayoutId() {
        return R.layout.activity_googdetail;
    }

    @Override
    public void initView() {

        setLeftViewIcon(R.drawable.icon_black_left_back);
        initTabView();
    }

    public void initTabView(){

        mTabView = (TabLayout) findViewById(R.id.tablayout);
        mTabTitle.clear();
        mTabTitle.add("商品详情");
        mTabTitle.add("购买记录");

        mTabView.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int i = 0; i < mTabTitle.size(); i++){
            if (i == 0) {
                mTabView.addTab(mTabView.newTab().setText(mTabTitle.get(i)), true);
            } else {
                mTabView.addTab(mTabView.newTab().setText(mTabTitle.get(i)), false);
            }
        }
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

    public void bottomClick(View view) {
        switch (view.getId()) {
            case R.id.main_type_first:
                break;
            case R.id.main_type_my:
                break;
        }
    }

}
