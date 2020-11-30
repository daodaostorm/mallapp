package  com.ran.mall.ui.adapter

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.ran.library.base.SuperViewHolder
import com.ran.mall.R
import com.ran.mall.entity.bean.GoodInfo
import com.ran.mall.utils.CheckDoubleClickListener

/**
 */
private val TAG = GoodInfoAdapter::class.java.simpleName

class GoodInfoAdapter(mContext: Context) : ListBaseAdapter<GoodInfo>(mContext) {
    private var mListener: GoodClickListener? = null

    override fun getLayoutId(): Int {
        return R.layout.item_view_goodinfo
    }


    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {
        val bean = mDataList[position]

        val image_back = holder.getView<ImageView>(R.id.main_pic_id)

        Glide.with(mContext.getApplicationContext()).load(bean.detailpic1).into(image_back)

        holder.itemView.setOnClickListener(

                CheckDoubleClickListener {

                    mListener?.onItemClick(Gson().toJson(bean))
                }
        )

    }

    fun setOnItemClickListener(listener: GoodClickListener?) {
        Log.d(TAG, "setOnItemClickListener: ")
        this.mListener = listener
    }

    interface GoodClickListener {
        fun onItemClick(strJson: String)
    }

}
