package com.yiban.yblaas.thread;

import com.yiban.yblaas.util.HttpUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: yblaas
 * @description: QQ多线程发送
 * @author: xiaozhu
 * @create: 2020-03-28 13:16
 **/
public class QqThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(QqThread.class);

    private String qq;
    private String message;

    public QqThread(String StudentQq, String message){
        this.qq = StudentQq;
        this.message = message;
    }

    public void run(){
//        String url1 ="QQ查询链接";
//        String url2 ="QQ发送链接";
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("user_id",qq);
//        String json1 = HttpUtil.ajaxPost(url1,jsonObject.toString());
//        Boolean located = JSONObject.fromObject(json1).getBoolean("located");
//        if(located){
//            jsonObject.put("message",message);
//            int count = 1;
//            Boolean sendState = true;
//            while (sendState){
//                String json2 = HttpUtil.ajaxPost(url2,jsonObject.toString());
//                if(json2!=null){
//                    String msg = JSONObject.fromObject(json2).getString("msg");
//                    if(msg.equals("ok")){
//                        sendState = false;
//                    }
//                }
//                if(count>3){
//                    //超过三次测试还未成功就跳出循环
//                    sendState = false;
//                }
//                if(sendState){
//                    //每次请求等待三秒
//                    try {
//                        ++count;
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        logger.error("QQ线程休眠3秒错误，错误信息："+e.toString());
//                    }
//                }
//            }
//        }
        logger.info("触发了QQ发送线程");
    }
}
