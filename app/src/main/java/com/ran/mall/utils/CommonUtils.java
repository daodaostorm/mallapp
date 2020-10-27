package com.ran.mall.utils;

import com.ran.mall.entity.bean.VideoListInfo;

import java.util.ArrayList;

public class CommonUtils {

	public static ArrayList<VideoListInfo> arrayListAddTop(ArrayList<VideoListInfo> list, VideoListInfo addinfo) {
		ArrayList<VideoListInfo> resultlist = new ArrayList<VideoListInfo>();

		resultlist.add(addinfo);
		int listsize = list.size();
		for (int i = 0; i < listsize; i++){
			resultlist.add(list.get(i));
		}

		return resultlist;
	}


}
