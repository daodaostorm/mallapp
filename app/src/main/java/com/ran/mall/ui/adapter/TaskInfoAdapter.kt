package  com.ran.mall.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.ran.library.base.SuperViewHolder
import com.ran.mall.R
import com.ran.mall.entity.bean.TaskDetailInfo
import com.ran.mall.entity.constant.Constant
import com.ran.mall.ui.adapter.ListBaseAdapter
import com.ran.mall.utils.CheckDoubleClickListener
import com.ran.mall.utils.DateUtils
import org.json.JSONException
import java.text.ParseException
import java.util.*

/**
 */
private val TAG = TaskInfoAdapter::class.java.simpleName

class TaskInfoAdapter(mContext: Context) : ListBaseAdapter<TaskDetailInfo>(mContext) {
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

        //将世界时间转化成国内时间
        try {
            if (!bean.ctime.isNullOrEmpty())
                createTime.text = "时间: " + DateUtils.UTCToCST(bean.ctime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        reportID.text = "报案号: " +bean.fields!!.reportId
        carNumber.text = "车牌号: " +bean.fields!!.carNumber
        name.text = "被保人: " +bean.fields!!.insurantName
        address.text = "地址: " + bean.fields!!.reportPlace

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
