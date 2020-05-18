package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yblaas
 * @description: 老师查询学生信息实体类
 * @author: xiaozhu
 * @create: 2020-04-11 20:30
 **/
@Data
public class TeacherStudent implements Serializable {
    private static final long serialVersionUID = 9048935564124563533L;
    private String name;
    private String numberId;
    private String sex;
    private String student;
}
