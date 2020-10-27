package com.ran.mall.entity.bean;

/**
 * Created by DELL on 2017/12/29.
 */

public class LocationInfo {
    //纬度
    private String latitude = "0";
    //经度
    private String longitude = "0";

    //位置信息
    private String address;
    //报案所在省
    private String province;
    //报案所在市
    private String city;

    public Double getdLatitude() {
        return Double.parseDouble(latitude);
    }


    public Double getdLongitude() {
        return Double.parseDouble(longitude);
    }


    public String getLatitude() {
        return latitude == null ? "" : latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude == null ? "" : longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
