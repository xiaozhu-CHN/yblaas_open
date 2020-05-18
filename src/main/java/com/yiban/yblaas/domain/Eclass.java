package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yblaas
 * @description: 班级表实体类
 * @author: xiaozhu
 * @create: 2020-03-24 20:46
 **/
@Data
public class Eclass implements Serializable {
    private static final long serialVersionUID = 4556745625681136366L;
    private Integer eclassId;
    private Integer collegeId;
    private String name;
    private String teacher;
}
