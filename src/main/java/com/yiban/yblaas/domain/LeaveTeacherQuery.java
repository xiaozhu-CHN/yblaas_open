package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: yblaas
 * @description: 老师用与查询学生假条实体类
 * @author: xiaozhu
 * @create: 2020-04-01 09:02
 **/
@Data
public class LeaveTeacherQuery implements Serializable {
    private static final long serialVersionUID = 8229083642589562069L;
    private Long id; //假条编号
    private String studentName; //学生姓名
    private String collegeName; //学院
    private String eclassName; //班别
    private String sex; //性别
    private String numberId; //学号
    private Date timeStart;  //假条开始时间
    private Date timeEnd;  //假条结束时间
    private Integer day;  //假条的天数
    private String whereabouts; //请假的去向
    private String cause; //请假的事由
    private String state;  //假条的状态
}
