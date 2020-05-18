package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: yblaas
 * @description: 学生查询假条实体类
 * @author: xiaozhu
 * @create: 2020-03-23 10:54
 **/
@Data
public class LeaveStudentQuery implements Serializable {
    private static final long serialVersionUID = -8044281606174115812L;
    private Long id; //假条编号
    private String studentName; //学生姓名
    private String collegeName; //学院
    private String eclassName; //班别
    private String sex; //性别
    private String numberId; //学号
    private Date timeStart;  //假条开始时间
    private Date timeEnd;  //假条结束时间
    private Date timeChange;  //假条申请的时间
    private Integer day;  //假条的天数
    private String whereabouts; //请假的去向
    private String cause; //请假的事由
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
