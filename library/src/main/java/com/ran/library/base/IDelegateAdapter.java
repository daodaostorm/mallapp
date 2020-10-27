package com.ran.library.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Justin on 2018/11/14/014 13:48.
 * email：WjqJustin@163.com
 * effect：
 */

public interface IDelegateAdapter {


    // 查找委托时调用的方法，返回自己能处理的类型即可。
    boolean isForViewType(BaseRvBean baseRvBean);

    // 用于委托Adapter的onCreateViewHolder方法
    SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    // 用于委托Adapter的onBindViewHolder方法
    void onBindViewHolder(RecyclerView.ViewHolder holder, int position, BaseRvBean baseRvBean);


}
