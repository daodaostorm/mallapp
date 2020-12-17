package  com.ran.mall.ui.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.ran.library.base.SuperViewHolder
import com.ran.mall.R
import com.ran.mall.entity.bean.GoodInfo
import com.ran.mall.utils.CheckDoubleClickListener
import com.ran.mall.utils.LogUtils

/**
 */
private val TAG = "GoodInfoAdapter"

class GoodInfoAdapter(mContext: Context) : ListBaseAdapter<GoodInfo>(mContext) {
    private var mListener: GoodClickListener? = null

    override fun getLayoutId(): Int {
        return R.layout.item_view_goodinfo
    }


    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {
        val bean = mDataList[position]

        val main_image = holder.getView<ImageView>(R.id.gooditem_pic_id)
        val main_title = holder.getView<TextView>(R.id.gooditem_title_id)
        val price_value = holder.getView<TextView>(R.id.gooditem_price_id)
        main_title.text = bean.name
        price_value.text = bean.price.toString() + "元"
        Glide.with(mContext.getApplicationContext()).load(bean.detailpic1).into(main_image)

        holder.itemView.setOnClickListener(

                CheckDoubleClickListener {

                    mListener?.onItemClick(Gson().toJson(bean))
                }
        )

    }

    fun setOnItemClickListener(listener: GoodClickListener?) {
        LogUtils.d(TAG, "setOnItemClickListener: ")
        this.mListener = listener
    }

    interface GoodClickListener {
        fun onItemClick(strJson: String)
    }

}
