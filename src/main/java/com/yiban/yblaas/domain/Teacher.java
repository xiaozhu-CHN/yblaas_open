package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yblaas
 * @description: 老师实体类
 * @author: xiaozhu
 * @create: 2020-03-18 16:11
 **/
@Data
public class Teacher implements Serializable {
    private static final long serialVersionUID = 8393248924910763176L;
    private String teacher;
    private String name;
    private String teacherTell;
    private String teacherEmail;
    private Integer collegeId;
    private String teacherQq;
    private String role;
}
