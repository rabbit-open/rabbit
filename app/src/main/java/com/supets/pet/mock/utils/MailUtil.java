//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.supets.pet.mock.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
    static String user = "";
    static String password = "";

    public MailUtil() {
    }


    public static void setUser(String user) {
        MailUtil.user = user;
    }

    public static void setPassword(String password) {
        MailUtil.password = password;
    }


    public static void sendEmail(final String from, final String to, final String subject, final String body) {
        (new Thread(new Runnable() {
            public void run() {
                try {

                    Properties pro = new Properties();
                    pro.setProperty("mail.host", "smtp.qq.com");//设置QQ邮件服务器
                    pro.setProperty("mail.transport.protocol", "smtp");//设置传输协议
                    pro.setProperty("mail.smtp.auth", "true");//需要验证用户名密码

                    //Java发送邮件的5个步骤
                    //1.创建应用程序所需的环境信息的Session对象
                    Session session = Session.getDefaultInstance(pro, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user, password);//发件人邮箱及授权码
                        }
                    });
                    session.setDebug(true);//开启debug模式，查看进度状态

                    //2.通过session得到transport对象
                    Transport ts = session.getTransport();
                    //3.使用邮箱用户名及授权码连接上邮件服务器
                    ts.connect("smtp.qq.com", user, password);

                    //4.创建邮件
                    //创建邮件对象
                    MimeMessage message = new MimeMessage(session);

                    //指定邮件发件人
                    message.setFrom(new InternetAddress(from));

                    //指定邮件收件人
                    message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

                    //邮件标题
                    message.setSubject(subject);

                    //邮件的文本内容
                    message.setContent(body, "text/html;charset=UTF-8");

                    //发送邮件
                    ts.sendMessage(message, message.getAllRecipients());

                    ts.close();

                } catch (Exception var7) {
                    var7.printStackTrace();
                }

            }
        })).start();
    }
}
