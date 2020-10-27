package com.txt.library.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 封装adapter（注意：仅供参考，根据需要选择使用demo中提供的封装adapter）
 *
 * @param
 */
public  class BaseRvAdapter extends RecyclerView.Adapter<SuperViewHolder> {
    protected Context mContext;
    private LayoutInflater mInflater;

    protected List<BaseRvBean> mDataList = new ArrayList<>();
    List<IDelegateAdapter> delegateAdapters = new ArrayList<>();

    public void addDelegate(IDelegateAdapter delegateAdapter) {
        delegateAdapters.add(delegateAdapter);
    }

    public BaseRvAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public SuperViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        IDelegateAdapter iDelegateAdapter = delegateAdapters.get(viewType);
        return iDelegateAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(SuperViewHolder holder, int position) {
        int itemViewType = holder.getItemViewType();
        IDelegateAdapter iDelegateAdapter = delegateAdapters.get(itemViewType);
        iDelegateAdapter.onBindViewHolder(holder, position, mDataList.get(position));
    }


    @Override
    public int getItemViewType(int position) {
        BaseRvBean baseRvBean = mDataList.get(position);
        for (IDelegateAdapter delegateAdapter : delegateAdapters) {
            if (delegateAdapter.isForViewType(baseRvBean)) {
                return delegateAdapters.indexOf(delegateAdapter);
            }

        }

        throw new RuntimeException("没有可以处理的委托Adapter");
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public List<BaseRvBean> getDataList() {
        return mDataList;
    }

    public void addAll(Collection<BaseRvBean> list) {
        int lastIndex = this.mDataList.size();
        if (this.mDataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void setDataList(Collection<BaseRvBean> list) {
        mDataList.clear();
        mDataList.addAll(list);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        this.mDataList.remove(position);
        notifyItemRemoved(position);

        if (position != (getDataList().size())) { // 如果移除的是最后一个，忽略
            notifyItemRangeChanged(position, this.mDataList.size() - position);
        }
    }

    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }
}
