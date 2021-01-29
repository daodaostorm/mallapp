package com.ran.mall.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.ran.library.base.SuperViewHolder
import com.ran.mall.R
import com.ran.mall.entity.bean.AddressInfoBean
import com.ran.mall.utils.LogUtils

/**
 */
private val TAG = "AddressInfoAdapter"

class AddressInfoAdapter(mContext: Context) : ListBaseAdapter<AddressInfoBean>(mContext) {

    override fun getLayoutId(): Int {
        return R.layout.item_addressinfo
    }


    private var mListener: DeviceClickListener? = null

    interface DeviceClickListener {
        fun onItemListener(it: View, result: AddressInfoBean, position: Int)
    }

    fun setDeviceClickListener(listener: DeviceClickListener) {
        mListener = listener
    }

    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {
        val bean = mDataList[position]
        val lableNameTv = holder.getView<TextView>(R.id.address_label)
        val receiveNameTv = holder.getView<TextView>(R.id.receive_name)
        val receivePhoneTv = holder.getView<TextView>(R.id.receive_phone)
        val receiveAddressTv = holder.getView<TextView>(R.id.receive_address)
        val editAddress = holder.getView<TextView>(R.id.edit_address)
        lableNameTv.text =  bean.label
        receiveNameTv.text = bean.receivername

        editAddress.setOnClickListener {
            LogUtils.i("editAddress:" + position)
            mListener?.onItemListener(it, bean, position)
        }

        holder.itemView.setOnClickListener {
            LogUtils.i("item:" + position)
            mListener?.onItemListener(it, bean, position)
        }
    }

}
