package com.yiban.yblaas.thread;

import cn.yiban.util.HTTPSimple;
import com.yiban.yblaas.util.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: yblaas
 * @description: 短信发送的Thead类
 * @author: xiaozhu
 * @create: 2020-03-25 18:49
 **/
public class MessageThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(MessageThread.class);

    private String phone;
    private String verification;

    public MessageThread(String phone, String verification){
        this.phone = phone;
        this.verification = verification;
    }

    public void run(){
//        String url ="短信API";
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("id","1");
//        jsonObject.put("content",verification);
//        jsonObject.put("phone",phone);
//        int count = 1;
//        Boolean sendState = true;
//        while (sendState){
//            String json = HttpUtil.ajaxPost(url,jsonObject.toString());
//            if(json!=null){
//                String msg = JSONObject.fromObject(json).getString("msg");
//                if(msg.equals("ok")){
//                    sendState = false;
//                }
//            }
//            if(count>3){
//                //超过三次测试还未成功就跳出循环
//                sendState = false;
//            }
//            if(sendState){
//                //每次请求等待三秒
//                try {
//                    ++count;
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    logger.error("短信线程休眠3秒错误，错误信息："+e.toString());
//                }
//            }
//        }
        logger.info("触发了短信发送线程");
    }
}
