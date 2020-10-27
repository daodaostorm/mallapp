package com.ran.mall.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pc on 2017/10/20.
 */

public class ClasssiTypeBean implements Serializable {

    public ArrayList<ReasionThirdBean> reasonReturn;
    public ArrayList<ReasionThirdBean> carHurt;
    public ArrayList<ReasionThirdBean> carHurtType;
    public ArrayList<ReasionThirdBean> personHurt;
    public ArrayList<ReasionThirdBean> personHurtLevel;
    public ArrayList<ReasionFirstBean> reasonAccid = null;

}
