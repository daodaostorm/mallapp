package com.ran.mall.entity.bean;

/**
 * Created by Jutsin on 2018/3/29/029.
 * email：WjqJutin@163.com
 * effect：
 */

public enum PicType {

    CARPICS("车损照", "carPics"), IDFRONT("身份证(正面)", "IdFront"), IDREVERT("身份证(背面)", "IdRevert"),
    DRIVERMAIN("驾驶证(主页)", "driverMain"), DRIVERSIDE("驾驶证(副页)", "driverSide"), DRIVINGMAIN("行驶证(主页)", "drivingMain"), DRIVINGSIDE("行驶证(副页)", "drivingSide"), BANKCARDPICS("银行卡", "bankCardPics"), OTHERCARDPICS("其他", "otherCardPics");

    // 成员变量
    private String name;
    private String type;

    // 构造方法
    private PicType(String name, String type) {
        this.name = name;
        this.type = type;
    }

    // 普通方法
    public static String getPicType(String name) {
        for (PicType c : PicType.values()) {
            if (c.getName() == name) {
                return c.type;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
