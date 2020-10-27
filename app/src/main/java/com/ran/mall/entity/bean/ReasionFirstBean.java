package com.ran.mall.entity.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pc on 2017/10/20.
 */

public class ReasionFirstBean implements Serializable {

    public String name;
    public String key;
    public Boolean disable;
    public ArrayList<ReasionSecondBean> childs = null;

}
