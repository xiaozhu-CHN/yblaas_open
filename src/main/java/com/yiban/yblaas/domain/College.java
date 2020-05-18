package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yblaas
 * @description: 学院实体类
 * @author: xiaozhu
 * @create: 2020-03-24 17:29
 **/
@Data
public class College implements Serializable {
    private static final long serialVersionUID = 6642385626623442257L;
    private Integer collegeId;
    private String name;
}
