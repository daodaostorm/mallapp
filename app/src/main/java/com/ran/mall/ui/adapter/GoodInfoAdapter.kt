package  com.ran.mall.ui.adapter

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.ran.library.base.SuperViewHolder
import com.ran.mall.R
import com.ran.mall.entity.bean.GoonInfo
import com.ran.mall.ui.adapter.ListBaseAdapter
import com.ran.mall.utils.CheckDoubleClickListener

/**
 */
private val TAG = GoodInfoAdapter::class.java.simpleName

class GoodInfoAdapter(mContext: Context) : ListBaseAdapter<GoonInfo>(mContext) {
    private var mListener: ReportClickListener? = null

    override fun getLayoutId(): Int {
        return R.layout.item_view_goodinfo
    }


    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {
        val bean = mDataList[position]

        val image_back = holder.getView<ImageView>(R.id.main_pic_id)
        val title_text = holder.getView<TextView>(R.id.main_title_id)
        val detail_text = holder.getView<TextView>(R.id.main_detail_id)

        //image_back.drawable =
        title_text.text = bean.name
        detail_text.text = bean.detail

        holder.itemView.setOnClickListener(

                CheckDoubleClickListener {

                    skipVideoActivity(Gson().toJson(bean).toString())
                }
        )

    }

    fun setOnReportClickListener(listener: ReportClickListener?) {
        Log.d(TAG, "setOnReportClickListener: ")
        this.mListener = listener
    }

    interface ReportClickListener {
        fun skipVideoActivity(strJson: String)
    }

    //页面跳转
    fun skipVideoActivity(strJson: String) {


    }
}
