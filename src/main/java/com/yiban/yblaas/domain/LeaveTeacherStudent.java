package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yblaas
 * @description: 老师查询学生信息的实体类
 * @author: xiaozhu
 * @create: 2020-04-03 12:54
 **/
@Data
public class LeaveTeacherStudent implements Serializable {
    private static final long serialVersionUID = -8733489008869714434L;
    private String studentEmail;
    private String studentTell;
    private String parentTell;
    private String parenName;
    private String address;
    private String ems;
    private String studentQq;
    private String city;
}
