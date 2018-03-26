package com.supets.pet.mock.wechaht;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class WeiXinComment implements Serializable {

    private String meWeiXinId;  // 当前手机登录的账号id
    private String meAccount;   // 当前手机登录的账号
    private String friWeiXinId; // 好友的微信id
    private String friNickName; // 好友的昵称
    private String custId;      // 客户id
    private String content;     // 评论的内容
    private Date weiXinCreateDate;//评论的时间
    private Date createDate;    //统计时间
    private String weiXinCircleId;// 微信朋友圈数据的id，snsid

    public String getMeWeiXinId() {
        return meWeiXinId;
    }

    public void setMeWeiXinId(String meWeiXinId) {
        this.meWeiXinId = meWeiXinId;
    }

    public String getMeAccount() {
        return meAccount;
    }

    public void setMeAccount(String meAccount) {
        this.meAccount = meAccount;
    }

    public String getFriWeiXinId() {
        return friWeiXinId;
    }

    public void setFriWeiXinId(String friWeiXinId) {
        this.friWeiXinId = friWeiXinId;
    }

    public String getFriNickName() {
        return friNickName;
    }

    public void setFriNickName(String friNickName) {
        this.friNickName = friNickName;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getWeiXinCreateDate() {
        return weiXinCreateDate;
    }

    public void setWeiXinCreateDate(Date weiXinCreateDate) {
        this.weiXinCreateDate = weiXinCreateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "WeiXinComment [meWeiXinId=" + meWeiXinId + ", meAccount=" + meAccount + ", friWeiXinId=" + friWeiXinId
                + ", friNickName=" + friNickName + ", custId=" + custId + ", content=" + content + ", weiXinCreateDate="
                + weiXinCreateDate + ", createDate=" + createDate + "]";
    }

    public String getWeiXinCircleId() {
        return weiXinCircleId;
    }

    public void setWeiXinCircleId(String weiXinCircleId) {
        this.weiXinCircleId = weiXinCircleId;
    }

}