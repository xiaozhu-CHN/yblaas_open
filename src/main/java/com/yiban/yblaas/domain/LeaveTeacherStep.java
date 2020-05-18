package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: yblaas
 * @description: 假条的审核流程实体类
 * @author: xiaozhu
 * @create: 2020-04-01 19:07
 **/
@Data
public class LeaveTeacherStep implements Serializable {
    private static final long serialVersionUID = -8580065113867185383L;
    private Date timeChange;  //假条申请的时间
    private String fdyName; //辅导员姓名
    private Date fdyTime; //辅导员审核时间
    private String xyldName; //学院领导姓名
    private Date xyldTime; //学院领导审核时间
    private String xgcName; //学工处姓名
    private Date xgcTime; //学工处审核时间
    private String xjName; //销假老师姓名
    private Date xjtTime; //销假时间
    private String state;  //假条的状态
}
