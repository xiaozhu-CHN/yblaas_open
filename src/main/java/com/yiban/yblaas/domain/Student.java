package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yblaas
 * @description: 学生信息实体类
 * @author: xiaozhu
 * @create: 2020-03-18 14:25
 **/
@Data
public class Student implements Serializable {
    private static final long serialVersionUID = -8979263903619735038L;
    private String student;
    private String numberId;
    private Integer eclassId;
    private String studentEmail;
    private String name;
    private String studentTell;
    private String parentTell;
    private String parenName;
    private String address;
    private String ems;
    private String sex;
    private String studentQq;
    private String examine;
    private String city;
    private String collegeName;
    private String eclassName;
}
