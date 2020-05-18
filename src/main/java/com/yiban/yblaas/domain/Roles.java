package com.yiban.yblaas.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: yblaas
 * @description: 老师角色实体类
 * @author: xiaozhu
 * @create: 2020-03-11 23:06
 **/
@Data
public class Roles implements Serializable {
    private static final long serialVersionUID = 4206548612647118881L;
    private Integer id;
    private String userId;
    private String roleName;
}
