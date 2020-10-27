package com.ran.mall.entity.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pc on 2017/10/20.
 */

public class SurVeyBean implements Serializable {


    private String _id;
    private String ctime;
    private String utime;
    private String reportId;
    private String carNumber;
    //private String claimAdjuster;
    private String flowId;
    private String channel;
    private String surveyOperater;

    public Boolean getIsEarlyStage() {
        return isEarlyStage;
    }

    public void setIsEarlyStage(Boolean isEarlyStage) {
        this.isEarlyStage = isEarlyStage;
    }

    private Boolean isEarlyStage;
    //private String doneSum;
    //private String vinNo;
    //private String evalId;
    //private String estimationStatus;


    /*public String getVinNo() {
        return vinNo;
    }

    public void setVinNo(String vinNo) {
        this.vinNo = vinNo;
    }

    public String getEvalId() {
        return evalId;
    }

    public void setEvalId(String evalId) {
        this.evalId = evalId;
    }

    public String getEstimationStatus() {
        return estimationStatus;
    }

    public void setEstimationStatus(String estimationStatus) {
        this.estimationStatus = estimationStatus;
    }*/



    /*private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDoneSum(String doneSum) {
        this.doneSum = doneSum;
    }

    public String getDoneSum() {

        return doneSum;
    }*/

    public String getShowAgentName() {
        return showAgentName;
    }

    public void setShowAgentName(String showAgentName) {
        this.showAgentName = showAgentName;
    }

    public String showAgentName = "";

    public String getSurveyOperater() {
        return surveyOperater;
    }

    public void setSurveyOperater(String surveyOperater) {
        this.surveyOperater = surveyOperater;
    }

    public String getSurveyPhone() {
        return surveyPhone;
    }

    public void setSurveyPhone(String surveyPhone) {
        this.surveyPhone = surveyPhone;
    }

    private String surveyPhone;

    public String getAgentId() {
        return agentId._id;
    }

    public void setAgentId(String agentId) {
        this.agentId._id = agentId;
    }

    private AgentInfo agentId;
    private int __v;
    private String state;
    private List<?> shotPics;
    private List<?> otherCarLoss;
    private List<?> videoCaptures;

    public boolean isLossConfirm() {
        return lossConfirm;
    }

    public void setLossConfirm(boolean lossConfirm) {
        this.lossConfirm = lossConfirm;
    }

    private boolean lossConfirm;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

   /* public String getClaimAdjuster() {
        return claimAdjuster;
    }

    public void setClaimAdjuster(String claimAdjuster) {
        this.claimAdjuster = claimAdjuster;
    }*/

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<?> getShotPics() {
        return shotPics;
    }

    public void setShotPics(List<?> shotPics) {
        this.shotPics = shotPics;
    }

    public List<?> getOtherCarLoss() {
        return otherCarLoss;
    }

    public void setOtherCarLoss(List<?> otherCarLoss) {
        this.otherCarLoss = otherCarLoss;
    }

    public List<?> getVideoCaptures() {
        return videoCaptures;
    }

    public void setVideoCaptures(List<?> videoCaptures) {
        this.videoCaptures = videoCaptures;
    }
}
