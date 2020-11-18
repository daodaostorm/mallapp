package  com.ran.mall.ui.adapter

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.ran.library.base.SuperViewHolder
import com.ran.mall.R
import com.ran.mall.entity.bean.EssayAdapterBean
import com.ran.mall.entity.bean.EssayInfo
import com.ran.mall.utils.CheckDoubleClickListener

/**
 */
private val TAG = EssayDetailInfoAdapter::class.java.simpleName

class EssayDetailInfoAdapter(mContext: Context) : ListBaseAdapter<EssayAdapterBean>(mContext) {
    private var mListener: EssayClickListener? = null

    override fun getLayoutId(): Int {
        return R.layout.item_view_essaydetailinfo
    }


    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {
        val bean = mDataList[position]

        val mainPic = holder.getView<ImageView>(R.id.main_pic_id)
        val mainDetail = holder.getView<TextView>(R.id.main_detail_id)

        Glide.with(mContext.getApplicationContext()).load(bean.detailpic).into(mainPic)
        mainDetail.text = bean.detailtext

        holder.itemView.setOnClickListener(

                CheckDoubleClickListener {

                    mListener?.onItemClick(Gson().toJson(bean))
                }
        )

    }

    fun setOnItemClickListener(listener: EssayClickListener?) {
        Log.d(TAG, "setOnItemClickListener: ")
        this.mListener = listener
    }

    interface EssayClickListener {
        fun onItemClick(strJson: String)
    }

}
