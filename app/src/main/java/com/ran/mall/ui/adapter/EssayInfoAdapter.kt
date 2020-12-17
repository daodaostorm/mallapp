package  com.ran.mall.ui.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.ran.library.base.SuperViewHolder
import com.ran.mall.R
import com.ran.mall.entity.bean.EssayInfo
import com.ran.mall.utils.CheckDoubleClickListener
import com.ran.mall.utils.LogUtils

/**
 */
private val TAG = "EssayInfoAdapter"

class EssayInfoAdapter(mContext: Context) : ListBaseAdapter<EssayInfo>(mContext) {
    private var mListener: EssayClickListener? = null

    override fun getLayoutId(): Int {
        return R.layout.item_view_essayinfo
    }


    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {
        val bean = mDataList[position]

        val mainPic = holder.getView<ImageView>(R.id.main_pic_id)
        val mainTitle = holder.getView<TextView>(R.id.main_title_id)
        val mainDetail = holder.getView<TextView>(R.id.main_detail_id)
        val readCount = holder.getView<TextView>(R.id.main_read_id)
        val dianzanCount = holder.getView<TextView>(R.id.main_dianzan_id)

        Glide.with(mContext.getApplicationContext()).load(bean.detailpic1).into(mainPic)
        mainTitle.text = bean.name
        mainDetail.text = bean.detailtext1

        readCount.text = "3万+"
        dianzanCount.text = "120"
        holder.itemView.setOnClickListener(

                CheckDoubleClickListener {

                    mListener?.onItemClick(Gson().toJson(bean))
                }
        )

    }

    fun setOnItemClickListener(listener: EssayClickListener?) {
        LogUtils.d(TAG, "setOnItemClickListener: ")
        this.mListener = listener
    }

    interface EssayClickListener {
        fun onItemClick(strJson: String)
    }

}
