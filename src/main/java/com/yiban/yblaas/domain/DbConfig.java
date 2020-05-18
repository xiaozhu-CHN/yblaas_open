package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yblaas
 * @description: 系统设置实体类
 * @author: xiaozhu
 * @create: 2020-03-17 18:12
 **/
@Data
public class DbConfig implements Serializable {
    private static final long serialVersionUID = 8677596048655344746L;
    private String yblaasTitle; //系统标题
    private String yblaasCopyright; // 底部版权信息
    private String yblaasBa; //备案号
    private String yibanAppId; //易班AppID
    private String yibanAppSecret; //易班易班AppSecret
    private String yibanUrl; //易班站内地址
    private String yibanSchool; //是否具有校级权限
    private String yibanThis; //是否拦截非本校师生
    private String leaveXyld; //学院领导审核天数
    private String leaveXgc; //	学工处审核天数
    private String attendanceAccuracy; //考勤精度
}
