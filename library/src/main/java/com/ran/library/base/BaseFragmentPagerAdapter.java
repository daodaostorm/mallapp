package com.ran.library.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * effect：FragmentPagerAdapter的基类
 */
public abstract class BaseFragmentPagerAdapter<T extends Fragment> extends FragmentPagerAdapter {

    public List<T> mFragments = new ArrayList<>(); // Fragment集合

    public T mCurrentFragment; // 当前显示的Fragment

    public List<String> mTitlePages = new ArrayList<>();

    /**
     * 在Activity中使用ViewPager适配器
     */
    public BaseFragmentPagerAdapter(FragmentActivity activity, int count) {
        this(activity.getSupportFragmentManager(), count);
    }

    public BaseFragmentPagerAdapter(FragmentActivity activity) {
        this(activity.getSupportFragmentManager(), 0);

    }
    /**
     * 在Fragment中使用ViewPager适配器
     */
    public BaseFragmentPagerAdapter(Fragment fragment, int count) {
        this(fragment.getChildFragmentManager(), count);
    }

    public BaseFragmentPagerAdapter(FragmentManager fm, int count) {
        super(fm);
        init(fm, mFragments,mTitlePages, count);

    }

    //初始化Fragment
    protected abstract void init(FragmentManager fm, List<T> list,List<String> mTitlePages, int count);

    @Override
    public T getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            // 记录当前的Fragment对象
            mCurrentFragment = (T) object;
        }
        super.setPrimaryItem(container, position, object);
    }

    /**
     * 获取Fragment集合
     */
    public List<T> getAllFragment() {
        return mFragments;
    }

    /**
     * 获取当前的Fragment
     */
    public T getCurrentFragment() {
        return mCurrentFragment;
    }

    public List<String> getTitlePages() {
        if (mTitlePages == null) {
            return new ArrayList<>();
        }
        return mTitlePages;
    }

    public void setFragmentPages(List<T> fragment) {
        mFragments.clear();
        mFragments = fragment;
    }
    public void setTitlePages(List<String> titlePages) {
        mTitlePages = titlePages;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitlePages.get(position);
    }

    public boolean addFragmentPages(String titleName, T fragment){
        if (mTitlePages.size() > 3) {
            return false;
        }
            mTitlePages.add(titleName + mTitlePages.size());
            mFragments.add(fragment);
            this.notifyDataSetChanged();
            return true;

    }
    public void deleteFragmentPages(int index){
        /*mTitlePages.add(titleName + mTitlePages.size());
        int index = -1;
        for (int i = 0; i < mTitlePages.size(); i++){
            if (titleName.equals(mTitlePages.get(i))){
                index = i;
                break;
            }
        }*/
        if (index != -1 && index < mTitlePages.size()){
            mTitlePages.remove(index);
            mFragments.remove(index);
        }
        this.notifyDataSetChanged();

    }
}