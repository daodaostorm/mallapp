package com.ran.mall.utils

import android.content.Context
import com.tencent.stat.StatService
import org.json.JSONObject
import java.util.*

/**
 * Created by Jutsin on 2018/5/17/017.
 * email：WjqJutin@163.com
 * effect：数据统计（腾讯移动分析）
 */
class DataStatisticsUtils {


    companion object {

        @JvmField
        val login = "登录"

        @JvmField
        val login_state = "登录状态"

        @JvmField
        val use_name = "名称"

        @JvmField
        val login_name = "登录账号"

        @JvmField
        val main_pic = "首页-照片上传"

        @JvmField
        val main_pic_ocr = "首页-照片上传-车牌识别"

        @JvmField
        val main_pic_importocrnum = "首页-照片上传-输入车牌"

        @JvmField
        val main_pic_call = "首页-照片上传-发起定损"

        @JvmField
        val main_pic_photograph = "首页-照片上传-拍照"

        @JvmField
        val main_pic_flashlight = "首页-照片上传-手电"

        @JvmField
        val main_pic_changesinglecard = "首页-照片上传-选择单证"

        @JvmField
        val main_pic_finishphotograph = "首页-照片上传-完成拍摄"


        @JvmField
        val main_video = "首页-视频定损"

        @JvmField
        val main_video_ocr = "首页-视频定损-车牌识别"

        @JvmField
        val main_video_importocrnum = "首页-视频定损-输入车牌"

        @JvmField
        val main_video_call = "首页-视频定损-发起定损"

        @JvmField
        val main_video_photograph = "首页-视频定损-拍照"

        @JvmField
        val main_video_flashlight = "首页-视频定损-手电"

        @JvmField
        val main_video_changesinglecard = "首页-视频定损-选择单证"

        @JvmField
        val main_video_addcar = "首页-视频定损-添加车辆"

        @JvmField
        val main_video_finishphotograph = "首页-视频定损-完成拍摄"

        @JvmField
        val main_list_pic = "任务列表-照片上传"

        @JvmField
        val main_list_video = "任务列表-视频定损"

        @JvmField
        val main_phonecall = "任务列表-呼叫"


        @JvmStatic
        fun setDataEvent(context: Context, eventname: String, jsonObject: Properties) {
                StatService.trackCustomKVEvent(context,eventname,jsonObject)

        }
        @JvmStatic
        fun setGeidentify(context: Context, eventname: String, jsonObject: JSONObject) {

        }

    }


}