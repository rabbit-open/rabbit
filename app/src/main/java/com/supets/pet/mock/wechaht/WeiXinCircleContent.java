package com.supets.pet.mock.wechaht;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WeiXinCircleContent implements Serializable {

    private String meWeiXinId;  // 当前手机登录的账号id
    private String meAccount;   // 当前手机登录的账号
    private String friWeiXinId; // 好友的微信id
    private String friNickName; // 好友的昵称
    private String custId;      // 客户id
    private int type;           // 朋友圈类型
    private String shareTitle;  // 类型为分享时，输入的文字
    private String content;     // 文字
    private Set<String> urls = new HashSet<String>();   // 图片、小视频网络路径
    private Set<String> imgNames = new HashSet<String>();// 图片、小视频名称
    private Date weiXinCreateDate;// 发布时间
    private Date createDate;    // 采集时间
    private String weiXinCircleId;// 微信朋友圈数据的id，snsid

    private int clickPraiseSum; // 这条朋友圈的点赞数量
    // 点赞的好友：key,点赞的好友微信Id。
    // value，点赞的好友昵称。
    private Map<String, String> friMapOfCP = new HashMap<String, String>();


    /****************************************  start  ***************************************************/
    public void addUrl(String url) {
        if (url.startsWith("http://") || url.startsWith("https://")) {
            urls.add(url);
        }
    }

    /**
     * 增加点赞的好友：
     * key,点赞的好友微信Id。
     * value，点赞的好友昵称。
     */
    public void addClickPraiseFri(String friWeiXinId, String friNickName) {
        friMapOfCP.put(friWeiXinId, friNickName);
    }

    public void addImgNames(String imgName) {
        imgNames.add(imgName);
    }

    /******************************************  end   *************************************************/

    public String getCreateDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (createDate == null) {
            createDate = new Date();
        }
        return sdf.format(createDate);
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("微信号：").append(friWeiXinId).append("\n")//
                .append("时间：").append(getCreateDateStr()).append("\n")//
                .append("类型：").append(type).append("\n")//
                .append("内容：").append(content).append("\n")//
                .append("分享标题：").append(shareTitle).append("\n");

        sBuilder.append("路径：").append("\n");
        for (String urlTemp : urls) {
            sBuilder.append(urlTemp).append("\n");
        }

        sBuilder.append("文件名：").append("\n");
        for (String urlTemp : imgNames) {
            sBuilder.append(urlTemp).append("\n");
        }
        return sBuilder.toString();
    }

    public String getMeWeiXinId() {
        return meWeiXinId;
    }

    public void setMeWeiXinId(String meWeiXinId) {
        this.meWeiXinId = meWeiXinId;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Set<String> getUrls() {
        return urls;
    }

    public void setUrls(Set<String> urls) {
        this.urls = urls;
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

    public String getMeAccount() {
        return meAccount;
    }

    public void setMeAccount(String meAccount) {
        this.meAccount = meAccount;
    }

    public String getWeiXinCircleId() {
        return weiXinCircleId;
    }

    public void setWeiXinCircleId(String weiXinCircleId) {
        this.weiXinCircleId = weiXinCircleId;
    }

    public Set<String> getImgNames() {
        return imgNames;
    }

    public void setImgNames(Set<String> imgNames) {
        this.imgNames = imgNames;
    }

    public int getClickPraiseSum() {
        return clickPraiseSum;
    }

    public void setClickPraiseSum(int clickPraiseSum) {
        this.clickPraiseSum = clickPraiseSum;
    }

    public Map<String, String> getFriMapOfCP() {
        return friMapOfCP;
    }

    public void setFriMapOfCP(Map<String, String> friMapOfCP) {
        this.friMapOfCP = friMapOfCP;
    }
}