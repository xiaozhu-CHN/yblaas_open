package com.yiban.yblaas.thread;

import com.yiban.yblaas.mapper.DbConfigMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @program: yblaas
 * @description: 邮件发送的多线程
 * @author: xiaozhu
 * @create: 2020-03-28 14:34
 **/
public class EmailThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(EmailThread.class);

    private JavaMailSenderImpl javaMailSender;
    private DbConfigMapper dbConfigMapper;
    private String email;
    private String subject;
    private String content;

    public EmailThread(JavaMailSenderImpl javaMailSender, DbConfigMapper dbConfigMapper, String email, String subject, String content){
        this.javaMailSender = javaMailSender;
        this.dbConfigMapper = dbConfigMapper;
        this.email = email;
        this.subject = subject;
        this.content = content;
    }

    @Override
    public void run() {
        int count = 1;
        Boolean sendState = true;
        while (sendState){
            try {
                if(sendState){
                    //每次请求等待三秒
                    try {
                        if(count!=1){
                            Thread.sleep(3000);
                        }
                        ++count;
                    } catch (InterruptedException e) {
                        logger.error("短信线程休眠3秒错误，错误信息："+e.toString());
                    }
                }
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                String emailName = dbConfigMapper.selectValue("email_name");
                javaMailSender.setUsername(emailName);
                javaMailSender.setHost(dbConfigMapper.selectValue("email_host"));
                javaMailSender.setPassword(dbConfigMapper.selectValue("email_password"));
                javaMailSender.setPort(Integer.parseInt(dbConfigMapper.selectValue("email_port")));
                helper.setTo(email);
                helper.setFrom(dbConfigMapper.selectValue("email_call")+" <"+emailName+">");
                helper.setSubject(subject);
                helper.setText(content, true);
                javaMailSender.send(message);
                sendState = false;
            } catch (MessagingException e) {
                logger.error("多线程发送邮件错误，错误信息：",e);
                if(count>3){
                //超过三次测试还未成功就跳出循环
                sendState = false;
                }
            }
        }
    }
}
