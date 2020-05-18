package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yblaas
 * @description: 班级老师查询实体类
 * @author: xiaozhu
 * @create: 2020-04-11 19:10
 **/
@Data
public class EclassTeacher implements Serializable {
    private static final long serialVersionUID = 3484281668883996390L;
    private Integer eclassId; //班级ID
    private Integer collegeId; //学院ID
    private String eclassName; //班级名称
    private String collegeName; //学院名称
    private String teacherId; //老师ID
    private String teacherName; //老师名字
}
