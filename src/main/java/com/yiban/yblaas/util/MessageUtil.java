package com.yiban.yblaas.util;

import com.yiban.yblaas.mapper.DbConfigMapper;
import com.yiban.yblaas.thread.EmailThread;
import com.yiban.yblaas.thread.QqThread;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

/**
 * @program: yblaas
 * @description: 消息提醒的Util
 * @author: xiaozhu
 * @create: 2020-04-25 14:00
 **/
@Slf4j
@Component
public class MessageUtil {

    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private DbConfigMapper dbConfigMapper;

    /**
     * 功能描述:
     * (QQ提醒)
     *
     * @param qq 用户QQ
     * @param yblaasTitle 系统标题
     * @param type 发送类型
     * @param content 内容
     * @return : void
     * @author : xiaozhu
     * @date : 2020/4/25 14:49
     */
    public void Qq(String qq, String yblaasTitle, Integer type, String content){
        try {
            if (qq != null && yblaasTitle != null) {
                String message = yblaasTitle;
                if(type == 1){
                    message += "：您提交编号为【"+content+"】的假条申请，系统已经收到。";
                }else if(type == 2){
                    message += "：您编号为【"+content+"】的假条申请，老师已经审核通过。";
                }else if(type == 3){
                    message += "：您编号为【"+content+"】的假条申请，老师审核不通过。";
                }else if(type == 4){
                    message += "：您编号为【"+content+"】的假条，老师已经销假成功。";
                }else if(type == 5){
                    message += "：您编号为【"+content+"】的假条，您已经成功取消。";
                }else if(type == 6){
                    message += "：您有新考勤需要参与，考勤编号为"+content+"";
                }else if(type == 7){
                    message += "：您有新的假条需要审核，假条编号为【"+content+"】。";
                }else if(type == 8){
                    message += "：您提交编号为【"+content+"】的考勤，系统已经收到。";
                }else if(type == 9){
                    message += "：您提交的编号为【"+content+"】的考勤，系统已经删除成功。";
                }else if(type == 10){
                    message += "：您编号为【"+content+"】的考勤，老师已经取消考勤。";
                }
                Thread QqThread = new QqThread(qq,message);
                QqThread.start();
            }
        } catch (Exception e) {
            log.error("QQ提醒发送错误，错误信息："+e.toString());
        }
    }

    /**
     * 功能描述:
     * (Email提醒)
     *
     * @param sendEmail 用户邮箱
     * @param yblaasTitle 系统标题
     * @param type 发送类型
     * @param content 内容
     * @return : void
     * @author : xiaozhu
     * @date : 2020/4/25 15:36
     */
    public Boolean Email(String sendEmail, String yblaasTitle, Integer type, String content){
        try {
            if (sendEmail != null && yblaasTitle != null) {
                String subject = yblaasTitle;
                String contentSend = yblaasTitle;
                String yburl = dbConfigMapper.selectValue("yiban_url");
                if(type == 0){
                    subject = "邮件测试";
                    contentSend = "尊敬的管理员您好！<br/>&nbsp; &nbsp; &nbsp; 您的测试邮件已经成功发送。<br/><br/>"+"<a href='"+yburl+"'>"+yblaasTitle+"</a>";
                }else if(type == 1){
                    subject = "新的假条申请";
                    contentSend = "尊敬的学生用户您好！<br/>&nbsp; &nbsp; &nbsp; 您提交编号为【"+content+"】的假条申请，系统已经收到。<br/><br/>"+"<a href='"+yburl+"'>"+yblaasTitle+"</a>";
                }else if(type == 2){
                    subject = "假条申请通过";
                    contentSend = "尊敬的学生用户您好！<br/>&nbsp; &nbsp; &nbsp; 您编号为【"+content+"】的假条申请，老师已经审核通过。<br/><br/>"+"<a href='"+yburl+"'>"+yblaasTitle+"</a>";
                }else if(type == 3){
                    subject = "假条申请失败";
                    contentSend = "尊敬的学生用户您好！<br/>&nbsp; &nbsp; &nbsp; 您编号为【"+content+"】的假条申请，老师审核不通过。<br/><br/>"+"<a href='"+yburl+"'>"+yblaasTitle+"</a>";
                }else if(type == 4){
                    subject = "假条销假成功";
                    contentSend = "尊敬的学生用户您好！<br/>&nbsp; &nbsp; &nbsp; 您编号为【"+content+"】的假条，老师已经销假成功。<br/><br/>"+"<a href='"+yburl+"'>"+yblaasTitle+"</a>";
                }else if(type == 5){
                    subject = "假条取消成功";
                    contentSend = "尊敬的学生用户您好！<br/>&nbsp; &nbsp; &nbsp; 您编号为【"+content+"】的假条，您已经成功取消。<br/><br/>"+"<a href='"+yburl+"'>"+yblaasTitle+"</a>";
                }else if(type == 6){
                    subject = "新的考勤";
                    contentSend = "尊敬的学生用户您好！<br/>&nbsp; &nbsp; &nbsp; 您有新考勤需要参与，考勤编号为"+content+"<br/><br/>"+"<a href='"+yburl+"'>"+yblaasTitle+"</a>";
                }else if(type == 7){
                    subject = "新的假条审核";
                    contentSend = "尊敬的老师用户您好！<br/>&nbsp; &nbsp; &nbsp; 您有新的假条需要审核，假条编号为【"+content+"】。<br/><br/>"+"<a href='"+yburl+"'>"+yblaasTitle+"</a>";
                }else if(type == 8){
                    subject = "新的考勤";
                    contentSend = "尊敬的老师用户您好！<br/>&nbsp; &nbsp; &nbsp; 您提交编号为【"+content+"】的考勤，系统已经收到。<br/><br/>"+"<a href='"+yburl+"'>"+yblaasTitle+"</a>";
                }else if(type == 9){
                    subject = "考勤删除成功";
                    contentSend = "尊敬的老师用户您好！<br/>&nbsp; &nbsp; &nbsp; 您提交的编号为【"+content+"】的考勤，系统已经删除成功。<br/><br/>"+"<a href='"+yburl+"'>"+yblaasTitle+"</a>";
                }else if(type == 10){
                    subject = "考勤取消";
                    contentSend = "尊敬的学生用户您好！<br/>&nbsp; &nbsp; &nbsp; 您编号为【"+content+"】的考勤，老师已经取消考勤。<br/><br/>"+"<a href='"+yburl+"'>"+yblaasTitle+"</a>";
                }else if(type == 11){
                    subject = "验证码：" + content;
                    contentSend = "尊敬的师生您好！<br/>&nbsp; &nbsp; &nbsp; 您绑定邮箱的验证码为【"+content +"】，半小时内有效，请尽快在绑定页面中输入绑定。<br/><br/>"+"<a href='"+yburl+"'>"+yblaasTitle+"</a>";
                }
                if("true".equals(dbConfigMapper.selectValue("email"))){
                    Thread EmailThread = new EmailThread(javaMailSender, dbConfigMapper, sendEmail, subject, contentSend);
                    EmailThread.start();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.error("邮件提醒发送错误，错误信息："+e.toString());
            return false;
        }
    }
}
