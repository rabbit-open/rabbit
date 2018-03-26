package com.supets.pet.mock.wechaht;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * SnsInfo:朋友圈数据表
 *      content：朋友圈数据
 *      attrBuf：朋友圈点赞数据,本条朋友圈所有的点赞
 *      type:3,分享；2，纯文字；1，文字+图片；15小视频
 * SnsComment：朋友圈评论表
 *      curActionBuf:评论数据，每行只有一条评论数据
 *      talker:评论人的id
 * 
 * SnsInfo的snsID与SnsComment的snsId相同
 */
@SuppressWarnings("unused")
public final class WeiXinUtils {

    private static Set<String> filterSet;

    private static Set<String> filterSet2;

    static {
        //精确匹配
        filterSet = new HashSet<String>();
        filterSet.add("");
        filterSet.add("\" * 2 8 H P :");
        filterSet.add("\" * 2 8 H P X e :");
        filterSet.add("\" * B");
        filterSet.add("\" B J R");

        //模糊匹配
        filterSet2 = new HashSet<String>();
        filterSet2.add("微信小视频");
        filterSet2.add("h p z");
        filterSet2.add("D D");
        filterSet2.add("D pD :GZ");
        filterSet2.add("qqmap_");
        filterSet2.add("\" * ");
    }

    /**
     * 传入文件的方式
     * 处理微信朋友圈评论的数据 最终将数据封装成对象返回
     */
    private static WeiXinComment dealWeiXinDataOfComment(FileReader fileReader) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            int length = -1;
            char[] chars = new char[1024];
            while ((length = fileReader.read(chars)) != -1) {
                stringBuilder.append(chars, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dealWeiXinDataOfComment("", "", "", "", "", "", 0, stringBuilder.toString().toCharArray());
    }

    /**
     * 传入String的方式
     * 处理微信朋友圈评论的数据 最终将数据封装成对象返回
     */
    public static WeiXinComment dealWeiXinDataOfComment(String weiXinCircleId, String meWeiXinId, String meAccount, String friWeiXinId,
            String friNickName, String custId, long weiXinCreateDate, String curActionBuf) {

        char[] charsOfCurActionBuf = null;

        if (curActionBuf != null && !"".equals(curActionBuf.trim())) {
            charsOfCurActionBuf = curActionBuf.trim().toCharArray();
        }

        return dealWeiXinDataOfComment(weiXinCircleId, meWeiXinId, meAccount, friWeiXinId, friNickName, custId, weiXinCreateDate,
                charsOfCurActionBuf);
    }

    /**
     * 传入char[]的方式
     * 处理微信朋友圈评论的数据 最终将数据封装成对象返回
     */
    public static WeiXinComment dealWeiXinDataOfComment(String weiXinCircleId, String meWeiXinId, String meAccount, String friWeiXinId,
            String friNickName, String custId, long weiXinCreateDate, char[] charsOfComment) {

        WeiXinComment commentContent = new WeiXinComment();

        if (charsOfComment == null) {
            return commentContent;
        }

        try {
            commentContent.setMeWeiXinId(meWeiXinId);
            commentContent.setMeAccount(meAccount);
            commentContent.setFriWeiXinId(friWeiXinId);
            commentContent.setFriNickName(friNickName);
            commentContent.setCustId(custId);
            commentContent.setWeiXinCreateDate(new Date(weiXinCreateDate));
            commentContent.setCreateDate(new Date());
            commentContent.setWeiXinCircleId(weiXinCircleId);

            filterKongZhiChar(charsOfComment);

            String content = new String(charsOfComment);
            content = replaceStr(content);

            // 按回车分割成一行一行的
            String[] contentStrs = content.split("\n");
            String tempStr;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < contentStrs.length; i++) {
                tempStr = contentStrs[i].replaceAll("\\s+", " ").trim();
                if (!filterSet.contains(tempStr) && filter(tempStr)) { // 过滤不需要的内容
                    if (i == 1) {
                        String[] strs = tempStr.split(" ");
                        if (friWeiXinId == null || "".equals(friWeiXinId.trim())) {
                            commentContent.setFriWeiXinId(strs[0]);
                            commentContent.setMeWeiXinId(strs[1]);
                            commentContent.setFriNickName(strs[2].substring(0, strs[2].length() - 1));
                            commentContent.setMeAccount(strs[3].substring(0, strs[3].length() - 1));
                        }
                        sb.append(strs[4]).append("\n");
                    } else {
                        sb.append(tempStr).append("\n");
                    }
                }
            }
            commentContent.setContent(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commentContent;
    }

    /**
     * 传入String的方式
     * 处理微信朋友圈的数据 最终将数据封装成对象返回
     */
    public static WeiXinCircleContent dealWeiXinDataOfCircle(String weiXinCircleId, String meWeiXinId, String meAccount, String friWeiXinId,
            String friNickName, String custId, int type, long weiXinCreateDate, String content, String attrBuf) {

        char[] charsOfContent = null;
        char[] charsOfAttrBuf = null;

        if (content != null && !"".equals(content.trim())) {
            charsOfContent = content.trim().toCharArray();
        }
        if (attrBuf != null && !"".equals(attrBuf.trim())) {
            charsOfAttrBuf = attrBuf.trim().toCharArray();
        }

        return dealWeiXinDataOfCircle(weiXinCircleId, meWeiXinId, meAccount, friWeiXinId, friNickName, custId, type,
                weiXinCreateDate, charsOfContent, charsOfAttrBuf);
    }

    /**
     * 处理微信朋友圈的数据 最终将数据封装成对象返回
     */
    private static WeiXinCircleContent dealWeiXinDataOfCircle(int type, FileReader fileReaderOfContent) {
        try {
            // 处理微信朋友圈的数据
            StringBuilder stringBuilder = new StringBuilder();
            int length = -1;
            char[] chars = new char[1024];
            while ((length = fileReaderOfContent.read(chars)) != -1) {
                stringBuilder.append(chars, 0, length);
            }
            char[] charsOfContent = stringBuilder.toString().toCharArray();

            // 处理微信朋友圈的点赞数据
            stringBuilder = new StringBuilder();
            while ((length = fileReaderOfContent.read(chars)) != -1) {
                stringBuilder.append(chars, 0, length);
            }
            char[] charsOfAttrBuf = stringBuilder.toString().toCharArray();

            return dealWeiXinDataOfCircle("", "", "", "", "", "", type, 0, charsOfContent, charsOfAttrBuf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new WeiXinCircleContent();
    }

    /**
     * 处理微信朋友圈的数据 最终将数据封装成对象返回
     */
    public static WeiXinCircleContent dealWeiXinDataOfCircle(String weiXinCircleId, String meWeiXinId, String meAccount, String friWeiXinId,
            String friNickName, String custId, int type, long weiXinCreateDate, char[] charsOfContent, char[] charsOfAttrBuf) {

        WeiXinCircleContent circleContent = new WeiXinCircleContent();
        if (charsOfContent == null) {
            return circleContent;
        }

        try {
            circleContent.setMeWeiXinId(meWeiXinId);
            circleContent.setMeAccount(meAccount);
            circleContent.setFriWeiXinId(friWeiXinId);
            circleContent.setFriNickName(friNickName);
            circleContent.setCustId(custId);
            circleContent.setType(type);
            circleContent.setWeiXinCreateDate(new Date(weiXinCreateDate));
            circleContent.setCreateDate(new Date());
            circleContent.setWeiXinCircleId(weiXinCircleId);

            filterKongZhiChar(charsOfContent);
            // 按回车分割成一行一行的
            String[] contentStrs = new String(charsOfContent).split("\n");

            int startIndex = -1;
            int endIndex = -1;

            // 整理出需要的内容
            String tempStr;
            StringBuilder content = new StringBuilder();
            for (int i = 0; i < contentStrs.length; i++) {
                tempStr = contentStrs[i].replaceAll("\\s+", " ").trim();

                if (!filterSet.contains(tempStr) && filter(tempStr)) { // 过滤不需要的内容
                    if (i == 1) { // 第一行数据包含微信号和文字内容,取出
                        String[] strs = tempStr.split(" ");

                        // 没有传进微信号时
                        if (friWeiXinId == null || "".equals(friWeiXinId.trim())) {
                            if (strs.length < 2) { // 微信号在第二行开头的情况
                                tempStr = contentStrs[++i].replaceAll("\\s+", " ").trim();
                                strs = tempStr.split(" ");
                                strs[1] = strs[0];
                            }
                            circleContent.setFriWeiXinId(strs[1]);
                        }

                        // 文字内容
                        for (int j = 2; j < strs.length; j++) {
                            content.append(strs[j]);
                        }
                        content.append("\n");
                        continue;
                    }
                    // 除第一行外的数据
                    if (tempStr.contains("http://")) {// 图片、小视频的路径
                        if (type == 1) { // 文字+图片
                            startIndex = tempStr.indexOf("http://");
                            endIndex = tempStr.indexOf("/0(") + 2;
                        }
                        if (type == 15) { // 小视频
                            startIndex = tempStr.indexOf("http://");
                            endIndex = tempStr.indexOf("( 2 ");
                        }
                        if (type == 3) {
                            String[] strs = tempStr.split(" ");
                            circleContent.addUrl(strs[0]);
                            circleContent.setShareTitle(strs[1]);
                            break;
                        }

                        //对应手机上微信文件夹下的图片目录
                        circleContent.addImgNames(getCircleImg(tempStr.substring(0, 20)));

                        //朋友圈图片、小视频的网络路径
                        tempStr = tempStr.substring(startIndex, endIndex);
                        circleContent.addUrl(tempStr);// url

                    } else { // 文字内容
                        content.append(tempStr).append("\n");
                    }
                }
            }
            char c = content.charAt(1);
            int subStartIndex = 1;
            if (c == '*') {
                subStartIndex = 2;
            }
            circleContent.setContent(content.substring(subStartIndex, content.lastIndexOf("2"))); // 文字内容,去掉最后的"2"

            //处理微信朋友圈的点赞数据
            if (charsOfAttrBuf != null) {
                Map<String, String> friMapOfCP = dealWeiXinDataOfClickPraise(charsOfAttrBuf);
                circleContent.setFriMapOfCP(friMapOfCP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return circleContent;
    }

    /**
     * 传入char[]的方式
     * 处理微信朋友圈点赞的数据 最终将数据封装成对象返回
     */
    private static Map<String, String> dealWeiXinDataOfClickPraise(char[] charsOfAttrBuf) {

        // 点赞的好友：key,点赞的好友微信Id。
        // value，点赞的好友昵称。
        Map<String, String> friMap = new HashMap<String, String>();

        try {
            filterKongZhiChar(charsOfAttrBuf);
            String content = new String(charsOfAttrBuf);
            // 按回车分割成一行一行的
            String[] contentStrs = content.split("\n");

            String tempStr;
            for (int i = 0; i < contentStrs.length; i++) {
                // haoyou272355  ️京品匯️     *     0 8 @ P X h p 
                tempStr = contentStrs[i].replaceAll("\\s+", " ").trim();
                if (!filterSet.contains(tempStr) && filter(tempStr)) {
                    if (i != 0) {
                        String[] strs = tempStr.split(" ");
                        friMap.put(strs[0], strs[1]);
                    }
                }
            }
            return friMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return friMap;
    }

    /**
     * 获取微信好友的头像
     * 数据库：EnMicroMsg.db
     * 表：rcontact
     * 字段：usermane
     * 
     * 返回值：处理好友的好友头像路径
     */
    public static String getFriAvatar(String username) throws IOException {

        String md5 =  Md5.getMd5Value(username);

        String path1 =md5.substring(0,2) ;
        String path2 =md5.substring(2,4) ;

        return "/avatar/"+path1+"/"+path2+"/user_"+md5+".png";
    }


    /**
     * 获取微信朋友圈的图片路径
     * 数据库：SnsMicroMsg.db
     * 表：SnsInfo
     * 字段：content
     * 
     * 返回值：处理好的图片路径
     */
    private static String getCircleImg(String imgName) throws IOException {

        String md5 =  Md5.getMd5Value(imgName);

        String path1 =md5.substring(0,1) ;
        String path2 =md5.substring(1,2) ;

        return "/sns/"+path1+"/"+path2+"/snst_"+imgName;
    }

    /**
     * 过滤掉不需要的行
     */
    private static boolean filter(String tempStr) {
        for (String string : filterSet2) {
            if (tempStr.indexOf(string) != -1) {
                return false;
            }
        }
        return true;
    }

    private static void filterKongZhiChar(char[] chars) {
        // 去掉控制字符
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 10 || chars[i] == 13) {
                chars[i] = '\n';
                continue;
            }
            if (chars[i] < 32 || chars[i] == 65533) {
                chars[i] = ' ';
                continue;
            }
        }
    }

    private static String replaceStr(String content) {
        content = content.replaceAll("\\( 0 8     B ", " ");
        content = content.substring(0, content.lastIndexOf("H P"));
        return content;
    }

    /**
     * 只用于测试时显示大概的过滤结果
     * 过滤掉控制字符直接打印
     */
    private static void print(FileReader fileReader) throws IOException {
        char[] chars = new char[1024];
        StringBuffer sb = new StringBuffer();
        int length = -1;

        // 去掉控制字符
        while ((length = fileReader.read(chars)) != -1) {
            for (int i = 0; i < length; i++) {
                if (chars[i] == 10 || chars[i] == 13) {
                    chars[i] = '\n';
                    continue;
                }
                if (chars[i] < 32 || chars[i] == 65533) {
                    chars[i] = ' ';
                    continue;
                }
            }
            sb.append(new String(chars, 0, length));
        }
        System.out.println(sb.toString());
    }


    public static void main(String[] args) throws FileNotFoundException, IOException {

        //获取朋友圈图片的调用
        //System.err.println(getCircleImgs());

        //获取好友头像的调用
        System.err.println(getFriAvatar("yonghai-168"));
    }
}