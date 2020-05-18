package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: yblaas
 * @description: 老师考勤统计实体类
 * @author: xiaozhu
 * @create: 2020-04-05 23:17
 **/
@Data
public class TeacherAttendance implements Serializable {
    private static final long serialVersionUID = -5608543800579709248L;
    private String student; //学生ID
    private String name; //学生姓名
    private String numberId; //学生学号
    private Date time; //考勤时间
    private String type; //考勤类型
}
