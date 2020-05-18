package com.yiban.yblaas.domain;

import lombok.Data;

/**
 * @program: yblaas_open
 * @description: 邮件信息实体类
 * @author: xiaozhu
 * @create: 2020-05-17 15:25
 **/
@Data
public class Email {
    private String email; //是否开启邮件功能
    private String emailHost; //邮件服务器
    private String emailPort; //邮件端口
    private String emailName; //发送账号
    private String emailPassword; //发送密码
    private String emailCall; //发送昵称
}
