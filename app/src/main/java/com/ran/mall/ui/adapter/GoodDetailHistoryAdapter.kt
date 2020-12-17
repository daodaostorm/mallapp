package  com.ran.mall.ui.adapter

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.ran.library.base.SuperViewHolder
import com.ran.mall.R
import com.ran.mall.entity.bean.GoodHistoryBean
import com.ran.mall.utils.CheckDoubleClickListener
import com.ran.mall.utils.LogUtils

/**
 */
private val TAG = "GoodDetailHistoryAdapter"

class GoodDetailHistoryAdapter(mContext: Context) : ListBaseAdapter<GoodHistoryBean>(mContext) {
    private var mListener: EssayClickListener? = null

    override fun getLayoutId(): Int {
        return R.layout.item_view_goodhistory
    }


    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {
        val bean = mDataList[position]

        val userName = holder.getView<TextView>(R.id.user_name)
        val goodComment = holder.getView<TextView>(R.id.good_comment)

        userName.text = bean.username
        if (bean.usercomment != null && ! bean.usercomment.equals("")) {
            goodComment.text = bean.usercomment
        }

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
