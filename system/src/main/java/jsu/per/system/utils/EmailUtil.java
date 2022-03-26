package jsu.per.system.utils;

import lombok.extern.slf4j.Slf4j;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Slf4j
public class EmailUtil {
    public static void sendVerificationCode(String email,String subject,String msg){
        // 发件人电子邮箱
        String from = "1987888060@qq.com";

        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com";  //QQ 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");

        // 获取默认session对象
        Session session = Session.getInstance(properties, new Authenticator(){
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("1987888060@qq.com", "yrjvcfyjqizreaea");
                //第一个参数是发者的邮箱  第二个参数是你的授权码  这一步我已经改过不会出现错误
            }});

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            // Set Subject: 头部头字段
            message.setSubject(subject);

            // 设置消息体
            message.setText(msg);

            // 发送消息
            Transport.send(message);
            System.out.println("发送成功");
        }catch (javax.mail.MessagingException mex) {
            mex.printStackTrace();
            System.out.println("发送失败");
            log.warn("email发送失败");
        }
    }

}
