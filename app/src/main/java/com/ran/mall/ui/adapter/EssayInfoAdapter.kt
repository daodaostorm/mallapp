package  com.ran.mall.ui.adapter

import android.content.Context
import android.util.Log
import android.widget.TextView
import com.google.gson.Gson
import com.ran.library.base.SuperViewHolder
import com.ran.mall.R
import com.ran.mall.entity.bean.EssayInfo
import com.ran.mall.utils.CheckDoubleClickListener

/**
 */
private val TAG = TaskInfoAdapter::class.java.simpleName

class TaskInfoAdapter(mContext: Context) : ListBaseAdapter<EssayInfo>(mContext) {
    private var mListener: ReportClickListener? = null

    override fun getLayoutId(): Int {
        return R.layout.item_view_taskinfo
    }


    override fun onBindItemHolder(holder: SuperViewHolder, position: Int) {
        val bean = mDataList[position]

        val reportID = holder.getView<TextView>(R.id.report_id)
        val carNumber = holder.getView<TextView>(R.id.car_number)
        val createTime = holder.getView<TextView>(R.id.create_time)
        val name = holder.getView<TextView>(R.id.insure_name)
        val address = holder.getView<TextView>(R.id.address_id)


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
